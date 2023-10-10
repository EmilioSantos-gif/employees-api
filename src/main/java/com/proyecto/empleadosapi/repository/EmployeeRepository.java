package com.proyecto.empleadosapi.repository;

import com.proyecto.empleadosapi.model.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Employee findTopByOrderByIdDesc();
}
