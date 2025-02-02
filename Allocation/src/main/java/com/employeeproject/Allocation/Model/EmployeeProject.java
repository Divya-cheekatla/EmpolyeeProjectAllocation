package com.employeeproject.Allocation.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
@Data
@JsonSerialize(using = EmployeeProjectSerializer.class)
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Employee
    @ManyToOne()
    @JoinColumn(name = "employeeID")
    @JsonBackReference
    private Employee employee;

    // Many-to-one relationship with Project
    @ManyToOne()
    @JoinColumn(name = "projectID")
    @JsonBackReference
    private Project project;

    @Column(name = "allocation")
    private Double allocation;

    @Override
    public String toString() {
        return "EmployeeProject{" +
                "id=" + id +
                ", allocation=" + allocation +
                ", employeeID=" + (employee != null ? employee.getEmployeeID() : null) + // Avoid recursion
                ", projectID=" + (project != null ? project.getProjectID() : null) + // Avoid recursion
                '}';
    }
}
