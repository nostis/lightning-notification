package com.nostis.integration.soap;

import com.nostis.lightning_core.soap_api_client.SoapClientValidity;
import org.junit.Assert;
import org.junit.Test;

public class SoapValidityExclude {
    @Test
    public void whenCheckIfIsValid_thenReturnsTrue() {
        Assert.assertTrue(SoapClientValidity.checkIfIsValid("<key>"));
    }
}
