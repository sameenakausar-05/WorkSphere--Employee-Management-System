package com.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpSession;

import com.ems.entity.Employee;
import com.ems.service.EmployeeService;



@Controller
public class LoginController {
	
	@Autowired
	private EmployeeService employeeService;

    @GetMapping("/admin/login")
    public String adminLogin() {

        return "admin-login";
    }

    @GetMapping("/employee/login")
    public String employeeLogin() {

        return "employee-login";
    }
    
    @PostMapping("/admin/login")
    public String adminLogin(@RequestParam String username,
                             @RequestParam String password,
                             Model model,
                             HttpSession session) {

        Employee employee = employeeService.login(username, password);

        if (employee == null) {
            model.addAttribute("error", "Invalid Username or Password");
            return "admin-login";
        }

        if (!"ADMIN".equalsIgnoreCase(employee.getRole())) {
            model.addAttribute("error", "You are not authorized as Admin");
            return "admin-login";
        }
        session.setAttribute("loggedInUser", employee);

        return "redirect:/admin/dashboard";
    }
    
    @PostMapping("/employee/login")
    public String employeeLogin(@RequestParam String username,
                                @RequestParam String password,
                                Model model, HttpSession session) {

        Employee employee = employeeService.login(username, password);

        if (employee == null) {
            model.addAttribute("error", "Invalid Username or Password");
            return "employee-login";
        }

        if (!"EMPLOYEE".equalsIgnoreCase(employee.getRole())) {
            model.addAttribute("error", "You are not authorized as Employee");
            return "employee-login";
        }
        
        session.setAttribute("loggedInUser", employee);
        return "redirect:/employee/dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}