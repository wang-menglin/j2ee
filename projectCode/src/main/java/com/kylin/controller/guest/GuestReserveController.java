package com.kylin.controller.guest;

import com.kylin.controller.MyController;
import com.kylin.service.ReserveService;
import com.kylin.tools.DateHelper;
import com.kylin.tools.myenum.RoomType;
import com.kylin.vo.ReserveInputTableVO;
import com.kylin.vo.SearchHotelItemVO;
import com.kylin.vo.SearchInputVO;
import com.kylin.vo.common.MyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by kylin on 11/03/2017.
 * All rights reserved.
 */
@Controller
@RequestMapping("guest")
public class GuestReserveController extends MyController {

    @Autowired
    private ReserveService reserveService;

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public ModelAndView searchHotel(@ModelAttribute("SearchInputVO") SearchInputVO searchInputVO) {

        List<SearchHotelItemVO> result = this.reserveService.search(searchInputVO.getLocation(), searchInputVO.getFromDate(),
                searchInputVO.getEndDate(), searchInputVO.getRoomTypeInt(), searchInputVO.getRoomNumber());

        ModelAndView modelAndView = new ModelAndView("guest/search-result");
        modelAndView.addObject("searchResult", result);
        modelAndView.addObject("searchInputVO", searchInputVO);

        return modelAndView;
    }

    @RequestMapping(value = "selectHotel", method = RequestMethod.GET)
    public ModelAndView selectHotel(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("guest/reserve");

        String hotelName = request.getParameter("hotelName");
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        String fromDate = request.getParameter("fromDate");
        String endDate = request.getParameter("endDate");
        int roomTypeInt = Integer.parseInt(request.getParameter("roomTypeInt"));
        String strType = RoomType.getEnum(roomTypeInt).getType();
        int roomNumber = Integer.parseInt(request.getParameter("roomNumber"));
        int perPrice = Integer.parseInt(request.getParameter("perPrice"));
        int daysNumber = DateHelper.getDaysNumber(fromDate, endDate);

        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute("userID");

        // 计算总价格
        int price = perPrice * roomNumber * daysNumber;

        ReserveInputTableVO reserveInput = new ReserveInputTableVO(userId, hotelId, fromDate, endDate,
                roomTypeInt, roomNumber, "", "", "", price);
        System.out.println("selectHotel, reserveInput = " + reserveInput.toString());
        modelAndView.addObject("reserveInput", reserveInput);
        modelAndView.addObject("hotelName", hotelName);
        modelAndView.addObject("strType", strType);
        return modelAndView;
    }


    @RequestMapping(value = "reserve", method = RequestMethod.POST)
    public ModelAndView reserveHotel(@ModelAttribute("reserveInputTableVO") ReserveInputTableVO reserveInputTableVO) {
        System.out.println("reserve, reserveInputTableVO = " + reserveInputTableVO);
        MyMessage myMessage = this.reserveService.makeReservation(reserveInputTableVO);
        return this.handleMessage(myMessage, "redirect:/guest/orders", "guest/reserve");
    }

}
