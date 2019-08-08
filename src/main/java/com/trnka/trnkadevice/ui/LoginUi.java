package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.inputreader.InputReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginUi {


    public void enter(){
      log.info("Entering login UI");
        new InputReader().read();
    }

}
