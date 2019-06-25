package com.nostis.api_client;

import javax.xml.soap.*;
import java.util.HashMap;
import java.util.Map;


public class SoapClientLightnings {
    public static Map<String, String> findLightning(Float y, Float x, int radius, String apiKey) {
        SOAPMessage response = callSoapWebService("https://burze.dzis.net/soap.php", "https://burze.dzis.net/soap.php#szukaj_burzy", y, x, radius, apiKey);

        Map<String, String> map = new HashMap<>();

        try {
            map.put("count", response.getSOAPBody().getElementsByTagName("liczba").item(0).getFirstChild().getNodeValue());
            map.put("distance", response.getSOAPBody().getElementsByTagName("odleglosc").item(0).getFirstChild().getNodeValue());
            if(response.getSOAPBody().getElementsByTagName("kierunek").item(0).getTextContent().equals("")){
                map.put("direction", "");
            }
            else {
                map.put("direction", response.getSOAPBody().getElementsByTagName("kierunek").item(0).getFirstChild().getNodeValue());
            }
            map.put("interval", response.getSOAPBody().getElementsByTagName("okres").item(0).getFirstChild().getNodeValue());
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return map;
    }
    private static void createSoapEnvelope(SOAPMessage soapMessage, Float y, Float x, int radius, String apiKey) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "";
        String myNamespaceURI = "https://burze.dzis.net/soap.php";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("szukaj_burzy", myNamespace);

        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("y", myNamespace);
        soapBodyElem1.addTextNode(Float.toString(y));

        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("x", myNamespace);
        soapBodyElem2.addTextNode(Float.toString(x));

        SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("promien", myNamespace);
        soapBodyElem3.addTextNode(Integer.toString(radius));

        SOAPElement soapBodyElem4 = soapBodyElem.addChildElement("klucz", myNamespace);
        soapBodyElem4.addTextNode(apiKey);
    }

    private static SOAPMessage callSoapWebService(String soapEndpointUrl, String soapAction, Float y, Float x, int radius, String apiKey) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, y, x, radius, apiKey), soapEndpointUrl);

            //soapResponse.writeTo(System.out);

            soapConnection.close();
            return soapResponse;
            //return soapResponse.getSOAPBody().getElementsByTagName("return");


        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }

        return null;
    }

    private static SOAPMessage createSOAPRequest(String soapAction, Float y, Float x, int radius, String apiKey) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, y, x, radius, apiKey);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        soapMessage.saveChanges();

        return soapMessage;
    }
}
