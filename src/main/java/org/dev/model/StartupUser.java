package org.dev.model;

import jakarta.persistence.*;

@Entity
@Table(name="startup_users")
public class StartupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user.id")
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "startup.id")
    private Startup startup;

    @Column(name = "is_author")
    private boolean isAuthor;

    public StartupUser(){}
    public StartupUser(User user, Startup startup, boolean isAuthor) {
        this.user = user;
        this.startup = startup;
        this.isAuthor = isAuthor;
    }

    @Override
    public String toString() {
        return "StartupUser{" +
                "id=" + id +
                ", user=" + user +
                ", startup=" + startup +
                ", isAuthor=" + isAuthor +
                '}';
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Startup getStartup() {
        return startup;
    }

    public void setStartup(Startup startup) {
        this.startup = startup;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }
}
