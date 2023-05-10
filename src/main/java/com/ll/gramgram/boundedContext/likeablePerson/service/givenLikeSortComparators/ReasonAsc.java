package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;

import java.util.Comparator;

public class ReasonAsc implements Comparator<LikeablePerson> {
    LikeReasons[] reasons = LikeReasons.values();
    @Override
    public int compare(LikeablePerson likeablePerson1, LikeablePerson likeablePerson2) {
        int attractypeCode1 = likeablePerson1.getAttractiveTypeCode();
        int attractypeCode2 = likeablePerson2.getAttractiveTypeCode();

        return findMatches(attractypeCode1).compareTo(findMatches(attractypeCode2));
    }

    private String findMatches(int attractypeCode) {
        for (LikeReasons reason : reasons) {
            if (reason.getTypeCode() == attractypeCode) {
                return reason.getAttractiveType();
            }
        }
        return "";
    }
}
