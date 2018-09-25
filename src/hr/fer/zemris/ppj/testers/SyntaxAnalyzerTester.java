package hr.fer.zemris.ppj.testers;

import hr.fer.zemris.ppj.lab02.DescentFunction;
import hr.fer.zemris.ppj.lab02.SintaksniAnalizator;

import java.io.*;
import java.util.Scanner;

public class SyntaxAnalyzerTester {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        for ( int j = 1; j < 3; j++ ){
            for ( int i = 1; i < 21; i++ ){
                String testName = String.format("./testovi%d/test%02d/test.", j, i);
                System.setIn(new FileInputStream(testName + "in"));
                System.setOut(new PrintStream("./test.out"));
                SintaksniAnalizator.main(null);
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
                DescentFunction.resetFurthestSee();
            }
        }
    }

}
