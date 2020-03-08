package com.trnka.trnkadevice.ui.messages;

import com.trnka.trnkadevice.ui.Renderable;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Messages {
    AAAA("Ahoj {1,2,3}}", ""),

    LOGIN_VIEW_LABEL("Prihlasenie", "audio"),
    TYPE_IN_YOUR_PASSWORD("Zadaj 4 miestny prihlasovaci kod a potvrd", "audio"),
    YOU_HAVE_BEEN_SUCCESSFULY_LOGGED_IN("Boli ste uspesne prihlaseny", "audio"),
    WRONG_PASSWORD("Heslo nespravne", "audio"),
    MAIN_MENU_LABEL("Sekcia: Hlavne menu - Student", "audio"),
    MAIN_MENU("Vitaj, posun v menu tlacidlami hore/dole. Vstup do menu potvrdzovacim tlacidlom", "audio"),
    LEARNING_LABEL_MENU("Vyuka", "audio"),
    LEARNING_VIEW_MENU("Sekcia: Vyuka, vyber moznosti tlacidlami hore/dole", "audio"),

    TESTING_VIEW("Sekcia: Testovanie pismen. Vyber test tlacidlami hore/dole", "audio"),

    RESULTS_LABEL_MENU("Vysledky", "audio"),
    RESULTS_SELECTION_VIEW("Sekcia Vysledky: Vyber vysledok testu zo zoznamu tlacidlami hore/dole", "audio"),
    RESULTS_VIEW("Vysledky pre vybrany test", "audio"),
    RESULT_NO_RESULTS_TO_DISPLAY("Nenasli sa ziadne Vase vysledky", "audio"),

    LEARNING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre {} a potvrd! Pismeno pozostava z bodov:", ""),
    LEARNING_TYPE_IN_CHARACTER_BRAIL_1("Zadaj brail sekvenciu pre {}", ""),
    LEARNING_TYPE_IN_CHARACTER_BRAIL_2("a potvrd! Pismeno pozostava z bodov:", "audio"),

    LEARNING_CORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("Spravne, pokracujem na dalsie pismeno", "audio"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED("NESPRAVNE! Správne poradie bodov v znaku je {}", "audio"),
    LEARNING_INCORRECT_CHARACTER_BRAIL_SEQUENCE_SUBMITTED_LEFT_RETRIES("Ostáva {} pokusov", "audio"),

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
    METHODICAL_LEARNING_TEST_NOT_PASSED(
            "Žial, vase skore nie je dostatocne pre postup do ďalšieho výučbového bloku. Opakujte prosím test alebo sa vráťte späť na výučbu.", "audio"),

    TESTING_TYPE_IN_CHARACTER_BRAIL("Zadaj brail sekvenciu pre pismeno '{}' a potvrd!", "audio"),
    TESTING_SEQUENCE_END("Koniec testu!", "audio"),
    TESTING_SEQUENCE_OVERALL_RESULT("Vysledok testu {}{}{}", "audio"), // ZMENENE

    STATISTIC_SEQUENCE("Statistika sekvencie", "audio"), // ZMENENY LEN NAZOV
    STATISTIC_LETTER("Statistika pre pismeno {}", ""),
    TEST_RESULT_COMPONENT_LABEL("TEST {}", "audio"),

    LOG_OUT("Odhlasenie", "audio"),
    LOGGING_OUT("Pouzivatel bude odhlaseny", "audio"),

    XXX("XXXX, !", "audio"),

    CORRECT_GUESS("Spravne", ""), // TREBA PREHRAT !!!!
    INCORRECT_GUESS("Nespravne", ""),

    LEARNING_SEQUENCE_NAME("Vyuka {}", "audio"), // parameter bude brail representation
    TESTING_SEQUENCE_NAME("Testovanie {}", "audio"), // parameter bude brail representation
    STATISTIC_LETTER_GUESSED("Pismeno {} uhadnute {}", ""),
    STATISTIC_INCORRECT_RETRIES("Pocet nespravnych pokusov {}{}{}", ""), // NAHRAT

    ZERO("0", "audio"),   // PRENAHRAT
    ONE("1", "audio"),
    TWO("2", "audio"),
    THREE("3", "audio"),
    FOUR("4", "audio"),
    FIVE("5", "audio"),
    SIX("6", "audio"),
    SEVEN("7", "audio"),
    EIGHT("8", "audio"),
    NINE("9", "audio"),
    TEN("10", "audio"),

    A("a", "letters"),
    B("b", "letters"),
    C("c", "letters"),
    D("d", "audio"),
    E("e", "audio"),
    F("f", "audio"),
    G("g", "audio"),
    H("h", "audio"),
    I("i", "audio"),
    J("j", "audio"),
    K("k", "audio"),
    L("l", "audio"),
    M("m", "audio"),
    N("n", "audio"),
    O("o", "audio"),
    P("p", "audio"),
    R("r", "audio"),
    S("s", "audio"),
    T("t", "audio"),
    U("u", "audio"),
    V("v", "audio"),
    X("x", "audio"),
    Y("y", "audio"),
    Z("z", "audio"),

    // for testing messageToAudioPlayilist compiler
    TEST0("text", ""),
    TEST1("{}text", ""),
    TEST2("text{}", ""),
    TEST3("{}{}text{}{}", ""),
    TEST4("{}{}text{}text{}{}", ""),
    TEST5("{}{}text{}{}text{}{}", ""),
    TEST6("text{}", ""),
    TEST7("{}text", ""),
    TEST8("text{}text", ""),
    TEST9("text{}{}text", ""),


    LEARNING_SEQUENCE_STARTED("Zacali ste sekvenciu ucenia cislo {}", "audio"),
    UNKNOWN("Unknown", "");

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
        return String.format("%s.wav", name());
    }

    public String getNthPartOfAudioFile(Integer n) {
        return String.format("%s_%s.wav", name(), n);
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
