package seedu.pill.util;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import javax.swing.JFrame;
import java.util.logging.Logger;

/**
 * The Visualizer class is responsible for rendering graphical charts to visualize item data,
 * such as prices, costs, and stock levels. It utilizes the XChart library to generate bar charts.
 */
public class Visualizer {

    private static final Logger LOGGER = PillLogger.getLogger();
    private ArrayList<Item> items;
    private List<String> itemNamesWithDates;
    private List<Double> itemPrices;
    private List<Double> itemCosts;
    private List<Integer> itemStocks;

    /**
     * Constructs a Visualizer with the specified list of items.
     *
     * @param items The list of items to be visualized.
     */
    public Visualizer(ArrayList<Item> items) {
        this.items = items;
    }

    /**
     * Processes item data for prices, preparing the item names with expiry dates (if applicable)
     * and collecting price values for items that have a price greater than 0.
     */
    private void processPriceData() {
        itemNamesWithDates = new ArrayList<>();
        itemPrices = new ArrayList<>();

        for (Item item : items) {
            if (item.getPrice() > 0) {
                String itemNameWithDate = item.getName();
                if (item.getExpiryDate() != null && item.getExpiryDate().isPresent()) {
                    itemNameWithDate += " (Expires: " + item.getExpiryDate().get().toString() + ")";
                }
                itemNamesWithDates.add(itemNameWithDate);
                itemPrices.add(item.getPrice());
            }
        }
    }

    /**
     * Processes item data for costs, preparing the item names with expiry dates (if applicable)
     * and collecting cost values for items that have a cost greater than 0.
     */
    private void processCostData() {
        itemNamesWithDates = new ArrayList<>();
        itemCosts = new ArrayList<>();

        for (Item item : items) {
            if (item.getCost() > 0) {
                String itemNameWithDate = item.getName();
                if (item.getExpiryDate() != null && item.getExpiryDate().isPresent()) {
                    itemNameWithDate += " (Expires: " + item.getExpiryDate().get().toString() + ")";
                }
                itemNamesWithDates.add(itemNameWithDate);
                itemCosts.add(item.getCost());
            }
        }
    }

    /**
     * Processes item data for stocks, preparing the item names with expiry dates (if applicable)
     * and collecting stock quantities.
     */
    private void processStockData() {
        itemNamesWithDates = new ArrayList<>();
        itemStocks = new ArrayList<>();

        for (Item item : items) {
            String itemNameWithDate = item.getName();
            if (item.getExpiryDate() != null && item.getExpiryDate().isPresent()) {
                itemNameWithDate += " (Expires: " + item.getExpiryDate().get().toString() + ")";
            }
            itemNamesWithDates.add(itemNameWithDate);
            itemStocks.add(item.getQuantity());
        }
    }

    /**
     * Processes item data for cost and price comparison, preparing item names with expiry dates
     * (if applicable) and collecting both cost and price values for items that have both values greater than 0.
     */
    private void processCostPriceData() {
        itemNamesWithDates = new ArrayList<>();
        itemPrices = new ArrayList<>();
        itemCosts = new ArrayList<>();

        for (Item item : items) {
            if (item.getCost() > 0 && item.getPrice() > 0) {
                String itemNameWithDate = item.getName();
                if (item.getExpiryDate() != null && item.getExpiryDate().isPresent()) {
                    itemNameWithDate += " (Expires: " + item.getExpiryDate().get().toString() + ")";
                }
                itemNamesWithDates.add(itemNameWithDate);
                itemPrices.add(item.getPrice());
                itemCosts.add(item.getCost());
            }
        }
    }

    /**
     * Draws a bar chart comparing item costs and prices.
     * The chart displays two bars for each item: one for cost and one for price.
     */
    public void drawCostPriceChart() {
        LOGGER.info("Drawing Cost-Price Chart");
        processCostPriceData();

        CategoryChart costPriceChart = new CategoryChartBuilder()
                .width(900)
                .height(650)
                .title("Cost and Price Comparison Chart")
                .xAxisTitle("Item Name (with Expiry Date)")
                .yAxisTitle("Value")
                .build();

        costPriceChart.getStyler().setLegendVisible(true);
        costPriceChart.getStyler().setPlotGridLinesVisible(true);
        costPriceChart.getStyler().setXAxisLabelRotation(45);

        costPriceChart.addSeries("Price", itemNamesWithDates, itemPrices);
        costPriceChart.addSeries("Cost", itemNamesWithDates, itemCosts);

        SwingWrapper<CategoryChart> swingWrapper = new SwingWrapper<>(costPriceChart);
        JFrame chartFrame = swingWrapper.displayChart();
        chartFrame.setTitle("Cost and Price Comparison Chart");
        chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Draws a bar chart for item prices.
     * The chart displays each item with its name, expiry date (if applicable), and price.
     */
    public void drawPriceChart() {
        LOGGER.info("Drawing Price Chart");
        processPriceData();

        CategoryChart priceChart = new CategoryChartBuilder()
                .width(900)
                .height(650)
                .title("Item Prices Chart")
                .xAxisTitle("Item Name (with Expiry Date)")
                .yAxisTitle("Price")
                .build();

        priceChart.getStyler().setLegendVisible(false);
        priceChart.getStyler().setPlotGridLinesVisible(true);
        priceChart.getStyler().setXAxisLabelRotation(45);

        priceChart.addSeries("Price", itemNamesWithDates, itemPrices);

        SwingWrapper<CategoryChart> swingWrapper = new SwingWrapper<>(priceChart);
        JFrame chartFrame = swingWrapper.displayChart();
        chartFrame.setTitle("Item Prices Chart");
        chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Draws a bar chart for item costs.
     * The chart displays each item with its name, expiry date (if applicable), and cost.
     */
    public void drawCostChart() {
        LOGGER.info("Drawing Cost Chart");
        processCostData();

        CategoryChart costChart = new CategoryChartBuilder()
                .width(900)
                .height(650)
                .title("Item Costs Chart")
                .xAxisTitle("Item Name (with Expiry Date)")
                .yAxisTitle("Cost")
                .build();

        costChart.getStyler().setLegendVisible(false);
        costChart.getStyler().setPlotGridLinesVisible(true);
        costChart.getStyler().setXAxisLabelRotation(45);

        costChart.addSeries("Cost", itemNamesWithDates, itemCosts);

        SwingWrapper<CategoryChart> swingWrapper = new SwingWrapper<>(costChart);
        JFrame chartFrame = swingWrapper.displayChart();
        chartFrame.setTitle("Item Costs Chart");
        chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * Draws a bar chart for item stock levels.
     * The chart displays each item with its name, expiry date (if applicable), and stock quantity.
     */
    public void drawStockChart() {
        LOGGER.info("Drawing Stock Chart");
        processStockData();

        CategoryChart stockChart = new CategoryChartBuilder()
                .width(900)
                .height(650)
                .title("Item Stocks Chart")
                .xAxisTitle("Item Name (with Expiry Date)")
                .yAxisTitle("Stock")
                .build();

        stockChart.getStyler().setLegendVisible(false);
        stockChart.getStyler().setPlotGridLinesVisible(true);
        stockChart.getStyler().setXAxisLabelRotation(45);

        stockChart.addSeries("Stock", itemNamesWithDates, itemStocks);

        SwingWrapper<CategoryChart> swingWrapper = new SwingWrapper<>(stockChart);
        JFrame chartFrame = swingWrapper.displayChart();
        chartFrame.setTitle("Item Stocks Chart");
        chartFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    // Add this method to close any open chart frames.
    public void closeCharts() {
        for (Frame frame : JFrame.getFrames()) {
            if (frame.getTitle().contains("Chart")) { // Close frames with "Chart" in their title
                frame.dispose();
            }
        }
    }
}
