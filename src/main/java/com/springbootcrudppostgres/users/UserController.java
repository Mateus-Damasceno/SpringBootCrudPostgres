package com.springbootcrudppostgres.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired private UserService service;

    @GetMapping("/users")
    public String showUserList (Model model){
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers",listUsers);
        return "users";
    }

    @GetMapping ("/users/new")
    public String showNewForm (Model model){
        model.addAttribute("user",new User());
        model.addAttribute("pageTitle","Adiciona Novo Usuario");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes ra){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String endcodedPassword = encoder.encode(user.getPassword());
        user.setPassword(endcodedPassword);
        service.save(user);
        ra.addFlashAttribute("message","usuario salvo com sucesso");

        return "succeed";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra){
        try {
            User user = service.get(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle","Editar Usuario (" + id +")");
            return "user_form";
        } catch (userNotFoundException e) {
            ra.addFlashAttribute("message","usuario salvo com sucesso");
           return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUsers(@PathVariable("id") Integer id, RedirectAttributes ra){
        try {
           service.delete(id);
            ra.addFlashAttribute("message","O usuario"+ id + " foi excluido");

        } catch (userNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
        }
        return "redirect:/users";
    }


}
