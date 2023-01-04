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
                .andExpect(status().isCreated())
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
                        is(employee.formatedFirstDayAsString())));
    }
    
    @Test
    public void givenListOfEmployees_GetAllEmployees_thenReturnEmployeesList() throws Exception{
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee e1 = new Employee(TYPE_ENUM.ADMIN, "username1", "password1",
                "name1", "address1", "phone1", new Date());
        Employee e2 = new Employee(TYPE_ENUM.ADMIN, "username2", "password2",
                "name2", "address2", "phone2", new Date(1500000));
        listOfEmployees.add(e1);
        listOfEmployees.add(e2);
        given(empRepo.findAll()).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/empleado/obtenertodos"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }
    
    //positive scenario (existing id)
    @Test
    public void givenEmployeeId_GetEmployeeById_thenReturnEmployee() throws Exception{
        // given - precondition or setup
        Integer employeeId = 1;
        Employee employee = new Employee(TYPE_ENUM.ADMIN, "username1", "password1",
                "name1", "address1", "phone1", new Date());
        given(empRepo.findById(employeeId)).willReturn(Optional.of(employee));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/empleado/obtenerporid/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
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
                        is(employee.formatedFirstDayAsString())));

    }
    
    // negative scenario (non-existing id)
    @Test
    public void givenInvalidEmployeeId_GetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        Integer employeeId = 2;
        Employee employee = new Employee(TYPE_ENUM.ADMIN, "username1", "password1",
                "name1", "address1", "phone1", new Date());
        given(empRepo.findById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/empleado/obtenerporid/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    
    //positive scenario (existing name)
    @Test
    public void givenEmployeeName_GetEmployeeByName_thenReturnEmployeeList() throws Exception{
        // given - precondition or setup
        String employeeName = "name1";
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee e1 = new Employee(TYPE_ENUM.ADMIN, "username1", "password1",
                "name1", "address1", "phone1", new Date());
        Employee e2 = new Employee(TYPE_ENUM.ADMIN, "username2", "password2",
                "name2", "address2", "phone2", new Date(1500000));
        Employee e3 = new Employee(TYPE_ENUM.ADMIN, "username3", "password3",
                "name1", "address3", "phone3", new Date(20000000));
        listOfEmployees.add(e1);
        listOfEmployees.add(e3);
        given(empRepo.findByNameContaining(employeeName)).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/empleado/obtenerpornombre/{nombre}", employeeName));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }
    
    //positive scenario (non-existing name)
    @Test
    public void givenInvalidEmployeeName_GetEmployeeByName_thenReturnEmployeeList() throws Exception{
        // given - precondition or setup
        String employeeName = "name4";
        List<Employee> listOfEmployees = new ArrayList<>();
        Employee e1 = new Employee(TYPE_ENUM.ADMIN, "username1", "password1",
                "name1", "address1", "phone1", new Date());
        Employee e2 = new Employee(TYPE_ENUM.ADMIN, "username2", "password2",
                "name2", "address2", "phone2", new Date(1500000));
        Employee e3 = new Employee(TYPE_ENUM.ADMIN, "username3", "password3",
                "name1", "address3", "phone3", new Date(20000000));
        given(empRepo.findByNameContaining(employeeName)).willReturn(listOfEmployees);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/empleado/obtenerpornombre/{nombre}", employeeName));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(0)));

    }
}
