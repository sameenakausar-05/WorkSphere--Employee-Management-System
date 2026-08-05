package com.ems.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
    
    @Override
    public Employee login(String username, String password) {

        Optional<Employee> employee =
                employeeRepository.findByUsernameAndPassword(username, password);

        return employee.orElse(null);
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {

        return employeeRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                        keyword,
                        keyword,
                        keyword,
                        keyword);

    }
    
    @Override
    public Employee updateEmployeeProfile(Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(employee.getId()).orElse(null);

        if (existingEmployee != null) {

            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            existingEmployee.setAddress(employee.getAddress());

            return employeeRepository.save(existingEmployee);
        }

        return null;
    }
    
    @Override
    public boolean changePassword(Long employeeId,
                                  String currentPassword,
                                  String newPassword) {

        Employee employee =
                employeeRepository.findById(employeeId).orElse(null);

        if (employee == null) {
            return false;
        }

        if (!employee.getPassword().equals(currentPassword)) {
            return false;
        }

        employee.setPassword(newPassword);

        employeeRepository.save(employee);

        return true;
    }
    
    @Override
    public long getTotalEmployees() {
        return employeeRepository.count();
    }

    @Override
    public long getActiveEmployees() {

        return employeeRepository.findAll()
                .stream()
                .filter(e -> "ACTIVE".equalsIgnoreCase(e.getStatus()))
                .count();
    }

    @Override
    public long getDepartmentCount() {

        return employeeRepository.findAll()
                .stream()
                .map(Employee::getDepartment)
                .distinct()
                .count();
    }
    
    @Override
    public long countDepartment(String department){

        return employeeRepository
                .countByDepartmentIgnoreCase(department);
    }
}