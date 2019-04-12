package com.example.demo.DAO;

import com.example.demo.Entities.SSHConnection;
import com.example.demo.Repository.SShRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SShImplDAO implements SShDAO {

    @Autowired
    SShRepository ssh;

    @Override
    public SSHConnection saveSshConnection(SSHConnection c) {
        return ssh.save(c);
    }

    @Override
    public List<SSHConnection> listSshConnections() {
        return ssh.findAll();
    }

    @Override
    public boolean deleteBoolean(Long id) {
        ssh.deleteById(id);
        return true ;
    }

    @Override
    public SSHConnection updateSshConnection(Long id, SSHConnection c) {
        c.setId(id);
        return ssh.save(c);
    }

    @Override
    public Optional<SSHConnection> findByNumeroSshConnection(Long id) {
        return ssh.findById(id);
    }
}
