package com.employeeproject.Allocation.service;

import com.employeeproject.Allocation.Model.EmployeeDTO;
import com.employeeproject.Allocation.Model.EmployeeDetailsDTO;
import com.employeeproject.Allocation.Model.EmployeeProject;
import com.employeeproject.Allocation.Model.EmployeeProjectDTO;

import java.util.List;

public interface EmployeeProjectService {
    List<EmployeeDetailsDTO> getAllocatedProjects();

    EmployeeProject modifyEmployeeProjectsByAllocationId(EmployeeProjectDTO employeeProject);

    EmployeeDTO getSecondHighestExperienceEmployee(String projectID);

    List<EmployeeDTO> getEmployeesWithPrimarySecondarySkills();

    List<EmployeeDTO> getEmployeesWithoutPrimarySkills();

}
