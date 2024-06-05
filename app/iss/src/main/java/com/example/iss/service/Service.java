package com.example.iss.service;

import com.example.iss.WindowAngajat;
import com.example.iss.WindowSef;
import com.example.iss.domain.Angajat;
import com.example.iss.domain.IEntity;
import com.example.iss.domain.Sarcina;
import com.example.iss.repo.RepoAngajati;
import com.example.iss.repo.RepoSarcini;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service<ID,E extends IEntity> {
    private RepoAngajati repoAngajati;
    private RepoSarcini repoSarcini;
    private List<WindowAngajat> angajati_logati=new ArrayList<WindowAngajat>();
    private WindowSef sef;

    public Service(RepoAngajati repoAngajati,RepoSarcini repoSarcini) {
        this.repoAngajati = repoAngajati;
        this.repoSarcini = repoSarcini;
    }

    public Angajat login(String nume, String parola) {
        System.out.println(nume+" "+parola);

        Iterable<Angajat> angajati = repoAngajati.getAll();
        for (Angajat a : angajati){
            System.out.println(a.getNume()+" "+a.getParola());

            if (Objects.equals(a.getNume(), nume) && Objects.equals(a.getParola(), parola))
                return a;}
        return null;
    }
    public void modifica_status(Angajat a){
        repoAngajati.update(a.getId(),a);
    }


    public Iterable<Sarcina> getSarcini() {
        return repoSarcini.getAll();
    }
    public void saveSarcina(Sarcina sarcina){
        repoSarcini.save(sarcina);

    }

    public void updateSarcina(Sarcina sarcina){
        repoSarcini.update(sarcina.getId(),sarcina);
    }



    public void deleteSarcina(Sarcina selectedSarcina) {
        repoSarcini.delete(selectedSarcina.getId());
    }

    public void logheaza_angajat(WindowAngajat windowAngajat){
        angajati_logati.add(windowAngajat);

        LocalTime currentTime = LocalTime.now();

        // Formatează ora ca șir de caractere
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String oraLogare = currentTime.format(formatter);

        Angajat angajat = windowAngajat.getAngajat();
        Angajat ag = new Angajat(angajat.getNume(),oraLogare,"activ",angajat.getParola());
        ag.setId(angajat.getId());

        repoAngajati.update(angajat.getId(),ag);


    }

    public void delogheaza_angajat(WindowAngajat windowAngajat){
        System.out.println("Am delogat angajatul");
        Angajat angajat = windowAngajat.getAngajat();
        Angajat ag = new Angajat(angajat.getNume(),angajat.getOraLogare(),"inactiv",angajat.getParola());
        ag.setId(angajat.getId());

        repoAngajati.update(angajat.getId(),ag);
        angajati_logati.remove(windowAngajat);
        sef.loadAngajatiLogati();
    }

    public Iterable<Angajat> getAngajatiLogati(){
        List<Angajat> angajati=new ArrayList<Angajat>();
        for(WindowAngajat w:angajati_logati){
            angajati.add(w.getAngajat());
        }
        return angajati;
    }

    public void setSef(WindowSef windowSef) {
        this.sef = windowSef;
    }

    public void update(){
        this.sef.loadSarcini();
    }

    public void updateAngajati() {
        for(WindowAngajat windowAngajat:angajati_logati){
            windowAngajat.load();
        }
    }
}