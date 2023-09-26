package com.konkuk.Eodikase.domain.course.entity;

import com.konkuk.Eodikase.domain.data.entity.*;
import com.konkuk.Eodikase.domain.hashtag.entity.Hashtag;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "course_hashtag_rel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseHashtagRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_hashtag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public CourseHashtagRel(Hashtag hashtag)
    {
        this.hashtag = hashtag;
    }

    public void assignCourse(Course course){
        this.course = course;
        course.getHashtagRelList().add(this);
    }
}
