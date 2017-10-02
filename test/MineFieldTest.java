package com.sah.test;

import com.sah.MineField;
import com.sah.Position;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;

import static org.junit.Assert.*;

/**
 * Created by Stephen on 9/29/2017.
 */
public class MineFieldTest
{
    @Test
    public void drawTest()
    {
        CharArrayReader reader = new CharArrayReader(". . A . .\n. . . . .\n. . . . .\n. . . . .\n. . A . .\n".toCharArray());

        BufferedReader stream = new BufferedReader(reader);
        MineField field = new MineField(stream);
        System.out.println(field.draw());
        field.setCenter(new Position(1,2));
        field.moveUp();
        System.out.println(field.draw());
        field.setCenter(new Position(1,1));
        field.moveUp();
        System.out.println(field.draw());
    }

    @Test
    public void destroyMine()
    {
        CharArrayReader reader = new CharArrayReader(". . A . .\n. . . . .\n. . . . .\n. . . . .\n. . A . .\n".toCharArray());

        BufferedReader stream = new BufferedReader(reader);
        MineField field = new MineField(stream);
        System.out.println(field.draw());
        field.setCenter(new Position(1,1));
        field.torpedoHit(new Position(2,4));
        System.out.println(field.draw());

    }
}