package com.app;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        //  TEST 1 : tester la connexion PostgreSQL
        try {
            Bdconnection db = new Bdconnection();
            Connection connection = db.getDBConnection();

            System.out.println(" Connexion à PostgreSQL RÉUSSIE !");
        } catch (Exception e) {
            System.err.println(" Connexion échouée : " + e.getMessage());
        }

        //  TEST 2 : tester les méthodes DataRetriever
        DataRetriever dr = new DataRetriever();

        System.out.println("\n=== Toutes les catégories ===");
        System.out.println(dr.getAllCategories());

        System.out.println("\n=== ++++ Pagination page 1, size 5 ++++ ===");
        System.out.println(dr.getProductList(1, 5));

        System.out.println("\n=== ++++Recherche productName='laptop'++++ ===");
        System.out.println(
                dr.getProductsByCriteria("laptop", null, null, null)
        );

        System.out.println("\n=== +++++ Recherche categoryName='gaming' page 1 size 3 ++++++ ===");
        System.out.println(
                dr.getProductsByCriteria(null, "gaming", null, null)

        );
    }
}
