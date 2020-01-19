package com.trnka.trnkadevice.ui.interaction;

import com.trnka.trnkadevice.inputreader.Keystroke;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInteraction {

    private boolean flowBreakingCondition;
    private Keystroke keystroke;

}
