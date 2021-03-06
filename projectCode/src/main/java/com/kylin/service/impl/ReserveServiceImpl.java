package com.kylin.service.impl;

import com.kylin.model.*;
import com.kylin.repository.*;
import com.kylin.service.ReserveService;
import com.kylin.tools.DateHelper;
import com.kylin.tools.myenum.HotelLevel;
import com.kylin.tools.myenum.MemberOrderStatus;
import com.kylin.tools.myenum.RoomStatus;
import com.kylin.tools.myenum.RoomType;
import com.kylin.vo.HotelRemainRoom;
import com.kylin.vo.RemainRoomInfo;
import com.kylin.vo.ReserveInputTableVO;
import com.kylin.vo.SearchHotelItemVO;
import com.kylin.vo.common.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * Created by kylin on 28/02/2017.
 * All rights reserved.
 */
@Service
public class ReserveServiceImpl implements ReserveService{

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelRoomRepository hotelRoomRepository;
    @Autowired
    private HotelRoomStatusRepository roomStatusRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    // 此方法找到,一个酒店的,指定房间类型,在起点-终点日期内为空闲的所有空余信息
    public List<HotelRemainRoom> emptyRoomSearch(int hotelId, String fromDate, String endDate, RoomType roomType) {
        // get all room of this hotel
        List<HotelRoom> hotelRoomIdList = this.hotelRoomRepository.findByHotelId(hotelId);
        List<HotelRemainRoom> result = new ArrayList<>();

        try {
            // date info
            Date from = DateHelper.getDate(fromDate);
            Date end = DateHelper.getDate(endDate);
            int dayNumber = DateHelper.getDaysNumber(from, end);
            // every room of this hotel
            for (HotelRoom room : hotelRoomIdList) {
                // every target type room between these dates
                if (room.getType() == roomType.ordinal()) {
                    // 找的是空闲的房间
                    int roomState = RoomStatus.Empty.ordinal();
                    List<HotelRoomStatus> roomStatusList = this.roomStatusRepository.findByRoomAndDateAndStatus
                            (room.getId(), from, end, roomState);

                    // 房间必须在所有这些天数里面,包含起点终点,都是空闲的
                    if (roomStatusList.size() == dayNumber) {
                        //发现一个符合要求的房间
                        HotelRemainRoom remainRoom = new HotelRemainRoom(room.getId(),
                                room.getRoomNumber(), RoomType.getEnum(room.getType()),
                                RoomStatus.Empty, room.getInformation());
                        result.add(remainRoom);
                    }
                }
                // type is wrong, check next room
            }
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


    @Override
    public List<SearchHotelItemVO> search(String location, String fromDate, String endDate,
                                          int roomTypeInt, int roomNumber) {
        List<SearchHotelItemVO> result = new ArrayList<>();
        try {
            Date checkInDate = DateHelper.getDate(fromDate);
            Date checkOutDate = DateHelper.getDate(endDate);

            // find by location
            List<Hotel> hotelList = this.hotelRepository.findByLocationIgnoreCaseContaining(location);
            RoomType roomType = RoomType.getEnum(roomTypeInt);

            // find by date and people
            for (Hotel hotel : hotelList) {
                // 酒店剩余房间信息
                List<HotelRemainRoom> remainRooms =
                        this.emptyRoomSearch(hotel.getId(), fromDate, endDate, roomType);
                // 还有足够的剩余房间
                if (remainRooms.size() >= roomNumber) {
                    // 一个酒店剩余房间的信息
                    SearchHotelItemVO vo = this.getRemainInfo(hotel, checkInDate, checkOutDate, remainRooms);
                    result.add(vo);
                }
                //否则这个酒店剩余的房间不够,继续检查下一个
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public MyMessage makeReservation(ReserveInputTableVO inputVO) {
        // 保存用户订单信息
        Hotel hotel = this.hotelRepository.findOne(inputVO.getHotelId());
        this.saveOrder(inputVO, hotel);

        // get input info
        Date checkIn = inputVO.getCheckInDate();
        Date checkOut = inputVO.getCheckOutDate();
        RoomType roomType = inputVO.getRoomType();
        int roomNumber = inputVO.getRoomNumber();

        // TODO 缓存技术
        // 找到当前酒店所有在指定日期/指定类型的,空闲的房间,例如有10个
        List<HotelRemainRoom> remainRooms = this.emptyRoomSearch(hotel.getId(),
                DateHelper.getDateString(checkIn), DateHelper.getDateString(checkOut), roomType);
        int remainRoomNum = remainRooms.size();
        if(remainRoomNum < roomNumber)
            return new MyMessage(false,hotel+"在"+checkIn+" 到 "+checkOut+"之间没有足够的空闲房间," +
                    "酒店只有"+remainRoomNum+"间空房");

        // 取出用户要求数量的房间数目,例如用户只要两个
        for (int i = 0; i < roomNumber; i++) {
            HotelRemainRoom remainRoom = remainRooms.get(i);
            int roomId = remainRoom.getRoomId();
            int roomState = RoomStatus.Empty.ordinal();

            // 得到这个房间的信息
            List<HotelRoomStatus> roomStatusList = this.roomStatusRepository.findByRoomAndDateAndStatus
                    (roomId, checkIn, checkOut, roomState);

            // 更新每一天房间的状态为被预定,防止被别人重复预定
            int targetState = RoomStatus.Reserved.ordinal();
            for (HotelRoomStatus roomStatus : roomStatusList) {
                roomStatus.setStatus(targetState);
                this.roomStatusRepository.save(roomStatus);
            }
        }

        return new MyMessage(true);
    }


    /**
     * 一个酒店剩余房间的信息
     *
     * @param hotel        酒店
     * @param checkInDate  时间
     * @param checkOutDate 时间
     * @param remainRooms  剩余房间信息列表
     * @return
     */
    private SearchHotelItemVO getRemainInfo(Hotel hotel, Date checkInDate, Date checkOutDate,
                                            List<HotelRemainRoom> remainRooms) {
        int lowestPerNightPrice = Integer.MAX_VALUE;
        List<RemainRoomInfo> roomResult = new ArrayList<>();

        // 初始化每一个类型
        Map<RoomType, RemainRoomInfo> typeAndNumber = new HashMap<>();
        for (RoomType oneType : RoomType.values()) {
            RemainRoomInfo remainRoomInfo = new RemainRoomInfo(oneType, 0, Integer.MAX_VALUE);
            typeAndNumber.put(oneType, remainRoomInfo);
        }

        // 对每一个空余的房间信息
        for (HotelRemainRoom room : remainRooms) {
            int roomId = room.getRoomId();
            RoomType roomType = room.getType();
            // 一个区域的房价以checkIn当天价格为准
            int price = this.roomStatusRepository.getPriceByRoomIdAndDate(roomId, checkInDate);

            //修改返回的搜索结果
            RemainRoomInfo oldInfo = typeAndNumber.get(roomType);
            oldInfo.increaseNumber();
            oldInfo.calculatePrice(price);
        }

        //将每种房间的信息返回
        roomResult.addAll(typeAndNumber.values());

        // TODO lowestPerNightPrice has bug
        return new SearchHotelItemVO(checkInDate, checkOutDate, hotel.getId(),
                hotel.getName(), HotelLevel.getEnum(hotel.getLevel()), hotel.getLocation(),
                lowestPerNightPrice, roomResult);
    }
    /**
     * 保存用户的输入订单信息
     *
     * @param inputVO
     * @param hotel
     */
    private void saveOrder(ReserveInputTableVO inputVO, Hotel hotel) {
        MemberOrder memberOrder = new MemberOrder();
        Member member = this.memberRepository.findOne(inputVO.getUserId());
        memberOrder.setMemberId(member.getId());

        memberOrder.setHotelId(hotel.getId());

        //时间信息
        Date checkIn = inputVO.getCheckInDate();
        Date checkOut = inputVO.getCheckOutDate();
        memberOrder.setCheckIn(checkIn);
        memberOrder.setCheckOut(checkOut);
        memberOrder.setOrderTime(new Date());

        //房间信息
        RoomType roomType = inputVO.getRoomType();
        memberOrder.setRoomType(roomType.ordinal());
        int roomNumber = inputVO.getRoomNumber();
        memberOrder.setRoomNumber(roomNumber);
        memberOrder.setPrice(inputVO.getTotalPrice());

        //联系人信息
        memberOrder.setContactName(inputVO.getContactPersonName());
        memberOrder.setContactPhone(inputVO.getContactPhone());
        memberOrder.setContactEmail(inputVO.getContactEmail());

        memberOrder.setStatus(MemberOrderStatus.Reserved.ordinal());
        //保存
        this.orderRepository.save(memberOrder);
    }

}

