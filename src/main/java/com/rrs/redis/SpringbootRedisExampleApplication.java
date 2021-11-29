package com.rrs.redis;

import com.rrs.redis.dao.EmployeeDAO;
import com.rrs.redis.dao.EmployeeRedisDAO;
import com.rrs.redis.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@Slf4j
@SpringBootApplication
public class SpringbootRedisExampleApplication implements CommandLineRunner {

    private EmployeeDAO employeeDAO;

    public SpringbootRedisExampleApplication(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisExampleApplication.class, args);
    }

    @Override
    public void run(String... args) {

        //saving one employee
        employeeDAO.save(new Employee(500, "Emp0", 2150.0));

        //saving multiple employees
        employeeDAO.save(
                Map.of( 501, new Employee(501, "Emp1", 2396.0),
                        502, new Employee(502, "Emp2", 2499.5),
                        503, new Employee(503, "Emp4", 2324.75)
                )
        );

        //modifying employee with empId 503
        employeeDAO.update(new Employee(503, "Emp3", 2325.25));

        //deleting employee with empID 500
        employeeDAO.delete(500);

        //retrieving all employees
        employeeDAO.list().forEach((k,v)-> System.out.println(k +" : "+v));

        //retrieving employee with empID 501
        System.out.println("Emp details for 501 : "+employeeDAO.find(501));
    }
}
