package com.example.demo.Entities;

public class Export {


    String instance;
    String userBd;
    String passwdBd;
    String nomObjExport;
    String database;
    String sid;
    String cheminExport;
    String typeExport;
    String typeBd;


    String nomObjetAexporter;
    String nomDump;

    public String getNomObjExport() {
        return nomObjExport;
    }

    public void setNomObjExport(String nomObjExport) {
        this.nomObjExport = nomObjExport;
    }




    public Export() {
    }

    public Export(String user,String password, String sid,String typeBd,String nomDump) {
        this.userBd=user;
        this.passwdBd=password;
        this.typeBd=typeBd;
        this.sid=sid;
        this.nomDump=nomDump;
    }
    public Export(String user,String password, String instance,String database,String typeBd,String nomDump) {
        this.userBd=user;
        this.passwdBd=password;
        this.typeBd=typeBd;
        this.instance=instance;
        this.database=database;
        this.nomDump=nomDump;
    }



    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getNomDump() {
        return nomDump;
    }

    public void setNomDump(String nomDump) {
        this.nomDump = nomDump;
    }

    public String getUserBd() {
        return userBd;
    }

    public void setUserBd(String userBd) {
        this.userBd = userBd;
    }


    public String getPasswdBd() {
        return passwdBd;
    }

    public void setPasswdBd(String passwdBd) {
        this.passwdBd = passwdBd;
    }

    public String getTypeBd() {
        return typeBd;
    }

    public void setTypeBd(String typeBd) {
        this.typeBd = typeBd;
    }

    public String getTypeExport() {
        return typeExport;
    }

    public void setTypeExport(String typeExport) {
        this.typeExport = typeExport;
    }
    public String getCheminExport() {
        return cheminExport;
    }

    public void setCheminExport(String cheminExport) {
        this.cheminExport = cheminExport;
    }

    public String getNomObjetAexporter() {
        return nomObjetAexporter;
    }

    public void setNomObjetAexporter(String nomObjetAexporter) {
        this.nomObjetAexporter = nomObjetAexporter;
    }
}