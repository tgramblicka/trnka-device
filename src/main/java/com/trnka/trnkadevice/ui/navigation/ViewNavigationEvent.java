package com.trnka.trnkadevice.ui.navigation;

import com.trnka.trnkadevice.ui.IView;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewNavigationEvent{

    private IView destinationView;
    private IView originView;

}
