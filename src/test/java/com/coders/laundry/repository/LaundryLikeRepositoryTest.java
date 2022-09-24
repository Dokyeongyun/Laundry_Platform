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
        int id = expected.getLaundryLikeId();
        LaundryLikeEntity actual = laundryLikeRepository.selectById(id);
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertEquals(expected.getLaundryId(), actual.getLaundryId());
        assertNotNull(actual.getLikeDate());
    }

    @Test
    void selectById() {
        LaundryLikeEntity expected = LaundryLikeEntity.builder()
                .memberId(1)
                .laundryId(1)
                .build();

        laundryLikeRepository.insert(expected);

        //Act
        LaundryLikeEntity like =laundryLikeRepository.selectById(1);

        //Assert
        assertEquals(1, like.getMemberId());
        assertEquals(1, like.getLaundryId());
        assertNotNull(like.getLikeDate());
    }

    @Test
    void update(){
        //Arrange
        LaundryLikeEntity laundryLikeEntity = LaundryLikeEntity.builder()
                .memberId(1)
                .laundryId(1)
                .build();
        int insertCount = laundryLikeRepository.insert(laundryLikeEntity);
        assertEquals(1, insertCount);

        laundryLikeEntity.setLaundryId(3);

        //Act
        int result = laundryLikeRepository.update(laundryLikeEntity);

        assertEquals(1, result);
        int id = laundryLikeEntity.getLaundryLikeId();
        LaundryLikeEntity actual = laundryLikeRepository.selectById(id);
        assertEquals(3, actual.getLaundryId());
    }

    @Test
    void delete(){
        //Arrange
        LaundryLikeEntity laundryLikeEntity = LaundryLikeEntity.builder()
                .memberId(5)
                .laundryId(1)
                .build();

        int insertCount = laundryLikeRepository.insert(laundryLikeEntity);
        assertEquals(1, insertCount);

        int id = laundryLikeEntity.getLaundryLikeId();

        //Act
        int result = laundryLikeRepository.delete(id);
        assertEquals(1, result);
        assertNull(laundryLikeRepository.selectById(id));
    }
}