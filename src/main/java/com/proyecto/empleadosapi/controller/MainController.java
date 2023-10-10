package com.proyecto.empleadosapi.controller;

import com.proyecto.empleadosapi.model.Employee;
import com.proyecto.empleadosapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.proyecto.empleadosapi.repository.UserRepository;
import com.proyecto.empleadosapi.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/employee") // This means URLs start with /demo (after Application path)
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private EmployeeRepository employeeRepository;

    @PostMapping("/addByName") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@RequestParam String firstName, @RequestParam String lastName) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Employee employee = new Employee(firstName, lastName);
        employeeRepository.save(employee);
        return "Saved";
    }

    @PostMapping("/add")
    public @ResponseBody ResponseEntity<String> addNewUser(@RequestBody Employee newEmployee) {
        try {
            employeeRepository.save(newEmployee);
        } catch (Exception e) {
            ResponseEntity.internalServerError().body("Ocurrió un error en el servidor");
        }
        return ResponseEntity.ok("Guardado correctamente");
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Employee> getAllEmployers() {
        // This returns a JSON or XML with the employee
        return employeeRepository.findAll();
    }

    // Obtener un empleado por su ID
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeRepository.findById(Math.toIntExact(id));
        return employee.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un empleado por su ID
    @PutMapping("/{id}")
    public @ResponseBody ResponseEntity<String> updateEmployeeById(@PathVariable Long id,
                                                                   @RequestBody Employee updatedEmployee) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(Math.toIntExact(id));
        if (optionalEmployee.isPresent()) {
            Employee existingEmployee = optionalEmployee.get();
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setSalary(updatedEmployee.getSalary());
            existingEmployee.setHireDate(updatedEmployee.getHireDate());
            existingEmployee.setPosition(updatedEmployee.getPosition());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            employeeRepository.save(existingEmployee);
            return ResponseEntity.ok("Employee updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un empleado por su ID
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> deleteEmployeeById(@PathVariable Long id) {
        if (employeeRepository.existsById(Math.toIntExact(id))) {
            employeeRepository.deleteById(Math.toIntExact(id));
            return ResponseEntity.ok("Employee deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/alive")
    public @ResponseBody Boolean isAlive() {
        return true;
    }

    @GetMapping("/latest")
    public @ResponseBody ResponseEntity<Employee> getLatestEmployee() {
        Employee employee = employeeRepository.findTopByOrderByIdDesc();
        if (Objects.nonNull(employee)){
            return ResponseEntity.ok(employee);
        }
        return ResponseEntity.internalServerError().build();
    }
}