package com.coders.laundry.controller;

import com.coders.laundry.service.SearchHistoryService;
import com.coders.laundry.service.TokenManagerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class)
class SearchHistoryControllerTest {

    private TokenManagerService tokenManagerService;

    private MockMvc mockMvc;

    @BeforeEach
    public void before() {
        SearchHistoryService searchHistoryService = mock(SearchHistoryService.class);
        this.tokenManagerService = mock(TokenManagerService.class);
        SearchHistoryController searchHistoryController
                = new SearchHistoryController(searchHistoryService, tokenManagerService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(searchHistoryController).build();
    }

    @Test
    void recentSearchKeywords_OK() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(true);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories/recency")
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
    void recentSearchKeywords_BadRequest() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(true);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories/recency")
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
    void recentSearchKeywords_Unauthorized() throws Exception {
        // Arrange
        String token = "Bearer test";
        when(tokenManagerService.verify(token)).thenReturn(false);

        // Act
        ResultActions actions = mockMvc.perform(get("/api/search/histories/recency")
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
}