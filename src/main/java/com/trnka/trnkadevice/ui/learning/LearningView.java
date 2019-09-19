package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LearningView implements IView {

    private IRenderer renderer;
    private LearningSequenceComponent learningSequenceComponent;

    @Autowired
    public LearningView(final IRenderer renderer) {
        this.renderer = renderer;
    }

    public void refresh(final LearningSequenceComponent learningSequenceComponent) {
        this.learningSequenceComponent = learningSequenceComponent;
    }

    @Override
    public void enter() {
        if (learningSequenceComponent == null) {
            log.error("Learning sequence component is null, this CANNOT HAPPEN");
            return;
        }
        this.renderer.renderLabel(learningSequenceComponent);
        this.learningSequenceComponent.getSequence().getSteps().forEach(step -> {
            Character cahr = step.getCharacter();


        });
    }

    @Override
    public Messages getLabel() {
        return null;
    }
}
