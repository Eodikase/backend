package com.konkuk.Eodikase.domain.comment.entity;

import com.konkuk.Eodikase.domain.course.entity.Course;
import com.konkuk.Eodikase.domain.member.entity.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    private String content;
}
