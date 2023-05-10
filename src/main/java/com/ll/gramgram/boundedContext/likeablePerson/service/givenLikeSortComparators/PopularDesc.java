package com.ll.gramgram.boundedContext.likeablePerson.service.givenLikeSortComparators;

import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.repository.LikeablePersonRepository;
import com.ll.gramgram.boundedContext.likeablePerson.service.LikeablePersonService;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
@RequiredArgsConstructor
public class PopularDesc implements Comparator<LikeablePerson> {
    private final LikeablePersonService likeablePersonService;
    @Override
    public int compare(LikeablePerson likeablePerson1, LikeablePerson likeablePerson2) {
        InstaMember fromInstaMember1 = likeablePerson1.getFromInstaMember();
        InstaMember fromInstaMember2 = likeablePerson2.getFromInstaMember();

        long compare = likeablePersonService.countPopularity(fromInstaMember1)
                - likeablePersonService.countPopularity(fromInstaMember2);

        if (compare > 0) {
            return -1;
        }

        if (compare < 0) {
            return 1;
        }

        return 0;
    }
}
