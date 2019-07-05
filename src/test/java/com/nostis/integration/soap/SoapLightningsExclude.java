package com.nostis.integration.soap;

import com.nostis.lightning_core.soap_api_client.SoapClientLightnings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SoapLightningsExclude {
    @Test
    public void whenFindLightning_thenReturnInformationsAboutLightning() {
        Map<String, String> informations = SoapClientLightnings.findLightning(52.24F, 16.9F, 50, "<key>");

        informations.forEach((v, k) -> System.out.println(v + " " + k));

        Assert.assertEquals(4, informations.size());
        Assert.assertTrue(informations.containsKey("count"));
        Assert.assertTrue(informations.containsKey("distance"));
        Assert.assertTrue(informations.containsKey("direction"));
        Assert.assertTrue(informations.containsKey("interval"));
    }
}
