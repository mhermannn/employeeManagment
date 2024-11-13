package com.techcorp.employeemanagment.controller;
import java.util.List;
import java.util.Map;


import com.techcorp.employeemanagment.domain.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.techcorp.employeemanagment.service.PersonService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PersonViewController {

    private final PersonService personService;

    public PersonViewController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("persons", personService.getPersonModel().getPersons());

        Map<String, Double> salarySummaryByCurrency = personService.getPersonModel().getAllPersons()
                .stream()
                .collect(Collectors.groupingBy(
                        Person::getCurrency,
                        Collectors.summingDouble(person -> Double.parseDouble(person.getSalary()))
                ));

        model.addAttribute("salarySummaryByCurrency", salarySummaryByCurrency);
        return "index";
    }


    @GetMapping("/employees")
    public String listPerson(Model model, @RequestParam(required = false) String country) {
    List<Person> filteredPersons;
    if (country != null && !country.isEmpty()) {
        filteredPersons = personService.getPersonModel().getPersons().stream()
                .filter(person -> person.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    } else {
        filteredPersons = personService.getPersonModel().getPersons();
    }

    List<String> countries = personService.getPersonModel().getAllPersons().stream()
            .map(Person::getCountry)
            .distinct()
            .collect(Collectors.toList());

    model.addAttribute("persons", filteredPersons);
    model.addAttribute("countries", countries);
    return "employees/list";
    }


    @GetMapping("details/{id}")
    public String details(@PathVariable int id, Model model) {
        Optional<Person> person = personService.getPersonModel().getPersonById(id);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
        } else {
            model.addAttribute("error", "Person not found");
            return "employees/error";
        }
        return "employees/details";
    }

    @GetMapping("/details/email/{email}")
    public String getEmployeeDetailsByEmail(@PathVariable String email, Model model) {
        Optional<Person> personOpt = personService.getPersonByEmail(email);

        if (personOpt.isEmpty()) {
            model.addAttribute("message", "Employee with email " + email + " not found.");
            return "employees/error";  // Zwraca stronę z komunikatem o błędzie
        }

        model.addAttribute("person", personOpt.get());
        return "employees/details";  // Zwraca stronę z danymi pracownika
    }

}
