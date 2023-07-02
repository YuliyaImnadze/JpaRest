package com.example.jparest.service;

import com.example.jparest.dto.EmployeeDto;
import com.example.jparest.entity.Employee;
import com.example.jparest.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper employeeMapper;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository,
                           ModelMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDto> findAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employee -> employeeMapper.map(employee, EmployeeDto.class)).collect(Collectors.toList());
    }


    public EmployeeDto findById(UUID id) {
        Employee employeeById = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employeeMapper.map(employeeById, EmployeeDto.class);
    }

    public EmployeeDto findByNickname(String nickname) {
        Employee employeeById = employeeRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return employeeMapper.map(employeeById, EmployeeDto.class);
    }


    @Transactional
    public Employee save(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.map(employeeDto, Employee.class);
        employee.setId(UUID.randomUUID());
        return employeeRepository.save(employee);
    }


    @Transactional
    public void update(EmployeeDto employeeDto) {
        Employee updatedEmployee = employeeRepository.findByNickname(employeeDto.getNickname())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));
        updatedEmployee.setName(employeeDto.getName());
        updatedEmployee.setAge(employeeDto.getAge());
        updatedEmployee.setEmail(employeeDto.getEmail());
        updatedEmployee.setNickname(employeeDto.getNickname());
        employeeRepository.save(updatedEmployee);
    }

    @Transactional
    public void delete(String nickname) {
        employeeRepository.deleteByNickname(nickname);
    }


}
