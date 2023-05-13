package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentDataService;
import ru.job4j.accidents.service.AccidentRuleDataService;
import ru.job4j.accidents.service.AccidentTypeDataService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @MockBean
    AccidentDataService service;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void viewCreateAccident() throws Exception {
        this.mockMvc.perform(get("/accident/addAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/createAccident"));
    }

    @Test
    @WithMockUser
    void whenFormEditDownloadSuccessfully() throws Exception {
        when(service.findById(1)).thenReturn(Optional.of(new Accident()));
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        service,
                        mock(AccidentRuleDataService.class),
                        mock(AccidentTypeDataService.class)
                ))
                .build();
        this.mockMvc.perform(get("/accident/formEditAccident")
                        .param("id", "1")
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accident/editAccident"));
    }

    @Test
    @WithMockUser
    void whenFormEditDownloadError() throws Exception {
        when(service.findById(1)).thenReturn(Optional.empty());
        this.mockMvc = MockMvcBuilders.standaloneSetup(new AccidentController(
                        service,
                        mock(AccidentRuleDataService.class),
                        mock(AccidentTypeDataService.class)
                ))
                .build();
        this.mockMvc.perform(get("/accident/formEditAccident")
                        .param("id", "1")
                ).andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/accident/error"));
    }

    @Test
    @WithMockUser
    void whenSaveSuccess() throws Exception {
        this.mockMvc.perform(post("/accident/saveAccident")
                        .param("id", "1")
                        .param("address", "address")
                        .param("name", "Igor")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> arg = ArgumentCaptor.forClass(Accident.class);
        verify(service).save(arg.capture());
        assertEquals("Igor", arg.getValue().getName());
        assertEquals("address", arg.getValue().getAddress());
    }

    @Test
    @WithMockUser
    void whenUpdateSuccess() throws Exception {
        this.mockMvc.perform(post("/accident/updateAccident")
                        .param("id", "1")
                        .param("address", "address")
                        .param("name", "Igor")
                        .param("rIds", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> arg = ArgumentCaptor.forClass(Accident.class);
        verify(service).update(arg.capture());
        assertEquals("Igor", arg.getValue().getName());
        assertEquals("address", arg.getValue().getAddress());
    }
}