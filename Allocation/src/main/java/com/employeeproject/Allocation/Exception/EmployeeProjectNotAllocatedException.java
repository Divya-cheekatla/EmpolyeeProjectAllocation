package com.employeeproject.Allocation.Exception;

public class EmployeeProjectNotAllocatedException extends RuntimeException {
    public EmployeeProjectNotAllocatedException(String message) {
        super(message);
    }

    public EmployeeProjectNotAllocatedException() {
    }
}
