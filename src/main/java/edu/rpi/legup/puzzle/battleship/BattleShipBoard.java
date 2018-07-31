package edu.rpi.legup.puzzle.battleship;

import edu.rpi.legup.model.gameboard.GridBoard;

import java.util.ArrayList;
import java.util.List;

public class BattleShipBoard extends GridBoard {

    private List<BattleShipClue> east;
    private List<BattleShipClue> south;

    public  BattleShipBoard(int width, int height) {
        super(width, height);

        this.east = new ArrayList<>();
        this.south = new ArrayList<>();

        for(int i = 0; i < height; i++)
        {
            east.add(null);
        }
        for(int i = 0; i < width; i++)
        {
            south.add(null);
        }
    }

    public BattleShipBoard(int size) {
        this(size,size);
    }

    public List<BattleShipClue> getEast()
    {
        return east;
    }

    public List<BattleShipClue> getSouth()
    {
        return south;
    }


    @Override
    public BattleShipCell getCell(int x, int y) {
        return (BattleShipCell) super.getCell(x, y);
    }

    @Override
    public BattleShipBoard copy()
    {
        BattleShipBoard copy = new BattleShipBoard(dimension.width, dimension.height);
        for(int x = 0; x < this.dimension.width; x++)
        {
            for(int y = 0; y < this.dimension.height; y++)
            {
                copy.setCell(x, y, getCell(x, y).copy());
            }
        }
        copy.east = this.east;
        copy.south = this.south;
        return copy;
    }
}