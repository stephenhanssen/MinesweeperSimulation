package com.sah.test;

import com.sah.GameCoordinator;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.CharArrayReader;

import static org.junit.Assert.*;

/**
 * Created by Stephen on 9/30/2017.
 */
public class GameCoordinatorTest
{
    @Test
    public void basicCoord()
    {
        CharArrayReader fieldReader = new CharArrayReader(". . A . .\n. . . . .\n. . . . .\n. . . . .\n. . A . .\n".toCharArray());
        CharArrayReader shipReader = new CharArrayReader("north \ndelta south \nsouth \ndelta".toCharArray());

        BufferedReader field = new BufferedReader(fieldReader);
        BufferedReader ship = new BufferedReader(shipReader);

        GameCoordinator coordinator = new GameCoordinator(ship, field);

        System.out.println(coordinator.run());

    }


}