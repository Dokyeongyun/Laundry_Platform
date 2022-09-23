package com.coders.laundry.repository;

import com.coders.laundry.configuration.MyBatisConfig;
import com.coders.laundry.domain.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisTest
@ActiveProfiles("dev")
@Import(MyBatisConfig.class)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void insert() {
        // Arrange
        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(1)
                .writerId(1)
                .contents("이 빨래방은 정말.....")
                .rating(5)
                .visitDate(LocalDate.of(2022, 9, 6))
                .build();

        // Act
        int result = reviewRepository.insert(entity);

        // Assert
        assertEquals(1, result);
        assertNotNull(entity.getReviewId());
    }

    @Test
    void selectById() {
        // Arrange
        ReviewEntity entity = insertReviewEntity(1, 1);

        // Act
        ReviewEntity result = reviewRepository.selectById(entity.getReviewId());

        // Assert
        assertNotNull(result);
        assertEquals(entity.getReviewId(), result.getReviewId());
        assertEquals(entity.getLaundryId(), result.getLaundryId());
        assertEquals(entity.getWriterId(), result.getWriterId());
        assertEquals(entity.getContents(), result.getContents());
        assertEquals(entity.getRating(), result.getRating());
        assertEquals(entity.getVisitDate(), result.getVisitDate());
        assertEquals(entity.getCreateDate(), result.getCreateDate());
        assertEquals(entity.getUpdateDate(), result.getUpdateDate());
    }

    @Test
    void deleteById() {
        // Arrange
        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(1)
                .writerId(1)
                .contents("이 빨래방은 정말.....")
                .rating(5)
                .visitDate(LocalDate.of(2022, 9, 6))
                .build();

        reviewRepository.insert(entity);

        // Act
        int result = reviewRepository.deleteById(entity.getReviewId());

        // Assert
        assertEquals(1, result);
    }

    @Test
    void selectByLaundryIdAndWriterId() {
        // Arrange
        ReviewEntity entity = insertReviewEntity(1, 1);

        // Act
        ReviewEntity result = reviewRepository.selectByLaundryIdAndWriterIdAndVisitDate(
                entity.getLaundryId(),
                entity.getWriterId(),
                entity.getVisitDate()
        );

        // Assert
        assertNotNull(result);
        assertEquals(entity.getReviewId(), result.getReviewId());
        assertEquals(entity.getLaundryId(), result.getLaundryId());
        assertEquals(entity.getWriterId(), result.getWriterId());
        assertEquals(entity.getContents(), result.getContents());
        assertEquals(entity.getRating(), result.getRating());
        assertEquals(entity.getVisitDate(), result.getVisitDate());
        assertEquals(entity.getCreateDate(), result.getCreateDate());
        assertEquals(entity.getUpdateDate(), result.getUpdateDate());
    }

    @Test
    void selectAllByLaundryId() {
        // Arrange
        int reviewCount = 10;
        Integer laundryId = 2;
        for (int i = 0; i < reviewCount; i++) {
            insertReviewEntity(laundryId, i + 1);
        }

        // Act
        List<ReviewEntity> result = reviewRepository.selectAllByLaundryId(laundryId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewCount, result.size());
        for (ReviewEntity reviewEntity : result) {
            assertEquals(laundryId, reviewEntity.getLaundryId());
        }
    }

    private ReviewEntity insertReviewEntity(Integer laundryId, Integer writerId) {
        LocalDate visitDate = LocalDate.now();
        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(laundryId)
                .writerId(writerId)
                .contents("testContents")
                .rating(5)
                .visitDate(visitDate)
                .build();

        reviewRepository.insert(entity);

        return entity;
    }

}