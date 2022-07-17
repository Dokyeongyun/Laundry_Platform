package com.coders.laundry.controller;

import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.Point;
import com.coders.laundry.dto.SearchedLaundry;
import com.coders.laundry.service.LaundryFindService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class LaundryControllerTest {

    private LaundryFindService laundryFindService;

    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        laundryFindService = mock(LaundryFindService.class);
        LaundryController laundryController = new LaundryController(laundryFindService);
        mockMvc = MockMvcBuilders.standaloneSetup(laundryController).build();
    }

    @Test
    void search_OK() throws Exception {
        // Arrange
        String token = "Bearer test";
        LocationSearch locationSearch
                = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);
        String keyword = "시흥시";
        String mode = "address";
        int offset = 0;
        int limit = 20;
        String sort = "distance";
        String sortType = "desc";

        SearchedLaundry searchedLaundry = SearchedLaundry.builder()
                .laundryId(148)
                .name("백양세탁")
                .jibunAddress("경기도 시흥시 정왕동 1975-7번지")
                .jibunAddressDetail(null)
                .doroAddress("경기도 시흥시 오이도중앙로6번길 5-14, 1층 일부호 (정왕동)")
                .doroAddressDetail(null)
                .latitude(37.3467219612)
                .longitude(126.6894764563)
                .partnership(true)
                .thumbnailImage(null)
                .ratingPoint(0.0)
                .reviewCount(0)
                .tags(new ArrayList<>())
                .distance(0)
                .like(false)
                .build();
        List<SearchedLaundry> list = List.of(searchedLaundry);

        when(laundryFindService.findCount(keyword, locationSearch, mode)).thenReturn(1);
        when(laundryFindService.search(keyword, locationSearch, new Pageable(offset, limit, sort, sortType), mode))
                .thenReturn(list);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/laundries")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .queryParam("keyword", keyword)
                .queryParam("baseLocation.latitude", locationSearch.getBaseLocation().getLatitude() + "")
                .queryParam("baseLocation.longitude", locationSearch.getBaseLocation().getLongitude() + "")
                .queryParam("radius", locationSearch.getRadius() + "")
                .queryParam("mode", mode)
                .queryParam("offset", offset + "")
                .queryParam("limit", limit + "")
                .queryParam("sort", sort)
                .queryParam("sortType", sortType)
        );

        // Assert
        actions.andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("totalCount").value(1))
                .andExpect(jsonPath("offset").value(offset))
                .andExpect(jsonPath("limit").value(limit))
                .andExpect(jsonPath("sort").value(sort))
                .andExpect(jsonPath("sortType").value(sortType))
                .andExpect(jsonPath("list").exists())
                .andExpect(jsonPath("$.list[0].laundryId").value(searchedLaundry.getLaundryId()))
                .andExpect(jsonPath("$.list[0].name").value(searchedLaundry.getName()))
                .andExpect(jsonPath("$.list[0].jibunAddress").value(searchedLaundry.getJibunAddress()))
                .andExpect(jsonPath("$.list[0].jibunAddressDetail").value(searchedLaundry.getJibunAddressDetail()))
                .andExpect(jsonPath("$.list[0].doroAddress").value(searchedLaundry.getDoroAddress()))
                .andExpect(jsonPath("$.list[0].doroAddressDetail").value(searchedLaundry.getDoroAddressDetail()))
                .andExpect(jsonPath("$.list[0].latitude").value(searchedLaundry.getLatitude()))
                .andExpect(jsonPath("$.list[0].longitude").value(searchedLaundry.getLongitude()))
                .andExpect(jsonPath("$.list[0].partnership").value(searchedLaundry.isPartnership()))
                .andExpect(jsonPath("$.list[0].thumbnailImage").value(searchedLaundry.getThumbnailImage()))
                .andExpect(jsonPath("$.list[0].ratingPoint").value(searchedLaundry.getRatingPoint()))
                .andExpect(jsonPath("$.list[0].reviewCount").value(searchedLaundry.getReviewCount()))
                .andExpect(jsonPath("$.list[0].tags").value(searchedLaundry.getTags()))
                .andExpect(jsonPath("$.list[0].distance").value(searchedLaundry.getDistance()))
                .andExpect(jsonPath("$.list[0].like").value(searchedLaundry.isLike()))
                .andDo(print());
    }

}