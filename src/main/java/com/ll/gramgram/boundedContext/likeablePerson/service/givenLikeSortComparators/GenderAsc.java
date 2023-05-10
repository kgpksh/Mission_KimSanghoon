package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;

import java.util.Comparator;

public class GenderAsc implements Comparator<LikeablePerson> {
    @Override
    public int compare(LikeablePerson likeablePerson1, LikeablePerson likeablePerson2) {
        char gender1 = likeablePerson1.getFromInstaMember().getGender().charAt(0);
        char gender2 = likeablePerson2.getFromInstaMember().getGender().charAt(0);

        return gender1 - gender2;
    }
}
