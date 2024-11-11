# Chittazhi Nivedit Nandakumar - Project Portfolio Page

## Overview

Pharmacy Inventory & Logistics Ledger (PILL) is a desktop application that allows
pharmacists to keep track of and manage medicinal inventory.

PILL is fully written in Java, and users can interact with the application using
Command Line Interface (CLI).

### Summary of Contributions

- **New Feature**: Added feature to fetch expired items ([#113](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/113)).
    - What it does: Filters and lists expired items to the user.
    - Justification: Allows user to retrieve and view expired items without reading through the entire inventory.
    - Highlights: Runs on startup. It can also be accessed using the `expired` command.
- **New Feature**: Added feature to fetch expiring items ([#114](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/114)).
    - What it does: Filters and lists expiring items based on the entered cut-off date to the user.
    - Justification: Allows for easy tracking of items that are expiring post a specified cut-off date.
    - Highlights: Can be accessed using the `expiring <date>` command.
- **New Feature**: Create commands and integrate order related features including placing, viewing and fulfilling
  orders, viewing transactions and querying transaction history over a specified date-range
  ([#142](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/142)).
    - What it does: Enables user to place, view and fulfill orders. Users may also view transactions and transaction
      history over a specified date range.
    - Justification: Allows for easy tracking of orders and transactions.
    - Highlights: Replicates real life inventory functioning where the inventory is controlled by orders that are
    - placed in bulk.
- **General Contributions**: Created PillLogger class ([#103](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/103)).
    - What it does: Responsible for console and file logging.
    - Justification: Logging is essential to understand and track the behaviour of the application.
    - Highlights: Singleton PillLogger class acts as a single entity responsible for all logging purposes.
- **New Feature**: Implemented Data Loading ([#68](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/68)).
    - What it does: Loads data stored in the local CSV file.
    - Justification: Enables loading of previously saved data.
    - Highlights: Loads any data on disk, skips corrupt lines.
- **New Feature**: Added ListCommand class
    - What it does: The class is responsible for handling the execution of the list
      command ([#7](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/7)).
    - Justification: Abstraction of the list command, inheriting from the base command class.
    - Highlights: Receives ItemMap instance and lists items.
- **New Feature**: Added FindItemCommand class
    - What it does: The class is responsible for handling the execution of the find
      command ([#20](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/20)).
    - Justification: Abstraction of the find command, inheriting from the base command class.
    - Highlights: Receives item name to search for and executes the command.
- **Code contributed**:
  [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=cnivedit&breakdown=true)
- **Project Management**:
    - Helped maintain some issues, opening
      [#69](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/69),
      [#71](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/71),
      [#143](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/143),
      [#145](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/145),
      [#146](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/146),
      [#163](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/163),
      [#239](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/239),

- **Testing**:
    - Commands
        - ListCommand ([#49](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/49))
        - FindCommand ([#51](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/51))
    - Methods
        - getExpiringItems ([#113](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/113))
        - loadLine ([#68](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/68))

- **Documentation**:
    - Developer Guide
        - Added the following sections
            - Orders and Transactions ([#241](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/241))
            - AddItem command sequence diagram ([#124](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/124))
            - Logging ([#121](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/121))
            - Target User Profile ([#115](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/115))
            - Value Proposition ([#115](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/115))
            - User Stories ([#115](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/115))
    - User Guide
        - Fixed inaccurate order command format ([#224](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/224))
        - Update fulfill command usage ([#235](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/235))

- **Community**:
    - PRs reviewed(with non-trivial comments):
      [#56](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/56),
      [#72](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/72),
      [#106](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/106),
      [#122](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/122),
      [#125](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/125),
      [#164](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/164),
      [#168](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/168),
      [#223](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/223),
      [#232](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/232),
      [#233](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/233),
      [#240](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/240),
      [#243](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/243)
