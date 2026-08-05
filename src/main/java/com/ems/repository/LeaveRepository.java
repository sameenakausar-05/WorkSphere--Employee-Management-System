package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;

public interface LeaveRepository
        extends JpaRepository<LeaveRequest, Long>{

    List<LeaveRequest> findByEmployeeId(Long employeeId);
    
    List<LeaveRequest> findByEmployee(Employee employee);
    
    long countByStatus(String status);
    
    long countByStatusIgnoreCase(String status);
    

}