package com.example.iss.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table( name = "Sarcini" )
@AttributeOverrides({
        @AttributeOverride(name="id", column = @Column(name="id"))
})
public class Sarcina extends IEntity implements Serializable {

    @Column(name="id_angajat")
    private Integer id_angajat;
    @Column(name="descriere")
    private String descriere;



    // Constructor
    public Sarcina(Integer id_angajat,String descriere) {
        this.id_angajat=id_angajat;
        this.descriere=descriere;
    }

    public Sarcina(){

    }

    // Getteri și Setteri




    public Integer getId_angajat() {
        return id_angajat;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String des) {
        this.descriere = des;
    }



    @Override
    public boolean equals(Object o) {
return false;
    }

    @Override
    public int hashCode(){
        return 0;
    }

    // Metodă toString pentru afisarea obiectului Angajat
    @Override
    public String toString() {
        return "Sarcina{" +
                "id=" + id +
                ", descriere='" + descriere + '\'' +

                '}';
    }

    public void setId_angajat(Integer idAngajat) {
        this.id_angajat=idAngajat;
    }
}