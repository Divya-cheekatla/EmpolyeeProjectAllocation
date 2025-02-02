package com.employeeproject.Allocation.service;

import com.employeeproject.Allocation.Exception.EmployeeProjectNotAllocatedException;
import com.employeeproject.Allocation.Model.*;
import com.employeeproject.Allocation.repository.EmployeeProjectRepository;
import com.employeeproject.Allocation.repository.EmployeeRepository;
import com.employeeproject.Allocation.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeProjectServiceImpl implements EmployeeProjectService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeProjectRepository employeeProjectRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public List<EmployeeDetailsDTO> getAllocatedProjects() {
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.stream().map(employee -> {
            List<EmployeeDetailsDTO.ProjectDTO> projects = employee.getEmployeeProjects().stream()
                    .map(employeeProject -> {
                        EmployeeDetailsDTO.ProjectDTO dto = new EmployeeDetailsDTO.ProjectDTO();
                        dto.setProjectID(employeeProject.getProject().getProjectID());
                        dto.setAccountName(String.valueOf(employeeProject.getProject().getAccountName()));
                        dto.setProjectName(employeeProject.getProject().getProjectName());
                        dto.setAllocation(employeeProject.getAllocation());
                        dto.setProjectStartDate(employeeProject.getProject().getProjectStartDate());
                        dto.setProjectEndDate(employeeProject.getProject().getProjectEndDate());
                        dto.setRemarks(employeeProject.getProject().getRemarks());
                        return dto;
                    })
                    .collect(Collectors.toList());

            EmployeeDetailsDTO dto = modelMapper.map(employee, EmployeeDetailsDTO.class);
            dto.setEmployeeProjects(projects);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public EmployeeProject modifyEmployeeProjectsByAllocationId(EmployeeProjectDTO employeeProject) {
        try {
            employeeProjectRepository.findById(employeeProject.getId())
                    .orElseThrow(() -> new EmployeeProjectNotAllocatedException("EmployeeProject not found with id " + employeeProject.getId()));
            long projectCountUnderEmployee = employeeProjectRepository.findByEmployee_EmployeeID(employeeProject.getEmployeeID())
                    .stream().filter(employeeProject1 -> !Objects.equals(employeeProject1.getId(), employeeProject.getId()))
                    .count();
            if (projectCountUnderEmployee >= 3) {
                log.info("projectCountUnderEmployee");
                throw new EmployeeProjectNotAllocatedException("Employee project allocation range exceeded");
            }
            return employeeProjectRepository.save(modelMapper.map(employeeProject, EmployeeProject.class));

        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("PUBLIC.CONSTRAINT_C_INDEX_8 ON PUBLIC.EMPLOYEE_PROJECT")) {
                throw new EmployeeProjectNotAllocatedException("Employee with employee ID " + employeeProject.getEmployeeID() + " already allotted with project ID " + employeeProject.getProjectID());
            }
            throw new EmployeeProjectNotAllocatedException("Database integrity constraint violation: " + e.getMessage());
        } catch (EmployeeProjectNotAllocatedException e) {
            log.info("Caught EmployeeProjectNotAllocatedException: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            if (e.getMessage().contains("Employee")) {
                throw new EmployeeProjectNotAllocatedException("Employee with ID " + employeeProject.getEmployeeID() + " does not exist.");
            }
            if (e.getMessage().contains("Project")) {
                throw new EmployeeProjectNotAllocatedException("Project with ID " + employeeProject.getProjectID() + " does not exist.");
            }
            throw new EmployeeProjectNotAllocatedException("An error occurred while assigning employee to project: " + e.getMessage());
        }
    }

    @Override
    public EmployeeDTO getSecondHighestExperienceEmployee(String projectID) {
        Employee employee = employeeRepository.findSecondMostExperiencedEmployee(projectID);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithPrimarySecondarySkills() {
        List<Employee> employeeList = employeeRepository.findEmployeesWithNonNullAndNonEmptySkills();
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<EmployeeDTO> getEmployeesWithoutPrimarySkills() {
        List<Employee> employeeList = employeeRepository.findEmployeesWithNullAndEmptyPrimarySkills();
        return employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toUnmodifiableList());
    }

}
