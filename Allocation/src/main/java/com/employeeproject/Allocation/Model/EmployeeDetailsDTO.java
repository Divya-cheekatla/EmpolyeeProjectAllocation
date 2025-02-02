package com.employeeproject.Allocation.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Data
@ToString
public class EmployeeDetailsDTO {
    private String employeeID;
    private String employeeName;
    private String capabilityCentre;
    private String dateOfJoining;
    private String designation;
    private String primarySkill;
    private String secondarySkill;
    private Integer overallExperience;
    private List<ProjectDTO> employeeProjects;

    // Constructors, Getters, Setters
    @Setter
    @Getter
    @Data
    @ToString
    public static class ProjectDTO {
        private String projectID;
        private String accountName;
        private String projectName;
        private Double allocation;
        private String projectStartDate;
        private String projectEndDate;
        private String remarks;

        // Constructors, Getters, Setters
    }
}
