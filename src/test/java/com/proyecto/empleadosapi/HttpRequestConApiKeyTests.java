package com.proyecto.empleadosapi;

import com.proyecto.empleadosapi.controller.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestConApiKeyTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Test
    public void testVerficiarEndPointAliveConApiKeyValida() throws Exception {
        this.mockMvc.perform(get("/employee/alive")
                .header("X-API-KEY", "Baeldung"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testVerficiarEndPointAliveConApiKeyInvalida() throws Exception {

        try {
            this.mockMvc.perform(get("/employee/alive")
                    .header("X-API-KEY", "wqewqewqeqeqweqw"))
                    .andExpect(status().isOk())
                    .andExpect(content().string("API Key inválida"));
        } catch (Exception e) {

        }
    }
}
