package com.coders.laundry.controller;

import com.coders.laundry.dto.*;
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

    // TODO convert to enum class below constants
    private static final List<String> AVAILABLE_SORT_LIST = List.of("created");

    private static final List<String> AVAILABLE_SORT_TYPE_LIST = List.of("asc", "desc");

    private static final List<String> AVAILABLE_SEARCH_TYPE_LIST = List.of("laundry", "board");

    @GetMapping(value = "/histories/recency",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> recentSearchKeywords(
            @RequestHeader(value = "Authorization", required = true) String token,
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
        int totalCount = searchHistoryService.findSearchHistoriesCountByMemberId(memberId);
        List<SearchHistory> list = searchHistoryService.findSearchHistoriesByMemberId(memberId, pageable);

        Page<SearchHistory> page = new Page<>(totalCount, pageable, list);

        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/histories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveSearchHistory(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody SearchHistoryRegisterRequest searchHistoryRegisterRequest
    ) {
        // TODO verify token value and retrieve user details if token is present(ex.memberId)
        if (token != null && !tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        // validate parameters value
        String keyword = searchHistoryRegisterRequest.getKeyword();
        String type = searchHistoryRegisterRequest.getType();
        if (keyword.length() > 30) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(
                            "'keyword' parameter value is not valid. It's length must be 30 length or less."));
        }

        if (!AVAILABLE_SEARCH_TYPE_LIST.contains(type)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(String.format(
                            "'type' parameter value is not valid. You can use this options %s.",
                            AVAILABLE_SEARCH_TYPE_LIST)));
        }

        int memberId = token == null ? -1 : tokenManagerService.findMemberId(token);
        SearchHistory searchHistory = searchHistoryService.saveSearchHistory(memberId, searchHistoryRegisterRequest);

        return ResponseEntity.ok().body(searchHistory);
    }

}
