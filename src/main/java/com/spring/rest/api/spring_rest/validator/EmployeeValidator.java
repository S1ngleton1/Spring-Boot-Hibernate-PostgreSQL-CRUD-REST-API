package com.spring.rest.api.spring_rest.validator;

import com.spring.rest.api.spring_rest.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
@Component
public class EmployeeValidator implements Validator {
    @Autowired
    private Environment environment;

    @Override
    public boolean supports(Class<?> aClass) {
        return Employee.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Employee employee = (Employee) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstname", "firstname_error",
                environment.getRequiredProperty("firstnameRequired"));
        if(employee.getFirstname().length()>255 || employee.getFirstname().length()<3){
            errors.rejectValue("firstname", "firstname_error", environment.getRequiredProperty("firstname.size"));
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastname", "lastname_error",
                environment.getRequiredProperty("lastnameRequired"));
        if(employee.getLastname().length()>255 || employee.getLastname().length()<3){
            errors.rejectValue("lastname", "lastname_error", environment.getRequiredProperty("lastname.size"));
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email_error",
                environment.getRequiredProperty("emailRequired"));
        if (!employee.getEmail().contains("@")) {
            errors.rejectValue("email", "email_error", environment.getRequiredProperty("valid.email"));
        }

    }
}
