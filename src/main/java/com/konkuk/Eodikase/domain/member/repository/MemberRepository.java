package com.konkuk.Eodikase.domain.member.repository;

import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Boolean existsByEmailAndPlatform(String email, MemberPlatform platform);

    Boolean existsByNickname(String nickname);
}
