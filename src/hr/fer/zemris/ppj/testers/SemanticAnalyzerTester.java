package hr.fer.zemris.ppj.testers;

import hr.fer.zemris.ppj.lab03.SemantickiAnalizator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class SemanticAnalyzerTester {

    public static void main(String[] args) throws FileNotFoundException{
        for ( int j = 1; j < 3; j++ ){
            for ( int i = j - 1; i < 21; i++ ){
                String testName = String.format("./testovi%d/test%02d/test.", j, i);
                System.setIn(new FileInputStream(testName + "in"));
                System.setOut(new PrintStream("./test.out"));
                SemantickiAnalizator.main(null);
                System.out.flush();
                Scanner correctOutput = new Scanner(new File(testName + "out"));
                Scanner output = new Scanner(new File("./test.out"));
                while (correctOutput.hasNext()) {
                    if (!output.nextLine().equals(correctOutput.nextLine())) {
                        System.err.println(testName + ": FAILED");
                        System.exit(1);
                    }
                }
                correctOutput.close();
                output.close();
                System.err.println(testName + ": PASSED");
            }
        }
    }

}
