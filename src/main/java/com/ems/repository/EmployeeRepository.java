package com.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUsernameAndPassword(String username,
                                                 String password);

    List<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String firstName,
            String lastName,
            String email,
            String department);
    
    long countByStatus(String status);

    long countDistinctByDepartmentIsNotNull();
    
    long countByDepartmentIgnoreCase(String department);

    long countByStatusIgnoreCase(String status);

}