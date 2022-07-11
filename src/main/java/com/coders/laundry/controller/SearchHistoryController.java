package com.coders.laundry.controller;

import com.coders.laundry.dto.ErrorResponse;
import com.coders.laundry.dto.Page;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.service.SearchHistoryService;
import com.coders.laundry.service.TokenManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;
    private final TokenManagerService tokenManagerService;

    private static final List<String> AVAILABLE_SORT_LIST = List.of("created");

    private static final List<String> AVAILABLE_SORT_TYPE_LIST = List.of("asc", "desc");

    @GetMapping(value = "/histories/recency",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recentSearchKeywords(
            @RequestHeader("Authorization") String token,
            @RequestParam int offset,
            @RequestParam int limit,
            @RequestParam String sort,
            @RequestParam(required = false, defaultValue = "asc") String sortType) {

        // TODO verify token value and retrieve user details (ex.memberId)
        if (!tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        // validate parameters value
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

        Pageable pageable = new Pageable(offset, limit, sort, sortType);

        int memberId = tokenManagerService.findMemberId(token);
        int totalCount = searchHistoryService.findRecentSearchKeywordsCountByMemberId(memberId);
        List<SearchHistory> list = searchHistoryService.findRecentSearchKeywordsByMemberId(memberId, pageable);

        Page<SearchHistory> page = new Page<>(totalCount, pageable, list);

        return ResponseEntity.ok().body(page);
    }

}
