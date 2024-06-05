package com.example.iss;

import com.example.iss.domain.Angajat;
import com.example.iss.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
public class WindowLogin {

    private Service service;
    @FXML
    public TextField numeField;
    @FXML
    public PasswordField parolaField;


    public void setUp(Service service) {
        this.service = service;
    }


    public void initialialize() throws IOException {
        try {


            String user = numeField.getText();
            String parola = parolaField.getText();
            Angajat l = service.login(user,parola);
            System.out.println("incerc sa loghez angajatul cu numele "+user+" si parola "+parola);


            if(l==null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Eroare!");
                alert.setHeaderText(null);
                alert.setContentText("Creditentiale incorecte sau invalide.");

                // Afișează mesajul
                alert.showAndWait();

            }

            if(l!=null && Objects.equals(l.getStatus(), "sef")) {
                System.out.println("se deschide fereastra sefului");
                FXMLLoader fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("window-sef.fxml"));
                //WindowAngajat windowAngajat = fxmlLoaderAdmin.getController();
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Sef " + l.getNume());

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(scene);
                WindowSef windowSef = fxmlLoader.getController();
                service.setSef(windowSef);



                // Formatează ora ca șir de caractere


                windowSef.setUp(l, service,dialogStage);



                dialogStage.show();


            }

            if(l!=null && !Objects.equals(l.getStatus(), "sef")) {
                System.out.println(l.getStatus());
                FXMLLoader fxmlLoader= new FXMLLoader(HelloApplication.class.getResource("window-angajat.fxml"));
                //WindowAngajat windowAngajat = fxmlLoaderAdmin.getController();
                Stage stage = new Stage();
                Scene scene = new Scene(fxmlLoader.load(), 600, 400);
                stage.setScene(scene);

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Angajat " + l.getNume());

                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.setScene(scene);
                WindowAngajat windowAngajat = fxmlLoader.getController();


                System.out.println("intru in loghin");
                LocalTime currentTime = LocalTime.now();

                // Formatează ora ca șir de caractere
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                String oraLogare = currentTime.format(formatter);

                l.setStatus("activ");
                l.setOraLogare(oraLogare);
                windowAngajat.setUp(l, service,dialogStage);

                service.logheaza_angajat(windowAngajat);

                dialogStage.show();


            }
            System.out.println("nu intru in loghin");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
