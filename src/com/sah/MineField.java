package com.sah;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import static java.lang.Integer.max;

/**
 * Created by Stephen on 9/29/2017.
 */
public class MineField
{
    private Map<Integer, Map<Integer, Mine>> twoDRep;
    private Mine maxMineX, minMineX, maxMineY, minMineY;
    private Position center;
    private int numberOfMines;
    private int numberOfDestroyedMines;

    private int numberMissedMines;

    public MineField(BufferedReader input)
    {
        twoDRep = new HashMap<>();
        int yVal = 0;
        maxMineX = new Mine(new Position(Integer.MIN_VALUE, 0), "a");
        minMineX = new Mine(new Position(Integer.MAX_VALUE, 0), "a");
        maxMineY = new Mine(new Position(0, Integer.MIN_VALUE), "a");
        minMineY = new Mine(new Position(0, Integer.MAX_VALUE), "a");
        String line;
        int maxX = 0;
        numberOfMines = 0;
        try
        {
            while ((line = input.readLine()) != null)
            {
                String[] values = line.split("");
                for (int xVal = 0; xVal < values.length; xVal++)
                {
                    if(!values[xVal].equals("."))
                    {
                        Mine newMine = new Mine(new Position(xVal, yVal), values[xVal]);
                        Map<Integer, Mine> row = twoDRep.get(yVal);
                        numberOfMines++;
                        if(row==null)
                        {
                            row = new HashMap<>();
                            twoDRep.put(yVal, row);
                        }
                        row.put(xVal, newMine);

                        if(xVal > maxMineX.pos.x)
                            maxMineX = newMine;
                        if(yVal > maxMineY.pos.y)
                            maxMineY = newMine;
                        if(xVal < minMineX.pos.x)
                            minMineX = newMine;
                        if(yVal < minMineY.pos.y)
                            minMineY = newMine;
                    }
                }
                yVal++;
                maxX = values.length;
            }
            center = new Position(Math.floorDiv(maxX, 2), Math.floorDiv(yVal, 2));
            numberOfDestroyedMines = 0;
            numberMissedMines = 0;
        }catch(IOException e)
        {
            throw new RuntimeException("Failed to parse minefiled input file " + e);
        }
    }

    public int getNumberMissedMines()
    {
        return numberMissedMines;
    }

    public int getNumberActiveMines()
    {
        return numberOfMines - numberOfDestroyedMines;
    }

    public int getTotalNumberOfStartingMines()
    {
        return numberOfMines;
    }
    public void torpedoHit(Position torp)
    {
        Map<Integer, Mine> row = twoDRep.get(torp.y);
        if(row != null)
        {
            Mine hit = row.get(torp.x);
            if(hit != null && hit.depth != '*')
            {
                Mine removed = row.remove(torp.x);
                numberOfDestroyedMines++;
                if(row.size() == 0)
                {
                    twoDRep.remove(torp.y);
                }
                if(removed == maxMineX)
                    findNewMaxX();
                if(removed == minMineX)
                    findNewMinX();
                if(removed == maxMineY)
                    findNewMaxY();
                if(removed == minMineY)
                    findNewMinY();
            }
        }
    }

    private void findNewMaxX()
    {
        if(twoDRep.size() != 0)
        {
            int maxValue = Integer.MIN_VALUE;
            Collection<Map<Integer, Mine>> values = twoDRep.values();
            for (Map<Integer, Mine> row : values)
            {
                Set<Integer> xValues = row.keySet();
                Integer maxRowValue = Collections.max(xValues);
                if (maxRowValue > maxValue)
                {
                    maxMineX = row.get(maxRowValue);
                }
            }
        }
    }

    private void findNewMinX()
    {
        if(twoDRep.size() != 0)
        {
            int maxValue = Integer.MAX_VALUE;
            Collection<Map<Integer, Mine>> values = twoDRep.values();
            for (Map<Integer, Mine> row : values)
            {
                Set<Integer> xValues = row.keySet();
                Integer minRowValue = Collections.min(xValues);
                if (minRowValue < maxValue)
                {
                    minMineX = row.get(minRowValue);
                }
            }
        }
    }

    private void findNewMaxY()
    {
        if(twoDRep.size() != 0)
        {
            Set<Integer> yValues = twoDRep.keySet();
            Integer maxYValue = Collections.max(yValues);
            Map<Integer, Mine> row = twoDRep.get(maxYValue);
            maxMineY = row.values().iterator().next();
        }
    }

    private void findNewMinY()
    {
        if(twoDRep.size() != 0)
        {
            Set<Integer> yValues = twoDRep.keySet();
            Integer minYValue = Collections.min(yValues);
            Map<Integer, Mine> row = twoDRep.get(minYValue);
            minMineY = row.values().iterator().next();
        }
    }

    public void moveUp()
    {
        Collection<Map<Integer, Mine> > values = twoDRep.values();
        for(Map<Integer, Mine> row: values)
        {
            Collection<Mine> mines = row.values();
            for(Mine mine : mines)
            {
                char depth = mine.depth;
                if(depth == 'A')
                {
                    depth = 'z';
                }
                else if(depth == 'a')
                {
                    depth = '*';
                    numberMissedMines++;
                }
                else
                {
                    depth = (char) (depth - 1);
                }
                mine.depth = depth;
            }
        }
    }

    public void setCenter(Position pos)
    {
        center = pos;
    }

    public Position getCenter()
    {
        return center;
    }

    public String draw()
    {
        int yDiff, xDiff, yMin, yMax, xMin, xMax;
        if(twoDRep.size()!=0)
        {
            yDiff = max(maxMineY.pos.y - center.y, center.y - minMineY.pos.y);
            xDiff = max(maxMineX.pos.x - center.x, center.x - minMineX.pos.x);
            yMin = center.y - yDiff;
            yMax = center.y + yDiff;
            xMin = center.x - xDiff;
            xMax = center.x + xDiff;
        }
        else
        {
            yMin = yMax = xMax = xMin = 0;
        }
        StringBuilder output = new StringBuilder();
        for(int y = yMin; y <= yMax; y++)
        {
            Map<Integer, Mine> row = twoDRep.get(y);
            for(int x = xMin; x <= xMax; x++)
            {
                Mine mine = null;
                if(row != null)
                    mine = row.get(x);
                if(mine!=null && mine.pos.equals(x,y))
                {
                    output.append(mine.depth);
                }
                else
                {
                    output.append(".");
                }
            }
            output.append("\n");
        }
        return output.toString();
    }

    private class Mine
    {
        private Position pos;
        private char depth;
        public Mine(Position position, String depth)
        {
            pos = position;
            this.depth = depth.charAt(0);
        }
    }
}
