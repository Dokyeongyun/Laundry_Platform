package com.coders.laundry.controller;

import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Page;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchedLaundry;
import com.coders.laundry.service.LaundryFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laundries")
@RequiredArgsConstructor
public class LaundryController {

    private final LaundryFindService laundryFindService;

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

        // TODO validate input value

        // find searching result
        Pageable pageable = new Pageable(offset, limit, sort, sortType);

        int totalCount = laundryFindService.findCount(keyword, locationSearch, mode);
        List<SearchedLaundry> list = laundryFindService.search(keyword, locationSearch, pageable, mode);

        return ResponseEntity.ok().body(new Page<>(totalCount, pageable, list));
    }
}
