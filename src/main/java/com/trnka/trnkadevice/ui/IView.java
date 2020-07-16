package com.trnka.trnkadevice.ui;

public interface IView extends Renderable{

    void enter();

    Class<? extends IView> onEscape();

}
