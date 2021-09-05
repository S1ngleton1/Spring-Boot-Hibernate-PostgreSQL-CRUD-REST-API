package com.spring.rest.api.spring_rest.service;

import com.spring.rest.api.spring_rest.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getALlEmployees();
    Employee getEmployeeById(Long id);
    Employee updateEmployee(Employee employee, Long id);
    void deleteEmployeeById(Long id);
//    List<JsonObject> generateValidationError(BindingResult bindingResult);
    Map<String, HashMap<String,String>> generateValidationError(BindingResult bindingResult);

    List<EntityModel<Employee>> getALlEmployeesHateoas();
}
