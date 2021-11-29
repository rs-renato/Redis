package com.rrs.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer empId;
    private String empName;
    private Double empSalary;
}
