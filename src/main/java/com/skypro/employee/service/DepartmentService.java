package com.skypro.employee.service;

import com.skypro.employee.model.Employee;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DepartmentService {

    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Collection<Employee> getDepartmentEmployees(int department) {
        return getEmployeeByDepartmentStream(department)
                .collect(Collectors.toList());
    }

    public int getSumOfSalariesByDepartment(int department) {
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .sum();
    }

    public int getMaxSalaryByDepartment(int department) {
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .max()
                .orElseThrow(IllegalArgumentException::new);
    }

    public int getMinSalaryByDepartment(int department) {
        return getEmployeeByDepartmentStream(department)
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Map<Integer, List<Employee>> getEmployeesGroupedByDepartments() {
        return employeeService.getAllEmployee().stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }

    private Stream<Employee> getEmployeeByDepartmentStream (int department) {
        return employeeService.getAllEmployee()
                .stream()
                .filter(e -> e.getDepartment() == department);
    }
 }
