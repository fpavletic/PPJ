package hr.fer.zemris.ppj.lab01;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeksickiAnalizator {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws FileNotFoundException {

        String text = new Scanner(System.in).useDelimiter("\\Z").next();
        Tokenizer PBJLove = new Tokenizer(text);
        //PrintWriter writer = new PrintWriter("./test.out");
        while ( PBJLove.hasNext() ){
            //writer.write(PBJLove.next().toString() + System.lineSeparator());
            System.out.println(PBJLove.next());
        }
        //writer.flush();
    }
}
