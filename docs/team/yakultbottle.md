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
- **New Feature**: Added Use command with priority removal for items
  - What it does: Uses items in inventory with priority for items with the soonest expiry date. 
  - Justification: When using items, the items that are the closest to expiry should be used first, 
      to allow for the rest of the inventory to last as long as possible. 
  - Highlights: Needed to implement a TreeSet of Items, by defining its compareTo method,
      such that the Items were sorted according to their expiry date, and could be
      extracted from the ItemMap easily. 
- **New Feature**: Added the ability to save data
  - What it does: Automatically saves added items into a human-readable file. 
  - Justification: This feature improves the product significantly because 
      a user might want to refer to the stock list of items even when the product
      is offline.
  - Highlights: Save Data is stored in CSV format, and storage is done automatically
      after any command edits the inventory list. The Save Data is able to take in commas
      `,` as well, by escaping the comma character when saving and unescaping it when loading. 
- **Code contributed**: [RepoSense link](https://nus-cs2113-ay2425s1.github.io/tp-dashboard/?search=yakultbottle&breakdown=true)
- **Project Management**:
  - Helped do consistent bug-testing during the whole project
      [#73](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/73),
      [#74](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/74),
      [#101](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/101),
      [#150](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/150),
      [#153](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/153),
      [#154](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/154),
      [#155](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/155),
      [#156](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/156),
      [#157](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/157),
      [#166](https://github.com/AY2425S1-CS2113-W14-4/tp/issues/166)
    

- **Documentation**:
  - User Guide:
    - Added documentation for `edit` feature [#126](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/126)
    - Updated documentation for `OrderCommand` [#240](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/240)
  - Developer Guide:
    - Added implementation details of `Item` and `ItemMap`, including class diagrams for both.
        Example diagram for ItemMap below
        <img src="../diagrams/ItemMap-ClassDiagram.png" alt="ItemMap Class Diagram" width="350"/>
    - Added implementation details of `Parser` and `Exceptions`
    - Helped polish sequence diagram for `AddItemCommand`, splitting up previous diagram created
        by Nivedit [#229](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/229)
    - Added Glossary details and minor nits [#232](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/232)
- **Community**:
  - PRs reviewed(with non-trivial comments): 
  [#102](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/102), 
  [#106](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/106),
  [#139](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/139),
  [#231](https://github.com/AY2425S1-CS2113-W14-4/tp/pull/231)
