package com.trnka.trnkadevice.ui.messages;

public enum Messages implements IMessage {
    LOGIN_VIEW_LABEL("Sekcia: Prihlasenie", "audio"),
    TYPE_IN_YOUR_PASSWORD("Zadajte 4 miestny prihlasovaci kod", "audio"),
    YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN("Boli ste uspesne prihlaseny", "audio"),
    WRONG_PASSWORD("Heslo nespravne", "audio"),
    MAIN_MENU_LABEL("Sekcia: hlavne menu Student", "audio"),
    MAIN_MENU("Vitaj {}. Posun v menu tlacidlami hore/dole. Vstup do menu potvrdzovacim tlacidlom", "audio"),
    LEARNING_LABEL("Vyuka", "audio"),
    LEARNING_VIEW("Sekcia: Vyuka, vyber moznosti tlacidlami hore/dole", "audio"),
    TESTING_LABEL("Testovanie", "audio"),
    TESTING_VIEW("Sekcia: Testovanie. Vyber test tlacidlami hore/dole", "audio"),
    RESULTS_LABEL("Vysledky", "audio"),
    RESULTS_VIEW("Sekcia Vysledky: Vyber vysledok testu zo zoznamu tlacidlami hore/dole", "audio"),

    LEARNING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre {} a potvrd!", "audio"),
    LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("Spravne, pokracujem na dalsie pismeno", "audio"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("NESPRAVNE! Spravna sekvencia je: {} .Ostava {} pokusov.", "audio"),
    LEARNING_MAXIMUM_NEGATIVE_TRIES_REACHED("Maximalny pocet nespravnych opakovani bol dosiahnuty!", "audio"),
    LEARNING_SEQUENCE_END("Koniec uciacej sekvencie!", "audio"),

    TESTING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre {} a potvrd!", "audio"),
    TESTING_SEQUENCE_END("Koniec testu!", "audio"),
    TESTING_SEQUENCE_OVERALL_RESULT("Vysledok testu {}", "audio"),


    SEQUENCE_STATISTIC("Statistika sekvencie: \n{}", "audio"),




    LOG_OUT("Odhlasenie", "audio"),
    LOGGING_OUT("Pouzivatel bude odhlaseny", "audio"),

    XXX("XXXX, !", "audio"),

    ONE("Sekvencia 1", "audio"),
    TWO("Sekvencia 2", "audio"),
    THREE("{} 3", "audio"),
    FOUR("{} 4", "audio"),
    FIVE("{} 5", "audio"),
    SIX("{} 6", "audio"),
    SEVEN("{} 7", "audio"),
    EIGHT("{} 8", "audio"),
    NINE("{} 9", "audio"),

    A("a", "audio"),
    B("b", "audio"),
    L("l", "audio"),
    E("e", "audio"),
    K("k", "audio"),
    U("u", "audio"),

    LEARNING_SEQUENCE_STARTED("Zacali ste sekvenciu ucenia cislo {}", "audio");

    private final String text;
    private final String audioFile;

    Messages(final String text,
             final String audioFile) {
        this.text = text;
        this.audioFile = audioFile;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getAudioFile() {
        return audioFile;
    }
}
