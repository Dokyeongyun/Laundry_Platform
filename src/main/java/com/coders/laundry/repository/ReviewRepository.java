package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewRepository {

    int insert(ReviewEntity review);

}
