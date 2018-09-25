package hr.fer.zemris.ppj.lab02;

import hr.fer.zemris.ppj.lab01.Token;

public class SyntaxErrorException extends Exception{

    private static final long serialVersionUID = 1L;
    private static Token lastToken;
    private Token token;
    private int tokenMoves;

    public SyntaxErrorException(Token token, int tokenMoves) {
        this.token = token;
        lastToken = token;
        this.tokenMoves = tokenMoves;
    }

    public String message() { return token.toString(); }
    public int tokenMoves() { return tokenMoves; }
    public Token token() { return token; }

}
