package hr.fer.zemris.ppj.lab02;

import hr.fer.zemris.ppj.lab01.Symbol;
import hr.fer.zemris.ppj.lab01.Token;
import hr.fer.zemris.ppj.lab02.DescentFunction.Container;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static Node generateTree(Token head, int tokenCount){
        Container result;
        try {
            result = new DescentFunction().apply(Symbol.PROGRAM, head);
        } catch (SyntaxErrorException ignoreable) {
            return new Node(DescentFunction.getFurthestSee().token(), null, true);
        }
        if ( result.getTokenMoves() != tokenCount ){
            return new Node(DescentFunction.getFurthestSee().token(), null, true);
        }
        return result.getOutput().get(0);
    }

    public static Node readTree(InputStream inputStream){

        Pattern pattern = Pattern.compile("(\\s*)(<[_A-Za-z]+>|([_A-Z]+) ([0-9]+) ([^\\n]+)|\\$)");
        String text = new Scanner(inputStream).useDelimiter("\\Z").next();
        Matcher matcher = pattern.matcher(text);
        Token lastFinalToken = null;

        if (!matcher.find()){
            System.out.println("Someone did an oopsie!");
        }
        Node head = new Node(matcher.group(2).trim(), null, false);
        Node current = head;
        int lastNonFinalLeadingWhitespace = 0;

        while (matcher.find()) {
            int leadingWhitespace = matcher.group(1).length() -1;
                if ( lastNonFinalLeadingWhitespace != leadingWhitespace -1 ){
                    current = current.parent(lastNonFinalLeadingWhitespace - leadingWhitespace +1);
                    lastNonFinalLeadingWhitespace = leadingWhitespace -1;
                }
            //Final Symbol
            if (matcher.group(3) != null) {
                Token tmp = new Token(
                        matcher.group(5),
                        Symbol.enHRToSymbol(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)));
                if ( lastFinalToken != null ){
                    lastFinalToken.setNext(tmp);
                }
                lastFinalToken = tmp;
                current.addChild(new Node(tmp, current, false ));
            //Non Final Symbol || Lambda
            } else {
                Node tmp = new Node(matcher.group(2), current, false);
                current.addChild(tmp);
                current = tmp;
                lastNonFinalLeadingWhitespace = leadingWhitespace;
            }
        }
        return head;
    }
}
