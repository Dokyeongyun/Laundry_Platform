package com.coders.laundry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Builder
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewUploadRequest {

    @NotNull
    @Positive
    private Integer laundryId;

    @Range(min = 1L, max = 5L)
    private int rating;

    @NotBlank
    @Length(min = 5, max = 300)
    private String contents;

    @NotNull
    @JsonFormat(pattern = "yyyyMMdd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyyMMdd")
    private LocalDate visitDate;

}
