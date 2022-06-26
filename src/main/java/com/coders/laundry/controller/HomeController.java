package com.coders.laundry.controller;

import com.coders.laundry.domain.entity.MemberEntity;
import com.coders.laundry.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @GetMapping("/")
    public String home(Model model) {
        List<MemberEntity> coders = homeService.findAllTeamMemberList();
        model.addAttribute("coders", coders);
        return "home";
    }
}
