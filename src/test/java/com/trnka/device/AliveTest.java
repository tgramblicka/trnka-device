package com.trnka.device;

import com.trnka.device.rest.RestClient;
import org.junit.Assert;
import org.junit.Test;

public class AliveTest {


    @Test
    public void testAlive() {
        RestClient client = new RestClient();
        String response = client.sendGetRequest("alive");
        Assert.assertEquals(response, "alive");
    }
}
