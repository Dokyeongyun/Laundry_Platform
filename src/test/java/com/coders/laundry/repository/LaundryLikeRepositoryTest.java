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

    @Test
    void selectById() {
        //사전세팅: member_id = 1, laundry_id = 1인 LaundryLikeEntity 한 개 생성
        LaundryLikeEntity expected = LaundryLikeEntity.builder()
                .memberId(1)
                .laundryId(1)
                .build();

        laundryLikeRepository.insert(expected); //laundry_like테이블에 삽입.

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
        LaundryLikeEntity laundryLikeEntity = laundryLikeRepository.selectById(1);

        laundryLikeEntity.setMemberId(3);

        int result = laundryLikeRepository.update(laundryLikeEntity);

        assertEquals(1, result);
        int id = laundryLikeEntity.getLaundryLikeId();
        LaundryLikeEntity actual = laundryLikeRepository.selectById(id);
        assertEquals(laundryLikeEntity, actual);
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
        assertEquals(1, result);// result가 0으로 test 실패.
        assertNull(laundryLikeRepository.selectById(id));
    }
}