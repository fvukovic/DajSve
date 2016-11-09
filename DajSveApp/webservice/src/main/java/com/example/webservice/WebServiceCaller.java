package com.example.webservice;

import android.os.AsyncTask;
import android.os.StrictMode;
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

    public int GetDataFromWeb(){
        TextView Naziv[];
        TextView Id[];
        int a = 0;

        //ovaj primjer sam nasao na netu, treba parsirat XML
        //ali nisam siguran da li treba podatke parsirat u ovoj klasi ili u nekoj drugoj
        //probaj to skuzit


        try{
            String address = "http://www.dajsve.com/rss.ashx?svigradovi=1";
            URL gradoviXmlUrl = new URL(address);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(gradoviXmlUrl.openStream());

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Grad");

            Naziv = new TextView[nodeList.getLength()];

            List<Grad> gradoviLista = null;

            for(int i=0; i<nodeList.getLength(); i++){
                Element element = (Element) nodeList.item(i);
                NodeList nazivGrada = element.getElementsByTagName("Naziv");
                NodeList idGrada = element.getElementsByTagName("Id");
                Element nazivGradaElement = (Element) nazivGrada.item(i);
                Element idGradaElement = (Element) idGrada.item(i);
                String gradNaziv = nazivGradaElement.getAttribute("Naziv");

//                a = gradNaziv;



                /*Grad grad = null;
                grad.setNaziv(nazivGrada);
                grad.setId(idGradaElement);


                gradoviLista.add(idGradaElement, nazivGradaElement);*/
//
//
//                Node node = nodeList.item(i);
//
//                Naziv[i] = new TextView(this);
//                Id[i] = new TextView(this);
            }

            a = nodeList.getLength();
            //ovdje u varijablu zapisujem broj gradova, koje kasnije koristim samo za provjeru u main aktivitiju


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


        //ovdje vracam string koji pozivam u mainActivity, to mi je koristilo samo da vidim jel mi je preuzelo podatke iz xml-a (je, preuzelo ih je),
        //samo provjeravam dal ima onoliko gradova koliko ih je u xmlu
        return a;
    }

}
