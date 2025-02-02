package com.employeeproject.Allocation.service;

import java.util.List;

public interface SecurityClientService {
    void isTokenValid(String authorization, List<String> roles);
}
