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
    List<PostEntity> selectHotPosts();
    List<PostEntity> selectSpecificCategoryPosts(int categoryId);
    int writePost(PostEntity post);
    PostEntity selectById(int postId);
    int updatePost(PostEntity post);
    int deletePost(int postId);
}
