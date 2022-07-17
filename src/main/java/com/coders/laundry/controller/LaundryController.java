package com.coders.laundry.controller;

import com.coders.laundry.dto.*;
import com.coders.laundry.service.LaundryFindService;
import com.coders.laundry.service.TokenManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laundries")
@RequiredArgsConstructor
public class LaundryController {

    private static final List<String> AVAILABLE_SORT_LIST = List.of("distance", "review", "point");

    private static final List<String> AVAILABLE_SORT_TYPE_LIST = List.of("asc", "desc");

    private static final List<String> AVAILABLE_SEARCH_MODE_LIST = List.of("address", "keyword");

    private final LaundryFindService laundryFindService;

    private final TokenManagerService tokenManagerService;

    @GetMapping(value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam String keyword,
            LocationSearch locationSearch,
            @RequestParam String mode,
            @RequestParam int offset,
            @RequestParam int limit,
            @RequestParam String sort,
            @RequestParam(required = false, defaultValue = "asc") String sortType
    ) {

        // validate parameters value
        if (keyword.length() > 30) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("'keyword' parameter value is not valid. 'keyword' parameter values range from 0 to 30."));
        }

        if (!AVAILABLE_SEARCH_MODE_LIST.contains(mode)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(String.format(
                            "'mode' parameter value is not valid. You can use this options %s.", AVAILABLE_SEARCH_MODE_LIST)));
        }

        if (!AVAILABLE_SORT_LIST.contains(sort)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(String.format(
                            "'sort' parameter value is not valid. You can use this options %s.", AVAILABLE_SORT_LIST)));
        }

        if (!AVAILABLE_SORT_TYPE_LIST.contains(sortType)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(String.format(
                            "'sortType' parameter value is not valid. You can use this options %s.", AVAILABLE_SORT_TYPE_LIST)));
        }

        // TODO verify token value and retrieve user details (ex.memberId)
        int memberId = -1;
        if (token != null && tokenManagerService.verify(token)) {
            memberId = tokenManagerService.findMemberId(token);
        }

        // find searching result
        Pageable pageable = new Pageable(offset, limit, sort, sortType);

        int totalCount = laundryFindService.findCount(keyword, locationSearch, mode);
        List<SearchedLaundry> list = laundryFindService.search(memberId, keyword, locationSearch, pageable, mode);

        return ResponseEntity.ok().body(new Page<>(totalCount, pageable, list));
    }
}
