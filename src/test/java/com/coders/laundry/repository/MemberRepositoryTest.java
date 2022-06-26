package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void selectById() {
    }

    @Test
    void insert() {
        // Arrange
        MemberEntity expected = MemberEntity.builder()
                .phoneNum("01047504911")
                .password("1234567890")
                .nickname("ㅇㅇㅇ")
                .birthday(LocalDate.of(1998, 8, 3))
                .gender("F")
                .build();

        // Act
        int result = memberRepository.insert(expected);

        // Assert
        assertEquals(1, result);

        int id = expected.getMemberId();
        MemberEntity actual = memberRepository.selectById(id);
        assertEquals(expected.getPhoneNum(), actual.getPhoneNum());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getNickname(), actual.getNickname());
        assertEquals(expected.getBirthday(), actual.getBirthday());
        assertEquals(expected.getGender(), actual.getGender());
        assertFalse(actual.isAutoLoginYn());
        assertNotNull(actual.getJoinDate());
        assertNull(actual.getWithdrawalDate());

    }

    @Test
    void update(){
        // Arrange
        MemberEntity member = memberRepository.selectById(3);

        member.setNickname("도경윤"); //사전 세팅
        member.setNickname("도이소"); //닉네임 변경(업데이트)

        // Act
        int result = memberRepository.update(member);

        // Assert
        assertEquals(1, result);
        assertEquals("도이소",member.getNickname());
    }

    @Test
    void delete(){
        //Arrange
        MemberEntity member = MemberEntity.builder()
                .phoneNum("01012342222")
                .password("12345yuin25")
                .nickname("땡땡땡")
                .birthday(LocalDate.of(1998, 8,3))
                .gender("F")
                .build();

        int insertedCount = memberRepository.insert(member);
        assertEquals(1, insertedCount);

        int generatedMemberId = member.getMemberId();

        //Act
        int result = memberRepository.delete(generatedMemberId);

        //Assert
        assertEquals(1, result);
        MemberEntity deleted = memberRepository.selectById(generatedMemberId);
        assertNull(deleted);
    }

}