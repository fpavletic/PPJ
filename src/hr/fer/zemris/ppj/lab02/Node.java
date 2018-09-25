package hr.fer.zemris.ppj.lab02;

import hr.fer.zemris.ppj.lab01.Symbol;
import hr.fer.zemris.ppj.lab01.Token;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class Node {

    private List<Node> children;
    private Token token;
    private Node parent;
    private boolean isErr = false;

    public Node( Token token, Node parent, boolean isErr ){
        this.token = token;
        this.parent = parent;
        this.isErr = isErr;
        children = new LinkedList<>();
    }

    public Node( String text, Node parent, boolean isErr ){
        this( new Token( text ), parent, isErr );
    }

    public Node( Symbol symbol, Node parent, boolean isErr ){
        this( new Token( null, symbol, -1 ), parent, isErr );
    }

    public Node parent( int count ){
        if (count < 0) {
            return this;
        }
        if (count == 0) {
            return this;
        }
        if (parent == null) {
            throw new RuntimeException( this + " has no parent!" );
        }
        return parent.parent( count - 1 );
    }

    public List<Node> children(){
        return children;
    }

    public void addChild( Node child ){
        children.add( child );
    }

    public void addChildren( List<Node> children ){
        this.children.addAll( children );
    }

    public String toString(){
        return token.toString();
    }

    public void print( int indentation, PrintStream output ){
        if ( isErr ){
            output.print( "err " );
        }
        output.format( "%" + (indentation > 0 ? indentation : "") + "s%s\n", "", token.symbol() == Symbol.EOF ? "kraj" : token.toString() );
        for (Node child : children) {
            child.print( indentation + 1, output );
        }
    }

    public Token token(){
        return token;
    }
}
