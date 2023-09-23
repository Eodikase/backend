package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSBG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDataSBGRepository extends JpaRepository<CourseDataSBG, Long> {
    List<CourseDataSBG> findByCourseDataCategory(CourseDataCategory category);

    @Query("SELECT COUNT(cd) FROM CourseDataSBG cd WHERE " +
            "(POWER(69.1 * (cd.lat - :lat), 2) + POWER(69.1 * (:lng - cd.lng) * COS(cd.lat / 57.3), 2)) < :radiusSquared")
    int countDataInRadius(double lat, double lng, double radiusSquared);
}
