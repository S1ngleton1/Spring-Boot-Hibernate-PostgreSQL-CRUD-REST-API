package com.spring.rest.api.spring_rest.controller;

import com.spring.rest.api.spring_rest.assembler.EmployeeModelAssembler;
import com.spring.rest.api.spring_rest.model.Employee;
import com.spring.rest.api.spring_rest.service.EmployeeService;
import com.spring.rest.api.spring_rest.validator.EmployeeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
public class MainController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeValidator employeeValidator;
    @Autowired
    private EmployeeModelAssembler employeeModelAssembler;

    //Save employee to db rest api
//    @PostMapping()
//    public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee,
//                                                 BindingResult bindingResult){
//        employeeValidator.validate(employee,bindingResult);
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<Object>(employeeService.saveEmployee(employee),HttpStatus.CREATED);
//    }

    //Save employee to db rest api with HATEOAS
    @PostMapping()
    public ResponseEntity<Object> saveEmployee(@RequestBody Employee employee,
                                               BindingResult bindingResult){
        employeeValidator.validate(employee,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
        }
        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(employeeService.saveEmployee(employee));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    //Get all employees rest api
//    @GetMapping()
//    public List<Employee> getEmployees(){
//        return employeeService.getALlEmployees();
//    }
    //Get all employees rest api with HATEOAS
    @GetMapping()
    public CollectionModel<EntityModel<Employee>> getEmployees() {
        List<EntityModel<Employee>> employees = employeeService.getALlEmployeesHateoas();
        return CollectionModel.of(employees, linkTo(methodOn(MainController.class).getEmployees()).withSelfRel());
    }


    //Get employee by Id rest api
//    @GetMapping("{id}")
//    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
//        return new ResponseEntity<Employee>(employeeService.getEmployeeById(id),HttpStatus.OK);
//    }

    //Get employee by Id rest api with HATEOAS
    @GetMapping("{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Long id){
        return employeeModelAssembler.toModel(employeeService.getEmployeeById(id));
    }

    //Update employee by Id rest api
//    @PutMapping("{id}")
//    public ResponseEntity<Object> updateEmployee(@PathVariable Long id,
//                                                   @RequestBody Employee employee,
//                                                   BindingResult bindingResult){
//        employeeValidator.validate(employee,bindingResult);
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<Object>(employeeService.updateEmployee(employee,id),HttpStatus.OK);
//    }

    //Update employee by Id rest api with HATEOAS
    @PutMapping("{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id,
                                                 @RequestBody Employee employee,
                                                 BindingResult bindingResult){
        employeeValidator.validate(employee,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<Object>(employeeService.generateValidationError(bindingResult),HttpStatus.BAD_REQUEST);
        }
        EntityModel<Employee> entityModel = employeeModelAssembler.toModel(employeeService.updateEmployee(employee,id));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }


    //Delete employee by Id rest api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<String>(String.format("Employee with id = %s deleted successfully", id), HttpStatus.OK);
    }

}
