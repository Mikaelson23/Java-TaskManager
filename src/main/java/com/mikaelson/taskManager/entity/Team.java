package com.mikaelson.taskManager.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idTeam;
    private String teamName;
    @ManyToOne
    private User manager;
    @ManyToMany
    private List<User> usuarios;

    public Long getIdTeam() {
        return idTeam;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void addUser(User user){
        this.usuarios.add(user);
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public List<User> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<User> usuarios) {
        this.usuarios = usuarios;
    }
}
