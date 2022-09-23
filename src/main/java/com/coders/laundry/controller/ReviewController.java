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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final TokenManagerService tokenManagerService;

    @GetMapping(value = "/laundries/{laundryId}/reviews",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findLaundryReviewsByLaundryId(@PathVariable Integer laundryId) {
        List<Review> reviews = reviewService.findAllByLaundryId(laundryId);
        return ResponseEntity.ok().body(reviews);
    }

    @PostMapping(value = "/laundries/{laundryId}/reviews",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadLaundryReview(
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
        Review createdReview = reviewService.upload(memberId, reviewUploadRequest);

        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{reviewId}")
                        .buildAndExpand(createdReview.getReviewId())
                        .toUri())
                .body(createdReview);
    }

}
