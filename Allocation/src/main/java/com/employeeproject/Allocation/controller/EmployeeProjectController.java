package com.employeeproject.Allocation.controller;

import com.employeeproject.Allocation.Model.EmployeeDTO;
import com.employeeproject.Allocation.Model.EmployeeDetailsDTO;
import com.employeeproject.Allocation.Model.EmployeeProject;
import com.employeeproject.Allocation.Model.EmployeeProjectDTO;
import com.employeeproject.Allocation.constants.AppConstants;
import com.employeeproject.Allocation.kafka.KafkaProducer;
import com.employeeproject.Allocation.service.EmployeeProjectService;
import com.employeeproject.Allocation.service.SecurityClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/projects")
@Slf4j
public class EmployeeProjectController {
    @Autowired
    EmployeeProjectService employeeProjectService;
    @Autowired
    private SecurityClientService securityClientService;
    @Autowired
    private KafkaProducer kafkaProducer;

    @GetMapping("/allocate")
    public ResponseEntity<List<EmployeeDetailsDTO>> getAllocatedProjects(@RequestHeader(name = "Authorization") String authorizationHeader) {
        List<String> authorizedRoles = Arrays.asList(AppConstants.ADMIN_ROLE, AppConstants.USER_ROLE);
        securityClientService.isTokenValid(authorizationHeader, authorizedRoles);
        List<EmployeeDetailsDTO> employeeList = employeeProjectService.getAllocatedProjects();
        return ResponseEntity.ok(employeeList);
    }

    @PostMapping("/allocate/updateProjectAllocation")
    public ResponseEntity<EmployeeProject> modifyEmployeeProjectsByAllocationId(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody EmployeeProjectDTO employeeProject) {
        List<String> authorizedRoles = List.of(AppConstants.ADMIN_ROLE);
        securityClientService.isTokenValid(authorizationHeader, authorizedRoles);
        EmployeeProject updatedEmployeeProject = employeeProjectService.modifyEmployeeProjectsByAllocationId(employeeProject);
        log.info("updatedEmployeeProject {}", updatedEmployeeProject);
        kafkaProducer.sendMessage(updatedEmployeeProject.toString());
        return ResponseEntity.ok(updatedEmployeeProject);
    }

    @GetMapping("/allocate/getSecondHighestExperienceEmployee/{projectID}")
    public ResponseEntity<EmployeeDTO> getSecondHighestExperienceEmployee(@PathVariable String projectID) {
        EmployeeDTO secondHighestExperienceEmployee = employeeProjectService.getSecondHighestExperienceEmployee(projectID);
        log.info("SecondHighestExperienceEmployee {}", secondHighestExperienceEmployee);
        return ResponseEntity.ok(secondHighestExperienceEmployee);
    }

    @GetMapping("/allocate/getEmployeesWithPrimarySecondarySkills")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithPrimarySecondarySkills() {
        List<EmployeeDTO> employeesWithPrimarySecondarySkills = employeeProjectService.getEmployeesWithPrimarySecondarySkills();
        log.info("EmployeesWithPrimarySecondarySkills {}", employeesWithPrimarySecondarySkills);
        return ResponseEntity.ok(employeesWithPrimarySecondarySkills);
    }

    @GetMapping("/allocate/getEmployeesWithoutPrimarySkills")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesWithoutPrimarySkills() {
        List<EmployeeDTO> employeesWithoutPrimarySkills = employeeProjectService.getEmployeesWithoutPrimarySkills();
        log.info("EmployeesWithoutPrimarySkills {}", employeesWithoutPrimarySkills);
        return ResponseEntity.ok(employeesWithoutPrimarySkills);
    }
}
