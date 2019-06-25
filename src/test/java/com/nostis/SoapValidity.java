package com.nostis;

import com.nostis.api_client.SoapClientValidity;
import org.junit.Assert;
import org.junit.Test;

public class SoapValidity {
    @Test
    public void whenCheckIfIsValid_thenReturnsTrue() {
        Assert.assertTrue(SoapClientValidity.checkIfIsValid("api_key"));
    }
}
