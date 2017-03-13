package com.kylin.vo;

import com.kylin.tools.DateHelper;
import com.kylin.tools.myenum.RoomType;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by kylin on 20/02/2017.
 * All rights reserved.
 * <p>
 * 会员预定酒店登记表格
 */
public class ReserveInputTableVO {

    private int userId;

    private int hotelId;

    // date info
    private Date checkInDate;
    private String strDate1;

    private Date checkOutDate;
    private String strDate2;

    // room info
    private RoomType roomType;
    private int intRoomType;

    private int roomNumber;

    // contact info
    private String contactPersonName;

    private String contactPhone;

    private String contactEmail;

    // price
    private int totalPrice;

    public ReserveInputTableVO() {
    }

    public ReserveInputTableVO(int userId, int hotelId, String strDate1, String strDate2,
                               int intRoomType, int roomNumber,
                               String contactPersonName, String contactPhone, String contactEmail, int totalPrice) {
        this.userId = userId;
        this.hotelId = hotelId;
        this.strDate1 = strDate1;
        this.strDate2 = strDate2;
        this.intRoomType = intRoomType;
        this.roomNumber = roomNumber;
        this.contactPersonName = contactPersonName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.totalPrice = totalPrice;
        this.init();
    }

    public void init() {
        try {
            this.checkInDate = DateHelper.getDate(this.strDate1);
            this.checkOutDate = DateHelper.getDate(this.strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.roomType = RoomType.getEnum(this.intRoomType);
    }

    public int getUserId() {
        return userId;
    }

    public int getHotelId() {
        return hotelId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getStrDate1() {
        return strDate1;
    }

    public String getStrDate2() {
        return strDate2;
    }

    public int getIntRoomType() {
        return intRoomType;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public void setStrDate1(String strDate1) {
        this.strDate1 = strDate1;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public void setStrDate2(String strDate2) {
        this.strDate2 = strDate2;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setIntRoomType(int intRoomType) {
        this.intRoomType = intRoomType;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ReserveInputTableVO{" +
                "userId=" + userId +
                ", hotelId=" + hotelId +
                ", checkInDate=" + checkInDate +
                ", strDate1='" + strDate1 + '\'' +
                ", checkOutDate=" + checkOutDate +
                ", strDate2='" + strDate2 + '\'' +
                ", roomType=" + roomType +
                ", intRoomType=" + intRoomType +
                ", roomNumber=" + roomNumber +
                ", contactPersonName='" + contactPersonName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
