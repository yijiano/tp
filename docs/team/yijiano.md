# Zhang Yijian - Project Portfolio Page

## Overview
Pharmacy Inventory & Logistics Ledger (PILL) is a desktop application that allows
pharmacists to keep track of and manage medicinal inventory.

PILL is fully written in Java, and users can interact with the application using
Command Line Interface (CLI).

### Summary of Contributions

- **New Feature**: Added User Interface for the application ([#39](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/39)).
    - What it does: The Ui class is responsible for handling the user interface of the application.
    - Justification: Abstraction of the user interface from the main logic of the application.
    - Highlights: ASCII Art, welcome message, exit message, error messages and command prompt.
- **New Feature**: Added parser for commands. ([#39](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/39)).
    - What it does: Abstracts the parsing of user input into commands.
    - Justification: Separation of concerns between user input and command execution.
    - Highlights: Handles all exceptions and errors within the Parser class, allowing for cleaner code in the main logic.
- **New Feature**: Added `stock-check` command ([#106](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/106)).
    - What it does: Queries for the items in the inventory that are below a threshold quantity determined by the user input.
    - Justification: Allows for easy tracking of items that are running low in stock.
    - Highlights: Allows for Ui to prompt user to restock relevant items upon launch. Allows for other commands to determine which items are running low.
- **New Feature**: Added tests for various commands ([#122](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/122)).
    - What it does: Tests the functionality of the commands in the application.
    - Justification: Ensures that the commands are robust and working as intended.
    - Highlights: Success cases, failure cases and various edge cases and scenarios are tested to ensure that the commands are working as intended.
- **General Contributions**: Refactor ItemList to ItemMap ([#79](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/79)).
    - What it does: Allows for input of duplicate item names with different expiry dates.
    - Justification: Differentiates between items with the same name but different expiry dates. This is common occurrence in real pharmacies that deal with different batches of stock.
    - Highlights: Allows for more accurate tracking of stock levels through using expiry dates.
- **General Contributions**: Test cases for several commands ([#122](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/122), [#233](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/233))
- **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=yijiano&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2024-09-20&tabOpen=true&tabType=authorship&tabAuthor=yijiano&tabRepo=AY2425S1-CS2113-W14-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)
- **Project Management**:
    - Creation of the fork and pull request to the original repository: [PR Link](https://github.com/nus-cs2113-AY2425S1/tp/pull/28)
    - Helped maintain some issues, opening
      [#5](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/5),
      [#6](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/6),
      [#7](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/7),
      [#8](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/8),
      [#9](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/9),
      [#12](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/12),
      [#13](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/13),
      [#14](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/14),
      [#15](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/15),
      [#16](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/16),
      [#17](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/17),
      [#18](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/18),
      [#19](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/19),
      [#20](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/20),
      [#23](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/23),
      [#24](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/24),
      [#25](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/25),
      [#26](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/26),
      [#27](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/27),
      [#28](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/28),
      [#29](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/29),
      [#30](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/30),
      [#31](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/31),
      [#32](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/32),
      [#33](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/33),
      [#34](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/34),
      [#35](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/35),
      [#36](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/36),
      [#37](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/37),
      [#38](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/38),
      [#40](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/40),
      [#41](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/41),
      [#47](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/47),
      [#59](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/59),
      [#60](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/60),
      [#61](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/61),
      [#62](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/62),
      [#66](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/66),
      [#77](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/77),
      [#78](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/78),
      [#80](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/80),
      [#82](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/82),
      [#83](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/83),
      [#84](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/84),
      [#85](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/85),
      [#86](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/86),
      [#87](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/87),
      [#88](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/88),
      [#89](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/89),
      [#90](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/90),
      [#91](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/91),
      [#92](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/92),
      [#93](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/93),
      [#94](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/94),
      [#95](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/95),
      [#107](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/107),
      [#108](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/108),
      [#109](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/109),
      [#120](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/120),
      [#138](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/138)
      
- **Documentation**:
    - Developer Guide ([#123](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/123)):
      - Added the following sections
        - Contents
        - Acknowledgements
        - Design & Implementation
        - High-Level Overview
        - UI
        - Glossary
        - Testing Instructions
- **Community**:
    - PRs reviewed(with non-trivial comments):
      [#46](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/46),
      [#96](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/96),
      [#99](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/99),
      [#100](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/100),
      [#104](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/104),
      [#111](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/113),
      [#113](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/113),
      [#126](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/126)
