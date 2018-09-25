package hr.fer.zemris.ppj.lab02;

import hr.fer.zemris.ppj.lab01.Symbol;
import hr.fer.zemris.ppj.lab01.Token;

import java.util.LinkedList;
import java.util.List;

public class DescentFunction {

    private List<Node> output;
    private int tokenMoves;
    private static SyntaxErrorException furthestSee = null;

    public DescentFunction() {
        super();
        output = new LinkedList<>();
        tokenMoves = 0;

    }

    public DescentFunction(int tokenMoves) {
        super();
        output = new LinkedList<>();
        this.tokenMoves = tokenMoves;
    }

    public Container apply(Symbol symbol, Token token ) throws SyntaxErrorException {
        List<List<Symbol>> projections = symbol.getProjections();
        //final symbol
        if ( projections == null ){
            //valid final symbol
            if ( symbol == token.symbol() ){
                output.add(new Node(token, null, false));
                tokenFw(token);
                return new Container(tokenMoves, output);
            //symbol differs from token, check if lambda
            } else if (symbol == Symbol.LAMBDA) {
                output.add(new Node(symbol, null, false));
                return new Container(tokenMoves, output);
            }
            //symbol invalid
            throw new SyntaxErrorException(token, tokenMoves);
        }

        //non final symbol
        //for every projection
        for ( List<Symbol> p : projections ){
            //try to parse it
            try {
                Node parent = new Node(symbol, null, false);
                for ( Symbol s : p) {
                    token = update(new DescentFunction(tokenMoves).apply(s, token), token, parent);
                }
                output.add(parent);
                return new Container(tokenMoves, output);
            //if it fails, check how far it went ( need this for error handling )
            } catch (SyntaxErrorException see){
                if ( furthestSee == null || see.tokenMoves() > furthestSee.tokenMoves() ){
                    furthestSee = see;
                }
            }
        }
        //if no projection parses, syntax error
        throw new SyntaxErrorException(furthestSee.token(), furthestSee.tokenMoves());

    }

    public static SyntaxErrorException getFurthestSee() {
        return furthestSee;
    }

    private Token tokenFw(Token t, int count ){
        tokenMoves += count;
        return t.fw(count);
    }

    private Token tokenFw( Token t ){
        tokenMoves++;
        return t.fw(1);
    }

    private Token update(Container container, Token token, Node parent) {
        parent.addChildren(container.output);
        return tokenFw(token, container.tokenMoves - tokenMoves);
    }

    public static void resetFurthestSee() {
        furthestSee = null;
    }

    public class Container {

        private int tokenMoves;
        private List<Node> output;

        public Container(int tokenMoves, List<Node> output) {
            super();
            this.tokenMoves = tokenMoves;
            this.output = output;
        }

        public List<Node> getOutput() {
            return output;
        }

        public int getTokenMoves() { return tokenMoves; }
    }

}
