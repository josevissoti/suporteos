package com.curso.domains;

import com.curso.domains.dtos.UserDTO;
import com.curso.domains.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User extends Person {

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<ServiceOrder> serviceOrders = new ArrayList<>();

    public User(Long idPerson, String firstName, String lastName, String cpf, String email, String password) {
        super(idPerson, firstName, lastName, cpf, email, password);
        addPersonType(PersonType.USER);
    }

    public User() {
        super();
        addPersonType(PersonType.USER);
    }

    public User(UserDTO obj) {
        this.idPerson = obj.getIdPerson();
        this.firstName = obj.getFistName();
        this.lastName = obj.getLastName();
        this.cpf = obj.getCpf();
        this.email = obj.getEmail();
        this.password = obj.getPassword();
        this.createdAt = obj.getCreatedAt();
        this.personType = obj.getPersonType().stream()
                .map(x -> x.getId()).collect(Collectors.toSet());
        addPersonType(PersonType.USER);
    }

    public List<ServiceOrder> getServiceOrders() {
        return serviceOrders;
    }

    public void setServiceOrders(List<ServiceOrder> serviceOrders) {
        this.serviceOrders = serviceOrders;
    }
}

