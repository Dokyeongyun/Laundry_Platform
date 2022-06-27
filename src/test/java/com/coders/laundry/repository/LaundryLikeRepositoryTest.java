package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryLikeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("dev")
class LaundryLikeRepositoryTest {

    @Autowired
    private LaundryLikeRepository laundryLikeRepository;
    @Test
    void insert() {
        //Arrange
        LaundryLikeEntity expected = LaundryLikeEntity.builder()
                .memberId(1)
                .laundryId(1)
                .build();

        //Act
        int result = laundryLikeRepository.insert(expected);

        //Assert
        assertEquals(1, result);
        //실제값이랑 비교하는 건 selectById 만든 후에 추가할 예정.
    }
}