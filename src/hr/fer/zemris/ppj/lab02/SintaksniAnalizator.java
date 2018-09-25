package hr.fer.zemris.ppj.lab02;

import hr.fer.zemris.ppj.lab01.Symbol;
import hr.fer.zemris.ppj.lab01.Token;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class SintaksniAnalizator {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        Scanner text = new Scanner(System.in);

        Token head = null;
        Token tail = null;
        int tokenCount = 0;

        while (text.hasNextLine()) {
            String[] split = text.nextLine().split(" ");
            if (split.length != 3) {
                throw new RuntimeException("Something went veeery wrong!");
            }
            Token current = new Token(split[2], Symbol.enHRToSymbol(split[0]), Integer.parseInt(split[1]));
            if (head == null) {
                head = current;
            } else {
                tail.setNext(current);
            }
            tail = current;
            tokenCount++;
        }
        tail.setNext(Token.eof(tail));

        new Parser().generateTree(head, tokenCount).print(0, System.out);
    }


}
