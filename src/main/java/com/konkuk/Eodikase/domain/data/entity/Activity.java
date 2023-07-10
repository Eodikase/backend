package com.konkuk.Eodikase.domain.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@DiscriminatorValue("A")
public class Activity extends CourseData{
    //TODO 각 특성에 맞는 데이터
    private String test;
}
