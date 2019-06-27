package com.nostis.soap;

import com.nostis.soap_api_client.SoapClientValidity;
import org.junit.Assert;
import org.junit.Test;

public class SoapValidityIT {
    @Test
    public void whenCheckIfIsValid_thenReturnsTrue() {
        Assert.assertTrue(SoapClientValidity.checkIfIsValid("api_key"));
    }
}
