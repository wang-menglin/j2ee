package com.kylin.vo;

/**
 * Created by kylin on 20/02/2017.
 * All rights reserved.
 *
 * 会员个人信息修改提交表格
 */
public class MemberUpdateTableVO {

    private int id;

    private String name;

    private String phone;

    private String email;

    private String bankCard;

    public MemberUpdateTableVO(int id, String name, String phone, String email, String bankCard) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bankCard = bankCard;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBankCard() {
        return bankCard;
    }
}