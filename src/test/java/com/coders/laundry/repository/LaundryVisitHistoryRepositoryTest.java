package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryVisitHistoryEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("dev")
class LaundryVisitHistoryRepositoryTest {

    @Autowired
    private LaundryVisitHistoryRepository laundryVisitHistoryRepository;
    @Test
    void insert() {
        //Arrange
        LaundryVisitHistoryEntity expected = LaundryVisitHistoryEntity.builder()
                .laundryId(1)
                .facilityId(1)
                .memberId(1)
                .build();

        //Act
        int result = laundryVisitHistoryRepository.insert(expected);

        //Assert
        assertEquals(1, result);
        int id =expected.getVisitHistorySeq();
        LaundryVisitHistoryEntity actual = laundryVisitHistoryRepository.selectById(id);
        assertEquals(expected.getLaundryId(), actual.getLaundryId());
        assertEquals(expected.getFacilityId(), actual.getFacilityId());
        assertEquals(expected.getMemberId(), actual.getMemberId());
        assertNotNull(actual.getVisitDate());

    }

    @Test
    void selectById(){
        //Arrange
        LaundryVisitHistoryEntity result = laundryVisitHistoryRepository.selectById(1);

        //Assert
        assertEquals(1, result.getLaundryId());
        assertEquals(1, result.getFacilityId());
        assertEquals(1, result.getMemberId());
        assertNotNull(result.getVisitDate());
    }

    @Test
    void update(){
        //Arrange
        LaundryVisitHistoryEntity laundryVisitHistoryEntity = laundryVisitHistoryRepository.selectById(1);
        // 이용시설 번호 변경
        laundryVisitHistoryEntity.setFacilityId(2);

        //Act
        int result = laundryVisitHistoryRepository.update(laundryVisitHistoryEntity);

        //Assert
        assertEquals(1, result);
        assertEquals(2, laundryVisitHistoryEntity.getFacilityId());
    }

    @Test
    void delete(){
        //Arrange
        LaundryVisitHistoryEntity laundryVisitHistoryEntity = LaundryVisitHistoryEntity.builder()
                .laundryId(1)
                .facilityId(2)
                .memberId(4)
                .build();

        int insertCount = laundryVisitHistoryRepository.insert(laundryVisitHistoryEntity);
        assertEquals(1, insertCount);

        int id = laundryVisitHistoryEntity.getVisitHistorySeq();

        //Act
        int result = laundryVisitHistoryRepository.delete(id);
        assertEquals(1, result);
        assertNull(laundryVisitHistoryRepository.selectById(id));
    }
}