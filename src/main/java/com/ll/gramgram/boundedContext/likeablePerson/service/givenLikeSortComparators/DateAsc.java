package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import org.springframework.stereotype.Component;

import java.util.Comparator;
@Component
public class DateAsc implements Comparator<LikeablePerson> {
    @Override
    public int compare(LikeablePerson likeablePerson1, LikeablePerson likeablePerson2) {
        return likeablePerson1.getModifyDate().compareTo(likeablePerson2.getModifyDate());
    }
}
