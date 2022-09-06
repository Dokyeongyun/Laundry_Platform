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
        int insertedRowCount = reviewRepository.insert(entity);

        // Assert
        assertEquals(1, insertedRowCount);
        assertNotNull(entity.getReviewId());
    }

}