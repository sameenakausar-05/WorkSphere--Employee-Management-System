package com.ems.service;

import java.util.List;

import com.ems.entity.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Employee getEmployeeById(Long id);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long id);

    // Only declaration
    Employee login(String username, String password);List<Employee> searchEmployees(String keyword);

    Employee updateEmployeeProfile(Employee employee);
    
    boolean changePassword(Long employeeId,
            String currentPassword,
            String newPassword);
    
    long getTotalEmployees();

    long getActiveEmployees();

    long getDepartmentCount();
    
    long countDepartment(String department);
}