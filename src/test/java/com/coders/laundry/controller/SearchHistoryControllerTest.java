package com.coders.laundry.controller;

import com.coders.laundry.domain.exceptions.NotAuthorizedException;
import com.coders.laundry.dto.Page;
import com.coders.laundry.dto.Pageable;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.dto.SearchHistoryRegisterRequest;
import com.coders.laundry.service.SearchHistoryService;
import com.coders.laundry.service.TokenManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(SearchHistoryController.class)
class SearchHistoryControllerTest {

    @MockBean
    private SearchHistoryService searchHistoryService;

    @MockBean
    private TokenManagerService tokenManagerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void inquirySearchKeywords_OK() throws Exception {
        // Arrange
        String token = "Bearer test";
        int memberId = 1;

        Pageable pageable = new Pageable(0, 20, "created", "desc");

        SearchHistory history = SearchHistory.builder()
                .searchHistoryId(1)
                .keyword("테스트")
                .type("laundry")
                .createdAt(LocalDateTime.now())
                .build();
        List<SearchHistory> list = new ArrayList<>(List.of(history));
        Page<SearchHistory> expected = new Page<>(list.size(), pageable, list);

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        when(searchHistoryService.findCountByMemberId(memberId)).thenReturn(list.size());
        when(searchHistoryService.findPageByMemberId(memberId, pageable)).thenReturn(expected);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .queryParam("offset", String.valueOf(pageable.getOffset()))
                .queryParam("limit", String.valueOf(pageable.getLimit()))
                .queryParam("sort", pageable.getSort())
                .queryParam("sortType", pageable.getSortType())
        );

        // Assert
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("totalCount").exists())
                .andExpect(jsonPath("offset").exists())
                .andExpect(jsonPath("limit").exists())
                .andExpect(jsonPath("sort").exists())
                .andExpect(jsonPath("sortType").exists())
                .andExpect(jsonPath("list").exists());
    }

    @Test
    void inquirySearchKeywords_BadRequest() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(true);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .queryParam("offset", "0")
                .queryParam("limit", "20")
                .queryParam("sort", "invalidSort")
                .queryParam("sortType", "desc")
        );

        // Assert
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void inquirySearchKeywords_Unauthorized() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(false);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .queryParam("offset", "0")
                .queryParam("limit", "20")
                .queryParam("sort", "created")
                .queryParam("sortType", "desc")
        );

        // Assert
        actions.andExpect(status().isUnauthorized());
    }

    @Test
    void saveSearchHistory_TokenIsNotNull() throws Exception {
        // Arrange
        String token = "Bearer test";
        int memberId = 1;
        int searchHistoryId = 1;
        String keyword = "테스트";
        String type = "laundry";

        SearchHistoryRegisterRequest request = new SearchHistoryRegisterRequest(keyword, type);

        SearchHistory searchHistory = SearchHistory.builder()
                .searchHistoryId(searchHistoryId)
                .keyword(keyword)
                .type(type)
                .createdAt(LocalDateTime.now())
                .build();

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        when(searchHistoryService.save(memberId, request)).thenReturn(searchHistory);

        String requestBody = objectMapper.writeValueAsString(request);

        // Act
        ResultActions actions = mockMvc.perform(post("/api/search/histories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .content(requestBody)
        );

        // Assert
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("searchHistoryId").value(searchHistoryId))
                .andExpect(jsonPath("keyword").value(keyword))
                .andExpect(jsonPath("type").value(type))
                .andExpect(jsonPath("createdAt").exists());
    }

    @Test
    void removeSearchHistory() throws Exception {
        // Arrange
        int memberId = 1;
        Integer searchHistoryId = 1;
        String token = "Bearer test";

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        doNothing().when(searchHistoryService).remove(memberId, searchHistoryId);

        String requestBody = objectMapper.writeValueAsString(searchHistoryId);

        // Act
        ResultActions actions = mockMvc.perform(delete("/api/search/histories/{searchHistoryId}", searchHistoryId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .content(requestBody)
        );

        // Assert
        actions.andExpect(status().isNoContent());
    }

    @Test
    void removeSearchHistory_Forbidden() throws Exception {
        // Arrange
        int memberId = 1;
        Integer searchHistoryId = 1;
        String token = "Bearer test";

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        doThrow(new NotAuthorizedException())
                .when(searchHistoryService)
                .remove(memberId, searchHistoryId);

        // Act
        ResultActions actions = mockMvc.perform(delete("/api/search/histories/{searchHistoryId}", searchHistoryId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
        );

        // Assert
        actions.andExpect(status().isForbidden());
    }
}