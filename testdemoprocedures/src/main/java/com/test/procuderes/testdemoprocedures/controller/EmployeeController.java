package com.test.procuderes.testdemoprocedures.controller;

import com.test.procuderes.testdemoprocedures.DAO.EmployeeDAO;
import com.test.procuderes.testdemoprocedures.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeDAO employeeDAO;

    @PostMapping("/employee")
    public Employee insertIntoEmployee(@RequestBody Employee employee) throws Exception {
        if(ObjectUtils.isEmpty(employee)){
            throw new Exception("need employee data to insert");
        }
        return employeeDAO.insertToEmployee(employee);
    }

    @DeleteMapping("/employee/delete/{username}")
    public String deleteEmployee(@PathVariable("username") String username) throws Exception {
        if(ObjectUtils.isEmpty(username)){
            throw new Exception("need employee data to insert");
        }
        return employeeDAO.deleteEmployeeWithUsername(username);
    }

    @PutMapping("/employee/{username}")
    public Employee updateEmployeeDetails(@PathVariable("username") String username,
                                        @RequestBody Employee employee) throws Exception {
        if(ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(employee)){
            throw new Exception("need employee data and username to insert");
        }
        return employeeDAO.updateEmployeeData(username, employee);
    }

    @GetMapping("/employee/{username}")
    public Employee getEmployeeDetails(@PathVariable("username") String username) throws Exception {
        if(ObjectUtils.isEmpty(username)){
            throw new Exception("need employee data and username to insert");
        }
        return employeeDAO.getEmployeeFromDB(username);
    }
}
