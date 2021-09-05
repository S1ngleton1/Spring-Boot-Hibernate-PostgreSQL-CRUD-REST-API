package com.spring.rest.api.spring_rest.service;

import com.spring.rest.api.spring_rest.assembler.EmployeeModelAssembler;
import com.spring.rest.api.spring_rest.exception.ResourceNotFoundException;
import com.spring.rest.api.spring_rest.model.Employee;
import com.spring.rest.api.spring_rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeModelAssembler employeeModelAssembler;

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getALlEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee", "Id", id));
    }

    @Override
    public Employee updateEmployee(Employee updateEmployee, Long id) {
        //Checking if an employee exists
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee","Id", id));
        //Getting request parameters and setting them for an existing employee
        existingEmployee.setFirstname(updateEmployee.getFirstname());
        existingEmployee.setLastname(updateEmployee.getLastname());
        existingEmployee.setEmail(updateEmployee.getEmail());
        //Save employee
        employeeRepository.save(existingEmployee);
        return existingEmployee;
    }

    @Override
    public void deleteEmployeeById(Long id) {
        //Checking if an employee exists
        employeeRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Employee","Id", id));
        employeeRepository.deleteById(id);
    }
    @Override
    public Map<String,HashMap<String,String>> generateValidationError(BindingResult bindingResult){
        Map<String, HashMap<String,String>> map =  new HashMap<>();
        List<String> firstnameList = new ArrayList<>();
        List<String> lastnameList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();
        List<FieldError> allErrors = bindingResult.getFieldErrors();

        for(int i=0;i<allErrors.size();i++){

            if(allErrors.get(i).getField().equals("firstname")){
                firstnameList.add(allErrors.get(i).getDefaultMessage());
            }
            if(allErrors.get(i).getField().equals("lastname")){
                lastnameList.add(allErrors.get(i).getDefaultMessage());
            }
            if(allErrors.get(i).getField().equals("email")){
                emailList.add(allErrors.get(i).getDefaultMessage());
            }
        }
        HashMap<String,String> firstnameArray = new HashMap<>();
        if(!firstnameList.isEmpty()){
            for(int i=0;i<firstnameList.size();i++){
                firstnameArray.put(String.valueOf(i),firstnameList.get(i));
            }
            map.put("firstname",firstnameArray);
        }
        if(!lastnameList.isEmpty()){
            HashMap<String,String> lastnameArray = new HashMap<>();
            for(int i=0;i<lastnameList.size();i++){
                lastnameArray.put(String.valueOf(i),lastnameList.get(i));
            }
            map.put("lastname",lastnameArray);
        }
        if(!emailList.isEmpty()){
            HashMap<String,String> emailArray = new HashMap<>();
            for(int i=0;i<emailList.size();i++){
                emailArray.put(String.valueOf(i),emailList.get(i));
            }
            map.put("email",emailArray);
        }
        return map;
    }

    @Override
    public List<EntityModel<Employee>> getALlEmployeesHateoas() {
        List<EntityModel<Employee>> employees = employeeRepository.findAll().stream() //
                .map(employeeModelAssembler::toModel) //
                .collect(Collectors.toList());
        return employees;
    }
}
