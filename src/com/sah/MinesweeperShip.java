package com.sah;

import javafx.geometry.Pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Stephen on 9/28/2017.
 */
public class MinesweeperShip {
    private enum FiringSolution { ALPHA, BETA, GAMMA, DELTA, HOLD }
    //private enum MovementDirection { NORTH, SOUTH, EAST, WEST, HOLD }
    static final Position NORTH =  new Position(0,-1);
    static final Position SOUTH =  new Position(0,1);
    static final Position EAST =  new Position(1,0);
    static final Position WEST =  new Position(-1,0);
    static final Position NO_MOV =  new Position(0,0);
    //firing solutions
    static final Position ALPHA1 = new Position(-1,-1);
    static final Position ALPHA2 = new Position(-1,1);
    static final Position ALPHA3 = new Position(1,-1);
    static final Position ALPHA4 = new Position(1,1);

    static final Position BETA1 = new Position(-1,0);
    static final Position BETA2 = new Position(0,-1);
    static final Position BETA3 = new Position(0,1);
    static final Position BETA4 = new Position(1,0);

    static final Position GAMMA1 = new Position(-1,0);
    static final Position GAMMA2 = new Position(0,0);
    static final Position GAMMA3 = new Position(1,0);

    static final Position DELTA1 = new Position(0,-1);
    static final Position DELTA2 = new Position(0,0);
    static final Position DELTA3 = new Position(0,1);

    private List<TurnActions> turns;
    private Position pos;
    private Iterator<TurnActions> currentTurnIterator;
    private TurnActions currentTurnAction;

    private int totalNumberOfSteps;
    private int stepsExecuted;
    private int shotsFired;
    private int movesTaken;

    public MinesweeperShip(BufferedReader input, Position startPos)
    {
        pos = startPos;
        turns = new ArrayList<>();
        String line = null;
        do
        {
            try
            {
                line = input.readLine();
            } catch (IOException e)
            {
                System.out.println("Failed to read line from scripts file: " + e);
                line = null;
            }
            if(line!=null)
                turns.add(parseScriptLine(line));
        } while(line!=null);
        currentTurnIterator = turns.listIterator();
        currentTurnAction = currentTurnIterator.next();
        totalNumberOfSteps = turns.size();
        stepsExecuted = 0;
        shotsFired = 0;
        movesTaken = 0;
    }

    public List<Position> fire()
    {
        List<Position> hitSquares = new ArrayList<>();
        int x = pos.x;
        int y = pos.y;
        switch(currentTurnAction.fire)
        {
            case ALPHA:
                hitSquares.add(pos.add(ALPHA1));
                hitSquares.add(pos.add(ALPHA2));
                hitSquares.add(pos.add(ALPHA3));
                hitSquares.add(pos.add(ALPHA4));
                shotsFired++;
                break;
            case BETA:
                hitSquares.add(pos.add(BETA1));
                hitSquares.add(pos.add(BETA2));
                hitSquares.add(pos.add(BETA3));
                hitSquares.add(pos.add(BETA4));
                shotsFired++;
                break;
            case GAMMA:
                hitSquares.add(pos.add(GAMMA1));
                hitSquares.add(pos.add(GAMMA2));
                hitSquares.add(pos.add(GAMMA3));
                shotsFired++;
                break;
            case DELTA:
                hitSquares.add(pos.add(DELTA1));
                hitSquares.add(pos.add(DELTA2));
                hitSquares.add(pos.add(DELTA3));
                shotsFired++;
                break;
            case HOLD:
                break;
        }
        return hitSquares;
    }

    public Position getCurrentPosition()
    {
        return pos;
    }

    public int getMovesTaken()
    {
        return movesTaken;
    }

    public int getShotsFired()
    {
        return shotsFired;
    }

    public TurnActions currentTurn()
    {
        return currentTurnAction;
    }

    public void move()
    {
        if(currentTurnAction.move != NO_MOV)
        {
            movesTaken++;
        }
        pos = pos.add(currentTurnAction.move);
    }

    public boolean nextTurn()
    {
        boolean isNextTurnValid = currentTurnIterator.hasNext();
        if(isNextTurnValid)
        {
            currentTurnAction = currentTurnIterator.next();
        }
        else
            currentTurnAction = null;
        stepsExecuted++;
        return isNextTurnValid;
    }

    public int numberOfStepsRemaining()
    {
        return totalNumberOfSteps - stepsExecuted;
    }

    public String toString()
    {
        StringBuilder output = new StringBuilder();
        for (TurnActions turn : turns)
        {
            output.append(turn.toString());
            output.append("\n");
        }
        return output.toString();
    }

    private TurnActions parseScriptLine(String line)
    {
        TurnActions rVal = new TurnActions();
        if(line!=null && !line.equals(""))
        {
            String splits[] = line.split("\\s+");
            if(splits.length==2)
            {
                boolean fireFound = rVal.setFiringFromString(splits[0]);
                if(!fireFound)
                    throw new RuntimeException("Illegal first parameter in script. Line: " + line);
                boolean moveFound = rVal.setMovementFromString(splits[1]);
                if(!moveFound)
                    throw new RuntimeException("Illegal second parameter in script. Line: " + line);
            }
            else
            {
                boolean found = rVal.setFiringFromString(splits[0]);
                if (!found)
                    found = rVal.setMovementFromString(splits[0]);
                if (!found)
                    throw new RuntimeException("Unrecognized script input. Line: " + line);
            }
        }
        return rVal;
    }

    private class TurnActions
    {
        private FiringSolution fire;
        private Position move;
        private String firingString;
        private String movementString;

        public TurnActions()
        {
            fire = FiringSolution.HOLD;
            firingString = "";
            move = NO_MOV;
            movementString = "";
        }

        public boolean setFiringFromString(String in)
        {
            boolean matched = false;
            switch(in)
            {
                case "alpha":
                {
                    matched = true;
                    fire = FiringSolution.ALPHA;
                    firingString = "alpha";
                    break;
                }
                case "beta":
                {
                    matched = true;
                    fire = FiringSolution.BETA;
                    firingString = "beta";
                    break;
                }
                case "gamma":
                {
                    matched = true;
                    fire = FiringSolution.GAMMA;
                    firingString = "gamma";
                    break;
                }
                case "delta":
                {
                    matched = true;
                    fire = FiringSolution.DELTA;
                    firingString = "delta";
                    break;
                }
            }
            return matched;
        }

        public boolean setMovementFromString(String in)
        {
            boolean matched = false;
            switch(in)
            {
                case "north":
                {
                    matched = true;
                    move = NORTH;
                    movementString = "north";
                    break;
                }
                case "south":
                {
                    matched = true;
                    move = SOUTH;
                    movementString = "south";
                    break;
                }
                case "east":
                {
                    matched = true;
                    move = EAST;
                    movementString = "east";
                    break;
                }
                case "west":
                {
                    matched = true;
                    move = WEST;
                    movementString = "west";
                    break;
                }
            }
            return matched;
        }

        public String toString()
        {
            String rVal = "";
            if(firingString.equals(""))
                rVal = movementString;
            else
                rVal = firingString + " " + movementString;
            return rVal;
        }
    }
}
