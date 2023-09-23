package com.konkuk.Eodikase.domain.data.repository;

import com.konkuk.Eodikase.domain.data.entity.CourseDataCategory;
import com.konkuk.Eodikase.domain.data.entity.CourseDataSH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDataSHRepository extends JpaRepository<CourseDataSH, Long> {
    List<CourseDataSH> findByCourseDataCategory(CourseDataCategory category);

    @Query("SELECT COUNT(cd) FROM CourseDataSH cd WHERE " +
            "(POWER(69.1 * (cd.lat - :lat), 2) + POWER(69.1 * (:lng - cd.lng) * COS(cd.lat / 57.3), 2)) < :radiusSquared")
    int countDataInRadius(double lat, double lng, double radiusSquared);
}
