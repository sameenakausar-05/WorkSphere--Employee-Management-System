package com.ems.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;
import com.ems.repository.LeaveRepository;
import com.ems.service.LeaveService;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Override
    public LeaveRequest applyLeave(LeaveRequest leave) {
        return leaveRepository.save(leave);
    }

    @Override
    public List<LeaveRequest> getEmployeeLeaves(Long employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }

    @Override
    public LeaveRequest getLeaveById(Long id) {
        return leaveRepository.findById(id).orElse(null);
    }

    @Override
    public LeaveRequest saveLeave(LeaveRequest leave) {
        return leaveRepository.save(leave);
    }
    
    @Override
    public List<LeaveRequest> getLeavesByEmployee(Employee employee) {

        return leaveRepository.findByEmployee(employee);

    }
    
    @Override
    public long getPendingLeaves() {

        return leaveRepository.findAll()
                .stream()
                .filter(l -> "PENDING".equalsIgnoreCase(l.getStatus()))
                .count();
    }
    
    @Override
    public long countStatus(String status){

        return leaveRepository
                .countByStatusIgnoreCase(status);
    }
}