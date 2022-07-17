package com.coders.laundry.service;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchedLaundry;
import com.coders.laundry.repository.LaundryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryFindService {

    private final LaundryRepository laundryRepository;

    public int findCount(String keyword,
                         LocationSearch locationSearch,
                         String searchMode) {

        if (searchMode.equals("address")) {
            return laundryRepository.selectAddressSearchListCount(keyword, locationSearch);
        } else if (searchMode.equals("keyword")) {
            return laundryRepository.selectKeywordSearchListCount(keyword, locationSearch);
        } else {
            throw new IllegalArgumentException("Invalid laundry search mode.");
        }
    }

    public List<SearchedLaundry> search(int memberId,
                                        String keyword,
                                        LocationSearch locationSearch,
                                        Pageable pageable,
                                        String searchMode) {

        List<LaundryEntity> list;
        if (searchMode.equals("address")) {
            list = laundryRepository.selectAddressSearchList(memberId, keyword, locationSearch, pageable);
        } else if (searchMode.equals("keyword")) {
            list = laundryRepository.selectKeywordSearchList(memberId, keyword, locationSearch, pageable);
        } else {
            throw new IllegalArgumentException("Invalid laundry search mode.");
        }

        // sorting
        String sort = pageable.getSort();
        String sortType = pageable.getSortType();
        switch (sort) {
            case "distance" -> list.sort(Comparator.comparing(LaundryEntity::getDistance));
            case "review" -> list.sort(Comparator.comparing(LaundryEntity::getReviewCount));
            case "point" -> list.sort(Comparator.comparing(LaundryEntity::getRatingPoint));
        }

        if(sortType.equals("desc")) {
            Collections.reverse(list);
        }

        List<SearchedLaundry> result = new ArrayList<>();
        for (LaundryEntity entity : list) {
            SearchedLaundry laundry = SearchedLaundry.builder()
                    .laundryId(entity.getLaundryId())
                    .name(entity.getName())
                    .jibunAddress(entity.getJibunAddress())
                    .jibunAddressDetail(entity.getJibunAddressDetail())
                    .doroAddress(entity.getDoroAddress())
                    .doroAddressDetail(entity.getDoroAddressDetail())
                    .latitude(entity.getLatitude())
                    .longitude(entity.getLongitude())
                    .partnership(entity.isPartnership())
                    .thumbnailImage(entity.getThumbnailImage())
                    .ratingPoint(entity.getRatingPoint())
                    .reviewCount(entity.getReviewCount())
                    .tags(entity.getTags())
                    .distance(entity.getDistance())
                    .like(entity.isLike())
                    .build();
            result.add(laundry);
        }

        return result;
    }
}
