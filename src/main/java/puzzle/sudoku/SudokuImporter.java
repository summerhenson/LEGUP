package puzzle.sudoku;

import model.PuzzleImporter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import puzzle.lightup.LightUp;
import puzzle.lightup.LightUpBoard;
import puzzle.lightup.LightUpCell;
import save.InvalidFileFormatException;

import java.awt.*;

public class SudokuImporter extends PuzzleImporter
{
    public SudokuImporter(Sudoku sudoku)
    {
        super(sudoku);
    }

    /**
     * Creates the board for building
     *
     * @param node xml document node
     * @throws InvalidFileFormatException
     */
    @Override
    public void initializeBoard(Node node) throws InvalidFileFormatException
    {
        try
        {
            if(!node.getNodeName().equalsIgnoreCase("board"))
            {
                throw new InvalidFileFormatException("Sudoku Importer: cannot find board element");
            }
            Element boardElement = (Element)node;
            if(boardElement.getElementsByTagName("cells").getLength() == 0)
            {
                throw new InvalidFileFormatException("Sudoku Importer: no data found for board");
            }
            Element dataElement = (Element)boardElement.getElementsByTagName("cells").item(0);
            NodeList elementDataList = dataElement.getElementsByTagName("cell");

            SudokuBoard sudokuBoard;
            int size;
            int minorSize;
            if(!boardElement.getAttribute("size").isEmpty())
            {
                size = Integer.valueOf(boardElement.getAttribute("size"));
                minorSize = (int)Math.sqrt(size);
                if(minorSize * minorSize != size)
                {
                    throw new InvalidFileFormatException("Sudoku Importer: invalid board dimensions");
                }
                sudokuBoard = new SudokuBoard(size);
            }
            else
            {
                throw new InvalidFileFormatException("Sudoku Importer: invalid board dimensions");
            }

            for(int i = 0; i < elementDataList.getLength(); i++)
            {
                SudokuCell cell = (SudokuCell)puzzle.getFactory().importCell(elementDataList.item(i), sudokuBoard);
                Point loc = cell.getLocation();
                if(cell.getValueInt() != 0)
                {
                    cell.setModifiable(false);
                    cell.setGiven(true);
                }
                sudokuBoard.setCell(loc.x, loc.y, cell);
            }

            for(int y = 0; y < size; y++)
            {
                for(int x = 0; x < size; x++)
                {
                    if(sudokuBoard.getCell(x, y) == null)
                    {
                        int groupIndex = x / minorSize * minorSize + y / minorSize;
                        SudokuCell cell = new SudokuCell(0, new Point(x, y), groupIndex);
                        cell.setIndex(y * size + x);
                        cell.setModifiable(true);
                        sudokuBoard.setCell(x, y, cell);
                    }
                }
            }
            puzzle.setCurrentBoard(sudokuBoard);
        }
        catch(NumberFormatException e)
        {
            throw new InvalidFileFormatException("Sudoku Importer: unknown value where integer expected");
        }
    }
}
