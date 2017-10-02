package com.sah;

import com.sah.MineField;
import com.sah.MinesweeperShip;

import java.io.BufferedReader;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

/**
 * Created by Stephen on 9/30/2017.
 */
public class GameCoordinator
{
    private MinesweeperShip ship;
    private MineField field;
    public GameCoordinator(BufferedReader shipIn, BufferedReader fieldIn)
    {
        field = new MineField(fieldIn);
        ship = new MinesweeperShip(shipIn, field.getCenter());
    }

    public String run()
    {
        StringBuilder output = new StringBuilder();
        int turnNumber = 1;
        do
        {
            output.append("Step " + turnNumber + "\n\n");
            output.append(field.draw() + "\n");
            output.append(ship.currentTurn() + "\n\n");
            List<Position> fireSolution = ship.fire();
            for(Position target : fireSolution)
            {
                field.torpedoHit(target);
            }
            ship.move();
            field.setCenter(ship.getCurrentPosition());
            field.moveUp();
            output.append(field.draw() + "\n");
            turnNumber++;
        } while (ship.nextTurn() && field.getNumberActiveMines()!=0 && field.getNumberMissedMines()==0);
        return output.toString();
    }

    public int scoreRun()
    {
        int score = 0;
        if(field.getNumberMissedMines() > 0 || field.getNumberActiveMines() > 0)
        {
            score = 0;
        }
        else if(ship.numberOfStepsRemaining()!=0)
        {
            score = 1;
        }
        else
        {
            int totalMines = field.getTotalNumberOfStartingMines();
            int maxScore = totalMines * 10;
            int moves = ship.getMovesTaken();
            int shotsFired = ship.getShotsFired();

            int penalty = min(moves*2, 3*totalMines) + min(shotsFired*5, 5*totalMines);
            score = maxScore - penalty;
        }
        return score;
    }
}
