package com.app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class Bdconnection{
    private static String url;
    private static String user;
    private static String password;


    static {
        try {
            Properties props = new Properties();

            InputStream input = Bdconnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if(input == null){
                throw new RuntimeException("Fichier db.properties introuvable dan resoures/");

            }
            props.load(input);


            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

            Class.forName("org.postgresql.Driver");

        }
        catch(Exception e){
            throw new RuntimeException("Erreur lors du chargement DB",e);
        }
    }
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(url,user,password);
        }
        catch(SQLException e){
            throw new RuntimeException("Impossible de se connecter à la base Postgresql",e);
        }

    }


}
