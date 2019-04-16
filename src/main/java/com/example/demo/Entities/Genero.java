package com.example.demo.Entities;

public class Genero {

    String from;
    String to;
    String file;

    public Genero() {
    }


    public Genero(String from, String to, String file) {
        this.from = from;
        this.to = to;
        this.file = file;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
