package com.konkuk.Eodikase.domain.hashtag.entity;


public enum HashTagName{
    CP("회식할때 "),
    DATE("데이트할 떄"),
    FLEX("플렉스하고싶을 때"),
    SOME("썸탈 떄"),
    ALC("술이 떙길 때"),
    FOCUS("집중하고 싶을 때"),
    GASUNG("가성비"),
    COST("텅장"),
    BADFOOT("발이 아파요"),
    GOODFOOT("가뿐해요"),
    CAR("차가 필요해요"),
    FRIEND("친구와 함꼐"),
    PARENTS("부모님과 함께"),
    COUPLE("연인과 함께"),
    PEER("동료와 함께"),
    FAMILY("가족과 함꼐");

    private final String description;

    HashTagName(String description){
        this.description = description;
    }
    public String description(){
        return description;
    }
}
