package hr.fer.zemris.ppj.lab01;

import java.util.*;
import java.util.function.Function;

@SuppressWarnings("serial")
public class Tokenizer {

    private String newLine = "\n";

    private static final Set<String> FINAL_STATES = new HashSet<>();

    static {
        FINAL_STATES.add("IDN");
        FINAL_STATES.add("BROJ");
        FINAL_STATES.add("=");
        FINAL_STATES.add("+");
        FINAL_STATES.add("-");
        FINAL_STATES.add("*");
        FINAL_STATES.add("/");
        FINAL_STATES.add("(");
        FINAL_STATES.add(")");
        FINAL_STATES.add("KR_ZA");
        FINAL_STATES.add("KR_OD");
        FINAL_STATES.add("KR_DO");
        FINAL_STATES.add("KR_AZ");
    }


    private static final Map<String, Function<Character, Set<String>>> TRANSITIONS = new HashMap<>();

    static {

        TRANSITIONS.put("default", c -> {
            Set<String> states = new HashSet<String>();
            if (Character.isDigit(c)) {
                states.add("BROJ");
            }
            if (Character.isLetter(c)) {
                states.add("IDN");
            }
            states.add(String.valueOf(Character.toUpperCase(c)));
            return states.isEmpty() ? null : states;
        });

        TRANSITIONS.put("IDN", c -> {
            if (Character.isDigit(c) || Character.isLetter(c)) {
                return new HashSet<String>() {{
                    add("IDN");
                }};
            }
            return null;
        });

        TRANSITIONS.put("BROJ", c -> {
            if (Character.isDigit(c)) {
                return new HashSet<String>() {{
                    add("BROJ");
                }};
            }
            return null;
        });

        TRANSITIONS.put("Z", c -> {
            if (c == 'A' || c == 'a') {
                return new HashSet<String>() {{
                    add("KR_ZA");
                }};
            }
            return null;
        });

        TRANSITIONS.put("O", c -> {
            if (c == 'D' || c == 'd') {
                return new HashSet<String>() {{
                    add("KR_OD");
                }};
            }
            return null;
        });

        TRANSITIONS.put("D", c -> {
            if (c == 'O' || c == 'o') {
                return new HashSet<String>() {{
                    add("KR_DO");
                }};
            }
            return null;
        });

        TRANSITIONS.put("A", c -> {
            if (c == 'Z' || c == 'z') {
                return new HashSet<String>() {{
                    add("KR_AZ");
                }};
            }
            return null;
        });

        TRANSITIONS.put("/", c -> {
            if (c == '/') {
                return new HashSet<String>() {{
                    add("KOMENTAR");
                }};
            }
            return null;
        });

        TRANSITIONS.put("KOMENTAR", c -> {
            if (c == '\n') {
                return null;
            }
            return new HashSet<String>() {{
                add("KOMENTAR");
            }};
        });
    }

    private static final Map<String, Symbol> STATE_TO_SYMBOL = new HashMap<>();

    static {
        STATE_TO_SYMBOL.put("IDN", Symbol.IDN);
        STATE_TO_SYMBOL.put("BROJ", Symbol.NUM);
        STATE_TO_SYMBOL.put("=", Symbol.OP_ASSIGN);
        STATE_TO_SYMBOL.put("+", Symbol.OP_PLUS);
        STATE_TO_SYMBOL.put("-", Symbol.OP_MINUS);
        STATE_TO_SYMBOL.put("*", Symbol.OP_MULTIPLY);
        STATE_TO_SYMBOL.put("/", Symbol.OP_DIVIDE);
        STATE_TO_SYMBOL.put("(", Symbol.LEFT_BRACKET);
        STATE_TO_SYMBOL.put(")", Symbol.RIGHT_BRACKET);
        STATE_TO_SYMBOL.put("KR_ZA", Symbol.FOR);
        STATE_TO_SYMBOL.put("KR_OD", Symbol.FROM);
        STATE_TO_SYMBOL.put("KR_DO", Symbol.TO);
        STATE_TO_SYMBOL.put("KR_AZ", Symbol.END);
    }

    private FiniteStateAutomaton<String, Character> fsa = new FiniteStateAutomaton<>(FINAL_STATES, TRANSITIONS, "default");
    private String text;
    private int index;
    private int generatorIndex;
    private int lineIndex;
    private Token last;
    private Token next;

    public Tokenizer(String text) {
        super();
        this.text = text;
        index = 0;
        generatorIndex = 0;
        lineIndex = 1;
        last = null;
        next = generateNext();
    }

    public boolean hasNext() {
        return index < text.length();
    }

    public Token next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        last = next;
        next = generateNext();
        return last;
    }

    private Token generateNext() {

        index = generatorIndex;

        if (!hasNext()) {
            return null;
        }
        for (; generatorIndex < text.length() && fsa.step(text.charAt(generatorIndex)); generatorIndex++) {
            if (text.length() > generatorIndex + newLine.length() && text.substring(generatorIndex, generatorIndex + newLine.length()).equals(newLine)) {
                lineIndex++;
            }
        }
        List<String> finalStates = new ArrayList<>(fsa.getCurrentFinalStates());
        fsa.reset();
        if (finalStates.size() < 1) {
            return generateNext();
        } else {
            if (finalStates.size() > 1) {
                finalStates.remove("IDN");
                if (finalStates.size() > 1) {
                    throw new RuntimeException("Something went wrong, veeeeery wrong");
                }
            }
            return new Token(text.substring(index, generatorIndex), STATE_TO_SYMBOL.get(finalStates.get(0)), lineIndex);
        }
    }

}
