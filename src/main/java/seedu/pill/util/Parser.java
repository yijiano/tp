package seedu.pill.util;

import seedu.pill.command.HelpCommand;
import seedu.pill.command.AddItemCommand;
import seedu.pill.command.DeleteItemCommand;
import seedu.pill.command.EditItemCommand;
import seedu.pill.command.ExpiredCommand;
import seedu.pill.command.ExpiringCommand;
import seedu.pill.command.FindCommand;
import seedu.pill.command.ListCommand;
import seedu.pill.command.RestockAllCommand;
import seedu.pill.command.RestockItemCommand;
import seedu.pill.command.SetCostCommand;
import seedu.pill.command.SetPriceCommand;
import seedu.pill.command.StockCheckCommand;
import seedu.pill.command.UseItemCommand;
import seedu.pill.command.TransactionHistoryCommand;
import seedu.pill.command.OrderCommand;
import seedu.pill.command.FulfillCommand;
import seedu.pill.command.ViewOrdersCommand;
import seedu.pill.command.TransactionsCommand;
import seedu.pill.command.VisualizeCostCommand;
import seedu.pill.command.VisualizePriceCommand;
import seedu.pill.command.VisualizeStockCommand;
import seedu.pill.command.VisualizeCostPriceCommand;

import seedu.pill.util.Order.OrderType;

import seedu.pill.exceptions.ExceptionMessages;
import seedu.pill.exceptions.PillException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Parser {
    private boolean exitFlag = false;
    private final ItemMap items;
    private final Storage storage;
    private final TransactionManager transactionManager;
    private final Ui ui;
    private final Visualizer visualizer;

    /**
     * Public constructor for Parser.
     */
    public Parser(ItemMap items, Storage storage, TransactionManager transactionManager, Ui ui) {
        this.items = items;
        this.storage = storage;
        this.transactionManager = transactionManager;
        this.ui = ui;
        this.visualizer = new Visualizer(items.getItemsAsArrayList());
    }

    /**
     * Processes the user's command.
     *
     * @param input The user's input command from the scanner.
     */
    public void parseCommand(String input) {
        try {
            input = input.trim();
            String[] splitInput = input.split("\\s+");
            String commandString = splitInput[0].toLowerCase();
            String argument = splitInput.length > 1 ? splitInput[1] : null;
            String flagStr = splitInput.length > 2 ? splitInput[2] : "";
            String arguments = String.join(" ", Arrays.copyOfRange(splitInput, 1, splitInput.length));

            switch (commandString) {
            case "exit":
                this.exitFlag = true;
                visualizer.closeCharts();
                break;
            case "add":
                parseAddItemCommand(arguments).execute(this.items, this.storage);
                break;
            case "delete":
                parseDeleteItemCommand(arguments).execute(this.items, this.storage);
                break;
            case "edit":
                parseEditItemCommand(arguments).execute(this.items, this.storage);
                break;
            case "find":
                new FindCommand(arguments).execute(this.items, this.storage);
                break;
            case "help":
                boolean flag = flagStr.equals("-v");
                new HelpCommand(argument, flag).execute(this.items, this.storage);
                break;
            case "list":
                if (splitInput.length > 1) {
                    throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
                }
                new ListCommand().execute(this.items, this.storage);
                break;
            case "stock-check":
                if (splitInput.length != 2) {
                    throw new PillException(ExceptionMessages.INVALID_STOCKCHECK_COMMAND);
                }
                if (!argument.matches("\\d+")) {
                    throw new PillException(ExceptionMessages.INVALID_STOCKCHECK_COMMAND);
                }
                new StockCheckCommand(argument).execute(this.items, this.storage);
                break;
            case "expired":
                if (splitInput.length > 1) {
                    throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
                }
                new ExpiredCommand().execute(this.items, this.storage);
                break;
            case "expiring":
                if (splitInput.length > 2) {
                    throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
                }
                if (!this.isValidDate(arguments)) {
                    throw new PillException(ExceptionMessages.PARSE_DATE_ERROR);
                }
                LocalDate expiryDate = parseExpiryDate(arguments);
                new ExpiringCommand(expiryDate).execute(this.items, this.storage);
                break;
            case "cost":
                parseSetCostCommand(arguments).execute(this.items, this.storage);
                break;
            case "price":
                parseSetPriceCommand(arguments).execute(this.items, this.storage);
                break;
            case "restock-all":
                parseRestockAllCommand(arguments).execute(this.items, this.storage);
                break;
            case "restock":
                parseRestockItemCommand(arguments).execute(this.items, this.storage);
                break;
            case "visualize-price":
                new VisualizePriceCommand(visualizer).execute(this.items, this.storage);
                break;
            case "visualize-cost":
                new VisualizeCostCommand(visualizer).execute(this.items, this.storage);
                break;
            case "visualize-stock":
                new VisualizeStockCommand(visualizer).execute(this.items, this.storage);
                break;
            case "visualize-cost-price":
                new VisualizeCostPriceCommand(visualizer).execute(this.items, this.storage);
                break;
            case "use":
                parseUseItemCommand(arguments).execute(this.items, this.storage);
                break;
            case "order":
                parseOrderCommand(arguments).execute(this.items, this.storage);
                break;
            case "view-orders":
                new ViewOrdersCommand(transactionManager).execute(this.items, this.storage);
                break;
            case "transactions":
                if (splitInput.length > 1) {
                    throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
                }
                new TransactionsCommand(transactionManager).execute(this.items, this.storage);
                break;
            case "transaction-history":
                parseTransactionHistoryCommand(arguments).execute(this.items, this.storage);
                break;
            case "fulfill-order":
                parseFulfillCommand(arguments).execute(this.items, this.storage);
                break;
            default:
                throw new PillException(ExceptionMessages.INVALID_COMMAND);
            }
        } catch (PillException e) {
            PillException.printException(e);
        }
    }

    /**
     * Parses the arguments provided to create a {@link FulfillCommand} instance, which is used to fulfill an order.
     *
     * @param arguments The command input containing the order UUID to be fulfilled.
     * @return A {@link FulfillCommand} instance that contains the order and transaction manager.
     * @throws PillException if the input contains too many arguments, is empty, cannot be parsed as a number,
     *                       or if the specified order UUID is invalid.
     *
     */
    private FulfillCommand parseFulfillCommand(String arguments) throws PillException {
        String[] commandArguments = arguments.split("\\s+");
        if (commandArguments.length > 1) {
            throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
        }
        if (commandArguments.length == 0 || arguments.isEmpty()) {
            throw new PillException(ExceptionMessages.INVALID_FULFILL_COMMAND);
        }
        try {
            List<Order> orders = transactionManager.getOrders();
            String orderToFetch = commandArguments[0];
            Order order = orders.stream()
                    .filter(orderInfo -> orderInfo.getId().toString().equals(orderToFetch))
                    .findFirst()
                    .orElse(null);
            if (order == null) {
                throw new PillException(ExceptionMessages.INVALID_ORDER);
            }
            return new FulfillCommand(order, transactionManager);
        } catch (IndexOutOfBoundsException e) {
            throw new PillException(ExceptionMessages.INVALID_ORDER);
        }
    }

    /**
     * Parses the arguments provided to create a {@link TransactionHistoryCommand} instance,
     * which retrieves the transaction history for a specified date range.
     *
     * @param arguments The command input containing either one or two date-time arguments
     *                  representing the start (and optionally the end) of the date range.
     * @return A {@link TransactionHistoryCommand} instance that contains the specified
     *                                             date range and transaction manager.
     * @throws PillException if the input contains too many arguments, is empty,
     *                       has an invalid date-time format.
     *
     */
    private TransactionHistoryCommand parseTransactionHistoryCommand(String arguments) throws PillException {
        String[] commandArguments = arguments.split("\\s+");
        LocalDate start;
        LocalDate end;
        if (commandArguments.length > 2) {
            throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
        }
        if (commandArguments.length == 0) {
            throw new PillException(ExceptionMessages.INVALID_TRANSACTION_HISTORY_COMMAND);
        } else if (commandArguments.length == 1) {
            try {
                start = LocalDate.parse(commandArguments[0]);
                return new TransactionHistoryCommand(start, LocalDate.now(), transactionManager);
            } catch (DateTimeParseException e) {
                throw new PillException(ExceptionMessages.INVALID_DATETIME_FORMAT);
            }
        } else {
            try {
                start = LocalDate.parse(commandArguments[0]);
                end = LocalDate.parse(commandArguments[1]);
                return new TransactionHistoryCommand(start, end, transactionManager);
            } catch (DateTimeParseException e) {
                throw new PillException(ExceptionMessages.INVALID_DATETIME_FORMAT);
            }
        }
    }

    /**
     * Parses the arguments provided to create an {@link OrderCommand} instance, which handles
     * ordering items for either purchasing or dispensing, based on the specified order type.
     *
     * @param arguments The command input containing the order type (either "PURCHASE" or "DISPENSE")
     *                  and the number of items to order.
     * @return An {@link OrderCommand} instance that contains the specified items to order,
     *                                 transaction manager, and order type.
     * @throws PillException if the input contains too many arguments, is empty, has an invalid order type,
     *                       or if item details are incorrectly formatted.
     *
     */
    private OrderCommand parseOrderCommand(String arguments) throws PillException {
        String commandArguments;
        String notes = null;

        if (arguments.contains("\"")) {
            // Split at the first quotation mark
            int quoteStart = arguments.indexOf("\"");

            // Extract the non-quoted section (order type and quantity)
            commandArguments = arguments.substring(0, quoteStart).trim();

            // Extract the quoted section as notes
            int quoteEnd = arguments.lastIndexOf("\"");
            if (quoteEnd == quoteStart) {
                throw new PillException(ExceptionMessages.INVALID_ORDER_COMMAND); // Unbalanced quotes
            }
            notes = arguments.substring(quoteStart + 1, quoteEnd);
        } else {
            // No notes, so the entire input is order type and quantity
            commandArguments = arguments.trim();
        }

        String[] orderTypeAndQuantity = commandArguments.split("\\s+");

        if (orderTypeAndQuantity.length > 2) {
            throw new PillException(ExceptionMessages.TOO_MANY_ARGUMENTS);
        } else if (orderTypeAndQuantity.length < 2) {
            throw new PillException(ExceptionMessages.INVALID_ORDER_COMMAND);
        }

        ItemMap itemsToOrder = new ItemMap();
        OrderType orderType = null;
        int numberOfItems = parseQuantity(orderTypeAndQuantity[1]);

        if (orderTypeAndQuantity[0].equalsIgnoreCase("PURCHASE")) {
            orderType = OrderType.PURCHASE;
        } else if (orderTypeAndQuantity[0].equalsIgnoreCase("DISPENSE")) {
            orderType = OrderType.DISPENSE;
        } else {
            throw new PillException(ExceptionMessages.INVALID_ORDER_COMMAND);
        }

        boolean hasBeenPrinted = false;
        for (int i = 0; i < numberOfItems; i++) {
            String userInput = ui.getRawInput();
            String[] itemArguments = userInput.split("\\s+");
            if (itemArguments.length < 2) {
                throw new PillException(ExceptionMessages.INVALID_ITEM_FORMAT);
            }
            try {
                Item item = parseItem(itemArguments);
                if (!item.getExpiryDate().equals(Optional.<LocalDate>empty())
                        && orderType.equals(OrderType.DISPENSE)) {
                    item.setExpiryDate(null);

                    if (!hasBeenPrinted) {
                        System.out.println("Expiry dates will be ignored for dispense orders");
                        hasBeenPrinted = true;
                    }
                }
                itemsToOrder.addItemSilent(item);
            } catch (PillException e) {
                throw new PillException(ExceptionMessages.INVALID_ITEM_FORMAT);
            }
        }

        return new OrderCommand(itemsToOrder, transactionManager, orderType, notes);
    }
    /**
     * Parses an array of item arguments and returns an {@code Item} object.
     * The item arguments array is expected to contain details such as item name, quantity, and optional expiry date.
     * This method validates the format of the item arguments and ensures that only one date is included and
     * positioned correctly as the last element if present.
     *
     * <p>The parsing logic follows these rules:
     * <ul>
     *     <li>If the quantity is specified, it should be a numeric value located at the end or second to last position.
     *     </li>
     *     <li>If an expiry date is specified, it should be a valid date string located at the last position in the
     *     array.</li>
     *     <li>If no quantity is specified, it defaults to "1".</li>
     *     <li>If no date is specified, it defaults to {@code null}.</li>
     * </ul>
     *
     * @param itemArguments An array of strings containing the arguments for the item.
     *                      It may include an item name, quantity, and an optional expiry date.
     * @return An {@code Item} object constructed from the parsed item arguments.
     * @throws PillException If there are multiple dates, an invalid date format, or if the arguments are in an invalid
     *                       format.
     */
    public Item parseItem(String[] itemArguments) throws PillException {
        Integer quantityIndex = null;
        Integer dateIndex = null;

        for (int j = 0; j < itemArguments.length; j++) {
            String currentArgument = itemArguments[j];

            if (isValidDate(currentArgument)) {
                if (dateIndex != null) {
                    throw new PillException(ExceptionMessages.INVALID_ITEM_FORMAT);
                }
                dateIndex = j;

                if (j != itemArguments.length - 1) {
                    throw new PillException(ExceptionMessages.INVALID_ITEM_FORMAT);
                }
            }

            if (isANumber(currentArgument)) {
                quantityIndex = j;
            }
        }

        String itemName;
        String quantityStr = null;
        String expiryDateStr = null;

        if (quantityIndex != null && quantityIndex == itemArguments.length - 1) {
            quantityStr = itemArguments[quantityIndex];
            expiryDateStr = null;
            itemName = buildItemName(itemArguments, 0, quantityIndex);
        } else if (quantityIndex != null && quantityIndex == itemArguments.length - 2
                && isValidDate(itemArguments[quantityIndex + 1])) {
            quantityStr = itemArguments[quantityIndex];
            expiryDateStr = itemArguments[quantityIndex + 1];
            itemName = buildItemName(itemArguments, 0, quantityIndex);
        } else if (dateIndex != null && dateIndex == itemArguments.length - 1) {
            expiryDateStr = itemArguments[dateIndex];
            quantityStr = "1";
            itemName = buildItemName(itemArguments, 0, dateIndex);
        } else {
            quantityStr = "1";
            expiryDateStr = null;
            itemName = buildItemName(itemArguments, 0, itemArguments.length);
        }

        return new Item(itemName, parseQuantity(quantityStr), parseExpiryDate(expiryDateStr));
    }

    /**
     * Parses the `restockall` command with an optional threshold.
     *
     * @param arguments The user's input after the `restockall` command.
     * @return A `RestockAllCommand` with the parsed threshold.
     * @throws PillException If the input format is invalid.
     */
    private RestockAllCommand parseRestockAllCommand(String arguments) throws PillException {
        int threshold = 50;
        if (!arguments.isEmpty()) {
            if (!isANumber(arguments)) {
                throw new PillException(ExceptionMessages.INVALID_RESTOCKALL_COMMAND);
            }
            threshold = Integer.parseInt(arguments);
            if (threshold <= 0) {
                throw new PillException(ExceptionMessages.INVALID_QUANTITY);
            }
        }
        return new RestockAllCommand(threshold);
    }

    /**
     * Parses the `restock` command for a specific item, with an optional expiry date and quantity.
     *
     * @param arguments The user's input after the `restock` command.
     * @return A `RestockItemCommand` with the parsed item name, expiry date, and quantity.
     * @throws PillException If the input format is invalid.
     */
    private RestockItemCommand parseRestockItemCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");
        if (splitArguments.length < 2) {
            throw new PillException(ExceptionMessages.INVALID_RESTOCK_COMMAND);
        }

        String itemName;
        Optional<LocalDate> expiryDate = Optional.empty();
        String quantityStr;

        if (isValidDate(splitArguments[splitArguments.length - 2])) {
            expiryDate = Optional.of(parseExpiryDate(splitArguments[splitArguments.length - 2]));
            quantityStr = splitArguments[splitArguments.length - 1];
            itemName = buildItemName(splitArguments, 0, splitArguments.length - 2);
        } else {
            quantityStr = splitArguments[splitArguments.length - 1];
            itemName = buildItemName(splitArguments, 0, splitArguments.length - 1);
        }

        if (!isAPositiveInteger(quantityStr)) {
            throw new PillException(ExceptionMessages.INVALID_QUANTITY);
        }

        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            throw new PillException(ExceptionMessages.INVALID_QUANTITY);
        }

        return new RestockItemCommand(itemName, expiryDate, quantity);
    }

    /**
     * Parses the user input and creates a {@code SetCostCommand} object.
     *
     * @param arguments A string representing the user's input for setting the cost.
     * @return A {@code SetCostCommand} containing the parsed item name and cost.
     * @throws PillException If the input format is invalid.
     */
    private SetCostCommand parseSetCostCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");
        if (splitArguments.length < 2) {
            throw new PillException(ExceptionMessages.INVALID_COST_COMMAND);
        }

        String itemName = buildItemName(splitArguments, 0, splitArguments.length - 1);
        String costStr = splitArguments[splitArguments.length - 1];

        if (!isANumber(costStr)) {
            throw new PillException(ExceptionMessages.INVALID_COST_COMMAND);
        }

        double cost = Double.parseDouble(costStr);
        if (cost < 0) {
            throw new PillException(ExceptionMessages.INVALID_COST_COMMAND);
        }

        return new SetCostCommand(itemName, cost);
    }

    /**
     * Parses the user input and creates a {@code SetPriceCommand} object.
     *
     * @param arguments A string representing the user's input for setting the price.
     * @return A {@code SetPriceCommand} containing the parsed item name and price.
     * @throws PillException If the input format is invalid.
     */
    private SetPriceCommand parseSetPriceCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");
        if (splitArguments.length < 2) {
            throw new PillException(ExceptionMessages.INVALID_PRICE_COMMAND);
        }

        String itemName = buildItemName(splitArguments, 0, splitArguments.length - 1);
        String priceStr = splitArguments[splitArguments.length - 1];

        if (!isANumber(priceStr)) {
            throw new PillException(ExceptionMessages.INVALID_PRICE_COMMAND);
        }

        double price = Double.parseDouble(priceStr);
        if (price < 0) {
            throw new PillException(ExceptionMessages.INVALID_PRICE_COMMAND);
        }

        return new SetPriceCommand(itemName, price);
    }

    /**
     * Parses the user input and creates an {@code AddItemCommand} object.
     * The input is expected to contain the item name, quantity, and optional expiry date.
     * If a valid date is found, it must be the last element in the input.
     * Only one date and one quantity are allowed.
     * <p>
     * The method loops through the input array to determine the item name, quantity, and expiry date,
     * applying default values when necessary (e.g., quantity defaults to 1 if not specified).
     *
     * @param arguments A string representing the user's input for adding an item
     * @return An {@code AddItemCommand} containing the parsed item name, quantity, and optional expiry date.
     * @throws PillException If the input format is invalid
     */
    private AddItemCommand parseAddItemCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");

        if (splitArguments.length == 0) {
            throw new PillException(ExceptionMessages.INVALID_ADD_COMMAND);
        }

        Integer quantityIndex = null;
        Integer dateIndex = null;

        for (int i = 0; i < splitArguments.length; i++) {
            String currentArgument = splitArguments[i];

            if (isValidDate(currentArgument)) {
                if (dateIndex != null) {
                    throw new PillException(ExceptionMessages.INVALID_ADD_COMMAND);
                }
                dateIndex = i;

                if (i != splitArguments.length - 1) {
                    throw new PillException(ExceptionMessages.INVALID_ADD_COMMAND);
                }
            }

            if (isANumber(currentArgument)) {
                quantityIndex = i;
            }
        }

        String itemName;
        String quantityStr = null;
        String expiryDateStr = null;

        if (quantityIndex != null && quantityIndex == splitArguments.length - 1) {
            quantityStr = splitArguments[quantityIndex];
            expiryDateStr = null;
            itemName = buildItemName(splitArguments, 0, quantityIndex);
        } else if (quantityIndex != null && quantityIndex == splitArguments.length - 2
                && isValidDate(splitArguments[quantityIndex + 1])) {
            quantityStr = splitArguments[quantityIndex];
            expiryDateStr = splitArguments[quantityIndex + 1];
            itemName = buildItemName(splitArguments, 0, quantityIndex);
        } else if (dateIndex != null && dateIndex == splitArguments.length - 1) {
            expiryDateStr = splitArguments[dateIndex];
            quantityStr = "1";
            itemName = buildItemName(splitArguments, 0, dateIndex);
        } else {
            quantityStr = "1";
            expiryDateStr = null;
            itemName = buildItemName(splitArguments, 0, splitArguments.length);
        }

        /*
        if (itemName.contains(",")) {
            throw new PillException(ExceptionMessages.INVALID_ITEM_NAME);
        }
        */

        if (expiryDateStr != null) {
            if (!isValidDate(expiryDateStr)) {
                throw new PillException(ExceptionMessages.INVALID_DATE_FORMAT);
            }
        }

        if (!isAPositiveInteger(quantityStr)) {
            throw new PillException(ExceptionMessages.INVALID_QUANTITY);
        }

        assert !itemName.isEmpty() : "Item name should not be empty";

        assert isANumber(quantityStr) : "Quantity should be a valid number";

        return new AddItemCommand(itemName, parseQuantity(quantityStr), parseExpiryDate(expiryDateStr));
    }

    /**
     * Parses the user input and creates a {@code DeleteItemCommand} object.
     * The input is expected to contain the item name and optionally an expiry date.
     * If a valid date is found, it must be the last element in the input.
     * <p>
     * The method constructs the item name by looping through the input until a valid date is found.
     * Any valid date found is treated as the item's expiry date.
     *
     * @param arguments A string representing the user's input for deleting an item
     * @return A {@code DeleteItemCommand} containing the parsed item name and optional expiry date.
     * @throws PillException If the input format is invalid (e.g., more than one date or no item name provided).
     */
    private DeleteItemCommand parseDeleteItemCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");

        if (splitArguments.length == 0) {
            throw new PillException(ExceptionMessages.INVALID_DELETE_COMMAND);
        }

        StringBuilder itemNameBuilder = new StringBuilder();
        int currentIndex = 0;
        String expiryDateStr = null;

        while (currentIndex < splitArguments.length) {
            if (isValidDate(splitArguments[currentIndex])) {
                expiryDateStr = splitArguments[currentIndex];
                break;
            }
            if (currentIndex > 0) {
                itemNameBuilder.append(" ");
            }
            itemNameBuilder.append(splitArguments[currentIndex]);
            currentIndex++;
        }

        String itemName = itemNameBuilder.toString().trim();

        assert !itemName.isEmpty() : "Item name should not be empty";

        LocalDate expiryDate = expiryDateStr != null ? parseExpiryDate(expiryDateStr) : null;

        return new DeleteItemCommand(itemName, expiryDate);
    }


    /**
     * Parses the user input to create an {@code EditItemCommand} object.
     * The input is expected to contain the item name, the new quantity, and optionally the expiry date.
     * The expiry date, if present, must be the second-to-last element, with the quantity being the last element.
     * <p>
     * The method loops through the input to determine the item name, quantity, and optional expiry date.
     * The quantity is mandatory for editing, while the expiry date is optional.
     *
     * @param arguments A string representing the user's input for editing an item.
     * @return An {@code EditItemCommand} containing the parsed item name, quantity, and optional expiry date.
     * @throws PillException If the input format is invalid (e.g., no quantity provided, or multiple dates found).
     */
    private EditItemCommand parseEditItemCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");

        if (splitArguments.length == 0) {
            throw new PillException(ExceptionMessages.INVALID_EDIT_COMMAND);
        }

        Integer quantityIndex = null;
        Integer dateIndex = null;

        for (int i = 0; i < splitArguments.length; i++) {
            String currentArgument = splitArguments[i];

            if (isValidDate(currentArgument)) {
                if (dateIndex != null) {
                    throw new PillException(ExceptionMessages.INVALID_EDIT_COMMAND);
                }
                dateIndex = i;

                if (i != splitArguments.length - 1 && i != splitArguments.length - 2) {
                    throw new PillException(ExceptionMessages.INVALID_EDIT_COMMAND);
                }
            }

            if (isANumber(currentArgument)) {
                quantityIndex = i;
            }
        }

        String itemName;
        String quantityStr = null;
        String expiryDateStr = null;

        if (quantityIndex != null && quantityIndex == splitArguments.length - 1) {
            quantityStr = splitArguments[quantityIndex];
            expiryDateStr = null;
            itemName = buildItemName(splitArguments, 0, quantityIndex);
        } else if (quantityIndex != null && quantityIndex == splitArguments.length - 2
                && isValidDate(splitArguments[quantityIndex + 1])) {
            quantityStr = splitArguments[quantityIndex];
            expiryDateStr = splitArguments[quantityIndex + 1];
            itemName = buildItemName(splitArguments, 0, quantityIndex);
        } else {
            throw new PillException(ExceptionMessages.INVALID_EDIT_COMMAND);
        }

        assert !itemName.isEmpty() : "Item name should not be empty";

        assert isANumber(quantityStr) : "Quantity should be a valid number";

        return new EditItemCommand(itemName, parseQuantity(quantityStr), parseExpiryDate(expiryDateStr));
    }

    /**
     * Parses the user input and creates an {@code UseItemCommand} object.
     * The input is expected to contain the item name and an optional quantity.
     * Only one quantity is allowed.
     * <p>
     * The method loops through the input array to determine the item name and quantity,
     * applying default values when necessary (e.g., quantity defaults to 1 if not specified).
     *
     * @param arguments A string representing the user's input for using an item
     * @return An {@code UseItemCommand} containing the parsed item name and quantity.
     * @throws PillException If the input format is invalid
     */
    private UseItemCommand parseUseItemCommand(String arguments) throws PillException {
        String[] splitArguments = arguments.split("\\s+");

        if (splitArguments.length < 2) {
            throw new PillException(ExceptionMessages.INVALID_USE_COMMAND);
        }

        StringBuilder itemNameBuilder = new StringBuilder();
        int currentIndex = 0;
        int quantity = 1; // default use amount is 1

        while (currentIndex < splitArguments.length) {
            if (isANumber(splitArguments[currentIndex])) {
                quantity = parseQuantity(splitArguments[currentIndex]);
                break;
            } else if (currentIndex > 0) {
                itemNameBuilder.append(" ");
            }
            itemNameBuilder.append(splitArguments[currentIndex]);
            currentIndex++;
        }

        String itemName = itemNameBuilder.toString().trim();
        assert !itemName.isEmpty() : "Item name should not be empty";

        return new UseItemCommand(itemName, quantity);
    }

    /**
     * Checks if the provided string represents a valid date in the format {@code YYYY-MM-DD}.
     * This method attempts to parse the string into a {@code LocalDate} object.
     *
     * @param dateStr A string representing the date to be validated.
     * @return {@code true} if the string is a valid date, {@code false} otherwise.
     */
    private boolean isValidDate(String dateStr) {
        try {
            String[] dateparts = dateStr.split("-");
            if (Integer.parseInt(dateparts[1]) > 12 || Integer.parseInt(dateparts[1]) < 1) {
                return false;
            } else if (Integer.parseInt(dateparts[2]) > 31 || Integer.parseInt(dateparts[2]) < 1) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Constructs the item name by concatenating the elements of the input array from startIndex to endIndex.
     * The method ensures that the item name is built by appending each element with a space between words.
     *
     * @param splitArguments An array of strings representing the user's input.
     * @param startIndex     The starting index (inclusive) for building the item name.
     * @param endIndex       The ending index (exclusive) for building the item name.
     * @return A string representing the item name, constructed from the input array.
     */
    private String buildItemName(String[] splitArguments, int startIndex, int endIndex) {
        StringBuilder itemNameBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                itemNameBuilder.append(" ");
            }
            itemNameBuilder.append(splitArguments[i]);
        }
        return itemNameBuilder.toString().trim();
    }

    /**
     * Parses a string representing an expiry date into a {@code LocalDate} object.
     *
     * @param expiryDateStr A string representing the expiry date in ISO-8601 format (yyyy-MM-dd).
     * @return A {@code LocalDate} object representing the expiry date, or {@code null} if no expiry date is provided.
     * @throws PillException If the expiry date string is not in the correct format.
     */
    private LocalDate parseExpiryDate(String expiryDateStr) throws PillException {
        try {
            if (expiryDateStr == null) {
                return null;
            }
            return LocalDate.parse(expiryDateStr);
        } catch (DateTimeParseException e) {
            throw new PillException(ExceptionMessages.PARSE_DATE_ERROR);
        }
    }

    /**
     * Checks if a given string is a valid number (integer or decimal).
     *
     * @param s The string to check.
     * @return {@code true} if the string can be parsed into a double; {@code false} otherwise.
     */
    private boolean isANumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a given string is a valid positive integer.
     *
     * @param s The string to check.
     * @return {@code true} if the string can be parsed into a positive integer; {@code false} otherwise.
     */
    private boolean isAPositiveInteger(String s) {
        try {
            int number = Integer.parseInt(s);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parses the quantity string into an integer.
     *
     * @param quantityStr The string representation of the quantity to parse.
     * @return The parsed quantity as an integer.
     * @throws PillException If the quantity is not a valid positive integer.
     */
    private int parseQuantity(String quantityStr) throws PillException {
        try {
            int quantity = Integer.parseInt(quantityStr);
            assert quantity > 0 : "Quantity must be positive";
            if (quantity <= 0) {
                throw new PillException(ExceptionMessages.INVALID_QUANTITY);
            }
            return quantity;
        } catch (NumberFormatException e) {
            throw new PillException(ExceptionMessages.INVALID_QUANTITY_FORMAT);
        }
    }

    /**
     * Returns an exit flag for the Pill bot to exit.
     *
     * @return The state of exit flag.
     */
    public boolean getExitFlag() {
        return this.exitFlag;
    }
}
