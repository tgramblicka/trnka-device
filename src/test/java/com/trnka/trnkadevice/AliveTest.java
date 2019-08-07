package com.trnka.trnkadevice;

import com.trnka.trnkadevice.controller.RestClient;
import org.junit.Assert;

public class AliveTest {

//    @Test
    public void testAlive() {
        RestClient client = new RestClient();
        String response = client.sendGetRequest("/monitoring/alive");
        Assert.assertEquals("im alive", response);
    }
}
