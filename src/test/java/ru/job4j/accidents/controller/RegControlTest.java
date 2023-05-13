package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.Main;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.RegService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class RegControlTest {
    @MockBean
    private RegService regService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void regPage() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/reg"));
    }

    @Test
    @WithMockUser
    void regSave() throws Exception {
        this.mockMvc.perform(post("/reg")
                        .param("id", "1")
                        .param("username", "Igor"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<User> arg = ArgumentCaptor.forClass(User.class);
        verify(regService).save(arg.capture());
        assertEquals("Igor", arg.getValue().getUsername());
    }
}