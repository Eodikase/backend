package com.konkuk.Eodikase.repository;

import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원이 Member_Quit 이 된지 thresholdDate 만큼 지났으면 모두 삭제한다.")
    void deleteDeletedMemberByCreatedTime() {
        Member member1 = new Member("dlawotn3@naver.com", "jisu0708!", "메리",
                MemberPlatform.HOME, null, MemberStatus.MEMBER_QUIT);
        Member member2 = new Member("dlawotn2@naver.com", "jisu0708!", "메이",
                MemberPlatform.HOME, null, MemberStatus.MEMBER_QUIT);
        memberRepository.save(member1);
        memberRepository.save(member2);

        LocalDate thresholdDateTime = LocalDate.now().plusDays(1);
        Instant instant = thresholdDateTime.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Date thresholdDate = Date.from(instant);
        memberRepository.deleteMemberByCreatedTime(thresholdDate, MemberStatus.MEMBER_QUIT);

        List<Member> actual = memberRepository.findAll();

        assertThat(actual).hasSize(0);
    }
}
