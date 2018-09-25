package hr.fer.zemris.ppj.lab01;

import java.util.*;

public enum Symbol {

    IDN("IDN", false),
    NUM("BROJ", false),
    FOR("KR_ZA", false),
    FROM("KR_OD", false),
    TO("KR_DO", false),
    END("KR_AZ", false),
    LEFT_BRACKET("L_ZAGRADA", false),
    RIGHT_BRACKET("D_ZAGRADA", false),
    OP_ASSIGN("OP_PRIDRUZI", false),
    OP_PLUS("OP_PLUS", false),
    OP_MINUS("OP_MINUS", false),
    OP_MULTIPLY("OP_PUTA", false),
    OP_DIVIDE("OP_DIJELI", false),
    PROGRAM("<program>", true),
    COMMAND_LIST("<lista_naredbi>", false),
    COMMAND("<naredba>", false),
    ASSIGNMENT_COMMAND("<naredba_pridruzivanja>", false),
    FOR_LOOP("<za_petlja>", true),
    E("<E>", false),
    E_LIST("<E_lista>", false),
    T("<T>", false),
    T_LIST("<T_lista>", false),
    P("<P>", false),
    LAMBDA("$", false),
    EOF("EOF", false);

    private final String enHR;
    private final boolean createsScope;

    Symbol(String enHR, boolean createsScope) {
        this.enHR = enHR;
        this.createsScope = createsScope;
    }

    @Override
    public String toString() {
        return enHR;
    }
    public boolean isCreatesScope(){
        return createsScope;
    };
    public List<List<Symbol>> getProjections(){
        return SYMBOL_TO_PROJECTIONS.get(this);
    }

    public static Symbol enHRToSymbol(String enHR){
        return enHRToSymbol.get(enHR);
    }

    private static final Map<Symbol, List<List<Symbol>>> SYMBOL_TO_PROJECTIONS = new HashMap<>();
    static {
        //Program
        List<List<Symbol>> projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{COMMAND_LIST}));
        SYMBOL_TO_PROJECTIONS.put(PROGRAM, projections);
        //Command List
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{COMMAND, COMMAND_LIST}));
        projections.add(Arrays.asList(new Symbol[]{LAMBDA}));
        SYMBOL_TO_PROJECTIONS.put(COMMAND_LIST, projections);
        //Command
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{ASSIGNMENT_COMMAND}));
        projections.add(Arrays.asList(new Symbol[]{FOR_LOOP}));
        SYMBOL_TO_PROJECTIONS.put(COMMAND, projections);
        //Assignment Command
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{IDN, OP_ASSIGN, E}));
        SYMBOL_TO_PROJECTIONS.put(ASSIGNMENT_COMMAND, projections);
        //For Loop
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{FOR, IDN, FROM, E, TO, E, COMMAND_LIST, END}));
        SYMBOL_TO_PROJECTIONS.put(FOR_LOOP, projections);
        //E
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{T, E_LIST}));
        SYMBOL_TO_PROJECTIONS.put(E, projections);
        //E List
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{OP_PLUS, E}));
        projections.add(Arrays.asList(new Symbol[]{OP_MINUS, E}));
        projections.add(Arrays.asList(new Symbol[]{LAMBDA}));
        SYMBOL_TO_PROJECTIONS.put(E_LIST, projections);
        //T
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{P, T_LIST}));
        SYMBOL_TO_PROJECTIONS.put(T, projections);
        //T List
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{OP_MULTIPLY, T}));
        projections.add(Arrays.asList(new Symbol[]{OP_DIVIDE, T}));
        projections.add(Arrays.asList(new Symbol[]{LAMBDA}));
        SYMBOL_TO_PROJECTIONS.put(T_LIST, projections);
        //P
        projections = new ArrayList<>();
        projections.add(Arrays.asList(new Symbol[]{IDN}));
        projections.add(Arrays.asList(new Symbol[]{NUM}));
        projections.add(Arrays.asList(new Symbol[]{OP_PLUS, P}));
        projections.add(Arrays.asList(new Symbol[]{OP_MINUS, P}));
        projections.add(Arrays.asList(new Symbol[]{LEFT_BRACKET, E, RIGHT_BRACKET}));
        SYMBOL_TO_PROJECTIONS.put(P, projections);

    }

    private static Map<String, Symbol> enHRToSymbol = new HashMap<>();
    static {
        enHRToSymbol.put(Symbol.IDN.enHR, Symbol.IDN);
        enHRToSymbol.put(Symbol.NUM.enHR, Symbol.NUM);
        enHRToSymbol.put(Symbol.FOR.enHR, Symbol.FOR);
        enHRToSymbol.put(Symbol.FROM.enHR, Symbol.FROM);
        enHRToSymbol.put(Symbol.TO.enHR, Symbol.TO);
        enHRToSymbol.put(Symbol.END.enHR, Symbol.END);
        enHRToSymbol.put(Symbol.LEFT_BRACKET.enHR, Symbol.LEFT_BRACKET);
        enHRToSymbol.put(Symbol.RIGHT_BRACKET.enHR, Symbol.RIGHT_BRACKET);
        enHRToSymbol.put(Symbol.OP_ASSIGN.enHR, Symbol.OP_ASSIGN);
        enHRToSymbol.put(Symbol.OP_PLUS.enHR, Symbol.OP_PLUS);
        enHRToSymbol.put(Symbol.OP_MINUS.enHR, Symbol.OP_MINUS);
        enHRToSymbol.put(Symbol.OP_MULTIPLY.enHR, Symbol.OP_MULTIPLY);
        enHRToSymbol.put(Symbol.OP_DIVIDE.enHR, Symbol.OP_DIVIDE);
        enHRToSymbol.put(Symbol.PROGRAM.enHR, Symbol.PROGRAM);
        enHRToSymbol.put(Symbol.COMMAND_LIST.enHR, Symbol.COMMAND_LIST);
        enHRToSymbol.put(Symbol.COMMAND.enHR, Symbol.COMMAND);
        enHRToSymbol.put(Symbol.ASSIGNMENT_COMMAND.enHR, Symbol.ASSIGNMENT_COMMAND);
        enHRToSymbol.put(Symbol.FOR_LOOP.enHR, Symbol.FOR_LOOP);
        enHRToSymbol.put(Symbol.E.enHR, Symbol.E);
        enHRToSymbol.put(Symbol.E_LIST.enHR, Symbol.E_LIST);
        enHRToSymbol.put(Symbol.T.enHR, Symbol.T);
        enHRToSymbol.put(Symbol.T_LIST.enHR, Symbol.T_LIST);
        enHRToSymbol.put(Symbol.P.enHR, Symbol.P);
        enHRToSymbol.put(Symbol.LAMBDA.enHR, Symbol.LAMBDA);
    }

}
