package org.example.backend.service;

import org.example.backend.entity.Movie;
import org.example.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;

/**
 * Created by arcuri82 on 13-Dec-17.
 * https://github.com/arcuri82/testing_security_development_enterprise_systems
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean createUser(String username, String password) {

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, username) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRoles(Collections.singleton("USER"));
        user.setEnabled(true);

        em.persist(user);

        return true;
    }

    public User getUserByUsername(String username) {
        Query query = em.createQuery("select u from User u WHERE u.username=?1");
        query.setParameter(1, username);

        User result = (User) query.getSingleResult();

        return result;
    }
}
