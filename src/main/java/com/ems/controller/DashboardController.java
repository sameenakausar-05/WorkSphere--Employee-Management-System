package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ems.entity.Employee;
import com.ems.service.EmployeeService;
import com.ems.service.LeaveService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session, Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if (employee == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("employee", employee);

        model.addAttribute("totalEmployees",
                employeeService.getTotalEmployees());

        model.addAttribute("activeEmployees",
                employeeService.getActiveEmployees());

        model.addAttribute("departmentCount",
                employeeService.getDepartmentCount());

        model.addAttribute("pendingLeaves",
                leaveService.getPendingLeaves());
        
        model.addAttribute("hrCount",
                employeeService.countDepartment("HR"));

        model.addAttribute("financeCount",
                employeeService.countDepartment("Finance"));

        model.addAttribute("itCount",
                employeeService.countDepartment("IT"));

        model.addAttribute("salesCount",
                employeeService.countDepartment("Sales"));

        model.addAttribute("marketingCount",
                employeeService.countDepartment("Marketing"));

        model.addAttribute("pendingCount",
                leaveService.countStatus("PENDING"));

        model.addAttribute("approvedCount",
                leaveService.countStatus("APPROVED"));

        model.addAttribute("rejectedCount",
                leaveService.countStatus("REJECTED"));

        return "admin-dashboard";
    }

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(HttpSession session,
                                    Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if (employee == null) {
            return "redirect:/employee/login";
        }

        model.addAttribute("employee", employee);

        return "employee-dashboard";
    }
}