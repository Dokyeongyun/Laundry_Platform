package com.coders.laundry.controller;

import com.coders.laundry.dto.*;
import com.coders.laundry.service.ReviewService;
import com.coders.laundry.service.TokenManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final TokenManagerService tokenManagerService;

    @PostMapping(value = "/laundries/{laundryId}/reviews",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token,
            @PathVariable Integer laundryId,
            @Valid @RequestBody ReviewUploadRequest reviewUploadRequest
    ) {
        // TODO verify token value and retrieve user details(ex.memberId) if token is present and validated
        if (token == null || !tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        if (!laundryId.equals(reviewUploadRequest.getLaundryId())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("The path variable 'laundryId' is not equals to request body's 'laundryId' parameter"));
        }

        Integer memberId = tokenManagerService.findMemberId(token);
        Review createdReview = reviewService.save(memberId, reviewUploadRequest);

        return ResponseEntity.ok().body(createdReview);
    }
    
}
