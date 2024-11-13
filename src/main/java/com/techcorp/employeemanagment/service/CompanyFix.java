package com.techcorp.employeemanagment.service;

import org.springframework.stereotype.Service;

@Service
public class CompanyFix {
    public String extractCompanyFromEmail(String email) {
        String domain = email.substring(email.indexOf("@") + 1);
        String[] domainParts = domain.split("\\.");
        String companyKey = domainParts[0];
        return companyKey;
    }
}
