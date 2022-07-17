package com.coders.laundry.service;

import com.coders.laundry.dto.LocationSearch;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchedLaundry;
import com.coders.laundry.repository.LaundryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryFindService {

    private final LaundryRepository laundryRepository;

    public int findCount(String keyword,
                         LocationSearch locationSearch,
                         String searchMode) {
        // TODO implement
        return 0;
    }

    public List<SearchedLaundry> search(int memberId,
                                        String keyword,
                                        LocationSearch locationSearch,
                                        Pageable pageable,
                                        String searchMode) {
        // TODO implement
        return new ArrayList<>();
    }
}
