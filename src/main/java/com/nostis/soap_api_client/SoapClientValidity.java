package com.nostis.soap_api_client;

import org.w3c.dom.NodeList;

import javax.xml.soap.*;

public class SoapClientValidity {
    public static boolean checkIfIsValid(String apiKey){
        return callSoapWebService("https://burze.dzis.net/soap.php", "https://burze.dzis.net/soap.php#KeyAPI", apiKey);
    }

    private static void createSoapEnvelope(SOAPMessage soapMessage, String apiKey) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "";
        String myNamespaceURI = "https://burze.dzis.net/soap.php";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("KeyAPI", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("klucz", myNamespace);
        soapBodyElem1.addTextNode(apiKey);
    }

    private static boolean callSoapWebService(String soapEndpointUrl, String soapAction, String apiKey) {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction, apiKey), soapEndpointUrl);
            NodeList nodes = soapResponse.getSOAPBody().getElementsByTagName("return");

            if(nodes.item(0).getFirstChild().getNodeValue().equals("true")){
                return true;
            }

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }

        return false;
    }

    private static SOAPMessage createSOAPRequest(String soapAction, String apiKey) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage, apiKey);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        soapMessage.saveChanges();

        return soapMessage;
    }
}
