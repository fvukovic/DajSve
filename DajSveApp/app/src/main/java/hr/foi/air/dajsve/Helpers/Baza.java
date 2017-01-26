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

public class Baza {
    Connection connection = null;
    String className = "net.sourceforge.jtds.jdbc.Driver";


    public Baza() {
        try {
            /* Povezivanje na bazu sa jtdsom 1.3.0
            85.94.77.105
            DB = dajsveandroid
            User = dajsveappuser, Pass = Pa55word
             10.0.3.2 - Genymotion
                 */
            Class.forName(className).newInstance();
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://85.94.77.105;databaseName=dajsveandroid;user=dajsveappuser;password=Pa55word!1234;");
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

    }

    public ResultSet IzvrsiUpit(String upit) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet reset = stmt.executeQuery(upit);


        return reset;
    }

    public boolean Prijava(String nadimak, String lozinka) throws SQLException {
        try{
            String upit = "select * from Korisnik where nadimak ='" + nadimak + "' and lozinka ='" + lozinka +"';";

            ResultSet reset = IzvrsiUpit(upit);

            if (!reset.isBeforeFirst() ) {
                connection.close();
                return false;
            }else{
                connection.close();
                return true;
            }
        }catch (SQLException ex){
            connection.close();
            return false;
        }
    }

    public ResultSet DohvatiDnevnikZaKorisnika(String korisnik){


        //IzvrsiUpit(upit);

        return null;
    }

}