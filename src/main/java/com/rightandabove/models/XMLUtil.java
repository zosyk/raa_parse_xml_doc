package com.rightandabove.models;

/**
 * Created by alex on 12.03.17.
 */


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.rightandabove.controllers.FileController;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Component
public class XMLUtil {

    private static final Logger logger = Logger.getLogger(XMLUtil.class);


    private XMLUtil() {
    }


    public synchronized void addCdList(List<CD> cdList) {

        DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder;
        try {

            Document doc = null;
            icBuilder = icFactory.newDocumentBuilder();
            try {

                doc = icBuilder.parse("/home/alex/Desktop/text_doc.xml");
            } catch (SAXException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            if (doc == null)
                doc = icBuilder.newDocument();


            Element mainRootElement = doc.getDocumentElement();

            if (mainRootElement == null) {

                mainRootElement = doc.createElement("CATALOG");
                doc.appendChild(mainRootElement);
            }


            final int cdListSize = cdList.size();
            for (int i = 0; i < cdListSize; i++) {
                final CD cd = cdList.get(i);

                final NodeList companyList = mainRootElement.getElementsByTagName("CD");

                final int companyListSize = companyList.getLength();

                for (int j = 0; j < companyListSize; j++) {
                    Element currentElement = (Element) companyList.item(j);


                    if (currentElement != null && currentElement.getElementsByTagName("TITLE").item(0).getTextContent().equals(cd.getTitle())) {

                        Node prev = currentElement.getPreviousSibling();        // delete blank lines
                        if (prev != null &&
                                prev.getNodeType() == Node.TEXT_NODE &&
                                prev.getNodeValue().trim().length() == 0) {
                            mainRootElement.removeChild(prev);
                        }

                        mainRootElement.removeChild(currentElement);
                    }
                }

                mainRootElement.appendChild(getCD(doc, cd.getTitle(), cd.getArtist(), cd.getCountry(), cd.getCompany(), cd.getPrice(), cd.getYear()));

            }

            // output DOM XML to file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult streamResult = new StreamResult("/home/alex/Desktop/text_doc.xml");
            transformer.transform(source, streamResult);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }


    public List<CD> getCDList(Object cdFile) throws IOException, SAXException, ParserConfigurationException {
        List<CD> cdList = new ArrayList<CD>();
        Document doc = null;
        InputStream inputStreamFromCdFile = null;

        if (cdFile instanceof InputStream)
            inputStreamFromCdFile = (InputStream) cdFile;


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            if (inputStreamFromCdFile != null)
                doc = dBuilder.parse(inputStreamFromCdFile);
            else
                doc = dBuilder.parse(cdFile.toString());

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("CD");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    final String title = eElement.getElementsByTagName("TITLE").item(0).getTextContent();
                    final String artist = eElement.getElementsByTagName("ARTIST").item(0).getTextContent();
                    final String country = eElement.getElementsByTagName("COUNTRY").item(0).getTextContent();
                    final String company = eElement.getElementsByTagName("COMPANY").item(0).getTextContent();
                    final String price = eElement.getElementsByTagName("PRICE").item(0).getTextContent();
                    final String year = eElement.getElementsByTagName("YEAR").item(0).getTextContent();

                    cdList.add(new CD(title, artist, country, company, price, year));
                }
            }
        } finally {
            if (inputStreamFromCdFile != null)
                inputStreamFromCdFile.close();
        }

        return cdList;
    }


    private Element getCD(Document doc, String title, String artist, String country, String company, String price, String year) {
        Element cd = doc.createElement("CD");
        cd.appendChild(getCDElements(doc, "TITLE", title));
        cd.appendChild(getCDElements(doc, "ARTIST", artist));
        cd.appendChild(getCDElements(doc, "COUNTRY", country));
        cd.appendChild(getCDElements(doc, "COMPANY", company));
        cd.appendChild(getCDElements(doc, "PRICE", price));
        cd.appendChild(getCDElements(doc, "YEAR", year));
        return cd;
    }

    private Element getCDElements(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}
