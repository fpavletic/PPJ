package hr.fer.zemris.ppj.lab03;

import hr.fer.zemris.ppj.lab02.Node;
import hr.fer.zemris.ppj.lab02.Parser;

import java.io.FileNotFoundException;
import java.util.List;

public class SemantickiAnalizator {

    public static void main(String[] args) throws FileNotFoundException{

        Node root = Parser.readTree(System.in);
                //.print(0, new PrintStream(new File("./primjer3.out")));
                //.print(0, System.out);
        List<String> output = new VariableScopeChecker(root).checkScopes();
        for ( String line : output ){
            System.out.println(line);
        }

    }

}
