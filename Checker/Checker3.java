package Checker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Checker3 {
    public static void main(String[] args) throws IOException {
        System.err.println("Enter");
        String input = new BufferedReader(new InputStreamReader(System.in)).readLine();
        if(input.equals("")) {
            System.err.println("Caught " + input);
        }
    }
}
