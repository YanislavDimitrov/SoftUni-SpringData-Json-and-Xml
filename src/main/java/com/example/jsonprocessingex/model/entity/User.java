package com.example.jsonprocessingex.model.entity;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "age")
    private Integer age;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(name = "users_friends"
            , joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends;

    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER)
    private Set<Product> soldProducts;

    public User() {
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<User> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(Set<Product> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
