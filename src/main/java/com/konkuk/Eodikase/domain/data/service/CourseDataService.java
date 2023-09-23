package com.konkuk.Eodikase.domain.data.service;

import com.konkuk.Eodikase.domain.data.dto.request.FilteredCourseDataRequest;
import com.konkuk.Eodikase.domain.data.dto.response.CourseDataDetailInfoResponse;
import com.konkuk.Eodikase.domain.data.dto.response.FilteredCourseDataByRadiusResponse;
import com.konkuk.Eodikase.domain.data.dto.response.FilteredCourseDataByRegionAndTypeResponse;
import com.konkuk.Eodikase.domain.data.dto.response.FilteredCourseDataResponse;
import com.konkuk.Eodikase.domain.data.entity.*;
import com.konkuk.Eodikase.domain.data.repository.*;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.exception.badrequest.InvalidDataOrderException;
import com.konkuk.Eodikase.exception.badrequest.InvalidRegionException;
import com.konkuk.Eodikase.exception.notfound.NotFoundCourseDataException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseDataService {

    private final MemberRepository memberRepository;
    private final CourseDataEMRepository courseDataEMRepository;
    private final CourseDataHIRepository courseDataHIRepository;
    private final CourseDataHSERepository courseDataHSERepository;
    private final CourseDataKSSRepository courseDataKSSRepository;
    private final CourseDataNSRepository courseDataNSRepository;
    private final CourseDataSBGRepository courseDataSBGRepository;
    private final CourseDataSHRepository courseDataSHRepository;

    // 첫 번째 코스 아이템 조회
    public FilteredCourseDataResponse filtersCourseData(
            Long memberId, String region, String type, int stage, int order, FilteredCourseDataRequest request,
            int page, int count
    ) {
        memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        // 지역, 타입 필터링
        List<FilteredCourseDataByRegionAndTypeResponse> filteredDataByRegionAndType =
                filtersCourseDataByRegionAndType(region, type);

        // 반경 필터링
        double mapX = request.getLat();
        double mapY = request.getLng();
        List<FilteredCourseDataByRadiusResponse> filteredDataByRadius =
                filtersCourseDataByRadius(filteredDataByRegionAndType, mapX, mapY, stage);

        // 순서 필터링
        if (order == 1) { // 첫 번째 순서인 경우에만 별점순
            return filtersCourseDataByScore(filteredDataByRadius, type, page, count);
        } else if (2 <= order && order <= 10) { // 두 번째 순서 이상부터는 거리 고려 -> 별점순
            return filtersCourseDataByDistanceAndScore(filteredDataByRadius, type, mapX, mapY, page, count);
        }

        throw new InvalidDataOrderException();
    }

    private List<FilteredCourseDataByRegionAndTypeResponse> filtersCourseDataByRegionAndType(String region, String type) {
        CourseDataCategory category = CourseDataCategory.from(type);
        List<FilteredCourseDataByRegionAndTypeResponse> filteredDataList = new ArrayList<>();

        if (region.equals("EM")) {
            List<CourseDataEM> filteredByData = courseDataEMRepository.findByCourseDataCategory(category);

            // CourseDataEM를 FilteredCourseDataByRegionAndTypeResponse로 변환하여 추가
            for (CourseDataEM data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("HI")) {
            List<CourseDataHI> filteredByData = courseDataHIRepository.findByCourseDataCategory(category);
            for (CourseDataHI data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("HSE")) {
            List<CourseDataHSE> filteredByData = courseDataHSERepository.findByCourseDataCategory(category);
            for (CourseDataHSE data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("KSS")) {
            List<CourseDataKSS> filteredByData= courseDataKSSRepository.findByCourseDataCategory(category);
            for (CourseDataKSS data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("NS")) {
            List<CourseDataNS> filteredByData= courseDataNSRepository.findByCourseDataCategory(category);
            for (CourseDataNS data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("SBG")) {
            List<CourseDataSBG> filteredByData= courseDataSBGRepository.findByCourseDataCategory(category);
            for (CourseDataSBG data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else if (region.equals("SH")) {
            List<CourseDataSH> filteredByData= courseDataSHRepository.findByCourseDataCategory(category);
            for (CourseDataSH data : filteredByData) {
                FilteredCourseDataByRegionAndTypeResponse response
                        = new FilteredCourseDataByRegionAndTypeResponse(data.getId(), data.getName(), data.getCategory(),
                        data.getLocation(), data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(),
                        data.getReviewCount(), data.getImageUrl(), data.getLat(), data.getLng());
                filteredDataList.add(response);
            }
        } else {
            throw new InvalidRegionException();
        }
        return filteredDataList;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구의 반지름 (km)

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c; // 두 지점 사이의 거리 (km)
    }

    private List<FilteredCourseDataByRadiusResponse> filtersCourseDataByRadius(
            List<FilteredCourseDataByRegionAndTypeResponse> filteredDataByRegionAndType,
            double mapX, double mapY, int stage
    ) {
        int radius = 350 * stage;
        List<FilteredCourseDataByRadiusResponse> filteredDataByRadius = new ArrayList<>();

        for (FilteredCourseDataByRegionAndTypeResponse data : filteredDataByRegionAndType) {
            double dataLat = data.getLat();
            double dataLng = data.getLng();

            // 두 지점 간의 거리 계산
            double distance = haversine(mapX, mapY, dataLat, dataLng);

            // 거리가 반경 내에 있는 경우 데이터를 추가
            if (distance <= radius) {
                FilteredCourseDataByRadiusResponse response = new FilteredCourseDataByRadiusResponse(
                        data.getId(), data.getName(), data.getCategory(), data.getLocation(),
                        data.getPhoneNumber(), data.getScoreByNaver(), data.getOperatingTime(), data.getReviewCount(),
                        data.getImageUrl(), data.getLat(), data.getLng()
                );
                filteredDataByRadius.add(response);
            }
        }
        return filteredDataByRadius;
    }

    private FilteredCourseDataResponse filtersCourseDataByScore(
            List<FilteredCourseDataByRadiusResponse> filteredDataList, String type, int page, int count) {
        filteredDataList.sort(Comparator.comparing(FilteredCourseDataByRadiusResponse::getScoreByNaver).reversed());
        PageRequest pageRequest = PageRequest.of(page, count);
        int startIndex = (int) pageRequest.getOffset();
        int endIndex = startIndex + pageRequest.getPageSize();

        List<FilteredCourseDataByRadiusResponse> paginatedData = filteredDataList.subList(startIndex, endIndex);

        return new FilteredCourseDataResponse(type, paginatedData);
    }

    private FilteredCourseDataResponse filtersCourseDataByDistanceAndScore(
            List<FilteredCourseDataByRadiusResponse> filteredDataByRadius, String type,
            double mapX, double mapY, int page, int count) {

        Comparator<FilteredCourseDataByRadiusResponse> distanceAndScoreComparator = (data1, data2) -> {
            double distance1 = haversine(mapX, mapY, data1.getLat(), data1.getLng());
            double distance2 = haversine(mapX, mapY, data2.getLat(), data2.getLng());

            // 거리 오름차순으로 정렬, 거리가 같다면 별점 내림차순으로 정렬
            if (distance1 != distance2) {
                return Double.compare(distance1, distance2);
            } else {
                return Double.compare(data2.getScoreByNaver(), data1.getScoreByNaver());
            }
        };
        filteredDataByRadius.sort(distanceAndScoreComparator);
        PageRequest pageRequest = PageRequest.of(page, count);
        int startIndex = (int) pageRequest.getOffset();
        int endIndex = startIndex + pageRequest.getPageSize();

        List<FilteredCourseDataByRadiusResponse> paginatedData = filteredDataByRadius.subList(startIndex, endIndex);

        return new FilteredCourseDataResponse(type, paginatedData);
    }

    public CourseDataDetailInfoResponse searchCourseDataDetailInfo(Long memberId, String region, Long dataId) {
        memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (region.equals("EM")) {
            CourseDataEM findData = courseDataEMRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("HI")) {
            CourseDataHI findData = courseDataHIRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("HSE")) {
            CourseDataHSE findData = courseDataHSERepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("KSS")) {
            CourseDataKSS findData = courseDataKSSRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("NS")) {
            CourseDataNS findData = courseDataNSRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("SBG")) {
            CourseDataSBG findData = courseDataSBGRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        } else if (region.equals("SHR")) {
            CourseDataSH findData = courseDataSHRepository.findById(dataId)
                    .orElseThrow(NotFoundCourseDataException::new);
            return new CourseDataDetailInfoResponse(findData.getId(), findData.getName(),
                    findData.getCategory(), findData.getLocation(), findData.getPhoneNumber(),
                    findData.getScoreByNaver(), findData.getHref(), findData.getOperatingTime(),
                    findData.getReviewCount(), findData.getImageUrl(), findData.getImg1(), findData.getImg2(),
                    findData.getImg3(), findData.getLat(), findData.getLng());
        }
        throw new InvalidRegionException();
    }
}
