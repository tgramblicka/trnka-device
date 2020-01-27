package com.trnka.trnkadevice.ui.messages;

import java.util.EnumSet;

public enum Messages implements IMessage {
    LOGIN_VIEW_LABEL("Prihlasenie", "audio"),
    TYPE_IN_YOUR_PASSWORD("Zadaj 4 miestny prihlasovaci kod a potvrd", "audio"),
    YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN("Boli ste uspesne prihlaseny", "audio"),
    WRONG_PASSWORD("Heslo nespravne", "audio"),
    MAIN_MENU_LABEL("Sekcia: Hlavne menu - Student", "audio"),
    MAIN_MENU("Vitaj {}. Posun v menu tlacidlami hore/dole. Vstup do menu potvrdzovacim tlacidlom", "audio"),
    LEARNING_LABEL_MENU("Vyuka", "audio"),
    LEARNING_VIEW_MENU("Sekcia: Vyuka, vyber moznosti tlacidlami hore/dole", "audio"),

    TESTING_VIEW("Sekcia: Testovanie pismen. Vyber test tlacidlami hore/dole", "audio"),

    RESULTS_LABEL_MENU("Vysledky", "audio"),
    RESULTS_SELECTION_VIEW("Sekcia Vysledky: Vyber vysledok testu zo zoznamu tlacidlami hore/dole", "audio"),
    RESULTS_VIEW("Vysledky pre vybrany test", "audio"),
    RESULT_NO_RESULTS_TO_DISPLAY("Nenasli sa ziadne Vase vysledky", "audio"),

    LEARNING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre '{}' a potvrd! Pismeno pozostava z bodov: {} ", "audio"),
    LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("Spravne, pokracujem na dalsie pismeno", "audio"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("NESPRAVNE! Správne poradie bodov v znaku je {} Ostáva {} Pokusov", "audio"),
    LEARNING_SEQUENCE_END("Koniec výučbového bloku {}. Chceš spustiť postupový test?", "audio"),


    TESTING_LETTERS_LABEL_MENU("Testovanie pismen", "audio"),


    LEARNING_LETTERS_SELECTION_MENU("Pismena", "audio"),
    LEARNING_LETTERS_SELECTION_VIEW("Sekcia: Metodicka vyuka - pismena. Vyber vyuku tlacidlami hore/dole", "audio"),

    LEARNING_SYLLABLES_SELECTION_MENU("Slabiky", "audio"),
    LEARNING_SYLLABLES_SELECTION_VIEW("Sekcia: Metodicka vyuka - slabiky. Vyber moznosti tlacidlami hore/dole", "audio"),


    LEARNING_WORDS_SELECTION_MENU("Slova", "audio"),
    LEARNING_WORDS_SELECTION_VIEW("Sekcia: Metodicka vyuka - slova. Vyber moznosti tlacidlami hore/dole", "audio"),

    LEARNING_SENTENCES_MENU("Vety", "audio"),
    LEARNING_SENTENCES_VIEW("Sekcia: Metodicka vyuka - vety. Vyber moznosti tlacidlami hore/dole", "audio"),

    NO_SEQUENCES_FOUND("Nenasli sa ziadne vyukove sekvencie!", "audio"),

    METHODICAL_LEARNING_MENU("Metodicka vyuka", "audio"),
    METHODICAL_LEARNING_MENU_VIEW("Sekcia Metodicka vyuka menu: vyber druh vyuky tlacidlami hore/dole", "audio"),
    METHODICAL_LEARNING_TEST_ENTERED("Zacina sa postupovy test", "audio"),
    METHODICAL_LEARNING_ENDED("Koniec postupoveho testu!", "audio"),
    METHODICAL_LEARNING_TEST_PASSED("Gratulujem! Uspesne ste presli postupovym testom!", "audio"),
    METHODICAL_LEARNING_TEST_NOT_PASSED("Žial, vase skore nie je dostatocne pre postup do ďalšieho výučbového bloku. Opakujte prosím test alebo sa vráťte späť na výučbu.", "audio"),

    TESTING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre pismeno '{}' a potvrd!", "audio"),
    TESTING_SEQUENCE_END("Koniec testu!", "audio"),
    TESTING_SEQUENCE_OVERALL_RESULT("Vysledok testu {}, uspesnost: {}%", "audio"),


    SEQUENCE_STATISTIC("Statistika sekvencie: \n{}", "audio"),
    TEST_RESULT_COMPONENT_LABEL("TEST {}", "audio"),





    LOG_OUT("Odhlasenie", "audio"),
    LOGGING_OUT("Pouzivatel bude odhlaseny", "audio"),

    XXX("XXXX, !", "audio"),

    ONE("Sekvencia 1: {}", "audio"),
    TWO("Sekvencia 2: {}", "audio"),
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

    private static String exportMessages() {
        EnumSet<Messages> set = EnumSet.allOf(Messages.class);
        StringBuilder b = new StringBuilder();
        for (Messages m : set){
//            b.append(String.format("%s|%s", m.name(), m.getText()));
            b.append(String.format("%s", m.getText()));
            b.append("\n");
        }
        return b.toString();
    }

    public static void main(String[] args) {
        System.out.println(exportMessages());
    }
}
