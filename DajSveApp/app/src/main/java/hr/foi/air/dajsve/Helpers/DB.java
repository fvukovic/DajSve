package hr.foi.air.dajsve.Helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Filip on 25.1.2017..
 */

public class DB {

    Connection connection = null;
    String ipAdresa = "192.168.10.236";
    String db = "DajSve";
    String username = "root";
    String password = null;
    String className = "net.sourceforge.jtds.jdbc.Driver";

    public void SpojiDB(){
        try {

            Class.forName(className).newInstance();
//            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://10.0.2.2/DajSve;");
            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://10.0.3.2/DajSve;");
//            connection = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost:1433/DajSve;encrypt=false;user=root;password=null;instance=DajSve;");

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

}
