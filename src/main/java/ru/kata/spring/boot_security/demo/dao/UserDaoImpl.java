package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;
    private final PasswordEncoder encoder;
    private final RolesDao rolesDao;

    public UserDaoImpl(PasswordEncoder encoder, RolesDao rolesDao) {
        this.encoder = encoder;
        this.rolesDao = rolesDao;
    }


    @Override
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public boolean updateUser(User user) {
        if (getUserById(user.getId()) != null) {
            user.setPassword(encoder.encode(user.getPassword()));
            entityManager.merge(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User findByUsername(String userName) {
        return entityManager.createQuery(
                        "SELECT u from User u  join fetch u.roles WHERE u.userName = :username", User.class).
                setParameter("username", userName).getSingleResult();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }



    @Override
    public boolean deleteUser(int id) {
        if (getUserById(id) != null) {
            entityManager.remove(getUserById(id));
            return true;
        }
        return false;
    }
}
