package seedu.pill.command;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;
import seedu.pill.util.ItemMap;
import seedu.pill.util.Storage;
import seedu.pill.util.Visualizer;

/**
 * Handles the visualization of item prices.
 */
public class VisualizePriceCommand extends Command {
    private final Visualizer visualizer;

    public VisualizePriceCommand(Visualizer visualizer) {
        this.visualizer = visualizer;
    }

    @Override
    public void execute(ItemMap items, Storage storage) throws PillException {
        visualizer.setItems(items.getItemsAsArrayList());

        try{
            visualizer.drawPriceChart();
        } catch (Exception e) {
            throw new PillException(ExceptionMessages.NOTHING_TO_VISUALIZE);
        }
    }
}
