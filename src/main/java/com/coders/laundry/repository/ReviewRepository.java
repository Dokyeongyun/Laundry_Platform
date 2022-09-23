package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface ReviewRepository {

    int insert(ReviewEntity review);

    ReviewEntity selectById(@Param("reviewId") Integer reviewId);

    int deleteById(@Param("reviewId") Integer reviewId);

    ReviewEntity selectByLaundryIdAndWriterIdAndVisitDate(@Param("laundryId") Integer laundryId,
                                                          @Param("writerId") Integer writerId,
                                                          @Param("visitDate") LocalDate visitDate);

    List<ReviewEntity> selectAllByLaundryId(@Param("laundryId") Integer laundryId);

}
