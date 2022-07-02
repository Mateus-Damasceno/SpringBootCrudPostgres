package com.springbootcrudppostgres;

import com.springbootcrudppostgres.users.User;
import com.springbootcrudppostgres.users.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepo repo;

    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testAddNew(){
        User user =new User();
        user.setEmail("email222111@email.com");
        user.setPassword("passaword123");
        user.setFirstName("primeiro segundp nome");
        user.setLastName("ultimo dos ultimos nome");

        User savedUser = repo.save(user);

        User existUser = entityManager.find(User.class,savedUser.getId());

        Assertions.assertThat(existUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindUserbyEmail(){
        String email = "email2322@email.com";
       User user = repo.findByEmail(email);

        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void  testListAll(){
    Iterable<User> users =    repo.findAll();
    Assertions.assertThat(users).hasSizeGreaterThan(0);

       for(User user :users){
           System.out.println(user);
       }
    }

    @Test
    public void testUpdate(){

        Integer userId = 1;
        Optional <User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("senha123");
        repo.save(user);

        User updateUser = repo.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("senha123");
    }
    @Test
    public void testGet() {

        Integer userId = 5;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();

        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
        Integer userId=1;
        repo.deleteById(userId);

        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isNotPresent();
    }

}
