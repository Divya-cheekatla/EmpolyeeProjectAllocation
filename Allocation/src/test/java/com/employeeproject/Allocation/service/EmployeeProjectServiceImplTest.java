package com.employeeproject.Allocation.service;

import com.employeeproject.Allocation.Exception.EmployeeProjectNotAllocatedException;
import com.employeeproject.Allocation.Model.*;
import com.employeeproject.Allocation.repository.EmployeeProjectRepository;
import com.employeeproject.Allocation.repository.EmployeeRepository;
import com.employeeproject.Allocation.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeProjectServiceImplTest {
    @InjectMocks
    EmployeeProjectServiceImpl employeeProjectService;
    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    EmployeeProjectRepository employeeProjectRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    ProjectRepository projectRepository;
    private Employee employee;
    private EmployeeProjectDTO employeeProjectDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock Employee object and associated projects
        employee = new Employee();
        employee.setEmployeeID("E001");

        EmployeeProject employeeProject1 = new EmployeeProject();
        employeeProject1.setAllocation(50.0);
        employeeProject1.setProject(new Project("P001", Project.AccountName.BNYM, "Project 1", 75.8, "Start Date", "End Date", "Remarks", List.of(new EmployeeProject())));

        EmployeeProject employeeProject2 = new EmployeeProject();
        employeeProject2.setAllocation(30.0);
        employeeProject2.setProject(new Project("P002", Project.AccountName.FORD, "Project 2", 00.1, "Start Date", "End Date", "Remarks", List.of(new EmployeeProject())));

        employee.setEmployeeProjects(Arrays.asList(employeeProject1, employeeProject2));

        employeeProjectDTO = new EmployeeProjectDTO();
        employeeProjectDTO.setId(1L);
        employeeProjectDTO.setEmployeeID("E001");
        employeeProjectDTO.setProjectID("P001");
        employeeProjectDTO.setAllocation(50.0);
    }

    @Test
    void testGetAllocatedProjects() {
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        EmployeeDetailsDTO expectedDTO = new EmployeeDetailsDTO();
        when(modelMapper.map(employee, EmployeeDetailsDTO.class)).thenReturn(expectedDTO);

        List<EmployeeDetailsDTO> allocatedProjects = employeeProjectService.getAllocatedProjects();

        assertNotNull(allocatedProjects);
        assertEquals(1, allocatedProjects.size());

        EmployeeDetailsDTO resultDTO = allocatedProjects.get(0);
        assertNotNull(resultDTO.getEmployeeProjects());
        assertEquals(2, resultDTO.getEmployeeProjects().size());

        // Assert project details
        EmployeeDetailsDTO.ProjectDTO project1 = resultDTO.getEmployeeProjects().get(0);
        assertEquals("P001", project1.getProjectID());
        assertEquals(Project.AccountName.BNYM.toString(), project1.getAccountName());
        assertEquals("Project 1", project1.getProjectName());
        assertEquals(50.0, project1.getAllocation());

        EmployeeDetailsDTO.ProjectDTO project2 = resultDTO.getEmployeeProjects().get(1);
        assertEquals("P002", project2.getProjectID());
        assertEquals(Project.AccountName.FORD.toString(), project2.getAccountName());
        assertEquals("Project 2", project2.getProjectName());
        assertEquals(30.0, project2.getAllocation());
    }

    @Test
    void testModifyEmployeeProjectsByAllocationId_Success() {
        EmployeeProject existingProject = new EmployeeProject();
        existingProject.setId(1L);
        employee = new Employee(
                "E001",
                "John Doe",
                Employee.CapabilityCentre.PRODUCT_AND_PLATFORM,
                "2020-01-01",
                Employee.Designation.ASSOC_ENGINEER,
                "Java",
                "Spring Boot",
                5,
                null
        );
        existingProject.setEmployee(employee);

        when(employeeProjectRepository.findById(1L)).thenReturn(Optional.of(existingProject));
        when(employeeProjectRepository.findByEmployee_EmployeeID("E001"))
                .thenReturn(Arrays.asList(existingProject, new EmployeeProject()));
        EmployeeProject updatedProject = new EmployeeProject();
        when(modelMapper.map(employeeProjectDTO, EmployeeProject.class)).thenReturn(updatedProject);
        when(employeeProjectRepository.save(updatedProject)).thenReturn(updatedProject);

        EmployeeProject result = employeeProjectService.modifyEmployeeProjectsByAllocationId(employeeProjectDTO);

        assertNotNull(result);
        assertEquals(updatedProject, result);
        verify(employeeProjectRepository, times(1)).save(updatedProject);
    }

    @Test
    void testModifyEmployeeProjectsByAllocationId_EmployeeNotFound() {
        // Mock findById to return empty (employee project not found)
        when(employeeProjectRepository.findById(1L)).thenReturn(Optional.empty());

        // Check if EmployeeProjectNotAllocatedException is thrown
        EmployeeProjectNotAllocatedException exception = assertThrows(EmployeeProjectNotAllocatedException.class, () ->
                employeeProjectService.modifyEmployeeProjectsByAllocationId(employeeProjectDTO)
        );
        assertEquals("EmployeeProject not found with id 1", exception.getMessage());
    }

    @Test
    void testModifyEmployeeProjectsByAllocationId_ProjectAllocationExceeds() {
        // Mock findById to return an existing employee project
        EmployeeProject existingProject = new EmployeeProject();
        existingProject.setId(1L);
        existingProject.setEmployee(employee);

        when(employeeProjectRepository.findById(1L)).thenReturn(Optional.of(existingProject));

        // Mock the count query to return 4 projects already under employee
        when(employeeProjectRepository.findByEmployee_EmployeeID("E001"))
                .thenReturn(Arrays.asList(existingProject, new EmployeeProject(), new EmployeeProject(), new EmployeeProject()));

        // Check if EmployeeProjectNotAllocatedException is thrown for exceeding project allocation range
        EmployeeProjectNotAllocatedException exception = assertThrows(EmployeeProjectNotAllocatedException.class, () ->
                employeeProjectService.modifyEmployeeProjectsByAllocationId(employeeProjectDTO)
        );
        assertEquals("Employee project allocation range exceeded", exception.getMessage());
    }
}