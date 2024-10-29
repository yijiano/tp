# Developer Guide

## Table of Contents

- [Developer Guide](#developer-guide)
    - [Table of Contents](#table-of-contents)
    - [Acknowledgements](#acknowledgements)
    - [Design \& Implementation](#design--implementation)
        - [UI and I/O](#ui-and-io)
        - [Commands](#commands)
        - [Storage](#storage)
        - [Item and ItemMap](#item-and-itemmap)
        - [DateTime](#datetime)
        - [Exceptions and Logging](#exceptions-and-logging)
    - [Product scope](#product-scope)
        - [Target user profile](#target-user-profile)
        - [Value proposition](#value-proposition)
    - [User Stories](#user-stories)
    - [Non-Functional Requirements](#non-functional-requirements)
    - [Glossary](#glossary)
    - [Instructions for Testing](#instructions-for-testing)
        - [Manual Testing](#manual-testing)
        - [JUnit Testing](#junit-testing)
        - [Text UI Testing](#text-ui-testing)
    - [Future Enhancements](#future-enhancements)

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

<!-- @@author cxc0418 -->

### UI and I/O

The program uses a command-line interface (CLI) for interaction with the user.
It receives input as text commands, processes these commands, and provides feedback through the console.

### Commands

Each user action (e.g. adding, deleting, or editing an item) is mapped to a specific command class.
These classes handle the logic for interpreting the input and calling the appropriate methods from the ItemMap and
Storage classes.

Example:

```
AddItemCommand command = new AddItemCommand(itemName, quantity, expiryDate);
command.execute(itemMap, storage);
```

<!-- @@author yakultbottle -->

### Storage

**API**: Storage.java

Entries are stored in Comma Separated Values(CSV) format. Fields read from left
to right are: **Item type**, **Quantity**, and **Expiry date**(optional). Items
may or may not have an expiry date, but all possess an Item type and a Quantity.

Example of stored entries:

```
panadol,1
panadol,30,2024-08-03
panadol,20,2024-08-09
bandage,34
```

The Storage class depends on self-defined classes PillException, Item, and
ItemMap. While it has other dependencies, such as File and FileWriter from
the Java standard library, PillException is the only custom class it depends on.

<!-- @@author cnivedit -->

### Logging

**API**: PillLogger.java

<img src = "diagrams/PillLogger.png" alt="Component Diagram for PillLogger"/>

The project uses the `java.util.logging` package for logging, with PillLogger serving as a centralized utility class to
handle logging across the entire application. PillLogger implements the singleton pattern by maintaining a single static
logger instance, which manages log creation, configuration, and output redirection.

#### Key Components
- File Output Configuration: The log level for file output is set by the `fileHandler.setLevel()` call, using `Level.ALL` to
capture all events during execution. The log file, named according to the `FILE_NAME` attribute, is created in the
directory specified by `PATH`.

- Console Output Configuration: Console output is managed by `consoleHandler.setLevel()`. To maintain a clean terminal
output for end-users, console logging is set to `Level.OFF` by default, ensuring it is suppressed unless required for
debugging.

#### Resilience and Error Handling
In the event of a failure in log file creation, PillLogger logs the error to the console and allows the application to
continue running. This design ensures the application’s functionality is not hindered by logging setup issues.

#### API Access
PillLogger exposes a single public method, `getLogger()`, which provides application-wide access to the singleton Logger
instance. Classes within the application use `getLogger()` to record events, without needing to set up or manage their own
loggers.

<!-- @@author -->

## Product scope

### Target user profile

Pharmacy Inventory & Logistics Ledger (PILL) is designed for personnel responsible for managing and monitoring
pharmaceutical inventories. Target users include pharmacists, pharmacy cashiers, logistics in-charge personnel, and
regional managers in pharmacies or healthcare facilities, particularly those who prefer a command-line interface (CLI)
for its efficiency and simplicity.

### Value proposition

PILL provides a streamlined, user-friendly command-line interface (CLI) to manage pharmaceutical inventory with ease.
This minimalistic tool facilitates quick and efficient inventory operations, tailored to the requirements of pharmacy
staff and logistics managers who need to track stock, monitor expiry dates, and generate reports without unnecessary
complexity.

## User Stories

| Version | As a ...            | I want to ...                             | So that I can ...                                            |
|---------|---------------------|-------------------------------------------|--------------------------------------------------------------|
| v1.0    | new user            | see usage instructions                    | refer to them when I forget how to use the application       |
| v1.0    | pharmacist          | add new medicine stock                    | keep track of new arrivals                                   |
| v1.0    | pharmacy cashier    | remove items from stock                   | remove them from the record when they run out                |
| v1.0    | logistics in-charge | list out all medicines currently stocked  | get an overview of what the pharmacy has at the moment       |
| v1.0    | regional manager    | export all items as a human readable file | compare across multiple outlets                              |
| v1.0    | frequent user       | find items provided a keyword             | find relevant items without having to look through all items |
| v2.0    | logistics in-charge | list all expiring items                   | remember to clear them out                                   |
| v2.0    | logistics in-charge | list all items that need to be restocked  | place orders before items run out                            |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
