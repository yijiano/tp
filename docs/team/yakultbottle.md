# Lim Wei Ming, Benjamin - Project Portfolio Page

## Overview
Pharmacy Inventory & Logistics Ledger (PILL) is a desktop application that allows
pharmacists to keep track of and manage medicinal inventory. 

PILL is fully written in Java, and users can interact with the application using
Command Line Interface (CLI).

### Summary of Contributions

- **New Feature**: Added an expiry date field for items
  - What it does: Items added can have an expiry date. When removed, the item
      with the earliest expiry date is automatically removed first.
  - Justification: Managing expiry dates of medicinal inventory is difficult, as 
      different batches of medicine might have different expiry dates. 
  - Highlights: Not all medicinal items might have an expiry date, such as bandages. 
      As such, implementation was more challenging, as most commands that used Items
      had to be refactored, and to organise items by expiry date, a compareTo method
      had to be defined for Item.
- **New Feature**: Added the ability to save data
  - What it does: Automatically saves added items into a human-readable file. 
  - Justification: This feature improves the product significantly because 
      a user might want to refer to the stock list of items even when the product
      is offline.
  - Highlights: Save Data is stored in CSV format, and storage is done automatically
      after any command edits the inventory list.
- **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=yakultbottle&breakdown=true)
- **Project Management**:
  - Helped maintain some issues, opening
      [#73](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/73),
      [#74](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/74),
      [#101](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/101)
- **Documentation**:
  - User Guide:
    - Added documentation for `edit` feature [#126](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/126)
  - Developer Guide:
    - Added implementation details of `Item` and `ItemMap`, including class diagrams for both.
      Example diagram for ItemMap below
      ![ItemMap-ClassDiagram](../diagrams/ItemMap-ClassDiagram.png)
- **Community**:
  - PRs reviewed(with non-trivial comments): 
  [#102](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/102), 
  [#106](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/106)
