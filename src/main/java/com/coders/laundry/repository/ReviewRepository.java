package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ReviewRepository {

    int insert(ReviewEntity review);

    ReviewEntity selectById(@Param("reviewId") Integer reviewId);

    int deleteById(@Param("reviewId") Integer reviewId);

    ReviewEntity selectByLaundryIdAndWriterId(@Param("laundryId") Integer laundryId,
                                              @Param("writerId") Integer writerId);

}
