package com.example.webservice;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.Settings;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import entities.Grad;

/**
 * Created by Filip on 9.11.2016..
 */

public class WebServiceCaller{

    public String GetDataFromWeb(){
        TextView Naziv[];
        TextView Id[];
        int a = 0;

        //Rijesno parsiranje xmla gradovi, provjerit gdje se parsiraju ovi podaci. Da li u ovoj klasi ili negdje drugdje..

        try{
            String address = "http://www.dajsve.com/rss.ashx?svigradovi=1";
            URL gradoviXmlUrl = new URL(address);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(gradoviXmlUrl.openStream());
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("Grad");
            Naziv = new TextView[nodeList.getLength()];
            List<Grad> citiesList = new ArrayList<Grad>();

            for(int i=0; i<nodeList.getLength(); i++){

                // Nalazim trenutni element u xmlu Gradovi i tražim čelije po tagovima Naziv i Id te ih spremam u listu

                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList cityList = fstElmnt.getElementsByTagName("Naziv");
                Element nameElement = (Element) cityList.item(0);
                cityList = nameElement.getChildNodes();
                String townName =  ("Name = " + ((Node) cityList.item(0)).getNodeValue());

                NodeList idList = fstElmnt.getElementsByTagName("Id");
                Element idElement = (Element) idList.item(0);
                idList = idElement.getChildNodes();
                String cityID=  (((Node) idList.item(0)).getNodeValue());
                Grad listElement = new Grad( Integer.parseInt(cityID),townName);
                citiesList.add(listElement);

            }

            //Provjera liste, ispis liste

            for(Grad item : citiesList){
                System.out.println(item.getNaziv());
            }



        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch (ParserConfigurationException pce){
            pce.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch(SAXException se){
            se.printStackTrace();
        }
        //ove exceptione moramo imat jer inace javlja gresku, i moras imat try catch


        return "";

    }


}
