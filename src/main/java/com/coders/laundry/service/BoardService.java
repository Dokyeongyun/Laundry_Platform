package com.coders.laundry.service;

import com.coders.laundry.domain.entity.CategoryEntity;
import com.coders.laundry.domain.entity.PostEntity;
import com.coders.laundry.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }

    public List<CategoryEntity> findAllCategory(){
        return boardRepository.selectAllCategory();
    }

    public List<PostEntity> findHotPost(){
        return boardRepository.selectHotPost();
    }
}
