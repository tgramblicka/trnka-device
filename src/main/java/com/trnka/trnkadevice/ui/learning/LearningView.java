package com.trnka.trnkadevice.ui.learning;

import com.trnka.trnkadevice.domain.BrailCharacter;
import com.trnka.trnkadevice.inputreader.InputReader;
import com.trnka.trnkadevice.inputreader.Keystroke;
import com.trnka.trnkadevice.renderer.IRenderer;
import com.trnka.trnkadevice.ui.IView;
import com.trnka.trnkadevice.ui.messages.Messages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class LearningView implements IView {

    private IRenderer renderer;
    private LearningSequenceComponent learningSequenceComponent;
    private InputReader inputReader;

    @Autowired
    public LearningView(final IRenderer renderer,
                        final InputReader inputReader) {
        this.renderer = renderer;
        this.inputReader = inputReader;
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
            renderer.renderMessage(step.getBrailCharacter());
            List<Keystroke> keyStrokes = new ArrayList<>();
            Keystroke keystroke = inputReader.readFromInput();

            while (keystroke.equals(Keystroke.SUBMIT)) {
                keyStrokes.add(keystroke);
                keystroke = inputReader.readFromInput();
            }

            if (step.getBrailCharacter().getCharacter().equals(keystroke.getValue())) {
                renderer.renderMessage(Messages.CORRECT_INPUT);
            }

        });
    }

    @Override
    public Messages getLabel() {
        return null;
    }
}
