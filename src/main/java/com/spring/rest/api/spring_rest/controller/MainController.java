package com.spring.rest.api.spring_rest.controller;

import com.spring.rest.api.spring_rest.model.Employee;
import com.spring.rest.api.spring_rest.service.EmployeeService;
import com.spring.rest.api.spring_rest.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class MainController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeValidator employeeValidator;

    //Save employee to db rest api
    @PostMapping()
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee,
                                                 BindingResult bindingResult){

        employeeValidator.validate(employee,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(employeeService.saveEmployee(employee),HttpStatus.CREATED);
    }

    //Get all employees rest api
    @GetMapping()
    public List<Employee> getEmployees(){
        return employeeService.getALlEmployees();
    }

    //Get employee by Id rest api
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        return new ResponseEntity<Employee>(employeeService.getEmployeeById(id),HttpStatus.OK);
    }

    //Update employee by Id rest api
    @PutMapping("{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee employee,
                                                   BindingResult bindingResult){
        employeeValidator.validate(employee,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(employeeService.updateEmployee(employee,id),HttpStatus.OK);
    }

    //Delete employee by Id rest api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<String>(String.format("Employee with id = %s deleted successfully", id), HttpStatus.OK);
    }

}
