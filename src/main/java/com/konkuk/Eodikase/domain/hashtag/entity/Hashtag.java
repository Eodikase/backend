package com.konkuk.Eodikase.domain.hashtag.entity;

import com.konkuk.Eodikase.domain.course.entity.CourseHashtagRel;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "hashtag")
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hashtag_id")
    private Long id;

    private String tagName;

    private String content;

    @OneToMany(mappedBy = "hashtag")
    private List<CourseHashtagRel> courseList = new ArrayList<>();

}
