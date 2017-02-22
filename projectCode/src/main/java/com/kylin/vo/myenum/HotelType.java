package com.kylin.vo.myenum;

/**
 * Created by kylin on 22/02/2017.
 * All rights reserved.
 */
public enum HotelType {

    FiveStar("五星级酒店"),
    FourStar("四星级酒店"),
    ThreeStar("三星级酒店"),
    TwoStar("二星级酒店"),
    OneStar("一星级酒店"),
    ChainHotel("快捷连锁酒店");

    private String type;

    HotelType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
