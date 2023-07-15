package com.example.jsonprocessingex.repository;

import com.example.jsonprocessingex.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User as u " +
            "WHERE (SELECT COUNT(p) FROM Product AS p WHERE p.seller=u AND p.buyer IS NOT NULL ) > 0" +
            "ORDER BY u.lastName,u.firstName")
    List<User> getAllUsersWithSoldProduct();

    @Query("SELECT u FROM User as u " +
            "WHERE (SELECT COUNT(p) FROM Product AS p WHERE p.seller=u AND p.buyer IS NOT NULL ) > 0" +
            "ORDER BY size(u.soldProducts) DESC ,u.lastName")
    List<User> getAllUsersWithSoldProductOrderBySoldProducts();
}
