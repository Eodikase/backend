package com.konkuk.Eodikase.domain.member.repository;

import com.konkuk.Eodikase.domain.member.entity.Member;
import com.konkuk.Eodikase.domain.member.entity.MemberPlatform;
import com.konkuk.Eodikase.domain.member.entity.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Boolean existsByEmailAndPlatform(String email, MemberPlatform platform);

    Boolean existsByNickname(String nickname);

    Optional<Member> findByEmailAndPlatform(String email, MemberPlatform platform);

    Optional<Member> findByPlatformAndPlatformId(MemberPlatform platform, String platformId);

    @Query("select m.id from Member m where m.platform = :platform and m.platformId = :platformId")
    Optional<Long> findIdByPlatformAndPlatformId(MemberPlatform platform, String platformId);

    @Query("select m from Member m where m.modifiedDate <= :thresholdDate and m.status = :status")
    List<Member> findMemberByCreatedTime(@Param("thresholdDate") Date thresholdDate, @Param("status") MemberStatus status);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Member m where m.modifiedDate <= :thresholdDate and m.status = :status")
    void deleteMemberByCreatedTime(@Param("thresholdDate") Date thresholdDate, @Param("status") MemberStatus status);
}
