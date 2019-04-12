package com.example.demo.DAO;

import com.example.demo.Entities.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurDAO {

    public Utilisateur save(Utilisateur c);
    public List<Utilisateur> listUtilisateur();
    public boolean delete(Long id);
    public Utilisateur update(Long id, Utilisateur c);
    public Optional<Utilisateur> findByNumeroUtilisateur(Long id);

}
