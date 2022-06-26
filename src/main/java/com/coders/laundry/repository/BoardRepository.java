package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.CategoryEntity;
import com.coders.laundry.domain.entity.PostEntity;
import com.coders.laundry.domain.entity.ReviewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BoardRepository {
    List<CategoryEntity> categoryList();
    List<PostEntity> selectHotPost();
    //List<PostEntity> selectSpecificCategoryPost(int categoryId);
}
