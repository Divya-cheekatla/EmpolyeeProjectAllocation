package com.employeeproject.Allocation.repository;

import com.employeeproject.Allocation.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    int countByEmployeeID(String employeeID);

    @Query(value = "SELECT e.employeeID AS empID, e.employee_name, e.capability_centre, e.date_of_joining, e.designation, " +
            "e.primary_skill, e.secondary_skill, e.overall_Experience, ep.projectID AS projID " +
            "FROM Employee e " +
            "JOIN employee_project ep ON e.employeeID = ep.employeeID " +
            "WHERE ep.projectID = :projectID " +
            "ORDER BY e.overall_Experience DESC " +
            "LIMIT 1 OFFSET 1", nativeQuery = true)
    Employee findSecondMostExperiencedEmployee(@Param("projectID") String projectID);

    @Query("SELECT e FROM Employee e WHERE e.primarySkill IS NOT NULL AND e.secondarySkill IS NOT NULL " +
            "AND e.primarySkill <> '' AND e.secondarySkill <> ''")
    List<Employee> findEmployeesWithNonNullAndNonEmptySkills();
    @Query("SELECT e FROM Employee e WHERE e.primarySkill IS NULL "+
            "OR e.primarySkill = ''")
    List<Employee> findEmployeesWithNullAndEmptyPrimarySkills();
}
