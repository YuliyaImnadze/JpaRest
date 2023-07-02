package com.example.jparest.controller;

import com.example.jparest.dto.EmployeeDto;
import com.example.jparest.entity.Employee;
import com.example.jparest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDto> showAll() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto showById(@PathVariable UUID id) {
        return employeeService.findById(id);
    }

    @GetMapping("/")
    public EmployeeDto showByNickname(@RequestParam("nickname") String nickname) {
        return employeeService.findByNickname(nickname);
    }

    @PostMapping("/create")
    public Employee create(@RequestBody EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }

    @PutMapping("/update")
    public void updatePerson(@RequestBody EmployeeDto personDto) {
        employeeService.update(personDto);
    }

    @DeleteMapping("/delete/")
    public void deletePerson(@RequestParam("nickname") String nickname) {
        employeeService.delete(nickname);
    }


}
