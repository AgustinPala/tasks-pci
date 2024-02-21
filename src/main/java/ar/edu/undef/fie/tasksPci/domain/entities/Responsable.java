package ar.edu.undef.fie.tasksPci.domain.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "responsable")
public class Responsable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String slackUser;
    private String role;

    // constructors
    public Responsable() {
    }

    public Responsable(String name, String slackUser, String role) {
        this.name = name;
        this.slackUser = slackUser;
        this.role = role;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlackUser() {
        return slackUser;
    }

    public void setSlackUser(String slackUser) {
        this.slackUser = slackUser;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}