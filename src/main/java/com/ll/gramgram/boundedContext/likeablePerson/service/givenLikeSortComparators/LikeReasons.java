package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

public enum LikeReasons {
    appearance(1, "외모")
    , personality(2, "성격")
    , competent(3, "능력");

    private int typeCode;
    private String attractiveType;
    LikeReasons(int key, String value){
        this.typeCode = key;
        this.attractiveType = value;
    }

    int getTypeCode() {
        return typeCode;
    }

    String getAttractiveType() {
        return attractiveType;
    }
}
