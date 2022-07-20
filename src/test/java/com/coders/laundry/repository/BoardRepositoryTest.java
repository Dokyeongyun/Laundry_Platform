package com.coders.laundry.repository;

import com.coders.laundry.domain.entity.CategoryEntity;
import com.coders.laundry.domain.entity.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("dev")
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void categoryList() {
        List<CategoryEntity> result = boardRepository.categoryList();
        assertNotNull(result);
    }

    @Test
    void selectHotPosts() {
        // todo.
    }

    @Test
    void selectSpecificCategoryPosts() {
        // todo.
    }

    @Test
    void writePost() {
        // Arrange
        PostEntity expected = PostEntity.builder()
                .categoryId(1)
                .writer(1)
                .title("first post")
                .contents("first contents")
                .build();

        // Act
        int result = boardRepository.writePost(expected);

        // Assert
        assertEquals(1, result);

        int generatedPostId = expected.getPostId();
        PostEntity actual = boardRepository.selectById(generatedPostId);
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getContents(), actual.getContents());
        assertNotNull(actual.getCreateDate());
        assertEquals(0, actual.getLikeNum());
        assertNull(actual.getUpdateDate());
    }

    @Test
    void selectById() {
        // Arrange
        PostEntity post = PostEntity.builder()
                .categoryId(1)
                .writer(1)
                .title("first post")
                .contents("first contents")
                .build();
        int writeCount = boardRepository.writePost(post);
        assertEquals(1, writeCount);
        int generatedPostId = post.getPostId();

        // Act
        PostEntity selected = boardRepository.selectById(generatedPostId);

        // Assert
        assertEquals(post.getPostId(), selected.getPostId());
        assertEquals(post.getCategoryId(), selected.getCategoryId());
        assertEquals(post.getWriter(), selected.getWriter());
        assertEquals(post.getTitle(), selected.getTitle());
        assertEquals(post.getContents(), selected.getContents());
    }

    @Test
    void updatePost() {
        // Arrange
        PostEntity post = PostEntity.builder()
                .categoryId(1)
                .writer(1)
                .title("first post")
                .contents("first contents")
                .build();
        int result = boardRepository.writePost(post);
        assertEquals(1, result);
        post.setTitle("update title");
        post.setContents("update contents");

        // Act
        result = boardRepository.updatePost(post);

        // Assert
        assertEquals(1, result);
        assertEquals("update title", post.getTitle());
        assertEquals("update contents", post.getContents());
    }

    @Test
    void deletePost() {
        // Arrange
        PostEntity post = PostEntity.builder()
                .categoryId(1)
                .writer(1)
                .title("temp post")
                .contents("temp contents")
                .build();
        int writeCount = boardRepository.writePost(post);
        assertEquals(1, writeCount);

        int generatedPostId = post.getPostId();

        // Act
        int result = boardRepository.deletePost(generatedPostId);

        // Assert
        assertEquals(1, result);
        PostEntity deleted = boardRepository.selectById(generatedPostId);
        assertNull(deleted);
    }
}
