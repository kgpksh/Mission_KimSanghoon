package com.ll.gramgram.boundedContext.likeablePerson.service;

import com.ll.gramgram.base.rsData.RsData;
import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.instaMember.service.InstaMemberService;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.likeablePerson.repository.LikeablePersonRepository;
import com.ll.gramgram.boundedContext.member.entity.Member;
import com.ll.gramgram.boundedContext.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeablePersonService {
    private final LikeablePersonRepository likeablePersonRepository;
    private final InstaMemberService instaMemberService;
    private final MemberService memberService;
    private static final int LIKE_NUMBER_LIMIT = 10;

    @Transactional
    public RsData<LikeablePerson> like(Member member, String username, int attractiveTypeCode) {
        if ( member.hasConnectedInstaMember() == false ) {
            return RsData.of("F-2", "먼저 본인의 인스타그램 아이디를 입력해야 합니다.");
        }
//        호감 표시 행위자의 인스타 아이디
        String actorName = member.getInstaMember().getUsername();

        if (actorName.equals(username)) {
            return RsData.of("F-1", "본인을 호감상대로 등록할 수 없습니다.");
        }

//        행위자가 호감 표시한 목록
        List<LikeablePerson> likeablePeopleList = likeablePersonRepository.findByFromInstaMemberUsername(actorName);

        if (likeablePeopleList.size() >= LIKE_NUMBER_LIMIT) {
            return RsData.of("F-3", "%d 보다 많은 호감상대를 등록할 수 없습니다".formatted(LIKE_NUMBER_LIMIT));
        }

//        SELECT한 리스트 재사용. 최대 길이 10인 리스트 순회하는 것이 DB 조회하는 것 보다 비용이 적다 판단.
        for (LikeablePerson likeablePerson : likeablePeopleList) {
            String likeTarget = likeablePerson.getToInstaMemberUsername();
            int likeReason = likeablePerson.getAttractiveTypeCode();

            if (likeTarget.equals(username)) {
                if (likeReason == attractiveTypeCode) {
                    return RsData.of("F-4", "이미 존재하는 사람에게 같은 이유로 호감을 표시할 수 없습니다.");
                }
            }
        }

        InstaMember toInstaMember = instaMemberService.findByUsernameOrCreate(username).getData();


        LikeablePerson likeablePerson = LikeablePerson
                .builder()
                .fromInstaMember(member.getInstaMember()) // 호감을 표시하는 사람의 인스타 멤버
                .fromInstaMemberUsername(member.getInstaMember().getUsername()) // 중요하지 않음
                .toInstaMember(toInstaMember) // 호감을 받는 사람의 인스타 멤버
                .toInstaMemberUsername(toInstaMember.getUsername()) // 중요하지 않음
                .attractiveTypeCode(attractiveTypeCode) // 1=외모, 2=능력, 3=성격
                .build();

        likeablePersonRepository.save(likeablePerson); // 저장

        return RsData.of("S-1", "입력하신 인스타유저(%s)를 호감상대로 등록되었습니다.".formatted(username), likeablePerson);
    }

    public List<LikeablePerson> findByFromInstaMemberId(Long fromInstaMemberId) {
        return likeablePersonRepository.findByFromInstaMemberId(fromInstaMemberId);
    }

    @Transactional(readOnly = false)
    public RsData<LikeablePerson> deleteFromAttracted(Long id, String username) {
        Optional<LikeablePerson> attraction = likeablePersonRepository.findById(id);

        if (!attraction.isPresent()) {
            return RsData.of("F-3", null, null);
        }

        System.out.println(memberService.getInstaId(username));

        Long idid=attraction.get().getFromInstaMember().getId();
        System.out.println(idid);
        if (memberService.getInstaId(username) != idid) {
            return RsData.of("F-4", null, null);
        }

        likeablePersonRepository.deleteById(id);
        return RsData.of("S-2", "선택한 호감 상대 삭제 성공", null);
    }

}
