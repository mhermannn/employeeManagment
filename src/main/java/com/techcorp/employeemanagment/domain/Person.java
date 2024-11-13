package com.techcorp.employeemanagment.domain;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String salary;
    private String currency;
    private String country;
    private String company;

    public Person(String firstName, String lastName, String email, String company, String salary, String currency, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.currency = currency;
        this.country = country;
        this.company = company;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + email + ") - " + company + " "
                + salary + " " + currency + " " + country;
    }
}
