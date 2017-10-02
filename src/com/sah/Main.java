package com.sah;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static java.lang.String.format;


public class Main {

    public static void main(String[] args) {
        String field = args[0];
        String script = args[1];
        FileReader fieldReader = null;
        FileReader scriptReader = null;

        try {
            fieldReader = new FileReader(field);
            scriptReader = new FileReader(script);
        } catch(FileNotFoundException e)
        {
            System.out.println("Failed to read file: " + e);
            return;
        }
        BufferedReader fieldStream = new BufferedReader(fieldReader);
        BufferedReader scriptStream = new BufferedReader(scriptReader);

        GameCoordinator game = new GameCoordinator(scriptStream, fieldStream);

        System.out.print(game.run());
        int score = game.scoreRun();
        if(score==0)
        {
            System.out.println(format("fail (%d)", score));
        }
        else
        {
            System.out.println(format("pass (%d)", score));
        }


    }
}
