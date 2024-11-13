package com.techcorp.employeemanagment.service;

import com.techcorp.employeemanagment.domain.Person;
import com.techcorp.employeemanagment.domain.PersonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class PersonService {
    private final CompanyFix companyFix;
    private final PersonModel personModel;

    @Autowired
    public PersonService(CompanyFix companyFix,
                         @Qualifier("president") Person president,
                         @Qualifier("vicePresident") Person vicePresident,
                         @Qualifier("secretary") Person secretary) {
        this.companyFix = companyFix;
        this.personModel = new PersonModel();

        president.setId(personModel.getPersons().size() + 1);
        vicePresident.setId(personModel.getPersons().size() + 2);
        secretary.setId(personModel.getPersons().size() + 3);

        personModel.addPerson(president);
        personModel.addPerson(vicePresident);
        personModel.addPerson(secretary);
    }

    public PersonModel getPersonModel() {
        return personModel;
    }

    public Optional<Person> getPersonByEmail(String email) {
        return personModel.getPersons().stream()
                .filter(person -> person.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }



    public void loadPersonsFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Pomijanie nagłówka
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String firstName = data[0];
                String lastName = data[1];
                String email = data[2];
                String salary = data[3];
                String currency = data[4];
                String country = data[5];
                String company = companyFix.extractCompanyFromEmail(email);

                // Tworzenie nowego obiektu Person z pełnymi danymi
                Person person = new Person(firstName, lastName, email, company, salary, currency, country);
                personModel.addPerson(person); // Dodanie do modelu
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
