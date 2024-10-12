package seedu.pill.command;

import seedu.pill.util.ItemList;


public class FindCommand extends Command {
    private final String itemName;

    public FindCommand(String itemName) { this.itemName = itemName;}

    @Override
    public void execute(ItemList itemList) {
        ItemList foundItems = itemList.findItem(itemName);
        ListCommand listCommand = new ListCommand();
        listCommand.execute(foundItems);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
