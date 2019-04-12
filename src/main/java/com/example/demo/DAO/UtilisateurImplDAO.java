package com.example.demo.DAO;

import com.example.demo.Entities.Utilisateur;
import com.example.demo.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UtilisateurImplDAO implements UtilisateurDAO {


    @Autowired
    private UtilisateurRepository user;
    @Override
    public Utilisateur save(Utilisateur c) {
        return user.save(c);
    }

    @Override
    public List<Utilisateur> listUtilisateur() {
        return user.findAll();
    }

    @Override
    public boolean delete(Long id) {
        user.deleteById(id);
        return true;
    }

    @Override
    public Utilisateur update(Long id, Utilisateur c) {
        c.setId(id);
        return user.save(c);
    }

    @Override
    public Optional<Utilisateur> findByNumeroUtilisateur(Long id) {
        return user.findById(id);
    }
}
