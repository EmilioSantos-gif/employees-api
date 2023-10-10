package com.proyecto.empleadosapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.empleadosapi.controller.MainController;
import com.proyecto.empleadosapi.model.Employee;
import com.proyecto.empleadosapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeRepository employeeRepositoryMocked;
    private static ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testVerificarEndPointRecuperarEmpleado() throws Exception {
        Employee employee = new Employee("Alexander", "Faucaux", BigDecimal.valueOf(52000), new Date(), "Marketer", "Marketing");

        employeeRepository.save(employee);

        MvcResult result =  this.mockMvc.perform(get("/employee/latest")
                .header("X-API-KEY", "Baeldung"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Employee fetchedEmployee = objectMapper.readValue(json, Employee.class);

        Assertions.assertTrue(fetchedEmployee.equals(employee));
    }

    @Test
    public void testVerficarEndPointPostEmpleado() throws Exception {
        Employee newEmployee = new Employee("Bryan", "Bocio", BigDecimal.valueOf(52000), new Date(), "Developer", "TI");

        String requestJson = new ObjectMapper().writeValueAsString(newEmployee);

        MvcResult result = mockMvc.perform(post("/employee/add")
                        .header("X-API-KEY", "Baeldung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), "Guardado correctamente");
    }

    @Test
    public void testVerificarEndPointActualizaEmpleado() throws Exception {
        Employee newEmployee = new Employee("Bryan", "Bocio", BigDecimal.valueOf(52000), new Date(), "Developer", "TI");

        String requestJson = new ObjectMapper().writeValueAsString(newEmployee);

        mockMvc.perform(put("/employee/1")
                        .header("X-API-KEY", "Baeldung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk()).andReturn();

        MvcResult result =  this.mockMvc.perform(get("/employee/1")
                        .header("X-API-KEY", "Baeldung"))
                        .andExpect(status().isOk())
                        .andReturn();

        String json = result.getResponse().getContentAsString();
        Employee fetchedEmployee = objectMapper.readValue(json, Employee.class);

        Assertions.assertTrue(fetchedEmployee.equals(newEmployee));
    }

    @Test
    public void testVerificarEndPointEliminaEmpleado() throws Exception {
        Employee newEmployee = new Employee("Bryan", "Bocio", BigDecimal.valueOf(52000), new Date(), "Developer", "TI");

        String requestJson = new ObjectMapper().writeValueAsString(newEmployee);

        mockMvc.perform(delete("/employee/26")
                        .header("X-API-KEY", "Baeldung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                        .andExpect(status().isOk()).andReturn();

        MvcResult result =  this.mockMvc.perform(get("/employee/1")
                        .header("X-API-KEY", "Baeldung"))
                        .andExpect(status().isNotFound())
                        .andReturn();
    }
}
