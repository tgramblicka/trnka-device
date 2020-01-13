package com.trnka.trnkadevice.ui.navigation;

import com.trnka.trnkadevice.ui.IView;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NavigationEvent<T extends IView> {

    private Class<T> destinationViewClass;
    private Class<T> originViewClass;

}
