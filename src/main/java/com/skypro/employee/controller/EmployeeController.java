package com.skypro.employee.controller;

import com.skypro.employee.model.Employee;
import com.skypro.employee.record.EmployeeRequest;
import com.skypro.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

/* Методы:
 * GET - получение ресурса
 * POST - создание ресурса
 * PUT - модификация (изменение) ресурса
 * PATCH - частичная модификация ресурса
 * DELETE - удаление ресурса
 */

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public Collection<Employee> getAllEmployee() {
        return this.employeeService.getAllEmployee();
    }

    @PostMapping ("/employees")
    public Employee createEmployee(@RequestBody EmployeeRequest employeeRequest){
        return this.employeeService.addEmployee(employeeRequest);
    }

    @GetMapping("/employees/salary/sum")
    public int getSalary(){
        return this.employeeService.getSalarySum();
    }

    @GetMapping("/employees/salary/min")
    public int getSalaryMin() {
        return this.employeeService.getSalaryMin().getSalary();
    }

    @GetMapping("/employees/salary/max")
    public int getSalaryMax() {
        return this.employeeService.getSalaryMax().getSalary();
    }

    @GetMapping("/employees/salary/high-salary")
    Set<Employee> getEmployeeHighSalary() {
        return this.employeeService.getEmployeeHighSalary();
    }
}