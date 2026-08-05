package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ems.entity.Employee;
import com.ems.entity.LeaveRequest;
import com.ems.service.EmployeeService;
import com.ems.service.LeaveService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @GetMapping("/admin/addEmployee")
    public String addEmployeePage(HttpSession session,
                                  Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if(loggedIn == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("employee", new Employee());

        return "add-employee";
    }
    @Autowired
    private EmployeeService employeeService;
    
    @PostMapping("/admin/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee) {

        employeeService.saveEmployee(employee);

        return "redirect:/admin/employees";
    }
    
    @GetMapping("/admin/employees")
    public String viewEmployees(HttpSession session,
                                Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("employees",
                employeeService.getAllEmployees());

        return "view-employees";
    }
    
    @GetMapping("/admin/editEmployee/{id}")
    public String editEmployee(@PathVariable Long id,
                               Model model,
                               HttpSession session)
    {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if(loggedIn==null)
            return "redirect:/admin/login";

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "add-employee";

    }
    
    @GetMapping("/admin/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable Long id,
                                 HttpSession session) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        employeeService.deleteEmployee(id);

        return "redirect:/admin/employees";
    }
    
    @GetMapping("/admin/searchPage")
    public String searchPage(HttpSession session) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        return "search-employee";
    }
    
    @GetMapping("/admin/search")
    public String searchEmployee(@RequestParam("keyword") String keyword,
                                 HttpSession session,
                                 Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("employees",
                employeeService.searchEmployees(keyword));

        return "view-employees";
    }
    
    @GetMapping("/admin/employeeDetails/{id}")
    public String employeeDetails(@PathVariable Long id,
                                  HttpSession session,
                                  Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if(loggedIn == null){
            return "redirect:/admin/login";
        }

        Employee employee =
                employeeService.getEmployeeById(id);

        model.addAttribute("employee", employee);

        return "employee-details";
    }
    
    @Autowired
    private LeaveService leaveService;
    
    @GetMapping("/admin/leaves")
    public String viewLeaves(HttpSession session,
                             Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("leaves",
                leaveService.getAllLeaves());

        return "view-leaves";
    }
    
    
    @GetMapping("/admin/approveLeave/{id}")
    public String approveLeave(@PathVariable Long id,
                               HttpSession session) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if(loggedIn == null){
            return "redirect:/admin/login";
        }

        LeaveRequest leave =
                leaveService.getLeaveById(id);

        leave.setStatus("APPROVED");

        leaveService.saveLeave(leave);

        return "redirect:/admin/leaves";
    }

    @GetMapping("/admin/rejectLeave/{id}")
    public String rejectLeave(@PathVariable Long id,
                              HttpSession session) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if(loggedIn == null){
            return "redirect:/admin/login";
        }

        LeaveRequest leave =
                leaveService.getLeaveById(id);

        leave.setStatus("REJECTED");

        leaveService.saveLeave(leave);

        return "redirect:/admin/leaves";
    }
   

    @GetMapping("/admin/manageLeaves")
    public String manageLeaves(HttpSession session,
                               Model model) {

        Employee loggedIn =
                (Employee) session.getAttribute("loggedInUser");

        if (loggedIn == null) {
            return "redirect:/admin/login";
        }

        model.addAttribute("leaves",
                leaveService.getAllLeaves());

        return "view-leaves";
    }
}