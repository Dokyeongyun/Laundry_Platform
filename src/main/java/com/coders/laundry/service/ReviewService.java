package com.coders.laundry.service;

import com.coders.laundry.domain.entity.ReviewEntity;
import com.coders.laundry.domain.exceptions.ResourceAlreadyExistsException;
import com.coders.laundry.dto.Review;
import com.coders.laundry.dto.ReviewUploadRequest;
import com.coders.laundry.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    @Validated
    public Review save(@NotNull Integer writerId, @Valid ReviewUploadRequest request) {
        // TODO Validate the existence of members

        ReviewEntity existedReview = reviewRepository
                .selectByLaundryIdAndWriterId(request.getLaundryId(), writerId);
        if (existedReview != null) {
            throw new ResourceAlreadyExistsException("Review");
        }

        ReviewEntity entity = ReviewEntity.builder()
                .laundryId(request.getLaundryId())
                .writerId(writerId)
                .rating(request.getRating())
                .contents(request.getContents())
                .visitDate(request.getVisitDate())
                .build();

        reviewRepository.insert(entity);

        ReviewEntity created = reviewRepository.selectById(entity.getReviewId());
        return ReviewEntity.toDto(created);
    }

}
