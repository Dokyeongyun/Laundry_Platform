package com.coders.laundry.repository;

import com.coders.laundry.configuration.MyBatisConfig;
import com.coders.laundry.domain.entity.ReviewEntity;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

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
        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(1)
                .writerId(1)
                .contents("이 빨래방은 정말.....")
                .rating(5)
                .build();

        reviewRepository.insert(entity);

        // Act
        ReviewEntity result = reviewRepository.selectById(entity.getReviewId());

        // Assert
        assertNotNull(result);
        assertEquals(entity.getReviewId(), result.getReviewId());
        assertEquals(entity.getLaundryId(), result.getLaundryId());
        assertEquals(entity.getWriterId(), result.getWriterId());
        assertEquals(entity.getContents(), result.getContents());
        assertEquals(entity.getRating(), result.getRating());
        assertNotNull(result.getCreateDate());
        assertNotNull(result.getUpdateDate());
    }

    @Test
    void deleteById() {
        // Arrange
        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(1)
                .writerId(1)
                .contents("이 빨래방은 정말.....")
                .rating(5)
                .build();

        reviewRepository.insert(entity);

        // Act
        int result = reviewRepository.deleteById(entity.getReviewId());

        // Assert
        assertEquals(1, result);
    }

}