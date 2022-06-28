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
        LaundryVisitHistoryEntity laundryVisitHistoryEntity = LaundryVisitHistoryEntity.builder()
                .laundryId(1)
                .facilityId(1)
                .memberId(1)
                .build();

        //Act
        int result = laundryVisitHistoryRepository.insert(laundryVisitHistoryEntity);

        //Assert
        assertEquals(1, result);

    }
}