package com.coders.laundry.controller;

import com.coders.laundry.dto.Review;
import com.coders.laundry.dto.ReviewUploadRequest;
import com.coders.laundry.service.ReviewService;
import com.coders.laundry.service.TokenManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@WebMvcTest(ReviewController.class)
@ExtendWith(MockitoExtension.class)
class ReviewControllerTest {

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private TokenManagerService tokenManagerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void findLaundryReviewsByLaundryId() throws Exception {
        // Arrange
        Integer laundryId = 1231245;
        LocalDateTime now = LocalDateTime.now();
        Review review = Review.builder()
                .reviewId(1)
                .laundryId(laundryId)
                .rating(5)
                .contents("리뷰 내용")
                .visitDate(LocalDate.of(2022, 9, 23))
                .createDate(now)
                .updateDate(now)
                .build();
        List<Review> foundReviews = List.of(review);
        when(reviewService.findAllByLaundryId(laundryId)).thenReturn(foundReviews);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/laundries/{laundryId}/reviews", laundryId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("laundryId", String.valueOf(laundryId))
        );

        // Assert
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(foundReviews.size())))
                .andExpect(jsonPath("$[0].laundryId").exists())
                .andDo(print());
    }

    @Test
    void findLaundryReviewsByLaundryId_WhenTargetLaundryNotFound() throws Exception {
        // Arrange
        Integer laundryId = 1231245;
        when(reviewService.findAllByLaundryId(laundryId)).thenThrow(new RuntimeException());

        // Act
        ResultActions actions = mockMvc.perform(get("/api/laundries/{laundryId}/reviews", laundryId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("laundryId", String.valueOf(laundryId))
        );

        // Assert
        actions.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void uploadLaundryReview() throws Exception {
        // Arrange
        String token = "Bearer test token";
        ReviewUploadRequest request = createReviewUploadRequest();
        Integer writerId = 1244125;

        LocalDateTime now = LocalDateTime.now();
        Review createdReview = Review.builder()
                .reviewId(12413235)
                .writerId(writerId)
                .laundryId(request.getLaundryId())
                .visitDate(request.getVisitDate())
                .contents(request.getContents())
                .rating(request.getRating())
                .createDate(now)
                .updateDate(now)
                .build();

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(writerId);
        when(reviewService.upload(writerId, request)).thenReturn(createdReview);

        // Act
        ResultActions actions = mockMvc.perform(post("/api/laundries/{laundryId}/reviews", request.getLaundryId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(jsonPath("reviewId").value(createdReview.getReviewId()))
                .andDo(print());
    }

    @Test
    void uploadLaundryReview_TokenIsNotVerified() throws Exception {
        // Arrange
        String token = "Bearer test token";
        ReviewUploadRequest request = createReviewUploadRequest();

        when(tokenManagerService.verify(token)).thenReturn(false);

        // Act
        ResultActions actions = mockMvc.perform(post("/api/laundries/{laundryId}/reviews", request.getLaundryId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isUnauthorized()).andDo(print());
    }

    @Test
    void uploadLaundryReview_WhenPathVariableLaundryIdIsNotMatchedWithRequest() throws Exception {
        // Arrange
        String token = "Bearer test token";
        ReviewUploadRequest request = createReviewUploadRequest();

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(reviewService.findAllByLaundryId(request.getLaundryId())).thenThrow(RuntimeException.class);

        // Act
        ResultActions actions = mockMvc.perform(post("/api/laundries/{laundryId}/reviews", request.getLaundryId() + 1)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isBadRequest()).andDo(print());
    }

    private ReviewUploadRequest createReviewUploadRequest() {
        return ReviewUploadRequest.builder()
                .laundryId(1213)
                .visitDate(LocalDate.of(2022, 9, 2))
                .contents("테스트 리뷰 내용")
                .rating(2)
                .build();
    }

}