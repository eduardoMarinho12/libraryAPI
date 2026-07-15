package com.library.libraryapi;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryApiIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createsLoanAndReturnsBookCopy() throws Exception {
        Long bookId = createBook();
        Long userId = createUser();

        MvcResult loanResult = mockMvc.perform(post("/api/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "bookId": %d,
                                  "userId": %d
                                }
                                """.formatted(bookId, userId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("ACTIVE")))
                .andExpect(jsonPath("$.book.quantity", is(1)))
                .andReturn();

        Long loanId = JsonTestUtils.extractId(loanResult);

        mockMvc.perform(post("/api/loans/{id}/return", loanId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("RETURNED")))
                .andExpect(jsonPath("$.book.quantity", is(2)));

        mockMvc.perform(get("/api/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity", is(2)));
    }

    @Test
    void exposesOpenApiDocumentation() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title", is("Library API")))
                .andExpect(jsonPath("$.paths['/api/books']").exists())
                .andExpect(jsonPath("$.paths['/api/users']").exists())
                .andExpect(jsonPath("$.paths['/api/loans']").exists());
    }

    private Long createBook() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Clean Code",
                                  "author": "Robert C. Martin",
                                  "publishedYear": 2008,
                                  "quantity": 2
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Clean Code")))
                .andReturn();

        return JsonTestUtils.extractId(result);
    }

    private Long createUser() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Maria Silva",
                                  "email": "maria@email.com",
                                  "phone": "85999990000"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is("maria@email.com")))
                .andReturn();

        return JsonTestUtils.extractId(result);
    }
}
