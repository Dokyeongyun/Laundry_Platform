package com.coders.laundry.controller;

import com.coders.laundry.common.validation.SearchHistorySortGroup;
import com.coders.laundry.dto.*;
import com.coders.laundry.service.SearchHistoryService;
import com.coders.laundry.service.TokenManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    private final TokenManagerService tokenManagerService;

    @GetMapping(value = "/histories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> inquiry(
            @RequestHeader("Authorization") String token,
            @Validated(SearchHistorySortGroup.class) Pageable pageable
    ) {

        // TODO verify token value and retrieve user details (ex.memberId)
        if (!tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        int memberId = tokenManagerService.findMemberId(token);
        int totalCount = searchHistoryService.findCountByMemberId(memberId);
        List<SearchHistory> list = searchHistoryService.findByMemberId(memberId, pageable);

        Page<SearchHistory> page = new Page<>(totalCount, pageable, list);

        return ResponseEntity.ok().body(page);
    }

    @PostMapping(value = "/histories",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @RequestHeader(value = "Authorization", required = false) String token,
            @Valid @RequestBody SearchHistoryRegisterRequest searchHistoryRegisterRequest
    ) {
        // TODO verify token value and retrieve user details if token is present(ex.memberId)
        if (token != null && !tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        int memberId = token == null ? -1 : tokenManagerService.findMemberId(token);
        SearchHistory searchHistory = searchHistoryService.save(memberId, searchHistoryRegisterRequest);

        return ResponseEntity.ok().body(searchHistory);
    }

    @DeleteMapping(value = "/histories/{searchHistoryId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> remove(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer searchHistoryId
    ) {
        // TODO verify token value and retrieve user details if token is present(ex.memberId)
        if (token != null && !tokenManagerService.verify(token)) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Please check authorization token value."));
        }

        int memberId = tokenManagerService.findMemberId(token);
        searchHistoryService.remove(memberId, searchHistoryId);

        return ResponseEntity.noContent().build();
    }

}
