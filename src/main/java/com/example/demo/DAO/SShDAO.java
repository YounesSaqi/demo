package com.example.demo.DAO;

import com.example.demo.Entities.SSHConnection;

import java.util.List;
import java.util.Optional;

public interface SShDAO {

    public SSHConnection saveSshConnection(SSHConnection c);
    public List<SSHConnection> listSshConnections();
    public boolean deleteBoolean(Long id);
    public SSHConnection updateSshConnection(Long id, SSHConnection c);
    public Optional<SSHConnection> findByNumeroSshConnection(Long id);
}
