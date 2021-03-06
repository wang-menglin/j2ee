package com.kylin.service;

import com.kylin.vo.MemberInfoVO;
import com.kylin.vo.MemberOrderVO;
import com.kylin.vo.SearchMemberVO;
import com.kylin.vo.common.MyMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kylin on 21/02/2017.
 * All rights reserved.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/test/test-context.xml"})
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    public void testServiceToRepo(){
        MemberInfoVO vo = this.memberService.getMemberInfo(1);
        System.out.println(vo.getId());
        System.out.println(vo.getActivatedTime());
    }

    @Test
    public void testOrderList(){
        List<MemberOrderVO> memberOrderVOS = this.memberService.getOrderList(1);
        for (MemberOrderVO memberOrderVO:memberOrderVOS){
            System.out.println(memberOrderVO.getHotelName());
            System.out.println(memberOrderVO.getCustomers().size());
        }
    }

    @Test
    public void testManagerSearch(){
        List<SearchMemberVO> g =  this.memberService.getOrderHistory("kylin");
        for (SearchMemberVO vo : g){
            System.out.println(vo.getHotelName());
            System.out.println(vo.getOrderTime());
        }
    }

    @Test
    public void testTopUp(){
        MyMessage myMessage = this.memberService.topUp(1,100,2200);
        System.out.println(myMessage.isSuccess());
        System.out.println(myMessage.getDisplayMessage());
    }

}
