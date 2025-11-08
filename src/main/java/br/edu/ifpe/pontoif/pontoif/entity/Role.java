package br.edu.ifpe.pontoif.pontoif.entity;

public enum Role {
    ADMIN,
    TEACHER,
    SECRETARY,
    STUDENT;

    public String toAuthorize() {
        return "ROLE_"+ this.name();
    }
}
