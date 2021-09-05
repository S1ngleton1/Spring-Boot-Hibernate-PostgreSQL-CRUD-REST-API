package com.spring.rest.api.spring_rest.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.spring.rest.api.spring_rest.controller.MainController;
import com.spring.rest.api.spring_rest.model.Employee;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
@Component
public class EmployeeModelAssembler implements RepresentationModelAssembler<Employee, EntityModel<Employee>> {
    @Override
    public EntityModel<Employee> toModel(Employee employee) {
        return EntityModel.of(employee, //
                linkTo(methodOn(MainController.class).getEmployeeById(employee.getId())).withSelfRel(),
                linkTo(methodOn(MainController.class).getEmployees()).withRel("employees"));
    }
}
