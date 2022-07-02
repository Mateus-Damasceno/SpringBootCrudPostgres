package com.springbootcrudppostgres.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;


    public List<User> listAll(){
    return (List<User>) repo.findAll();
}

    public void save(User user) {


        repo.save(user);
    }

    public User get(Integer id) throws userNotFoundException {
    Optional<User> result=repo.findById(id);
    if(result.isPresent()){
        return result.get();
    }
        throw new userNotFoundException("nao encontrou o usuario" + id);
    }

    public void delete(Integer id) throws userNotFoundException {
       Long count= repo.countById(id);
       if (count == null || count==0){
           throw new userNotFoundException("nao encontrou o usuario" + id);
       }
       repo.deleteById(id);
    }
}