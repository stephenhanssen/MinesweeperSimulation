package com.sah;

import javafx.geometry.Pos;

/**
 * Created by Stephen on 9/28/2017.
 */
public class Position
{
    public int x;
    public int y;
    public Position()
    {
        x = 0;
        y = 0;
    }
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public Position add(Position rhs)
    {
        return new Position(x+rhs.x, y+rhs.y);
    }

    public String toString()
    {
        return "x: " + x + " y: " + y;
    }

    public boolean equals(int x, int y)
    {
        return this.x == x && this.y == y;
    }
}
