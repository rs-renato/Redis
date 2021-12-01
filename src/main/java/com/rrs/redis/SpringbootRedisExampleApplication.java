package com.rrs.redis;

import com.rrs.redis.model.Employee;
import com.rrs.redis.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@Slf4j
@SpringBootApplication
public class SpringbootRedisExampleApplication implements CommandLineRunner {

    private EmployeeService employeeService;

    @Autowired
    public SpringbootRedisExampleApplication(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisExampleApplication.class, args);
    }

    @Override
    public void run(String... args) {

        //saving one employee
        employeeService.save(new Employee("Emp1", 2150.0));

        //saving multiple employees
        employeeService.save(
                List.of(new Employee("Emp2", 2396.0),
                       new Employee("Emp3", 2499.5),
                       new Employee("Emp4", 2324.75),
                       new Employee("Emp5", 2399.11)
                )
        );

        //retrieving all employees from database
        log.info("Employees from Database: {}", employeeService.list());
        log.info("Employees from Cache: {}", employeeService.list());

        employeeService.list().forEach(employee -> {
            log.info("From Database: {}", employeeService.find(employee.getEmpId()));
        });

        //retrieving all employees from cache
        employeeService.list().forEach(employee -> {
            log.info("From Cache: {}", employeeService.find(employee.getEmpId()));
        });

        //modifying employee with empId 3
        employeeService.update(new Employee(3, "Emp3", 3333.33));

        //deleting employee with empId 1
        employeeService.delete(1);
    }
}
