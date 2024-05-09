package puzzles.nurikabe.rules;

import edu.rpi.legup.model.tree.TreeNode;
import edu.rpi.legup.model.tree.TreeTransition;
import edu.rpi.legup.puzzle.nurikabe.Nurikabe;
import edu.rpi.legup.puzzle.nurikabe.NurikabeBoard;
import edu.rpi.legup.puzzle.nurikabe.NurikabeCell;
import edu.rpi.legup.puzzle.nurikabe.rules.UnreachableWhiteCellContradictionRule;
import edu.rpi.legup.save.InvalidFileFormatException;
import java.awt.*;
import legup.MockGameBoardFacade;
import legup.TestUtilities;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class UnreachableWhiteCellContradictionRuleTest {

    private static final UnreachableWhiteCellContradictionRule RULE =
            new UnreachableWhiteCellContradictionRule();
    private static Nurikabe nurikabe;

    @BeforeClass
    public static void setUp() {
        MockGameBoardFacade.getInstance();
        nurikabe = new Nurikabe();
    }

    /**
     * Tests the Unreachable White Cell contradiction rule for a simple case of an unreachable white
     * cell
     */
    @Test
    public void UnreachableWhiteCellContradictionRule_SimpleTest()
            throws InvalidFileFormatException {
        TestUtilities.importTestBoard(
                "puzzles/nurikabe/rules/UnreachableWhiteCellContradictionRule/SimpleUnreachableTest",
                nurikabe);
        TreeNode rootNode = nurikabe.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().get(0);
        transition.setRule(RULE);

        Assert.assertNull(RULE.checkContradiction((NurikabeBoard) transition.getBoard()));

        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        NurikabeCell cell1 = board.getCell(2, 2);

        for (int i = 0; i < board.getHeight(); i++) {
            for (int k = 0; k < board.getWidth(); k++) {
                Point point = new Point(k, i);
                if (point.equals(cell1.getLocation())) {
                    Assert.assertNull(RULE.checkRuleAt(transition, board.getCell(k, i)));
                } else {
                    Assert.assertNotNull(RULE.checkRuleAt(transition, board.getCell(k, i)));
                }
            }
        }
    }

    /**
     * Tests the Unreachable White Cell contradiction rule for a variety of white square that should
     * all be reachable in some manner
     */
    @Test
    public void UnreachableWhiteCellContradictionRule_AllCellsReachable()
            throws InvalidFileFormatException {
        TestUtilities.importTestBoard(
                "puzzles/nurikabe/rules/UnreachableWhiteCellContradictionRule/AllCellsReachable",
                nurikabe);
        TreeNode rootNode = nurikabe.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().get(0);
        transition.setRule(RULE);

        Assert.assertNotNull(RULE.checkContradiction((NurikabeBoard) transition.getBoard()));

        NurikabeBoard board = (NurikabeBoard) transition.getBoard();

        for (int i = 0; i < board.getHeight(); i++) {
            for (int k = 0; k < board.getWidth(); k++) {
                Assert.assertNotNull(RULE.checkRuleAt(transition, board.getCell(k, i)));
            }
        }
    }
}
