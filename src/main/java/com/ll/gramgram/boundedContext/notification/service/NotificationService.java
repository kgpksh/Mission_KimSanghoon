package com.ll.gramgram.boundedContext.notification.service;

import com.ll.gramgram.boundedContext.instaMember.entity.InstaMember;
import com.ll.gramgram.boundedContext.likeablePerson.entity.LikeablePerson;
import com.ll.gramgram.boundedContext.notification.entity.Notification;
import com.ll.gramgram.boundedContext.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public List<Notification> findByToInstaMember(InstaMember toInstaMember) {
        List<Notification> result = notificationRepository.findByToInstaMemberAndReadDateIsNull(toInstaMember);
        Collections.sort(result, new NotificationComparator());
        System.out.println(result + "__________________________________");
        return result;
    }

    class NotificationComparator implements Comparator<Notification> {
        @Override
        public int compare(Notification notification1, Notification notification2) {
            if (notification1.getModifyDate().isAfter(notification2.getModifyDate())) {
                return -1;
            }

            return 1;
        }
    }

    public void saveLike(LikeablePerson likeablePerson) {
        System.out.println();
        Notification notification =
                Notification
                        .builder()
                        .toInstaMember(likeablePerson.getToInstaMember())
                        .fromInstaMember(likeablePerson.getFromInstaMember())
                        .typeCode("Like")
                        .oldGender(likeablePerson.getFromInstaMember().getGender())
                        .newGender(likeablePerson.getFromInstaMember().getGender())
                        .newAttractiveTypeCode(likeablePerson.getAttractiveTypeCode())
                        .build();
        notificationRepository.save(notification);
    }

    public void saveModify(LikeablePerson likeablePerson, int oldAttractiveTypeCode, int newAttractiveTypeCode) {
        Notification notification =
                Notification
                        .builder()
                        .toInstaMember(likeablePerson.getToInstaMember())
                        .fromInstaMember(likeablePerson.getFromInstaMember())
                        .typeCode("ModifyAttractiveType")
                        .oldGender(likeablePerson.getFromInstaMember().getGender())
                        .newGender(likeablePerson.getFromInstaMember().getGender())
                        .oldAttractiveTypeCode(oldAttractiveTypeCode)
                        .newAttractiveTypeCode(newAttractiveTypeCode)
                        .build();
        notificationRepository.save(notification);
    }

}
