package puzzles.nurikabe.rules;

import legup.MockGameBoardFacade;
import legup.TestUtilities;
import model.tree.TreeNode;
import model.tree.TreeTransition;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import puzzle.nurikabe.Nurikabe;
import puzzle.nurikabe.NurikabeBoard;
import puzzle.nurikabe.NurikabeCell;
import puzzle.nurikabe.NurikabeType;
import puzzle.nurikabe.rules.TooFewSpacesContradictionRule;
import puzzle.nurikabe.rules.WhiteBottleNeckBasicRule;
import save.InvalidFileFormatException;

public class WhiteBottleNeckBasicRuleTest
{

    private static final WhiteBottleNeckBasicRule RULE = new WhiteBottleNeckBasicRule();
    private static Nurikabe nurikabe;

    @BeforeClass
    public static void setUp()
    {
        MockGameBoardFacade.getInstance();
        nurikabe = new Nurikabe();
    }

    @Test
    public void WhiteBottleNeckBasicRule_SimpleWhiteBottleNeckTest() throws InvalidFileFormatException
    {
        TestUtilities.importTestBoard("puzzles/nurikabe/rules/WhiteBottleNeckBasicRule/SimpleWhiteBottleNeck", nurikabe);
        TreeNode rootNode = nurikabe.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().get(0);
        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        transition.setRule(RULE);

        NurikabeCell cell = board.getCell(2, 1);
        cell.setValueInt(NurikabeType.WHITE.toValue());
        board.addModifiedData(cell);

        Assert.assertNull(RULE.checkRule(transition));

        for(int i = 0; i < transition.getBoard().getModifiedData().size(); i++)
        {
            if(i == cell.getIndex())
            {
                Assert.assertNull(RULE.checkRuleAt(transition, i));
            }
            else
            {
                Assert.assertNotNull(RULE.checkRuleAt(transition, i));
            }
        }
    }

    @Test
    public void WhiteBottleNeckBasicRule_NurikabeBoard1Test() throws InvalidFileFormatException
    {
        TestUtilities.importTestBoard("puzzles/nurikabe/rules/WhiteBottleNeckBasicRule/NurikabeBoard1", nurikabe);
        TreeNode rootNode = nurikabe.getTree().getRootNode();
        TreeTransition transition = rootNode.getChildren().get(0);
        NurikabeBoard board = (NurikabeBoard) transition.getBoard();
        transition.setRule(RULE);

        NurikabeCell cell = board.getCell(0, 0);
        cell.setValueInt(NurikabeType.WHITE.toValue());
        board.addModifiedData(cell);

        Assert.assertNotNull(RULE.checkRule(transition));

        for(int i = 0; i < transition.getBoard().getModifiedData().size(); i++)
        {
            Assert.assertNotNull(RULE.checkRuleAt(transition, i));
        }
    }
}
