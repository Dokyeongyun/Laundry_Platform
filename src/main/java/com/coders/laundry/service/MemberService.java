package com.coders.laundry.service;

import com.coders.laundry.domain.entity.LaundryEntity;
import com.coders.laundry.domain.entity.LaundryLikeEntity;
import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.dto.LaundryLike;
import com.coders.laundry.repository.LaundryLikeRepository;
import com.coders.laundry.repository.LaundryRepository;
import com.coders.laundry.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
@RequiredArgsConstructor
public class MemberService {

    private final LaundryLikeRepository laundryLikeRepository;
    private final MemberRepository memberRepository;
    private final LaundryRepository laundryRepository;
    public ArrayList<LaundryLike> findMyLikes(int memberId){

        ArrayList<LaundryLike> result = new ArrayList<>();

        ArrayList<LaundryLikeEntity> likes = laundryLikeRepository.selectByMember(memberId);
        MemberEntity member = memberRepository.selectById(memberId);

        for(LaundryLikeEntity entity: likes){
            LaundryEntity laundry = laundryRepository.selectById(entity.getLaundryId());
            LaundryLike laundryLike = LaundryLike.builder()
                    .memberId(memberId)
                    .nickname(member.getNickname())
                    .laundryId(laundry.getLaundryId())
                    .laundryName(laundry.getName())
                    .ratingPoint(laundry.getRatingPoint())
                    .reviewCount(laundry.getReviewCount())
                    .build();
            result.add(laundryLike);
        }
        return result;

    }
}
