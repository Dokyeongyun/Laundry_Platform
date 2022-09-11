package com.coders.laundry.controller;

import com.coders.laundry.service.notify.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notify")
public class NotificationController {

    private final NotificationService service;

    @GetMapping(value = "/fcm")
    public HttpStatus sendPushMessage(
        @RequestParam("memberId") int memberId,
        @RequestParam("totalTime") long totalTimeInMillis
    ) {
        service.send(memberId, totalTimeInMillis);
        return HttpStatus.ACCEPTED;
    }
}
