package com.app;

import java.sql.Connection;
import com.app.Bdconnection;

public class Main {
    public static void main(String[] args) {

        try (Connection connection = Bdconnection.getConnection()) {
            System.out.println(" Connexion à PostgreSQL RÉUSSIE !");
        } catch (Exception e) {
            System.err.println(" Connexion échouée : " + e.getMessage());
        }
    }
}
