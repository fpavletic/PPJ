package hr.fer.zemris.ppj.lab01;

public class Token {

    private String value;
    private Symbol symbol;
    private int lineIndex;
    private Token next;

    public Token(String value, Symbol symbol, int lineIndex) {
        super();
        this.value = value;
        this.symbol = symbol;
        this.lineIndex = lineIndex;
        next = null;
    }

    public Token(String line) {

        String[] split = line.split("\\s");
        symbol = Symbol.enHRToSymbol(split[0]);
        if ( split.length > 1 ) {
            lineIndex = Integer.parseInt(split[1]);
            value = split[2];
        } else {
            lineIndex = -1;
        }
        next = null;
    }

    public String value() {
        return value;
    }

    public Symbol symbol() {
        return symbol;
    }

    public int lineIndex() {
        return lineIndex;
    }

    public Token next() {
        return next;
    }

    public Token setNext(Token next) {
        Token tmp = this.next;
        this.next = next;
        return tmp;
    }

    public Token fw(int count) {
        Token tmp = this;
        for (; count > 0; count--) {
            if (tmp.next == null) {
                return eof(this);
            }

            tmp = tmp.next;
        }
        return tmp;
    }

    public static Token eof(Token current) {
        return new Token(null, Symbol.EOF, current.lineIndex);
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        if ( symbol != null ){
            toString.append(symbol);
        }
        if ( lineIndex != -1 ){
            toString.append(" ");
            toString.append(lineIndex);
        }
        if ( value != null ){
            toString.append(" ");
            toString.append(value);;
        }
        return toString.toString();
    }
}
