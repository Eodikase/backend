package com.konkuk.Eodikase.domain.member.repository;

import com.konkuk.Eodikase.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
