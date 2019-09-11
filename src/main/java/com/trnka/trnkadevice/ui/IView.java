package com.trnka.trnkadevice.ui;

import com.trnka.trnkadevice.ui.messages.Messages;

public interface IView extends Renderable{

    void enter();

    Messages getViewName();


}
