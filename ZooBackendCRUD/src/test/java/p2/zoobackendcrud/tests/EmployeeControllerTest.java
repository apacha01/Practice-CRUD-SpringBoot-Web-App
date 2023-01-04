/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package p2.zoobackendcrud.tests;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import p2.zoobackendcrud.repositories.EmployeeRepository;
import p2.zoobackendcrud.entities.Employee;
import p2.zoobackendcrud.auxiliar.TYPE_ENUM;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 *
 * @author Agustín Pacheco
 */
@WebMvcTest
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository empRepo;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void givenEmployee_CreateEmployee_thenReturnSavedEmployee() throws Exception {
        
        Date d = new Date();
        Employee employee = new Employee(TYPE_ENUM.ADMIN, "username", "password",
                "name", "address", "phone", d);
        
        // given - precondition or setup
        given(empRepo.save(any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/empleado/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the result or output using assert statements        
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type",
                        is(employee.getType().toString())))
                .andExpect(jsonPath("$.user_name",
                        is(employee.getUserName())))
                .andExpect(jsonPath("$.password",
                        is(employee.getPassword())))
                .andExpect(jsonPath("$.name",
                        is(employee.getName())))
                .andExpect(jsonPath("$.address",
                        is(employee.getAddress())))
                .andExpect(jsonPath("$.phone",
                        is(employee.getPhone())))
                .andExpect(jsonPath("$.first_day",
                        is(employee.getFormatedFirstDayAsString())));
    }
}
