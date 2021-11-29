package com.rrs.redis.dao;

import com.rrs.redis.dao.EmployeeDAO;
import com.rrs.redis.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Slf4j
@Repository
public class EmployeeRedisDAO implements EmployeeDAO {

    private static final String REDIS_KEY = Employee.class.getSimpleName();
    private HashOperations<String, Integer, Employee> hashOperations;

    public EmployeeRedisDAO(RedisTemplate<String, Employee> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(Employee employee) {
        log.info("Saving employee {}", employee);
        this.hashOperations.putIfAbsent(REDIS_KEY, employee.getEmpId(), employee);
    }

    @Override
    public void save(Map<Integer, Employee> employees) {
        log.info("Saving employees {}", employees);
        this.hashOperations.putAll(REDIS_KEY, employees);
    }

    @Override
    public Employee find(Integer id) {
        log.info("Fiding employee {}", id);
        return this.hashOperations.get(REDIS_KEY, id);
    }

    @Override
    public void update(Employee employee) {
        log.info("Updating employee {}", employee);
        this.hashOperations.put(REDIS_KEY,  employee.getEmpId(), employee);
    }

    @Override
    public Map<Integer, Employee> list() {
        log.info("Listing employees");
        return this.hashOperations.entries(REDIS_KEY);
    }

    @Override
    public void delete(Integer id) {
        log.info("Deleting employee {}", id);
        this.hashOperations.delete(REDIS_KEY, id);
    }
}
