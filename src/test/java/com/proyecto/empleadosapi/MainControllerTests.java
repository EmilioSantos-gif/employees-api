package com.proyecto.empleadosapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.empleadosapi.controller.MainController;
import com.proyecto.empleadosapi.model.Employee;
import com.proyecto.empleadosapi.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testVerificarEndPointRecuperarEmpleado() throws Exception {
        Employee employee = new Employee("Alexander", "Faucaux", BigDecimal.valueOf(52000), new Date(), "Marketer", "Marketing");

        employeeRepository.save(employee);

        MvcResult result =  this.mockMvc.perform(get("/employee/latest")
                .header("X-API-KEY", "Baeldung"))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        Employee fetchedEmployee = new ObjectMapper().readValue(json, Employee.class);

        Assertions.assertTrue(fetchedEmployee.equals(employee));
    }
}
