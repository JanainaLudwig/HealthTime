package utils;

import location.City;
import location.modal.ChangeLocation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class LocationUtils {

    public static City getCurrentCity() {
        String uri = "http://api.ipstack.com/check?access_key=26e8398d9528d2616219add34e7b1803&output=xml",
               city = null,
               state = null;

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(uri);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            //e.printStackTrace();
            System.out.println("----ipstack----");
            System.out.println("Error trying to get current city.");
            return null;
        }

        try {
            city = doc.getElementsByTagName("city").item(0).getTextContent();
            state = doc.getElementsByTagName("region_code").item(0).getTextContent();
        } catch (Exception e) {
            return null;
        }

        //TODO: city id -> should be searched in brazilian cities API
        // 4107207 -> Dois vizinhos id
        int id = getCityId(city);

        //City not found
        if (id == 0) {
            return null;
        }

        return new City(id, city, state);
    }

    public static ArrayList<City> getCitiesStartingWith(String start, int quantity) {
        //TODO: use API

        Document doc = parseXml();

        int numOfCities = doc.getElementsByTagName("city").getLength();

        ArrayList<City> cities = new ArrayList();

        String xmlCity, xmlState;
        int xmlId;

        for (int i = 0; i < numOfCities; i++) {
            if (cities.size() == quantity) break;

            xmlCity = doc.getElementsByTagName("name").item(i).getTextContent();
            if (xmlCity.toUpperCase().startsWith(start.toUpperCase())) {
                xmlId = Integer.parseInt(doc.getElementsByTagName("id").item(i).getTextContent());
                xmlState = doc.getElementsByTagName("state").item(i).getTextContent();
                cities.add(new City(xmlId, xmlCity, xmlState));
            }
        }

        return cities;
    }

    public static City getCity(String id) {
        City city = null;

        //TODO: get id from DB and other data from cities API
        Document doc = parseXml();

        int numOfCities = doc.getElementsByTagName("city").getLength();

        for (int i = 0; i < numOfCities; i++) {
            if (doc.getElementsByTagName("id").item(i).getTextContent().equals(id)) {
                int xmlId = Integer.parseInt(doc.getElementsByTagName("id").item(i).getTextContent());
                String xmlCity = doc.getElementsByTagName("name").item(i).getTextContent(),
                        xmlState = doc.getElementsByTagName("state").item(i).getTextContent();
                city = new City(xmlId, xmlCity, xmlState);
            }
        }

        //TODO: tratamento de erro
        if (city == null) {
            city = new City(1, "Error", "Error");
        }

        return city;
    }

    public static int getCityId(String name) {
        //TODO: get id from DB and other data from cities API
        int id = 0;
        Document doc = parseXml();
        int numOfCities = doc.getElementsByTagName("city").getLength();

        for (int i = 0; i < numOfCities; i++) {
            if (doc.getElementsByTagName("name").item(i).getTextContent().equals(name)) {
                id = Integer.parseInt(doc.getElementsByTagName("id").item(i).getTextContent());
            }
        }

        return id;
    }

    private static Document parseXml() {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(String.valueOf(ChangeLocation.class.getResource("cities.xml")));
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
