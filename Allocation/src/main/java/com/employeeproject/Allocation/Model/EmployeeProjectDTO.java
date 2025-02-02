package com.employeeproject.Allocation.Model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
@JsonSerialize(using = EmployeeProjectSerializer.class)
public class EmployeeProjectDTO {
    private Long id;
    private String employeeID;
    private String projectID;
    private Double allocation;
}
