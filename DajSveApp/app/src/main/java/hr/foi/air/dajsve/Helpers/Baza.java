package hr.foi.air.dajsve.Helpers;
import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Filip on 24.1.2017..
 */

public class Baza extends AsyncTask<Void, Void, Connection>{
    Connection connection = null;
    String className = "net.sourceforge.jtds.jdbc.Driver";

    @Override
    protected Connection doInBackground(Void... params) {

        try {
            /* Povezivanje na bazu sa jtdsom 1.3.0
            DB = DajSve
            User = admin, Pass = admin
             10.0.3.2 - Genymotion
                 */
            Class.forName(className).newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://10.0.3.2;databaseName=DajSve;user=admin;password=admin;");
             System.out.print("Uspjesno spojeni na bazu");
            Statement stmt = connection.createStatement();
            ResultSet reset = stmt.executeQuery("select * from Korisnik");

            if (!reset.isBeforeFirst() ) {
                System.out.println("nema podataka");
            }else{
                System.out.println("ima podataka");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print("Greska pri spajanju na bazu");
        } catch (ClassNotFoundException e){
            System.out.print("Greska - klasa nije pronađena");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return connection;
    }

}