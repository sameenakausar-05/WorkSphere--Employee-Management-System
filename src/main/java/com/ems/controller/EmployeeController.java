package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;
import com.ems.service.EmployeeService;
import com.ems.service.LeaveService;


@Controller
public class EmployeeController {

    @GetMapping("/")
    public String home() {

        return "index";

    }
    
    @GetMapping("/employee/profile")
    public String employeeProfile(HttpSession session,
                                  Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if(employee == null) {
            return "redirect:/employee/login";
        }

        model.addAttribute("employee", employee);

        return "employee-profile";
    }
    
    @GetMapping("/employee/editProfile")
    public String editProfile(HttpSession session,
                              Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if(employee == null) {
            return "redirect:/employee/login";
        }

        model.addAttribute("employee", employee);

        return "edit-profile";
    }
    
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/employee/updateProfile")
    public String updateProfile(@ModelAttribute Employee employee,
                                HttpSession session) {

        Employee updatedEmployee =
                employeeService.updateEmployeeProfile(employee);

        session.setAttribute("loggedInUser", updatedEmployee);

        return "redirect:/employee/profile";
    }
    
    @GetMapping("/employee/changePassword")
    public String changePasswordPage(HttpSession session) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if(employee == null)
            return "redirect:/employee/login";

        return "change-password";
    }
    
    @PostMapping("/employee/changePassword")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if (employee == null) {
            return "redirect:/employee/login";
        }

        if (!newPassword.equals(confirmPassword)) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "New Password and Confirm Password do not match.");

            return "redirect:/employee/changePassword";
        }

        boolean changed =
                employeeService.changePassword(
                        employee.getId(),
                        currentPassword,
                        newPassword);

        if (!changed) {

            redirectAttributes.addFlashAttribute(
                    "error",
                    "Current Password is incorrect.");

            return "redirect:/employee/changePassword";
        }

        // Keep session updated
        employee.setPassword(newPassword);
        session.setAttribute("loggedInUser", employee);

        redirectAttributes.addFlashAttribute(
                "success",
                "Password updated successfully!");

        return "redirect:/employee/profile";
    }

    @Autowired
    private LeaveService leaveService;
    
    @GetMapping("/employee/applyLeave")
    public String applyLeavePage(HttpSession session,
                                 Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if (employee == null) {
            return "redirect:/employee/login";
        }

        model.addAttribute("leave", new LeaveRequest());

        return "apply-leave";
    }
    
    @PostMapping("/employee/saveLeave")
    public String saveLeave(@ModelAttribute("leave") LeaveRequest leave,
                            HttpSession session) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if (employee == null) {
            return "redirect:/employee/login";
        }

        leave.setEmployee(employee);

        leave.setStatus("PENDING");

        leaveService.applyLeave(leave);

        return "redirect:/employee/dashboard";
    }
    
    
    @GetMapping("/employee/myLeaves")
    public String myLeaves(HttpSession session,
                           Model model) {

        Employee employee =
                (Employee) session.getAttribute("loggedInUser");

        if(employee == null)
            return "redirect:/employee/login";

        model.addAttribute("leaves",
                leaveService.getLeavesByEmployee(employee));

        return "employee-leaves";
    }
}