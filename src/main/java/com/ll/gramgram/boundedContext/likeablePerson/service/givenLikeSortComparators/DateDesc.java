package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;

import java.util.Comparator;

public class DateDesc implements Comparator<LikeablePerson> {
    @Override
    public int compare(LikeablePerson likeablePerson1, LikeablePerson likeablePerson2) {
        return - likeablePerson1.getModifyDate().compareTo(likeablePerson2.getModifyDate());
    }
}
