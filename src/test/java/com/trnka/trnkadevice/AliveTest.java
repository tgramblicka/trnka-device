package com.trnka.trnkadevice;

import com.trnka.trnkadevice.controller.RestClientBackend;
import org.junit.Assert;

public class AliveTest {

//    @Test
    public void testAlive() {
        RestClientBackend client = new RestClientBackend();
        String response = client.sendGetRequest("/monitoring/alive");
        Assert.assertEquals("im alive", response);
    }
}
