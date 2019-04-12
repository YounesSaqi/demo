package com.example.demo.Controller;


import com.example.demo.DAO.UtilisateurDAO;
import com.example.demo.Entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UtilisateurDAO userDAO;

    @RequestMapping(value="", method= RequestMethod.GET)
    public List<Utilisateur> getusers(){
        return userDAO.listUtilisateur();
    }

    @RequestMapping(value="", method=RequestMethod.POST)
    public Utilisateur saveusers(@RequestBody Utilisateur c){
        return userDAO.save(c);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public boolean deleteClient(@PathVariable Long id){
        userDAO.delete(id);
        return true;
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Utilisateur UpdateClient(@PathVariable Long id ,@RequestBody Utilisateur c){

        return userDAO.update(id, c);
    }


    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Optional<Utilisateur> listClient(@PathVariable Long id ){
        return userDAO.findByNumeroUtilisateur(id);
    }

}
