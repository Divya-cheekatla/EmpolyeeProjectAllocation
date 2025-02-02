package com.employeeproject.Allocation.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Setter
@Getter
@Data
@Entity
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Employee {
    @Id
    private String employeeID;
    @Column(name = "employeeName")
    private String employeeName;
    @Enumerated(EnumType.STRING)
    @Column(name = "capability_centre")
    private CapabilityCentre  capabilityCentre;
    @Column(name = "date_of_joining")
    private String dateOfJoining;
    @Enumerated(EnumType.STRING)
    @Column(name = "designation")
    private Designation designation;
    @Column(name = "primary_skill")
    private String primarySkill;
    @Column(name = "secondary_skill")
    private String secondarySkill;
    @Column(name = "overall_experience")
    private Integer overallExperience;
    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<EmployeeProject> employeeProjects;
    public enum CapabilityCentre {
        PRODUCT_AND_PLATFORM, DEP_CLOUD, DEVAA, DEP_QUALITY;
    }

    public enum Designation {
        PRINCIPAL_ENGINEER, STAFF_ENGINEER, TECHNICAL_LEAD, ARCHITECT, SENIOR_ENGINEER, ENGINEER, ASSOC_ENGINEER;
    }
}
