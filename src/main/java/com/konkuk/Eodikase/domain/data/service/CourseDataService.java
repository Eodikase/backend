package com.konkuk.Eodikase.domain.data.service;

import com.konkuk.Eodikase.domain.data.entity.*;
import com.konkuk.Eodikase.domain.data.repository.*;
import com.konkuk.Eodikase.domain.member.repository.MemberRepository;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataCountRequest;
import com.konkuk.Eodikase.dto.request.data.FilteredCourseDataRequest;
import com.konkuk.Eodikase.dto.response.data.*;
import com.konkuk.Eodikase.exception.badrequest.*;
import com.konkuk.Eodikase.exception.notfound.NotFoundCourseDataException;
import com.konkuk.Eodikase.exception.notfound.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
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

    public FilteredCourseDataResponse filtersCourseData(
            Long memberId, String region, String type, int stage, int order, FilteredCourseDataRequest request,
            int page, int size
    ) {
        memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (stage < 1 || stage > 4) {
            throw new InvalidStageException();
        }
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
            return filtersCourseDataByScore(filteredDataByRadius, type, page, size);
        } else if (2 <= order && order <= 10) { // 두 번째 순서 이상부터는 거리 고려 -> 별점순
            return filtersCourseDataByDistanceAndScore(filteredDataByRadius, type, mapX, mapY, page, size);
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
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(lat1 - lat2) * toRadian;
        double deltaLongitude = Math.abs(lon1 - lon2) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(lat1 * toRadian) * Math.cos(lat2 * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance * 1000; // km단위 * 1000 -> m
    }

    private List<FilteredCourseDataByRadiusResponse> filtersCourseDataByRadius(
            List<FilteredCourseDataByRegionAndTypeResponse> filteredDataByRegionAndType,
            double mapX, double mapY, int stage
    ) {
        int radius = 350 * stage; // m
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
        int endIndex = Math.min(filteredDataList.size(), page * count + count);
        List<FilteredCourseDataByRadiusResponse> paginatedData = filteredDataList.subList(page * count, endIndex);

        boolean last = endIndex == filteredDataList.size();

        return new FilteredCourseDataResponse(type, paginatedData, last);
    }

    private FilteredCourseDataResponse filtersCourseDataByDistanceAndScore(
            List<FilteredCourseDataByRadiusResponse> filteredDataByRadius, String type,
            double mapX, double mapY, int page, int size) {

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

        int endIndex = Math.min(filteredDataByRadius.size(), page * size + size);
        List<FilteredCourseDataByRadiusResponse> paginatedData = filteredDataByRadius.subList(page * size, endIndex);

        boolean last = endIndex == filteredDataByRadius.size();

        return new FilteredCourseDataResponse(type, paginatedData, last);
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
        } else if (region.equals("SH")) {
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

    public FilteredCourseDataCountResponse filterCourseDataCountByRadius(Long memberId, String region,
                                                                         FilteredCourseDataCountRequest request) {
        memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        double mapX = request.getLat();
        double mapY = request.getLng();
        List<FindAllCourseDataResponse> courseDataList = new ArrayList<>();

        if (region.equals("EM")) {
            List<CourseDataEM> allDatas = courseDataEMRepository.findAll();
            for (CourseDataEM data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("HI")) {
            List<CourseDataHI> allDatas = courseDataHIRepository.findAll();
            for (CourseDataHI data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("HSE")) {
            List<CourseDataHSE> allDatas = courseDataHSERepository.findAll();
            for (CourseDataHSE data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("KSS")) {
            List<CourseDataKSS> allDatas = courseDataKSSRepository.findAll();
            for (CourseDataKSS data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("NS")) {
            List<CourseDataNS> allDatas = courseDataNSRepository.findAll();
            for (CourseDataNS data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("SBG")) {
            List<CourseDataSBG> allDatas = courseDataSBGRepository.findAll();
            for (CourseDataSBG data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else if (region.equals("SH")) {
            List<CourseDataSH> allDatas = courseDataSHRepository.findAll();
            for (CourseDataSH data : allDatas) {
                FindAllCourseDataResponse response
                        = new FindAllCourseDataResponse(data.getId(), data.getLat(), data.getLng());
                courseDataList.add(response);
            }
        } else {
            throw new InvalidRegionException();
        }

        return countDataInRadius(courseDataList, mapX, mapY);
    }

    private FilteredCourseDataCountResponse countDataInRadius(List<FindAllCourseDataResponse> dataList,
                                  double mapX, double mapY) {
        int radius = 350;
        int stage1 = 0;
        int stage2 = 0;
        int stage3 = 0;
        int stage4 = 0;

        for (FindAllCourseDataResponse data : dataList) {
            double dataLat = data.getLat();
            double dataLng = data.getLng();
            double distance = haversine(mapX, mapY, dataLat, dataLng);
            // 거리가 반경 내에 있는 경우, 매장 개수 증가
            if (distance <= radius) {
                stage1++;
            }
        }

        for (FindAllCourseDataResponse data : dataList) {
            double dataLat = data.getLat();
            double dataLng = data.getLng();
            double distance = haversine(mapX, mapY, dataLat, dataLng);
            // 거리가 반경 내에 있는 경우, 매장 개수 증가
            if (distance <= radius*2) {
                stage2++;
            }
        }

        for (FindAllCourseDataResponse data : dataList) {
            double dataLat = data.getLat();
            double dataLng = data.getLng();
            double distance = haversine(mapX, mapY, dataLat, dataLng);
            // 거리가 반경 내에 있는 경우, 매장 개수 증가
            if (distance <= radius*3) {
                stage3++;
            }
        }

        for (FindAllCourseDataResponse data : dataList) {
            double dataLat = data.getLat();
            double dataLng = data.getLng();
            double distance = haversine(mapX, mapY, dataLat, dataLng);
            // 거리가 반경 내에 있는 경우, 매장 개수 증가
            if (distance <= radius*4) {
                stage4++;
            }
        }
        return new FilteredCourseDataCountResponse(stage1, stage2, stage3, stage4); // 반경 내의 매장 개수를 반환
    }

    public SearchCourseDatasResponse searchCourseDataByKeyword(Long memberId, String region, String category,
                                                               String keyword, int page, int size) {
        memberRepository.findById(memberId)
                .orElseThrow(NotFoundMemberException::new);
        if (keyword.isBlank()) {
            throw new BlankKeywordException();
        }
        Pageable pageable = PageRequest.of(page, size);
        Page<SearchCourseDataResponse> courseDataPage;
        List<SearchCourseDataResponse> courseDataResponses;
        CourseDataCategory dataCategory;
        if (category.equals("RES")) {
            dataCategory = CourseDataCategory.RES;
        } else if (category.equals("CAFE")) {
            dataCategory = CourseDataCategory.CAFE;
        } else if (category.equals("ACT")) {
            dataCategory = CourseDataCategory.ACT;
        } else {
            throw new InvalidCourseDataCategoryException();
        }

        if (region.equals("EM")) {
            courseDataPage = courseDataEMRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("HI")) {
            courseDataPage = courseDataHIRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("HSE")) {
            courseDataPage = courseDataHSERepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("KSS")) {
            courseDataPage = courseDataKSSRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("NS")) {
            courseDataPage = courseDataNSRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("SBG")) {
            courseDataPage = courseDataSBGRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else if (region.equals("SH")) {
            courseDataPage = courseDataSHRepository
                    .findByCourseDataCategoryAndNameContaining(dataCategory, keyword, pageable)
                    .map(data -> new SearchCourseDataResponse(data.getId(), data.getName()));
        } else {
            throw new InvalidRegionException();
        }
        courseDataResponses = courseDataPage.getContent();
        boolean last = !courseDataPage.hasNext();

        return new SearchCourseDatasResponse(courseDataResponses, last);
    }
}
