package manageme.logic.commands.link;

import static manageme.commons.core.Messages.MESSAGE_LINKS_LISTED_OVERVIEW;
import static manageme.logic.commands.CommandTestUtil.assertCommandSuccess;
import static manageme.testutil.TypicalLinks.LINK_A;
import static manageme.testutil.TypicalLinks.LINK_B;
import static manageme.testutil.TypicalManageMe.getTypicalManageMe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import manageme.model.Model;
import manageme.model.ModelManager;
import manageme.model.UserPrefs;
import manageme.model.link.LinkNameContainsKeywordsPredicate;

public class FindLinkCommandTest {
    private Model model = new ModelManager(getTypicalManageMe(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalManageMe(), new UserPrefs());

    @Test
    public void equals() {
        LinkNameContainsKeywordsPredicate firstPredicate =
                new LinkNameContainsKeywordsPredicate(Collections.singletonList("first"));
        LinkNameContainsKeywordsPredicate secondPredicate =
                new LinkNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindLinkCommand findFirstCommand = new FindLinkCommand(firstPredicate);
        FindLinkCommand findSecondCommand = new FindLinkCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindLinkCommand findFirstCommandCopy = new FindLinkCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different link-> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noLinkFound() {
        String expectedMessage = String.format(MESSAGE_LINKS_LISTED_OVERVIEW, 0);
        LinkNameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindLinkCommand command = new FindLinkCommand(predicate);
        expectedModel.updateFilteredLinkList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredLinkList());
    }

    @Test
    public void execute_multipleKeywords_multipleLinksFound() {
        String expectedMessage = String.format(MESSAGE_LINKS_LISTED_OVERVIEW, 2);
        LinkNameContainsKeywordsPredicate predicate = preparePredicate("CS");
        FindLinkCommand command = new FindLinkCommand(predicate);
        expectedModel.updateFilteredLinkList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LINK_A, LINK_B), model.getFilteredLinkList());
    }

    /**
     * Parses {@code userInput} into a {@code LinkNameContainsKeywordsPredicate}.
     */
    private LinkNameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new LinkNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}