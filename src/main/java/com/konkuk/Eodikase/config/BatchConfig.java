package com.konkuk.Eodikase.config;

import com.konkuk.Eodikase.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final MemberService memberService;

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul") // 매일 자정에 실행
    public void deleteMember() {
        memberService.deleteMemberAfter30Days();
    }
}
