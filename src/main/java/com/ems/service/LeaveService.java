package com.ems.service;

import java.util.List;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;

public interface LeaveService {

    LeaveRequest applyLeave(LeaveRequest leave);

    List<LeaveRequest> getEmployeeLeaves(Long employeeId);

    List<LeaveRequest> getAllLeaves();

    LeaveRequest getLeaveById(Long id);

    LeaveRequest saveLeave(LeaveRequest leave);
    
    List<LeaveRequest> getLeavesByEmployee(Employee employee);
    
    long getPendingLeaves();
    
    long countStatus(String status);

}