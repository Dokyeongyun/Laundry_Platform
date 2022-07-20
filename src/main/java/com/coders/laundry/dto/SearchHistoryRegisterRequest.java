package com.coders.laundry.dto;

import com.coders.laundry.common.validation.ValidEnum;
import com.coders.laundry.domain.enums.SearchHistorySearchType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistoryRegisterRequest {

    @Length(max = 30)
    private String keyword;

    @ValidEnum(enumClass = SearchHistorySearchType.class)
    private String type;
}
