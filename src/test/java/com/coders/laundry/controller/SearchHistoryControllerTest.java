package com.coders.laundry.controller;

import com.coders.laundry.domain.exceptions.NotAuthorizedException;
import com.coders.laundry.dto.SearchHistory;
import com.coders.laundry.dto.SearchHistoryRegisterRequest;
import com.coders.laundry.dto.SearchHistoryRemoveRequest;
import com.coders.laundry.service.SearchHistoryService;
import com.coders.laundry.service.TokenManagerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class SearchHistoryControllerTest {

    private SearchHistoryService searchHistoryService;

    private TokenManagerService tokenManagerService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void before() {
        searchHistoryService = mock(SearchHistoryService.class);
        this.tokenManagerService = mock(TokenManagerService.class);
        SearchHistoryController searchHistoryController
                = new SearchHistoryController(searchHistoryService, tokenManagerService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchHistoryController).build();
    }

    @Test
    void inquirySearchKeywords_OK() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(true);

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
        when(searchHistoryService.saveSearchHistory(memberId, request)).thenReturn(searchHistory);

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
        int searchHistoryId = 1;
        String token = "Bearer test";

        SearchHistoryRemoveRequest request = new SearchHistoryRemoveRequest(searchHistoryId);

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        doNothing().when(searchHistoryService).removeSearchHistory(memberId, request);

        String requestBody = objectMapper.writeValueAsString(request);

        // Act
        ResultActions actions = mockMvc.perform(delete("/api/search/histories")
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
        int searchHistoryId = 1;
        String token = "Bearer test";

        SearchHistoryRemoveRequest request = new SearchHistoryRemoveRequest(searchHistoryId);

        when(tokenManagerService.verify(token)).thenReturn(true);
        when(tokenManagerService.findMemberId(token)).thenReturn(memberId);
        doThrow(new NotAuthorizedException())
                .when(searchHistoryService)
                .removeSearchHistory(memberId, request);

        String requestBody = objectMapper.writeValueAsString(request);

        // Act
        ResultActions actions = mockMvc.perform(delete("/api/search/histories")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token)
                .content(requestBody)
        );

        // Assert
        actions.andExpect(status().isForbidden());
    }
}