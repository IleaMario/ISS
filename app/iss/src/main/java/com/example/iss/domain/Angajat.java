package com.example.iss.domain;
import javax.persistence.*;
import java.util.Objects;

import java.io.Serializable;

@Entity
@Table( name = "Angajati" )
@AttributeOverrides({
        @AttributeOverride(name="id", column = @Column(name="id"))
})
public class Angajat extends IEntity implements Serializable {

    @Column(name="nume")
    private String nume;
    @Column(name="ora_logare")
    private String oraLogare;
    @Column(name="status")
    private String status;
    @Column(name="parola")
    private String parola;


    // Constructor
    public Angajat(String nume, String oraLogare, String status, String parola) {
        this.nume = nume;
        this.oraLogare = oraLogare;
        this.status = status;
        this.parola = parola;
    }

    public Angajat() {

    }


    // Getteri și Setteri




    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
    public String getOraLogare() {
        return oraLogare;
    }

    public void setOraLogare(String oraLogare) {
        this.oraLogare = oraLogare;
    }
    //@Column(name="status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   // @Column(name="parola")
    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Angajat a)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getNume(), a.getNume()) && Objects.equals(getParola(), a.getParola() );
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNume(), getParola(), getOraLogare(),getStatus());
    }

    // Metodă toString pentru afisarea obiectului Angajat
    @Override
    public String toString() {
        return "Angajat{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", oraLogare=" + oraLogare +
                ", status='" + status + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}