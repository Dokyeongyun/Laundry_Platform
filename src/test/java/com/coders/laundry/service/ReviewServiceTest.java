package com.coders.laundry.service;

import com.coders.laundry.domain.entity.ReviewEntity;
import com.coders.laundry.domain.exceptions.ResourceAlreadyExistsException;
import com.coders.laundry.dto.Laundry;
import com.coders.laundry.dto.Review;
import com.coders.laundry.dto.ReviewUploadRequest;
import com.coders.laundry.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ReviewServiceTest {

    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private LaundryFindService laundryFindService;

    @BeforeEach
    public void setup() {
        reviewService = new ReviewService(reviewRepository, laundryFindService);
    }

    @Test
    void findAllByLaundryId() {
        // Arrange
        int reviewCount = 5;
        Integer laundryId = 1;
        Laundry targetLaundry = createReviewTargetLaundry(12345);
        List<ReviewEntity> reviews = getReviewEntityDummy(reviewCount, laundryId, 1);
        when(laundryFindService.findById(laundryId)).thenReturn(Optional.ofNullable(targetLaundry));
        when(reviewRepository.selectAllByLaundryId(laundryId)).thenReturn(reviews);

        // Act
        List<Review> result = reviewService.findAllByLaundryId(laundryId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewCount, result.size());
    }

    @Test
    void findAllByLaundryId_WhenLaundryNotFound() {
        // Arrange
        Integer laundryId = 1;
        when(laundryFindService.findById(laundryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reviewService.findAllByLaundryId(laundryId);
        });
    }

    @Test
    void upload() {
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
        when(reviewRepository.selectByLaundryIdAndWriterIdAndVisitDate(
                request.getLaundryId(),
                writerId,
                request.getVisitDate())
        ).thenReturn(null);
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

        Laundry laundry = createReviewTargetLaundry(request.getLaundryId());
        when(laundryFindService.findById(request.getLaundryId())).thenReturn(Optional.ofNullable(laundry));
        when(reviewRepository.selectById(createdReviewId)).thenReturn(created);

        // Act
        Review result = reviewService.upload(writerId, request);

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
    void upload_LaundryIsNotFound() {
        // Arrange
        Integer writerId = 1;
        ReviewUploadRequest request = ReviewUploadRequest.builder()
                .laundryId(11254)
                .rating(5)
                .contents("리뷰 내용입니다.")
                .visitDate(LocalDate.of(2022, 9, 6))
                .build();

        when(laundryFindService.findById(request.getLaundryId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reviewService.upload(writerId, request);
        });
    }

    @Test
    void upload_ResourceAlreadyExistsException() {
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

        Laundry laundry = createReviewTargetLaundry(124);
        when(laundryFindService.findById(request.getLaundryId())).thenReturn(Optional.of(laundry));
        when(reviewRepository.selectByLaundryIdAndWriterIdAndVisitDate(
                request.getLaundryId(),
                writerId,
                request.getVisitDate())
        ).thenReturn(exists);

        // Act & Assert
        assertThrows(ResourceAlreadyExistsException.class, () -> reviewService.upload(writerId, request));
    }

    private Laundry createReviewTargetLaundry(Integer laundryId) {
        return Laundry.builder()
                .laundryId(laundryId)
                .name("리뷰 테스트 빨래방")
                .build();
    }

    private List<ReviewEntity> getReviewEntityDummy(int dummyCount, Integer laundryId, Integer writerId) {
        Random random = new Random();
        List<ReviewEntity> reviews = new ArrayList<>();
        for (int i = 0; i < dummyCount; i++) {
            ReviewEntity entity = ReviewEntity.builder()
                    .laundryId(laundryId)
                    .writerId(writerId)
                    .rating(random.nextInt(5))
                    .contents("testReviewContents")
                    .visitDate(LocalDate.now())
                    .build();
            reviews.add(entity);
        }
        return reviews;
    }
}