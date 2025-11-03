package br.edu.ifpe.pontoif.pontoif.entity;

public enum Role {
    ADMIN,
    SECRETARY,
    PROFESSOR,
    STUDENT;

    public String toAuthorize() {
        return "ROLE_"+ this.name();
    }
}
