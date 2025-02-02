package com.employeeproject.Allocation.externalClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "Security", url = "${security.server.port}")
public interface SecurityClient {
    @GetMapping("/auth/validateToken")
    boolean isTokenValid(@RequestHeader String authorization, @RequestHeader List<String> roles);
}
