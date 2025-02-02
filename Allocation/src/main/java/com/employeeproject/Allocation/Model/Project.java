package com.employeeproject.Allocation.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Data
@Entity
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class Project {
    @Id
    private String projectID;
    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_NAME")
    private AccountName accountName;
    @Column(name = "PROJECT_NAME")
    private String projectName;
    @Column(name = "allocation")
    private Double allocation;
    @Column(name = "PROJECT_START_DATE")
    private String projectStartDate;
    @Column(name = "PROJECT_END_DATE")
    private String projectEndDate;
    @Column(name = "remarks")
    private String remarks;
    @OneToMany(mappedBy = "project")
    @JsonManagedReference
    private List<EmployeeProject> employeeProjects;
    public enum AccountName {
        ANCESTRY, BNYM, CALIBO_LLC, EXPERIAN, FORD, GUARANTEED_RATE, INVOICE_CLOUD,
        VATTIKUTI_VENTURES_LLC, ZIP_CO_US_INC, PAYPAL, JOHNSON_CONTROLS_INC, WESTERN_UNION;
    }
}
