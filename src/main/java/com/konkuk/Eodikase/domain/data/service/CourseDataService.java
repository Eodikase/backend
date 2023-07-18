package com.konkuk.Eodikase.domain.data.service;

import com.konkuk.Eodikase.domain.data.entity.Activity;
import com.konkuk.Eodikase.domain.data.entity.CourseData;
import com.konkuk.Eodikase.domain.data.repository.CourseDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseDataService {

    private final CourseDataRepository courseDataRepository;
    @Transactional
    public void save(CourseData data){

        courseDataRepository.save(data);
    }
}
