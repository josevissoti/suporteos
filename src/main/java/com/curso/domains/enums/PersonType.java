package com.curso.domains.enums;

public enum PersonType {

    ADMIN(0, "ROLE_ADMIN"),
    USER(1, "ROLE_USER"),
    TECHNICIAN(2, "ROLE_TECNICIAN");

    private Integer id;
    private String personType;

    PersonType(Integer id, String personType) {
        this.id = id;
        this.personType = personType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public static PersonType toEnum(Integer id) {
        if (id == null) return null;
        for (PersonType personType : PersonType.values()) {
            if (id.equals(personType.getId())) {
                return personType;
            }
        }
        throw new IllegalArgumentException("Perfil inválido");
    }
}

