package com.example.iss;

import com.example.iss.repo.RepoAngajati;
import com.example.iss.repo.RepoHBAngajati;
import com.example.iss.repo.RepoHBSarcini;
import com.example.iss.repo.RepoSarcini;
import com.example.iss.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HelloApplication extends Application {


    private static SessionFactory sessionFactory;

    private static void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    private static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }


    @Override
    public void start(Stage stage) throws IOException {
        RepoAngajati repoAngajati = new RepoHBAngajati(sessionFactory);
        RepoSarcini repoSarcini = new RepoHBSarcini(sessionFactory);

        Service service = new Service(repoAngajati,repoSarcini);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("window-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Log in page");
        stage.setScene(scene);

        WindowLogin loginController = fxmlLoader.getController();
        loginController.setUp(service);
        stage.show();
    }

    public static void main(String[] args) {
        setUp();
        launch();
    }
}