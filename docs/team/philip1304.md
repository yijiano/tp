# Philip Chang - Project Portfolio Page

## Overview
Pharmacy Inventory & Logistics Ledger (PILL) is a desktop application that allows pharmacists to keep track of and manage medicinal inventory.

PILL is fully written in Java, and users can interact with the application using Command Line Interface (CLI).

### Summary of Contributions
- **New Feature**: `HelpCommand`
    - **What it does**: Implements command to enable the user to ask the program for help. 
                        Every available command has its own help command, with optional verbose to provide the user with more detailed help information.
    - **Justification**: It is incredibly crucial for the user to ask the program for help on the go, 
                         especially if they do not have the User Guide at hand.
    - **Highlights**: Making sure every command had its own simple and detailed help command was a little 
                      mind-numbing, but it was an important feature that our program needed to have.
                      Writing the code so that the printed help information was consistent and looked tidy was
                      also a slow but important task.

- **New Feature**: `DateTime`
    - **What it does**: Allows the program to provide accurate and consistently formatted date & time information
                        to the user.
    - **Justification**: Medicine must be stored properly with expiry dates that are properly kept track of. It is also
                         imperative that all actions are accurately logged and timestamped in order for a pharmacy and
                         its staff to help its customers as best as possible.
    - **Highlights**: Implemented a robust and flexible DateTime wrapper class that works across the entire program, 
                      providing a consistent way to work with dates and times.

- **New Feature**: `Order`
    - **What it does**: Represents an order in the inventory. An order can either be a purchase order or a dispense
                        order. 
    - **Justification**: It is a critical component of the program which allows the user to formally track and process
                         both incoming stock and outgoing items via orders. It provides a structured way to manage
                         inventory changes and ensures consistency.
    - **Highlights**: Designed & implemented the Order class to support both purchasing and dispensing orders.

- **New Feature**: `StringMatcher`
    - **What it does**: Provides utility methods for string matching and comparison, which is particularly useful for
                        handling user input and providing helpful suggestions.
    - **Justification**: The Pill application needs to be able to handle a variety of user inputs, including commands
                         and item names. By providing robust string matching capabilities, the application can offer
                         useful suggestions to the user, improving the overall user experience and reducing errors.
    - **Highlights**: Implemented the Levenshtein distance algorithm to calculate the similarity between two strings,
                      which is used to find the closest matching command or item name when the user input doesn't
                      exactly match what the application expects. This helps the application provide helpful suggestions
                      to the user, even if they make minor typos or mistakes in their input.

- **New Feature**: `Transaction`
    - **What it does**: Represents a single transaction within the inventory management system, recording changes to the
                        inventory either through incoming stock (purchases) or outgoing stock (dispensing).
    - **Justification**: Maintaining a complete audit trail of all inventory changes is essential for a pharmacy
                         management system. The Transaction class provides a structured way to record these changes,
                         including the item name, quantity, transaction type, timestamp, and any associated order
                         information.
    - **Highlights**: The Transaction class includes unique identifiers, timestamps, and properties to capture the
                      details of each inventory transaction. It supports two main transaction types: incoming 
                      and outgoing. Transactions can be associated with a specific order,
                      providing a clear link between orders and the resulting inventory changes.

- **New Feature**: `TransactionManager`
    - **What it does**: Manages all transactions and orders in the inventory management system, serving as the central 
                        point for handling inventory movements, both incoming and outgoing 
                        transactions, as well as managing order creation and fulfillment.
    - **Justification**: The TransactionManager is a crucial component of the Pill application, as it ensures data
                         consistency between the actual inventory state and all related transactions and orders. By
                         centralizing these responsibilities, the application can maintain a complete audit trail and
                         provide reliable inventory management capabilities.
    - **Highlights**: The TransactionManager provides methods to create new transactions, either incoming
                      or outgoing, and associates them with the relevant order if applicable. It also
                      handles the creation and fulfillment of purchase and dispense orders, updating the inventory
                      accordingly. The TransactionManager maintains a comprehensive transaction history and order list,
                      which can be retrieved and listed by the application.

- **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=philip1304&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2024-09-20&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

- **Project Management**:
    - **Testing**: Wrote comprehensive unit tests for the classes I worked on (HelpCommand, DateTime, Order, 
                   StringMatcher, Transaction, TransactionManager).
    - Helped maintain some issues, working on:
      [#9](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/9)
      [#12](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/12)
      [#16](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/16)
      [#17](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/17)
      [#33](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/33)
      [#37](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/37)
      [#59](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/59)
      [#62](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/62)
      [#90](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/90)
      [#94](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/94)
  - Helped maintain some issues, opening:
      [#250](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/250)

- **Community**:
    - Reported bugs for other teams in the class.
