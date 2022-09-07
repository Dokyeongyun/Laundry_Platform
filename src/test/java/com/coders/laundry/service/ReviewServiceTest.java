package com.coders.laundry.service;

import com.coders.laundry.domain.entity.ReviewEntity;
import com.coders.laundry.domain.exceptions.ResourceAlreadyExistsException;
import com.coders.laundry.dto.Review;
import com.coders.laundry.dto.ReviewUploadRequest;
import com.coders.laundry.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ReviewServiceTest {

    private ReviewService reviewService;

    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setup() {
        reviewRepository = mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository);
    }

    @Test
    void save() {
        // Arrange
        Integer writerId = 1;
        ReviewUploadRequest request = ReviewUploadRequest.builder()
                .laundryId(1)
                .rating(5)
                .contents("리뷰 내용입니다.")
                .visitDate(LocalDate.of(2022, 9, 6))
                .build();

        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(request.getLaundryId())
                .writerId(writerId)
                .rating(request.getRating())
                .contents(request.getContents())
                .visitDate(request.getVisitDate())
                .build();

        int createdReviewId = 10;
        LocalDateTime createdAt = LocalDateTime.now();
        when(reviewRepository.selectByLaundryIdAndWriterId(request.getLaundryId(), writerId)).thenReturn(null);
        when(reviewRepository.insert(entity)).thenAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            ReviewEntity argEntity = (ReviewEntity) arguments[0];
            argEntity.setReviewId(createdReviewId);
            argEntity.setCreateDate(createdAt);
            argEntity.setUpdateDate(createdAt);
            return 1;
        });

        ReviewEntity created = ReviewEntity.builder()
                .reviewId(createdReviewId)
                .laundryId(request.getLaundryId())
                .writerId(writerId)
                .rating(request.getRating())
                .contents(request.getContents())
                .visitDate(request.getVisitDate())
                .createDate(createdAt)
                .updateDate(createdAt)
                .build();

        when(reviewRepository.selectById(createdReviewId)).thenReturn(created);

        // Act
        Review result = reviewService.save(writerId, request);

        // Assert
        assertNotNull(result.getReviewId());
        assertEquals(request.getLaundryId(), result.getLaundryId());
        assertEquals(writerId, result.getWriterId());
        assertEquals(request.getRating(), result.getRating());
        assertEquals(request.getContents(), result.getContents());
        assertEquals(request.getVisitDate(), result.getVisitDate());
        assertNotNull(result.getCreateDate());
        assertNotNull(result.getUpdateDate());
    }

    @Test
    void save_ResourceAlreadyExistsException() {
        // Arrange
        Integer writerId = 1;
        ReviewUploadRequest request = ReviewUploadRequest.builder()
                .laundryId(1)
                .rating(5)
                .contents("리뷰 내용입니다.")
                .visitDate(LocalDate.of(2022, 9, 6))
                .build();

        LocalDateTime createdAt = LocalDateTime.now();
        ReviewEntity exists = ReviewEntity.builder()
                .reviewId(1)
                .laundryId(request.getLaundryId())
                .writerId(writerId)
                .rating(request.getRating())
                .contents(request.getContents())
                .visitDate(request.getVisitDate())
                .createDate(createdAt)
                .updateDate(createdAt)
                .build();

        when(reviewRepository.selectByLaundryIdAndWriterId(request.getLaundryId(), writerId))
                .thenReturn(exists);

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> {
            reviewService.save(writerId, request);
        });
    }

}