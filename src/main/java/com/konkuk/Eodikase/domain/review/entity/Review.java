package com.konkuk.Eodikase.domain.review.entity;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_id")
    private Long id;

    private int score;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="course_id")
    private Course course;
}
