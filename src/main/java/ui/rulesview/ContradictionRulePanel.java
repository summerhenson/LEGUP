package ui.rulesview;

import javax.swing.*;

public class ContradictionRulePanel extends RulePanel
{
    /**
     * ContradictionRulePanel Constructor - creates a ContradictionRulePanel
     *
     * @param ruleFrame rule frame that this ContradictionRulePanel is contained in
     */
    ContradictionRulePanel(RuleFrame ruleFrame)
    {
        super(ruleFrame);
        this.icon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("images/Legup/Contradictions.gif"));
        this.name = "Contradiction Rules";
        this.toolTip = "Contradiction Rules";
    }
}
