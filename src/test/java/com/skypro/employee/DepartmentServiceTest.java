package com.skypro.employee;

import com.skypro.employee.model.Employee;
import com.skypro.employee.service.DepartmentService;
import com.skypro.employee.service.EmployeeService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    private final List<Employee> employees = List.of(
            new Employee("TestOne", "TestOne", 1, 4256),
            new Employee("TestTwo", "TestTwo", 1, 5256),
            new Employee("TestThree", "TestThree", 1, 4635),
            new Employee("TestFour", "TestFour", 2, 2537),
            new Employee("TestFive", "TestFive", 2, 11456)
    );

    @Mock
    EmployeeService employeeService;

    @InjectMocks
    DepartmentService departmentService;

    @BeforeEach
    void setUp() {
        when(employeeService.getAllEmployee())
                .thenReturn(employees);
    }
    @Test
    void getEmloyeesByDepartment() {
        Collection<Employee> employeeList = this.departmentService.getDepartmentEmployees(1);
        assertThat(employeeList)
                .hasSize(3)
                .contains(employees.get(0),
                        employees.get(1),
                        employees.get(2));

    }

    @Test
    void sumOfSalariesByDepartment() {
        int sum = this.departmentService.getSumOfSalariesByDepartment(1);
        assertThat(sum).isEqualTo(14_147);
    }

    @Test
    void maxSalaryInDepartment() {
        int max = this.departmentService.getMaxSalaryByDepartment(2);
        assertThat(max).isEqualTo(11_456);
    }

    @Test
    void minSalaryInDepartment() {
        int min = this.departmentService.getMinSalaryByDepartment(2);
        assertThat(min).isEqualTo(2_537);
    }

    @Test
    void groupedEmployees() {
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();

        assertThat(groupedEmployees)
                .hasSize(2)
                .containsEntry(1, List.of(employees.get(0), employees.get(1), employees.get(2)))
                .containsEntry(2, List.of(employees.get(3), employees.get(4)));
    }

    @Test
    void whenNoEmployeesThenGroupByReturnEmptyMap() {
        when(employeeService.getAllEmployee()).thenReturn(List.of());
        Map<Integer, List<Employee>> groupedEmployees = this.departmentService
                .getEmployeesGroupedByDepartments();
        assertThat(groupedEmployees).isEmpty();
    }

    @Test
    void whenNoEmployeesThenMaxSalaryInDepartmentThrowsException() {
        when(employeeService.getAllEmployee()).thenReturn(List.of());
                assertThatThrownBy(() ->
                        departmentService.getMinSalaryByDepartment(0))
                        .isInstanceOf(RuntimeException.class);
    }
}
