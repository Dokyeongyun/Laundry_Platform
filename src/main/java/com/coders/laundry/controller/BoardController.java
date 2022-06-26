package com.coders.laundry.controller;

import com.coders.laundry.domain.entity.CategoryEntity;
import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.domain.entity.PostEntity;
import com.coders.laundry.dto.CategoryDTO;
import com.coders.laundry.repository.BoardRepository;
import com.coders.laundry.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

}
