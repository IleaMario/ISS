package com.example.iss;

import com.example.iss.domain.Angajat;
import com.example.iss.domain.Sarcina;
import com.example.iss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Objects;

public class WindowAngajat {

    @FXML
    Stage stage;
    private Angajat angajat;
    private Service service;
    @FXML
    TableView<Sarcina> tabel_sarcini;
    @FXML
    TableColumn<Sarcina, String> col_descriere;
    @FXML
    private TextField descriere_text;

    private final ObservableList<Sarcina> obs_lst = FXCollections.observableArrayList();

    public void setUp(Angajat angajat, Service service,Stage stage) {
        this.angajat=angajat;
        this.stage=stage;
        this.service=service;
        angajat.setStatus("activ");
        service.modifica_status(angajat);

        load();


    }

    public Angajat getAngajat(){
        return this.angajat;
    }

    public void load() {

        col_descriere.setCellValueFactory(new PropertyValueFactory<Sarcina, String>("descriere"));


        tabel_sarcini.setItems(obs_lst);

        Iterable<Sarcina> sarcini = service.getSarcini();
        tabel_sarcini.getItems().clear();
        for (Sarcina s : sarcini) {
            if (Objects.equals(s.getId_angajat(), angajat.getId())) {
                tabel_sarcini.getItems().add(s);

            }
        }
        if (tabel_sarcini.getItems().size() > 0)
            tabel_sarcini.getSelectionModel().select(0);


    }

    public void logout(){
        stage.close();
        service.delogheaza_angajat(this);
    }

        public void rezolva_sarcina() {
            Sarcina selectedSarcina = tabel_sarcini.getSelectionModel().getSelectedItem();
            if (selectedSarcina != null) {
                descriere_text.setText(selectedSarcina.getDescriere());
                service.deleteSarcina(selectedSarcina);

                tabel_sarcini.getItems().remove(selectedSarcina);
                load();
                service.update();

            } else {
                System.out.println("Nu este selectată nicio sarcină.");
            }
        }


}
