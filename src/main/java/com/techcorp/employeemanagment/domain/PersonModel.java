package com.techcorp.employeemanagment.domain;

import java.util.*;
import java.util.stream.Collectors;

public class PersonModel {
    private final List<Person> persons = new ArrayList<>();
    private int currentId = 1;

    public List<Person> getAllPersons() {
        return persons;
    }

    public Optional<Person> getPersonById(int id) {
        return persons.stream().filter(p -> p.getId() == id).findFirst();
    }

    public Person addPerson(Person person) {
        person.setId(currentId++);
        persons.add(person);
        return person;
    }

    public Person updatePerson(int id, Person updatedPerson) {
        Optional<Person> existingPerson = getPersonById(id);
        if (existingPerson.isPresent()) {
            Person person = existingPerson.get();
            person.setFirstName(updatedPerson.getFirstName());
            person.setLastName(updatedPerson.getLastName());
            person.setEmail(updatedPerson.getEmail());
            person.setCompany(updatedPerson.getCompany());
            return person;
        } else {
            throw new NoSuchElementException("Person not found with ID: " + id);
        }
    }

    public boolean deletePerson(int id) {
        return persons.removeIf(person -> person.getId() == id);
    }

    public List<Person> filterByCompany(String companyName) {
        return persons.stream()
                .filter(person -> person.getCompany().equalsIgnoreCase(companyName))
                .collect(Collectors.toList());
    }

    public List<Person> sortByLastName() {
        return persons.stream()
                .sorted(Comparator.comparing(Person::getLastName))
                .collect(Collectors.toList());
    }

    public List<Person> getPersons() {
        return persons;
    }
}
