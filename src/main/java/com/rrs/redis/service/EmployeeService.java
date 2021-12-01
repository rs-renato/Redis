package com.rrs.redis.service;

import com.rrs.redis.model.Employee;
import com.rrs.redis.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@EnableCaching
public class EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Caching(evict = {
            @CacheEvict(value="employee", allEntries=true),
            @CacheEvict(value="employees", allEntries=true)})
    public void save(Employee employee) {
        log.info("Saving employee {}", employee);
        this.repository.save(employee);
    }

    @Caching(evict = {
            @CacheEvict(value="employee", allEntries=true),
            @CacheEvict(value="employees", allEntries=true)})
    public void save(List<Employee> employees) {
        log.info("Saving employees {}", employees);
        this.repository.saveAll(employees);
    }

    @Cacheable(value = "employee", key = "#id")
    public Employee find(Integer id) {
        log.info("Finding employee {}", id);
        return this.repository.findById(id).orElse(null);
    }

    @Caching(evict = {
            @CacheEvict(value="employee", allEntries=true),
            @CacheEvict(value="employees", allEntries=true)})
    public void update(Employee employee) {
        Employee empl = find(employee.getEmpId());
        empl.setEmpSalary(employee.getEmpSalary());
        empl.setEmpName(employee.getEmpName());
        log.info("Updating employee {}", empl);
        this.repository.save(empl);
    }

    @Cacheable(value = "employees", unless = "#result == null or #result.empty")
    public List<Employee> list() {
        log.info("Listing employees");
        return this.repository.findAll();
    }

    @CacheEvict(value = "employee", key = "#id")
    public void delete(Integer id) {
        log.info("Deleting employee {}", id);
        this.repository.deleteById(id);
    }
}