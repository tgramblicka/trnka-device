package com.trnka.trnkadevice;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.trnka.trnkadevice.domain.BrailCharacter;

public class MockBrails {

    public static final SortedSet<BrailCharacter> BRAIL = new TreeSet<>(Comparator.comparing(BrailCharacter::getLetter));

    static {
        BRAIL.add(brailCharacter(1, "a", 1));
        BRAIL.add(brailCharacter(2, "b", 1, 2));
        BRAIL.add(brailCharacter(3, "c", 1, 4));
        BRAIL.add(brailCharacter(4, "d", 1, 4, 5));
        BRAIL.add(brailCharacter(5, "e", 1, 5));
        BRAIL.add(brailCharacter(6, "f", 1, 2, 4));
        BRAIL.add(brailCharacter(7, "g", 1, 2, 4, 5));
        BRAIL.add(brailCharacter(8, "h", 1, 2, 5));
        BRAIL.add(brailCharacter(9, "i", 2, 4));
        BRAIL.add(brailCharacter(10, "j", 2, 4, 5));
        BRAIL.add(brailCharacter(11, "k", 3, 4));
        BRAIL.add(brailCharacter(12, "l", 4, 5, 6));
        BRAIL.add(brailCharacter(13, "m", 1, 3, 4));
        BRAIL.add(brailCharacter(14, "n", 1, 3, 4, 5));
        BRAIL.add(brailCharacter(15, "o", 1, 3, 5));
        BRAIL.add(brailCharacter(16, "p", 1, 2, 3, 4));
        BRAIL.add(brailCharacter(17, "q", 1, 2, 3, 4, 5));
        BRAIL.add(brailCharacter(18, "r", 1, 2, 3, 5));
        BRAIL.add(brailCharacter(19, "s", 2, 3, 4));
        BRAIL.add(brailCharacter(20, "t", 2, 3, 4, 5));
        BRAIL.add(brailCharacter(21, "u", 1, 3, 6));
        BRAIL.add(brailCharacter(22, "v", 1, 2, 3, 6));
        BRAIL.add(brailCharacter(23, "x", 1, 3, 4, 6));
        BRAIL.add(brailCharacter(24, "y", 1, 3, 4, 5, 6));
        BRAIL.add(brailCharacter(25, "z", 1, 3, 5, 6));
    }

    private MockBrails() {
        super();
    }

    private static BrailCharacter brailCharacter(final int id,
                                                 final String letter,
                                                 final Integer... brailRepre) {
        BrailCharacter brailCharacter = new BrailCharacter();
        // brailCharacter.setId(Long.valueOf(id));
        brailCharacter.setBrailRepresentation(Stream.of(brailRepre).collect(Collectors.toList()));
        brailCharacter.setLetter(letter);
        return brailCharacter;
    }

    public static BrailCharacter getBrailCharacter(String letter) {
        return BRAIL.stream().filter(b -> b.getLetter().equals(letter)).findFirst().orElse(null);
    }
}
