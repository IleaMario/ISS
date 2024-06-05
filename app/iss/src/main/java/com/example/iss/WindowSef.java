package com.example.iss;

import com.example.iss.domain.Angajat;
import com.example.iss.domain.Sarcina;
import com.example.iss.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Objects;

public class WindowSef {

    @FXML
    Stage stage;
    private Angajat angajat;
    private Service service;
    @FXML
    ListView<Sarcina> lista_sarcini;
    @FXML
    TableView<Angajat> tabel_angajati;

    @FXML
    TableColumn<Sarcina, String> col_descriere;
    @FXML
    TableColumn<Angajat, String> col_nume;
    @FXML
    TableColumn<Angajat, String> col_ora;
    @FXML
    TableColumn<Sarcina, String> col_sarcina;
    @FXML
    private TextArea descriere_sarcina;

    private final ObservableList<Angajat> obs_lst = FXCollections.observableArrayList();
    private final ObservableList<Sarcina> obs_lst2 = FXCollections.observableArrayList();


    public void setUp(Angajat angajat, Service service,Stage stage) {
        this.angajat=angajat;
        this.stage=stage;
        this.service=service;


        loadAngajatiLogati();
        loadSarcini();


    }
    public void loadAngajatiLogati(){
        Iterable<Angajat> angajati = service.getAngajatiLogati();

        col_nume.setCellValueFactory(new PropertyValueFactory<Angajat, String>("nume"));
        col_ora.setCellValueFactory(new PropertyValueFactory<Angajat, String>("oraLogare"));


        tabel_angajati.setItems(obs_lst);

        tabel_angajati.getItems().clear();
        for (Angajat s : angajati) {
            System.out.println("angajat logat "+s.getNume());

                tabel_angajati.getItems().add(s);


        }
        if (tabel_angajati.getItems().size() > 0)
            tabel_angajati.getSelectionModel().select(0);

    }

    public void atribuie_sarcina(){
        Angajat selectedAngajat = tabel_angajati.getSelectionModel().getSelectedItem();
        String descriere = descriere_sarcina.getText();
        Sarcina sarcina = new Sarcina(selectedAngajat.getId(),descriere);
        service.saveSarcina(sarcina);
        loadSarcini();
        service.updateAngajati();


    }

    public void loadSarcini() {

        Iterable<Sarcina> sarcini = service.getSarcini();

        lista_sarcini.setItems(obs_lst2);

        lista_sarcini.getItems().clear();
        for (Sarcina s : sarcini) {
            System.out.println("angajat logat "+s.getDescriere());
            lista_sarcini.getItems().add(s);
    }


    }
    public void stergeSarcina(){
        Sarcina selectedItem = lista_sarcini.getSelectionModel().getSelectedItem();
        service.deleteSarcina(selectedItem);
        loadSarcini();
        service.updateAngajati();

    }

    public void modificaSarcina(ActionEvent actionEvent) {
        Sarcina selectedItem = lista_sarcini.getSelectionModel().getSelectedItem();
        String descriere_noua = descriere_sarcina.getText();
        Sarcina s = new Sarcina(selectedItem.getId_angajat(),descriere_noua);
        s.setId(selectedItem.getId());
        service.updateSarcina(s);
        loadSarcini();
        service.updateAngajati();


    }
}
