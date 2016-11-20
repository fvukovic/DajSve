package com.example.webservice;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import entities.Grad;
import entities.Ponuda;

/**
 * Created by Filip on 9.11.2016..
 */

public class WebServiceCaller{

    WebServiceHandler webServiceHandler;

    public WebServiceCaller(WebServiceHandler webServiceHandler) {
        this.webServiceHandler = webServiceHandler;
    }

    public void dohvatiSve(final Type entityType){

        if(entityType == Ponuda.class)
            try {
                String address = "http://www.dajsve.com/rss.ashx?svigradovi";
                URL ponudeXmlUrl = new URL(address);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(ponudeXmlUrl.openStream());
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("item");
                System.out.println("****** Ukupno dohvacenih ponuda: "+nodeList.getLength());

                handlePonude(nodeList);

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (ParserConfigurationException pce){
                pce.printStackTrace();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }catch(SAXException se){
                se.printStackTrace();
            }
        else if(entityType == Grad.class)
            try{
                String address = "http://www.dajsve.com/rss.ashx?svigradovi=1";
                URL gradoviXmlUrl = new URL(address);

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(gradoviXmlUrl.openStream());
                doc.getDocumentElement().normalize();
                NodeList nodeList = doc.getElementsByTagName("Grad");

                handleGradovi(nodeList);

            } catch(MalformedURLException e){
                e.printStackTrace();
            } catch (ParserConfigurationException pce){
                pce.printStackTrace();
            }catch(IOException ioe){
                ioe.printStackTrace();
            }catch(SAXException se){
                se.printStackTrace();
            }
    }

    private void handleGradovi(NodeList nodeList) {
        List<Grad> citiesList = new ArrayList<Grad>();

        for(int i=0; i<nodeList.getLength(); i++){

            Node node = nodeList.item(i);
            Element fstElmnt = (Element) node;
            NodeList cityList = fstElmnt.getElementsByTagName("Naziv");
            Element nameElement = (Element) cityList.item(0);
            cityList = nameElement.getChildNodes();
            String townName =  ( ((Node) cityList.item(0)).getNodeValue());

            NodeList idList = fstElmnt.getElementsByTagName("Id");
            Element idElement = (Element) idList.item(0);
            idList = idElement.getChildNodes();
            String cityID=  (((Node) idList.item(0)).getNodeValue());
            Grad listElement = new Grad( Integer.parseInt(cityID),townName);
            citiesList.add(listElement);

        }

        if(webServiceHandler != null){
            webServiceHandler.onDataArrived(citiesList, true);
        }

    }

    private void handlePonude(NodeList nodeList){
        List<Ponuda> ponudaLista = new ArrayList<>();

        String tekstPonude = "nema podataka";
        String cijenaPonude = "0";
        String popust = "0";
        String cijenaorg = "0";
        String urlSlike = "nema podataka";
        String usteda = "0";
        String kategorija = "nemapodataka";
        String grad = "nema podataka";
        String datum = "nema podataka";

        for(int i=0; i<nodeList.getLength(); i++){

            Node node = nodeList.item(i);
            Element fstElmnt = (Element) node;

            NodeList ponudaList = fstElmnt.getElementsByTagName("TekstPonude");
            Element tekstElement = (Element) ponudaList.item(0);
            if (tekstElement.getChildNodes().getLength() > 0){
                ponudaList = tekstElement.getChildNodes();
                tekstPonude =  ( ponudaList.item(0).getNodeValue());
            }

            NodeList cijenaList = fstElmnt.getElementsByTagName("Cijena");
            Element cijenaElement = (Element) cijenaList.item(0);
            if (cijenaElement.getChildNodes().getLength() > 0){
                cijenaList = cijenaElement.getChildNodes();
                cijenaPonude=  (cijenaList.item(0).getNodeValue());
            }


            NodeList popustList = fstElmnt.getElementsByTagName("Popust");
            Element popustElement = (Element) popustList.item(0);
            if (popustElement.getChildNodes().getLength() > 0){
                popustList = popustElement.getChildNodes();
                popust=  (popustList.item(0).getNodeValue());
            }

            NodeList cijenaorgList = fstElmnt.getElementsByTagName("CijenaOriginal");
            Element cijenaorgElement = (Element) cijenaorgList.item(0);
            if (cijenaorgElement.getChildNodes().getLength() > 0){
                cijenaorgList = cijenaorgElement.getChildNodes();
                cijenaorg =  (cijenaorgList.item(0).getNodeValue());
            }

            NodeList urlSlikeList = fstElmnt.getElementsByTagName("UrlSlike");
            Element urlSlikeElement = (Element) urlSlikeList.item(0);
            if (urlSlikeElement.getChildNodes().getLength() > 0){
                urlSlikeList = urlSlikeElement.getChildNodes();
                urlSlike =  (urlSlikeList.item(0).getNodeValue());
            }

            NodeList ustedaList = fstElmnt.getElementsByTagName("Usteda");
            Element ustedaElement = (Element) ustedaList.item(0);
            if (ustedaElement.getChildNodes().getLength() > 0){
                ustedaList = ustedaElement.getChildNodes();
                usteda =  (ustedaList.item(0).getNodeValue());
            }

            NodeList kategorijaList = fstElmnt.getElementsByTagName("Kategorija");
            Element kategorijaElement = (Element) kategorijaList.item(0);
            if (kategorijaElement.getChildNodes().getLength() > 0){
                kategorijaList = kategorijaElement.getChildNodes();
                kategorija =  (kategorijaList.item(0).getNodeValue());
            }

            NodeList gradList = fstElmnt.getElementsByTagName("Grad");
            Element gradElement = (Element) gradList.item(0);
            if (gradElement.getChildNodes().getLength() > 0){
                gradList = gradElement.getChildNodes();
                grad=  (gradList.item(0).getNodeValue());
            }

            NodeList datumList = fstElmnt.getElementsByTagName("DatumPonude");
            Element datumElement = (Element) datumList.item(0);
            if (datumElement.getChildNodes().getLength() > 0){
                datumList = datumElement.getChildNodes();
                datum=  (datumList.item(0).getNodeValue());
            }

            Ponuda listElement = new Ponuda(i,tekstPonude, Integer.parseInt(cijenaPonude),Integer.parseInt(popust),Integer.parseInt(cijenaorg), urlSlike,Integer.parseInt(usteda), kategorija, grad, datum);
            ponudaLista.add(listElement);

        }

        if(webServiceHandler != null){
            webServiceHandler.onDataArrived(ponudaLista, true);
        }

    }
}
