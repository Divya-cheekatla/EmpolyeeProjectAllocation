package com.employeeproject.Allocation.repository;

import com.employeeproject.Allocation.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {
    int countByProjectID(String projectID);
}
