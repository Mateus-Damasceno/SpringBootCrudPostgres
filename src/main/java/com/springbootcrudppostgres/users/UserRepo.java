package com.springbootcrudppostgres.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User,Integer> {
    public Long countById(Integer id);

@Query("SELECT u FROM User u where u.email=?1")
    User findByEmail (String email);


}
