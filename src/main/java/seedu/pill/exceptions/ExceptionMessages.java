package seedu.pill.exceptions;

public enum ExceptionMessages {
    INVALID_COMMAND                     ("Invalid command, please try again." +
                   "\nType the 'help' command for a list of valid commands."),
    SAVE_ERROR                          ("Error saving to file, please try again."),
    LOAD_ERROR                          ("Error loading saved data"),
    INVALID_LINE_FORMAT                 ("File corrupted. Ignoring invalid line format..."),
    INVALID_QUANTITY                    ("Quantity is invalid, please try again"),
    INVALID_QUANTITY_FORMAT             ("Quantity provided is not a number, please try again."),
    INVALID_ADD_COMMAND                 ("Invalid Add command format..."),
    INVALID_DELETE_COMMAND              ("Invalid Delete command format..."),
    INVALID_EDIT_COMMAND                ("Invalid Edit command format..."),
    INVALID_USE_COMMAND                 ("Invalid Use command format..."),
    STOCK_UNDERFLOW                     ("Trying to use more items than is available, please try again."),
    NO_ITEM_ERROR                       ("No item with that name in inventory, please try again."),
    PARSE_DATE_ERROR                    ("Date provided is in the wrong format, please try again."),
    ITEM_NOT_FOUND_ERROR                ("Item not found..."),
    TOO_MANY_ARGUMENTS                  ("Too many arguments. Please type 'help' for accepted commands."),
    INVALID_STOCKCHECK_COMMAND          ("Invalid stock-check command format..."),
    INVALID_COST_COMMAND                ("Invalid Cost command format..."),
    INVALID_PRICE_COMMAND               ("Invalid Price command format..."),
    INVALID_RESTOCKALL_COMMAND          ("Invalid restock-all command format..."),
    INVALID_RESTOCK_COMMAND             ("Invalid restock item command format..."),
    TRANSACTION_ERROR                   ("Error creating transaction"),
    INVALID_FULFILL_COMMAND             ("Invalid fulfillment command format..."),
    INVALID_TRANSACTION_HISTORY_COMMAND ("Invalid transaction history command format..."),
    INVALID_DATETIME_FORMAT             ("Invalid datetime format, please try again."),
    INVALID_ORDER                       ("Order not found..."),
    INVALID_INDEX                       ("Index out of bounds, please try again."),
    INVALID_ORDER_COMMAND               ("Invalid order command format..."),
    INVALID_ITEM_FORMAT                 ("Invalid item format..."),
    ORDER_NOT_PENDING                   ("Order already fulfilled..."),
    INVALID_DATE_FORMAT                 ("Invalid expiry date format. Please try again..."),
    NOTHING_TO_VISUALIZE                ("There is nothing to visualize...");


    private final String message;

    /**
     * Constructor for ExceptionMessages.
     *
     * @param message The message of the exception.
     */
    ExceptionMessages(String message) {
        this.message = message;
    }

    /**
     * Gets the message of the exception.
     *
     * @return The message of the exception.
     */
    public String getMessage() {
        return this.message;
    }
}
