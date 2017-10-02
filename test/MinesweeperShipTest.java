package com.sah.test;

import com.sah.MinesweeperShip;
import com.sah.Position;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Stephen on 9/28/2017.
 */
public class MinesweeperShipTest {

    @Test
    public void verifyRead()
    {
        CharArrayReader reader = null;

        reader = new CharArrayReader("gamma\nnorth\nalpha west");

        BufferedReader stream = new BufferedReader(reader);
        MinesweeperShip ship = new MinesweeperShip(stream, new Position());
        System.out.println(ship.toString());
    }

    @Test
    public void getFiringLocation()
    {
        CharArrayReader reader = new CharArrayReader("alpha\nbeta\ngamma\ndelta\nalpha\n\ndelta".toCharArray());

        BufferedReader stream = new BufferedReader(reader);
        MinesweeperShip ship = new MinesweeperShip(stream, new Position(5,5));
        int ctr = 1;
        do
        {
            List<Position> hits = ship.fire();
            System.out.println("Squares hit are: ");
            for (Position h: hits)
            {
                System.out.print("[" + h + "] ");
            }
            System.out.println("");
            ctr++;
        }while(ship.nextTurn());
        System.out.println("total turns = " + ctr);
    }

    @Test
    public void getMovement()
    {
        CharArrayReader reader = new CharArrayReader("north\nwest\nsouth\neast\neast\n\nsouth".toCharArray());

        BufferedReader stream = new BufferedReader(reader);
        MinesweeperShip ship = new MinesweeperShip(stream, new Position(5,5));
        do
        {
            System.out.println("Current Position: " + ship.getCurrentPosition());
            ship.move();
        } while(ship.nextTurn());
        System.out.println("Current Position: " + ship.getCurrentPosition());
    }
}