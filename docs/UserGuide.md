# Pharmacy Inventory & Logistics Ledger (PILL) User Guide

**Version 2.0**

1. [Introduction](#introduction)
2. [Features](#features)
   - [Viewing Help: `help`](#viewing-help-help)
   - [Adding New Item: `add`](#adding-new-item-add)
   - [Listing All Items: `list`](#listing-all-items-list)
   - [Deleting Existing Item: `delete`](#deleting-existing-item-delete)
   - [Editing Existing Item: `edit`](#editing-existing-item-edit)
   - [Finding Items: `find`](#finding-items-find)
   - [List Expiring Items: `expiring`](#list-expiring-items-expiring)
   - [List Expired Items: `expired`](#list-expired-items-expired)
   - [Query Existing Stock: `stock-check`](#query-existing-stock-stock-check)
   - [Set Item Cost: `cost`](#set-item-cost-cost)
   - [Set Item Price: `price`](#set-item-price-price)
   - [Restock Specific Item: `restock`](#restock-specific-item-restock)
   - [Restock All Items Below Threshold: `restockall`](#restock-all-items-below-threshold-restockall)
   - [Exiting the Program: `exit`](#exiting-the-program-exit)
   - [Saving the Data](#saving-the-data)
   - [Editing the Data File](#editing-the-data-file)

## Introduction

Pharmacy Inventory & Logistics Ledger (PILL) is a Command Line Interface (CLI) tool designed to assist in managing and tracking medicinal inventory.



## Features

### Viewing Help: `help`

Displays a list of all available commands and their descriptions.

**Format**: `help`

**Sample Output**:

`> help`

```
help
- Displays a list of all available commands and their descriptions
```

```
add n/NAME q/QUANTITY
- Allows the user to add a new item to the inventory
```

```
list
- Shows all the items that have been added to the inventory
```

```
delete INDEX 
- Removes a specified item index from the inventory
```

```
exit
- Terminates the application
```



---

### Adding New Item: `add`

Adds a new item to the inventory, specifying its name and quantity.

**Format**: `add n/NAME e/QUANTITY`

**Sample Output**:

`> add n/Panadol q/2`

```
Added the following item to the inventory:
1. Panadol: 2 in stock
```



---

### Listing All Items: `list`

Displays a list of all items currently stored in the inventory, including their names and quantities.

**Format**: `list`

**Sample Output**:

`> list`

```
1. Panadol: 2 in stock
2. Ibuprofen: 1 in stock 
```



---

### Deleting Existing Item: `delete`

Deletes an existing item entry in the inventory.

**Format**: `delete n/NAME`

- Delete the items with specified`NAME` .

**Example**:

- `delete n/NAME`  
  deletes the item as referenced in the `list`.

**Sample Output**:

`> delete n/Ibuprofen`

```
Deleted the following item from the inventory: 
	2. Ibuprofen: 1
```
---
### Editing Existing Item: 'edit'

Edits the quantity of an existing item entry in the inventory. 

**Format**: `edit NAME QUANTITY (EXPIRY_DATE)`

- Edits the items with specified `NAME` to have quantity `QUANTITY`.
- Edit can be called with an optional expiry date. If no expiry date is supplied,
it will attempt to edit the item entry with no expiry date. 

**Sample Output**

- `> edit Panadol 20`

```
Edited item: Panadol: 20 in stock
```

- `> edit Zyrtec 30 2025-02-03`

```
Edited item: Zyrtec: 30 in stock, expiring: 2025-02-03
```
---

### Finding Items: `find`

Finds all items in the inventory that match a specified name or partial name.

**Format**: `find ITEM_NAME`

**Sample Output**:

`> find Panadol`

```
Listing all items:

1. Panadol: 20 in stock, expiring: 2024-12-31
2. Big Panadol: 50 in stock
```
---
### List Expiring Items: `expiring`

Displays all items that will expire before a specified date.

**Format**: `expiring EXPIRY_DATE`

- The `EXPIRY_DATE` must be in `YYYY-MM-DD` format.

**Sample Output**:

`> expiring 2024-12-31`
```
Listing all items expiring before 2024-12-31:

1. Panadol: 99 in stock, expiring: 2024-12-12
```

---

### List Expired Items: `expired`

Lists all items that have expired as of the current date.

**Format**: `expired`

**Sample Output**:

`> expired`
```
Listing all items that have expired:

1. Ibuprofen: 10 in stock, expiring: 2023-11-01
2. Aspirin: 5 in stock, expiring: 2023-10-10
```
---
### Query Existing Stock: `stock-check`

Displays all items that have stock levels below a specified threshold.

**Format**: `stock-check THRESHOLD`

- `THRESHOLD` is the minimum stock level for listing items.

**Sample Output**:

`> stock-check 50`
```
Listing all items that need too be restocked (less than 50):

Ibuprofen: 10 in stock
Aspirin: 5 in stock
```

---
### Set Item Cost: `cost`

Sets the cost for a specified item, applied to all entries with the same name.

**Format**: `cost ITEM_NAME AMOUNT`

**Sample Output**:

`> cost Panadol 15.50`

```
Set cost of Panadol to $15.50.
```
---
### Set Item Price: `price`

Sets the selling price for a specified item, applied to all entries with the same name.

**Format**: `price ITEM_NAME AMOUNT`

**Sample Output**:

`> price Panadol 20.00`

```
Set price of Panadol to $20.00.
```
---
### Restock Specific Item: `restock`

Restocks a specific item to a new quantity if it is below the desired level, displaying the restock cost.

**Format**: `restock ITEM_NAME (EXPIRY_DATE) QUANTITY`

- Optional `EXPIRY_DATE` in `YYYY-MM-DD` format specifies a specific entry if multiple exist.

**Sample Output**:

`> restock Panadol 2024-12-31 100`

```
Restocked Item: Panadol, Current Stock: 1, New Stock: 100, Total Restock Cost: $1485.00
```

---

### Restock All Items Below Threshold: `restockall`

Restocks all items with a quantity below a specified threshold to that threshold, listing each item's restock cost.

**Format**: `restockall THRESHOLD`

**Sample Output**:

`> restockall 50`

```
Item: Ibuprofen, Current Stock: 10, New Stock: 50, Restock Cost: $40.00 
Total Restock Cost for all items below threshold 50: $40.00
```
---
### Exiting the Program: `exit`

Exits the program.

**Format**: `exit`

**Sample Output**:

`> exit`

`Exiting the inventory interface. Have a nice day! :)`



---

### Saving the Data

The system automatically saves any changes to the inventory to the hard disk after commands that modify the data (e.g., `add`, `delete`). The data is also saved upon using the `exit` command. There is no need to manually save changes.



---

### Editing the Data File

Inventory data is stored in a `.csv` file. Users can edit this file manually if necessary.