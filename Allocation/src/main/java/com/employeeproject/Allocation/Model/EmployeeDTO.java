package com.employeeproject.Allocation.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Data
@ToString
public class EmployeeDTO {
    private String employeeID;
    private String employeeName;
    private String capabilityCentre;
    private String dateOfJoining;
    private String designation;
    private String primarySkill;
    private String secondarySkill;
    private Integer overallExperience;
}
