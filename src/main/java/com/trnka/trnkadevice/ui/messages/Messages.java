package com.trnka.trnkadevice.ui.messages;

import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Messages {
    LOGIN_VIEW_LABEL("Prihlasenie", "commands/"),
    TYPE_IN_YOUR_PASSWORD("Zadaj 4 miestny prihlasovaci kod a potvrd", "commands/"),
    YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN("Boli ste uspesne prihlaseny", "commands/"),
    WRONG_PASSWORD("Heslo nespravne", "commands/"),
    MAIN_MENU_LABEL("Sekcia: Hlavne menu - Student", "commands/"),
    MAIN_MENU("Vitaj, posun v menu tlacidlami hore/dole. Vstup do menu potvrdzovacim tlacidlom", "commands/"),
    LEARNING_LABEL_MENU("Vyuka", "commands/"),
    LEARNING_VIEW_MENU("Sekcia: Vyuka, vyber moznosti tlacidlami hore/dole", "commands/"),

    TESTING_VIEW("Sekcia: Testovanie pismen. Vyber test tlacidlami hore/dole", "commands/"),

    RESULTS_LABEL_MENU("Vysledky", "commands/"),
    RESULTS_SELECTION_VIEW("Sekcia Vysledky: Vyber vysledok testu zo zoznamu tlacidlami hore/dole", "commands/"),
    RESULTS_VIEW("Vysledky pre vybrany test", "commands/"),
    RESULT_NO_RESULTS_TO_DISPLAY("Nenasli sa ziadne Vase vysledky", "commands/"),

    LEARNING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre {} a potvrd! Pismeno pozostava z bodov:", "commands/"),

    LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("Spravne, pokracujem na dalsie pismeno", "commands/"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("NESPRAVNE! Správne poradie bodov v znaku je {}", "commands/"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED_LEFT_RETRIES("Ostáva {} pokusov", "commands/"),

    LEARNING_SEQUENCE_END("Koniec výučbového bloku {}. Chceš spustiť postupový test?", "commands/"),

    TESTING_LETTERS_LABEL_MENU("Testovanie pismen", "commands/"),

    LEARNING_LETTERS_SELECTION_MENU("Pismena", "commands/"),
    LEARNING_LETTERS_SELECTION_VIEW("Sekcia: Metodicka vyuka - pismena. Vyber vyuku tlacidlami hore/dole", "commands/"),

    LEARNING_SYLLABLES_SELECTION_MENU("Slabiky", "commands/"),
    LEARNING_SYLLABLES_SELECTION_VIEW("Sekcia: Metodicka vyuka - slabiky. Vyber moznosti tlacidlami hore/dole", "commands/"),

    LEARNING_WORDS_SELECTION_MENU("Slova", "commands/"),
    LEARNING_WORDS_SELECTION_VIEW("Sekcia: Metodicka vyuka - slova. Vyber moznosti tlacidlami hore/dole", "commands/"),

    LEARNING_SENTENCES_MENU("Vety", "commands/"),
    LEARNING_SENTENCES_VIEW("Sekcia: Metodicka vyuka - vety. Vyber moznosti tlacidlami hore/dole", "commands/"),

    NO_SEQUENCES_FOUND("Nenasli sa ziadne vyukove sekvencie!", "commands/"),

    METHODICAL_LEARNING_MENU("Metodicka vyuka", "commands/"),
    METHODICAL_LEARNING_MENU_VIEW("Sekcia Metodicka vyuka menu: vyber druh vyuky tlacidlami hore/dole", "commands/"),
    METHODICAL_LEARNING_TEST_ENTERED("Zacina sa postupovy test", "commands/"),
    METHODICAL_LEARNING_ENDED("Koniec postupoveho testu!", "commands/"),
    METHODICAL_LEARNING_TEST_PASSED("Gratulujem! Uspesne ste presli postupovym testom!", "commands/"),
    METHODICAL_LEARNING_TEST_NOT_PASSED(
            "Žial, vase skore nie je dostatocne pre postup do ďalšieho výučbového bloku. Opakujte prosím test alebo sa vráťte späť na výučbu.", "commands/"),

    TESTING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre pismeno '{}' a potvrd!", "commands/"),
    TESTING_SEQUENCE_END("Koniec testu!", "commands/"),
    TESTING_SEQUENCE_OVERALL_RESULT("Vysledok testu {}{}{}", "commands/"), // ZMENENE

    STATISTIC_SEQUENCE("Statistika sekvencie", "commands/"), // ZMENENY LEN NAZOV
    STATISTIC_LETTER("Statistika pre pismeno {}", "commands/"),
    TEST_RESULT_COMPONENT_LABEL("TEST {}", "commands/"),

    LOG_OUT("Odhlasenie", "commands/"),
    LOGGING_OUT("Pouzivatel bude odhlaseny", "commands/"),

    XXX("XXXX, !", "commands/"),

    CORRECT_GUESS("Spravne", "commands/"),
    INCORRECT_GUESS("Nespravne", "commands/"),

    LEARNING_SEQUENCE_NAME("Vyuka {}", "commands/"), // parameter bude brail representation
    TESTING_SEQUENCE_NAME("Testovanie {}", "commands/"), // parameter bude brail representation
    STATISTIC_LETTER_GUESSED("Pismeno {} uhadnute {}", "commands/"),
    STATISTIC_INCORRECT_RETRIES("Pocet nespravnych pokusov {}{}{}", "commands/"), // NAHRAT

    ZERO("0", "letters/"),   // PRENAHRAT
    ONE("1", "letters/"),
    TWO("2", "letters/"),
    THREE("3", "letters/"),
    FOUR("4", "letters/"),
    FIVE("5", "letters/"),
    SIX("6", "letters/"),
    SEVEN("7", "letters/"),
    EIGHT("8", "letters/"),
    NINE("9", "letters/"),
    TEN("10", "letters/"),

    A("a", "letters/"),
    B("b", "letters/"),
    C("c", "letters/"),
    D("d", "letters/"),
    E("e", "letters/"),
    F("f", "letters/"),
    G("g", "letters/"),
    H("h", "letters/"),
    I("i", "letters/"),
    J("j", "letters/"),
    K("k", "letters/"),
    L("l", "letters/"),
    M("m", "letters/"),
    N("n", "letters/"),
    O("o", "letters/"),
    P("p", "letters/"),
    R("r", "letters/"),
    S("s", "letters/"),
    T("t", "letters/"),
    U("u", "letters/"),
    V("v", "letters/"),
    W("w", "letters/"),
    X("x", "letters/"),
    Y("y", "letters/"),
    Z("z", "letters/"),

    A_DLHE("á", "letters/"),
    A_PREHLASOVANE("ä", "letters/"),
    C_MAKCEN("č", "letters/"),
    D_MAKCEN("ď", "letters/"),
    E_DLHE_ALEBO_ZNAMIENKO_VACSI("é", "letters/"),
    L_DLHE("ĺ", "letters/"),
    L_MAKKE("ľ", "letters/"),
    N_MAKCEN("ň", "letters/"),
    O_DLHE("ó", "letters/"),
    O_VOKAN("ň", "letters/"),
    R_DLHE("ŕ", "letters/"),
    S_MAKCEN("š", "letters/"),
    T_MAKCEN("ť", "letters/"),
    U_DLHE_ALEBO_PARAGRAF("ú", "letters/"),
    Y_DLHE("ý", "letters/"),
    Z_MAKCEN("ž", "letters/"),
    CZ("čz", "letters/"),
    PZ("pz", "letters/"),
    VP("vp", "letters/"),
    CIARKA(",", "letters/"),
    BODKA(".", "letters/"),
    VYKRICNIK_ALEBO_ZNAMIENKO_PLUS("!", "letters/"),
    POMLCKA("-", "letters/"),
    DVOJBODKA(":", "letters/"),
    UVODZOVKY("\"", "letters/"),
    APOSTROF("apostrof", "letters/"),
    LAVA_ZATVORKA("(", "letters/"),
    PRAVA_ZATVORKA("(", "letters/"),
    HVIEZDICKA("*", "letters/"),


    // for testing messageToAudioPlayilist compiler
    TEST0("text", "commands/"),
    TEST1("{}text", "commands/"),
    TEST2("text{}", "commands/"),
    TEST3("{}{}text{}{}", "commands/"),
    TEST4("{}{}text{}text{}{}", "commands/"),
    TEST5("{}{}text{}{}text{}{}", "commands/"),
    TEST6("text{}", "commands/"),
    TEST7("{}text", "commands/"),
    TEST8("text{}text", "commands/"),
    TEST9("text{}{}text", "commands/"),


    LEARNING_SEQUENCE_STARTED("Zacali ste sekvenciu ucenia cislo {}", "commands/"),
    UNKNOWN("Unknown", "commands/");

    private final String text;
    private final String directory;

    Messages(final String text,
             final String directory) {
        this.text = text;
        this.directory = directory;
    }

    public String getText() {
        return text;
    }

    public String getAudioFile() {
        return String.format("%s%s.wav", directory, name());
    }

    public String getNthPartOfAudioFile(Integer n) {
        return String.format("%s%s_%s.wav", directory, name(), n);
    }

    private static String exportMessages() {
        EnumSet<Messages> set = EnumSet.allOf(Messages.class);
        StringBuilder b = new StringBuilder();
        for (Messages m : set) {
            // b.append(String.format("%s|%s", m.name(), m.getText()));
            b.append(String.format("%s", m.getText()));
            b.append("\n");
        }
        return b.toString();
    }

    public String[] split() {
        return getText().split("\\{\\}");
    }

    public long getParameterCount(){
        return getText().chars().filter(ch -> ch == '{').count();
    }

    public Boolean isMessageSplitToMultipleParts() {
        if (getParameterCount() == 0) {
            return false;
        }
        List<String> parts = Stream.of(split()).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        return parts.size() > 1;
    }

    public static Messages fromText(String text) {
        return EnumSet.allOf(Messages.class).stream().filter(m -> m.getText().equals(text)).findFirst().orElse(null);
    }

    public static Messages fromNumber(Integer value) {
        return fromText(String.valueOf(value));
    }

    public static void main(String[] args) {
        System.out.println(exportMessages());
    }
}
