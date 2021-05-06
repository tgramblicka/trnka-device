package com.trnka.trnkadevice.domain.visitor;

import com.trnka.trnkadevice.domain.LearningSequence;
import com.trnka.trnkadevice.domain.MethodicalLearningSequence;
import com.trnka.trnkadevice.domain.TestingSequence;

public interface SequenceVisitor<T> {
    T visit(MethodicalLearningSequence seq);
    T visit(TestingSequence seq);
    T visit(LearningSequence seq);
}
