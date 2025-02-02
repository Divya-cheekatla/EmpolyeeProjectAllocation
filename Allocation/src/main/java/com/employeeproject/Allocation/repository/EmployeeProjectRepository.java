package com.employeeproject.Allocation.repository;

import com.employeeproject.Allocation.Model.EmployeeProject;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE Employee_Project ep SET ep.allocation = :allocation , ep.projectId = :projectId , ep.employeeId = :employeeId WHERE ep.id = :allocationId", nativeQuery = true)
    EmployeeProject updateAllocationById(@Param("employeeId") String employeeId, @Param("projectId") String projectId, @Param("allocation") double allocation, @Param("allocationId") Long allocationId);

    List<EmployeeProject> findByEmployee_EmployeeID(String employeeID);
}
