package com.trnka.trnkadevice.ui.messages;

public enum Messages {
    LOGIN_VIEW_LABEL("Login", "audio"),
    TYPE_IN_YOUR_PASSWORD("Zadajte 4 miestny registracny kod", "audio"),
    YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN("Boli ste uspesne prihlaseny", "audio"),
    WRONG_PASSWORD("Heslo nespravne", "audio"),
    MAIN_MENU_LABEL("Menu Student", "audio"),
    MAIN_MENU("Hlavne menu, vitaj {}. Posun v menu tlacidlami hore/dole. Vstup do menu potvrdzovacim tlacidlom", "audio"),
    LEARNING_LABEL("Vyuka", "audio"),
    LEARNING_VIEW("Vyuka, vyber moznosti tlacidlami hore/dole", "audio"),
    TESTING_LABEL("Testovanie", "audio"),
    TESTING_VIEW("Vyber test tlacidlami hore/dole", "audio"),
    RESULTS_LABEL("Vysledky", "audio"),
    RESULTS_VIEW("Vyber vysledok testu zo zoznamu tlacidlami hore/dole", "audio"),
    RESULT_VIEW("Vysledky testu {}", "audio"),

    LEARNING_CHARACTER_VIEW_LABEL("Vyber znaku", "audio"),
    WRONG_INPUT_KEY("Nespravna klavesa!!!", "audio");

    private final String text;
    private final String audioFile;

    Messages(final String text,
             final String audioFile) {
        this.text = text;
        this.audioFile = audioFile;
    }

    public String getText() {
        return text;
    }

    public String getAudioFile() {
        return audioFile;
    }
}
