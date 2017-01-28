package hr.foi.air.dajsve.Helpers;
import android.app.Activity;
import android.os.AsyncTask;

import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.os.Bundle;
import android.provider.Settings.Secure;


/**
 * Created by Filip on 24.1.2017..
 */

public class Baza extends Activity{
    Connection connection = null;
    String className = "net.sourceforge.jtds.jdbc.Driver";
    public String android_id = "";

    public Baza() {
    }

    public Baza(String android_id) {
        try {
            /* Povezivanje na bazu sa jtdsom 1.3.0
            85.94.77.105
            DB = dajsveandroid
            User = dajsveappuser, Pass = Pa55word
             10.0.3.2 - Genymotion
                 */
            this.android_id = android_id;

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
//        upit = "select count(*) as broj from Dnevnik GROUP BY dodatneInformacije, tipZapisa having tipZapisa = 5 and dodatneInformacije = 'ffaletar';";
        Statement stmt = connection.createStatement();
        ResultSet reset = stmt.executeQuery(upit);
//        int brojOtvaranja = 0;
//        while(reset.next()){
//            brojOtvaranja = reset.getInt("broj");
//        }
        return reset;
    }

    public void Upisi(String upit) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(upit);
    }

    public boolean Prijava(String nadimak, String lozinka) throws SQLException {
        try{
            String upit = "select * from Korisnik where nadimak ='" + nadimak + "' and lozinka ='" + lozinka +"';";

            ResultSet reset = IzvrsiUpit(upit);

            if (!reset.isBeforeFirst() ) {
                ZapisiUDnevnik(5, android_id, "Neuspješna prijava", nadimak, 1);
                return false;
            }else{
                ZapisiUDnevnik(5, android_id, "Uspješna prijava", nadimak, 1);
                return true;
            }
        }catch (SQLException ex){
            ZapisiUDnevnik(5, android_id, "Neuspješna prijava", nadimak, 1);
            return false;
        }
    }

    public void ZapisiUDnevnik(int tipZapisa, String korisnikID, String opis, String dodatneInformacije, int status){
        String upit = "insert into Dnevnik(tipZapisa, korisnikID,opis,vrijeme,dodatneInformacije,status) values(" + tipZapisa +",'"+korisnikID+"','"+opis+"','"+trenutnoVrijeme()+"','"+dodatneInformacije+"',"+status+");";
        try{
            Upisi(upit);
        }catch (SQLException ex){
            System.out.println("Zapis nije zapisan u dnevnik");
        }
    }


    public static Timestamp trenutnoVrijeme() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
        return currentTimestamp;
    }

    public Integer DohvatiBrojOtvaranjaPonude(int tipZapisa,String nazivPonude){
        try{
//            String upit = "select count(*) from Dnevnik where tipZapisa = " + tipZapisa + " and dodatneInformacije ='" + nazivPonude +"';";
            String upit2 = "select count(*) as broj from Dnevnik GROUP BY dodatneInformacije, tipZapisa having tipZapisa = "+tipZapisa+" and dodatneInformacije = '"+nazivPonude+"';";
            ResultSet reset = IzvrsiUpit(upit2);
            int brojOtvaranja = 0;
            while(reset.next()){
                brojOtvaranja = reset.getInt("broj");
            }
            return brojOtvaranja;
        }catch (SQLException ex){
            return 0;
        }
    }

}