package com.konkuk.Eodikase.domain.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@DiscriminatorValue("R")
public class Restaurant extends CourseData {
    //TODO 각 특성에 맞는 데이터
}
