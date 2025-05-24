package com.curso.domains.dtos;

import com.curso.domains.Technician;
import com.curso.domains.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TechnicianDTO {

    protected Long idPerson;

    @NotNull(message = "O campo fistName não pode ser nulo")
    @NotBlank(message = "O capo fistName não pode ser vazio")
    protected String fistName;

    @NotNull(message = "O campo lastName não pode ser nulo")
    @NotBlank(message = "O campo lastName não poode ser vazio ")
    protected String lastName;

    @NotNull(message = "O campo cpf não pode ser nulo")
    @CPF
    protected String cpf;

    @NotNull(message = "O campo email não pode ser nulo")
    @NotBlank(message = "O campo email não pode ser vazio")
    protected String email;

    @NotNull(message = "O campo password não pode ser nulo")
    @NotBlank(message = "O campo password não pode ser vazio")
    protected String password;

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate createdAt = LocalDate.now();

    protected Set<Integer> personType = new HashSet<>();

    public TechnicianDTO() {
    }

    public TechnicianDTO(Technician obj) {
        this.idPerson = obj.getIdPerson();
        this.fistName = obj.getFirstName();
        this.lastName = obj.getLastName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.personType.stream().map(PersonType::toEnum).collect(Collectors.toSet());
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<PersonType> getPersonType() {
        return personType == null ? Collections.emptySet() :
                personType.stream().map(PersonType::toEnum).collect(Collectors.toSet());
    }

    public void addPersonType(PersonType personType) {
        this.personType.add(personType.getId());
    }

}
