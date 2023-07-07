package com.konkuk.Eodikase.domain.course.entity;

import com.konkuk.Eodikase.domain.hashtag.entity.Hashtag;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "course_hashtag_rel")
public class CourseHashtagRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rel_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;
}
