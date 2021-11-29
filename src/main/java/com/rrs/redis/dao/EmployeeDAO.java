package com.rrs.redis.dao;

import com.rrs.redis.model.Employee;

import java.util.Map;

public interface EmployeeDAO {

    void save(Employee employee);
    void save(Map<Integer, Employee> employees);
    Employee find(Integer id);
    void update(Employee employee);
    Map<Integer, Employee> list();
    void delete(Integer id);
}
