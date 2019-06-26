package com.nostis;

import com.nostis.api_client.SoapClientLightnings;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class SoapLightningsIT {
    @Test
    public void whenFindLightning_thenReturnInformationsAboutLightning() {
        Map<String, String> informations = SoapClientLightnings.findLightning(52.3F, 16.9F, 20, "api_key");

        Assert.assertEquals(4, informations.size());
        Assert.assertTrue(informations.containsKey("count"));
        Assert.assertTrue(informations.containsKey("distance"));
        Assert.assertTrue(informations.containsKey("direction"));
        Assert.assertTrue(informations.containsKey("interval"));
    }
}