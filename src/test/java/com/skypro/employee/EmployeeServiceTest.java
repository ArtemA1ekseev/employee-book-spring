package com.skypro.employee;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    public void setUp(){
        this.employeeService = new EmployeeService();
        Stream.of(
                new EmployeeRequest("TestOne", "TestOne", 2, 4256),
                new EmployeeRequest("TestTwo", "TestTwo", 4, 5256),
                new EmployeeRequest("TestThree", "TestThree", 1, 4635),
                new EmployeeRequest("TestFour", "TestFour", 3, 2537),
                new EmployeeRequest("TestFive", "TestFive", 1, 11456)
        ).forEach(employeeService::addEmployee);
    }

    @Test
    public void addEmployee() {
        EmployeeRequest request = new EmployeeRequest(
                "Valid","Valid", 3, 5000);
        Employee result = employeeService.addEmployee(request);
        assertEquals(request.getFirstName(),result.getFirstName());
        assertEquals(request.getLastName(),result.getLastName());
        assertEquals(request.getDepartment(),result.getDepartment());
        assertEquals(request.getSalary(),result.getSalary());

        Assertions
                .assertThat(employeeService.getAllEmployee())
                .contains(result);
    }

    @Test
    public void listEmployees() {
        Collection<Employee> employees = employeeService.getAllEmployee();
        Assertions.assertThat(employees).hasSize(5);
        Assertions.assertThat(employees)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestOne");
    }

    @Test
    public void sumOfSalaries() {
        int sum = employeeService.getSalarySum();
        Assertions.assertThat(sum).isEqualTo(28_140);
    }

    @Test
    public void employeeWithMaxSalary() {
        Employee employee = employeeService.getSalaryMax();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestFive");
    }

    @Test
    public void employeeWithMinSalary() {
        Employee employee = employeeService.getSalaryMin();
        Assertions.assertThat(employee)
                .isNotNull()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestFour");
    }

    @Test
    public void removeEmployee() {
    employeeService.removeEmployee(employeeService.getAllEmployee().iterator().next().getId());
    Collection<Employee> employees = employeeService.getAllEmployee();
    Assertions.assertThat(employees).hasSize(4);
    }


    @Test
    public void averageOfSalaries() {
        int average = employeeService.getSalaryAverage();
        Assertions.assertThat(average).isEqualTo(5_628);
    }

    @Test
    public void getEmployeesWithSalaryMoreThatAverages() {
        Set<Employee> highSalary = employeeService.getEmployeeHighSalary();
        Assertions.assertThat(highSalary)
                .first()
                .extracting(Employee::getFirstName)
                .isEqualTo("TestFive");
    }


    @Test
    public void shouldReturnEmployeeIncorrectParamException() {
        assertThrows(IllegalArgumentException.class,
                () -> employeeService.addEmployee(new EmployeeRequest(null, "TestOne", 2, 4256)));
    }

}
