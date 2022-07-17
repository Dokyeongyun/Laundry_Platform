package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.Point;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class LaundryRepositoryTest {

    @Autowired
    private LaundryRepository laundryRepository;

    @Test
    void insert() {
        // Arrange
        LaundryEntity expected = LaundryEntity.builder()
                .name("크린토피아 코인워시 양재점")
                .jibunAddress("양재동 17-21")
                .jibunAddressDetail("1층")
                .doroAddress("서울 서초구 강남대로30길 28")
                .doroAddressDetail("1층")
                .latitude(37.4820556875)
                .longitude(127.0388680845)
                .partnership(false)
                .build();

        // Act
        // * this method will be return updated row count
        int result = laundryRepository.insert(expected);

        // Assert
        assertEquals(1, result);

        int generatedLaundryId = expected.getLaundryId();
        LaundryEntity actual = laundryRepository.selectById(generatedLaundryId);
        assertEquals(expected, actual);
    }

    @Test
    void selectById() {
        // Act
        LaundryEntity result = laundryRepository.selectById(1);

        // Assert
        assertEquals(1, result.getLaundryId());
        assertEquals("화이트365 개포점", result.getName());
        assertEquals("개포동 1259", result.getJibunAddress());
        assertEquals("한영빌딩 1층 103호", result.getJibunAddressDetail());
        assertEquals("서울 강남구 개포로 235", result.getDoroAddress());
        assertEquals("한영빌딩 1층 103호", result.getDoroAddressDetail());
        assertEquals(37.4790788271, result.getLatitude());
        assertEquals(127.048425603, result.getLongitude());
        assertTrue(result.isPartnership());
    }

    @Test
    void update() {
        // Arrange
        LaundryEntity laundry = laundryRepository.selectById(1);

        // Pre-setting that laundry's partnership is true
        laundry.setPartnership(false);

        // And update laundry's partnership to false
        laundry.setPartnership(false);

        // Act
        // * this method will be return updated row count
        int result = laundryRepository.update(laundry);

        // Assert
        assertEquals(1, result);
        assertFalse(laundry.isPartnership());
    }

    @Test
    void delete() {
        // Arrange
        LaundryEntity laundry = LaundryEntity.builder()
                .name("테스트 빨래방")
                .jibunAddress("양재동 17-21")
                .jibunAddressDetail("1층")
                .doroAddress("서울 서초구 강남대로30길 28")
                .doroAddressDetail("1층")
                .latitude(37.4820556875)
                .longitude(127.0388680845)
                .partnership(false)
                .build();

        int insertedCount = laundryRepository.insert(laundry);
        assertEquals(1, insertedCount);

        int generatedLaundryId = laundry.getLaundryId();

        // Act
        // * this method will be return updated row count
        int result = laundryRepository.delete(generatedLaundryId);

        // Assert
        assertEquals(1, result);
        LaundryEntity deleted = laundryRepository.selectById(generatedLaundryId);
        assertNull(deleted);
    }

    @Test
    void selectAll() {
        // Act
        List<LaundryEntity> result = laundryRepository.selectAll();

        // Assert
        assertNotNull(result);
    }

    @Test
    void selectAddressSearchListCount() {
        // Arrange
        String keyword = "경기도";
        LocationSearch locationSearch = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);

        // Act
        int result = laundryRepository.selectAddressSearchListCount(keyword, locationSearch);

        // Assert
        assertTrue(result >= 0);
    }

    @Test
    void selectAddressSearchList() {
        // Arrange
        String keyword = "경기도";
        LocationSearch locationSearch = new LocationSearch(new Point(37.3467219612, 126.6894764563), 10000);
        Pageable pageable = new Pageable(0, 3, "review", "desc");

        // Act
        List<LaundryEntity> result = laundryRepository.selectAddressSearchList(keyword, locationSearch, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= pageable.getLimit());
    }

    @Test
    void selectKeywordSearchListCount() {
        // Arrange
        String keyword = "크리닝";
        LocationSearch locationSearch = new LocationSearch(new Point(37.6494184000, 127.2478329000), 20000);

        // Act
        int result = laundryRepository.selectKeywordSearchListCount(keyword, locationSearch);

        // Assert
        assertTrue(result >= 0);
    }

    @Test
    void selectKeywordSearchList() {
        // Arrange
        String keyword = "크리닝";
        LocationSearch locationSearch = new LocationSearch(new Point(37.6494184000, 127.2478329000), 20000);
        Pageable pageable = new Pageable(0, 3, "review", "desc");

        // Act
        List<LaundryEntity> result = laundryRepository.selectKeywordSearchList(keyword, locationSearch, pageable);

        // Assert
        assertNotNull(result);
        assertTrue(result.size() <= pageable.getLimit());
    }
}