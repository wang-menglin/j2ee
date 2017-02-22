package com.kylin.controller;

import com.kylin.model.Hotel;
import com.kylin.repository.HotelRepository;
import com.kylin.service.MemberService;
import com.kylin.vo.MemberInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kylin on 18/02/2017.
 * All rights reserved.
 */

@Controller
public class TestController {

    @Autowired
    HotelRepository repository;

    @Autowired
    MemberService service;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(){
        Hotel hotel = repository.findOne(2);
        System.out.println(hotel.getName());

        System.out.println(hotel.getApprovals().size());
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public void test2(){
        MemberInfoVO vo = this.service.getMemberInfo(1);
        System.out.println(vo.getId());
        System.out.println(vo.getAccount());
        System.out.println(vo.getPassword());
    }
}