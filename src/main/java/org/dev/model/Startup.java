package org.dev.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="startups")
public class Startup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="title")
    private String title;

    @Column(name="description")
    private String description;

    @Column(name="users")
    @OneToMany(mappedBy = "startup", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<StartupUser> listStartupUsers = new ArrayList<>();

    public Startup(){}
    public Startup(String title, String description, List<StartupUser> listStartupUsers) {
        this.title = title;
        this.description = description;
        this.listStartupUsers = listStartupUsers;
    }

    @Override
    public String toString() {
        return "Startup{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", listStartupUsers=" + listStartupUsers +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<StartupUser> getListStartupUsers() {
        return listStartupUsers;
    }

    public void setListStartupUsers(List<StartupUser> listStartupUsers) {
        this.listStartupUsers = listStartupUsers;
    }
}
