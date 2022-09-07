package com.coders.laundry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUploadRequest {

    @NotNull
    private Integer laundryId;

    @Range(min = 1L, max = 5L)
    private int rating;

    @NotNull
    @Length(min = 5, max = 300)
    private String contents;

    @JsonFormat(pattern = "yyyyMMdd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyyMMdd")
    private LocalDate visitDate;

}
