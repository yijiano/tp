# Pharmacy Inventory & Logistics Ledger (PILL) User Guide

**Version 2.0**

1. [Introduction](#introduction)
2. [Features](#features)
   - **General Commands**
      - [Viewing Help: `help`](#viewing-help-help)
      - [Listing All Items: `list`](#listing-all-items-list)
      - [Exiting the Program: `exit`](#exiting-the-program-exit)
   - **Item Management**
      - [Adding New Item: `add`](#adding-new-item-add)
      - [Deleting Existing Item: `delete`](#deleting-existing-item-delete)
      - [Editing Existing Item: `edit`](#editing-existing-item-edit)
      - [Finding Items: `find`](#finding-items-find)
      - [Priority Removal of Items: `use`](#priority-removal-of-items-use)
   - **Expiry Management**
      - [List Expiring Items: `expiring`](#list-expiring-items-expiring)
      - [List Expired Items: `expired`](#list-expired-items-expired)
   - **Stock Management**
      - [Query Existing Stock: `stock-check`](#query-existing-stock-stock-check)
      - [Restock Specific Item: `restock`](#restock-specific-item-restock)
      - [Restock All Items Below Threshold: `restock-all`](#restock-all-items-below-threshold-restock-all)
   - **Price and Cost Management**
      - [Set Item Cost: `cost`](#set-item-cost-cost)
      - [Set Item Price: `price`](#set-item-price-price)
   - **Visualization**
      - [Visualize item prices: `visualize-price`](#visualize-item-prices-visualize-price)
      - [Visualize item costs: `visualize-cost`](#visualize-item-costs-visualize-cost)
      - [Visualize item stock: `visualize-stock`](#visualize-item-stock-visualize-stock)
      - [Visualize item costs and prices: `visualize-cost-price`](#visualize-item-costs-and-prices-visualize-cost-price)
   - **Order and Transaction Management**
      - [Order Items: `order`](#order-items-order)
      - [View All Orders: `view-orders`](#view-all-orders-view-orders)
      - [Fulfill Order: `fulfill-order`](#fulfill-order-fulfill-order)
      - [Viewing Transactions: `transactions`](#transactions)
      - [Viewing Transaction History: `transaction-history`](#transaction-history)
3. [Important Note](#important-note)
   - [Case Sensitivity](#case-sensitivity)


## Introduction

Pharmacy Inventory & Logistics Ledger (PILL) is a Command Line Interface (CLI) tool designed to assist in managing and tracking medicinal inventory.



## Features

### Viewing Help: `help`

Displays a list of all available commands and their descriptions.

**Format**: `help (COMMAND_NAME) (-v)`

- Optional: COMMAND_NAME specifies the command to display help for.
- Optional: `-v` flag to display verbose help for the specified command.

**Sample Output**:
`> help`

```
Available commands:

Item Management:
  add                   - Adds a new item to the list
  delete                - Deletes an item from the list
  edit                  - Edits an item in the list
  find                  - Finds all items with the same keyword
  expired               - Lists all items that have expired
  expiring              - Lists items expiring before a specified date
  list                  - Lists all items
  stock-check           - Lists all items that need to be restocked
  restock               - Restocks a specified item with an optional expiry date and quantity
  restock-all           - Restocks all items below a specified threshold
  use                   - Priority removal of items from the list, starting with earliest expiry date

Visualization:
  visualize-price       - Visualizes item prices as a bar chart
  visualize-cost        - Visualizes item costs as a bar chart
  visualize-stock       - Visualizes item stocks as a bar chart
  visualize-cost-price  - Visualizes item costs and prices side-by-side as a bar chart

Price and Cost Management:
  cost                  - Sets the cost for a specified item
  price                 - Sets the selling price for a specified item

Order Management:
  order                 - Creates a new purchase or dispense order
  fulfill-order         - Processes and completes a pending order
  view-orders           - Lists all orders

Transaction Management:
  transactions          - Views all transactions
  transaction-history   - Views transaction history in a given time period

Other Commands:
  help          - Shows this help message
  exit          - Exits the program

Type 'help <command>' for more information on a specific command.
Type 'help <command> -v' for verbose output with examples.
```



---

### Adding New Item: `add`

Adds a new item to the inventory, specifying its name and quantity.

**Format**: `add NAME (QUANTITY) (EXPIRY_DATE)`

- Optional: QUANTITY specifies the quantity of the item to add. Defaults to 1.
- Optional: EXPIRY_DATE specifies the expiry date of the item in `YYYY-MM-DD` format.

**Sample Output**:

`> add Aspirin 100 2024-05-24`

```
Added the following item to the inventory: 
Aspirin: 100 in stock, expiring: 2024-05-24
```



---

### Listing All Items: `list`

Displays a list of all items currently stored in the inventory, including their names and quantities.

**Format**: `list`

**Sample Output**:

`> list`

```
Listing all items:
1. candles: 900 in stock, cost: $110.0
2. can: 900 in stock
3. panadol: 999990 in stock, expiring: 2024-05-16
4. panadol: 1000 in stock
5. syringe: 100 in stock
6. cans: 10 in stock
7. Aspirin: 100 in stock, expiring: 2024-05-24
```



---

### Deleting Existing Item: `delete`

The `delete` command is used to remove an existing item entry from the inventory. The behavior of this command depends on whether the item has an associated expiry date.

**Format**: `delete NAME (EXPIRY_DATE)`

- `NAME`: The name of the item you wish to delete. 
- `EXPIRY_DATE`: An optional parameter in the `YYYY-MM-DD` format that must be provided if the item you want to delete has an expiry date.

**Command Behavior**:

- If an item does not have an expiry date, you can delete it using only the NAME. 
- If an item has an expiry date, you must specify the EXPIRY_DATE to delete the correct entry. 
- If you attempt to delete an item with an expiry date but do not provide the EXPIRY_DATE, the system will return an "Item not found" error.

**Sample Output**:

`> delete cans`

```
Deleted the following item from the inventory: 
cans: 10 in stock
```

`> delete pear 2010-12-12`

```
Deleted the following item from the inventory:
pear: 20 in stock, expiring: 2010-12-12
```

**Notes**:
- Always provide the `EXPIRY_DATE` when deleting items that have expiry dates to ensure the correct entry is removed. 
- Use the `list` command to view all items and their expiry dates before attempting to delete.

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

**Format**: `find KEYWORD`

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

`> cost Panadol 15`

```
Set cost of Panadol to $15.00.
```
---
### Set Item Price: `price`

Sets the selling price for a specified item, applied to all entries with the same name.

**Format**: `price ITEM_NAME AMOUNT`

**Sample Output**:

`> price Panadol 20`

```
Set price of Panadol to $20.00.
```
---
### Visualize item prices: `visualize-price`

The visualize-price command will display a chart showing the prices of all items in the inventory. Each bar represents the price of an item, and items are labeled with their names and expiry dates (if applicable).

**Format**: `visualize-price`

---
### Visualize item costs: `visualize-cost`

This command will display a chart showing the costs of all items in the inventory. Each bar represents the cost of an item, and items are labeled with their names and expiry dates (if applicable).

**Format**: `visualize-cost`

---
### Visualize item stock: `visualize-stock`

This command will display a chart showing the quantity of items in stock. Each bar represents the stock level of an item, and items are labeled with their names and expiry dates (if applicable).

**Format**: `visualize-stock`

---
### Visualize item costs and prices: `visualize-cost-price`

This command will display a chart comparing the costs and prices of all items. Each item will have two bars: one for cost and one for price, labeled with the item name and expiry date (if applicable).

**Format**: `visualize-cost-price`

---
### Restock Specific Item: `restock`

The restock command allows you to restock a specific item to a desired quantity if it is below the specified stock level
, displaying the restock cost. The behavior of the command differs based on whether or not an expiry date is provided.

**Format**: `restock ITEM_NAME (EXPIRY_DATE) QUANTITY`
- `ITEM_NAME`: The name of the item you wish to restock.
- `(EXPIRY_DATE)`: An optional parameter in the `YYYY-MM-DD` format. This specifies which item entry to restock if there are multiple entries with the same item name but different expiry dates. 
- `QUANTITY`: The desired new stock quantity.

**Command Behavior**:
- The `EXPIRY_DATE` is mandatory when restocking items that have expiry dates. You must specify the expiry date explicitly, even if only one entry with that expiry date exists.
- If an item **does not have an expiry date**, the command will restock that entry without needing an expiry date.
- If you attempt to restock an item with an expiry date but fail to provide the `EXPIRY_DATE`, the system will display an "Item not found" error.

**Sample Output**:

`> restock Panadol 2024-12-31 100`

```
Restocked Item: Panadol, Current Stock: 1, New Stock: 100, Total Restock Cost: $1485.00
```

---

### Restock All Items Below Threshold: `restock-all`

Restocks all items with a quantity below a specified threshold to that threshold, listing each item's restock cost.

**Format**: `restock-all THRESHOLD`

**Sample Output**:

`> restock-all 50`

```
Item: Ibuprofen, Current Stock: 10, New Stock: 50, Restock Cost: $40.00 
Total Restock Cost for all items below threshold 50: $40.00
```
---

### Priority Removal of Items `use`

Priority removal of items from the list, starting with the earliest expiry date.

**Format**: `use ITEM_NAME`

**Sample Output**:

`> use panadol 100`

```
Deleted the following item from the inventory: 
panadol: 90 in stock, expiring: 2023-05-17
Edited item: panadol: 999990 in stock, expiring: 2024-05-16
Partially used item with expiry date 2024-05-16 (reduced from 1000000 to 999990): 
panadol: 999990 in stock, expiring: 2024-05-16
```

---

### Order Items: `order`

Creates a new purchase or dispense order.

**Format**: `order ORDER_TYPE ITEM_COUNT` 
This is followed by `ITEM_COUNT` number of lines of `ITEM_NAME QUANTITY (EXPIRY_DATE)` 

- ORDER_TYPE: `purchase` or `dispense`

**Sample Output**:

```
> order purchase 2 
syringe 100 
cans 10
```

```
Order placed! Listing order details
UUID: cec43f38-5c63-40b6-8964-00f8b4225c17
Type: PURCHASE
Creation Time: 2024-11-08T00:06:30.735047100
Fulfillment Time: null
Status: PENDING
Notes: null
Items: 
1. syringe: 100 in stock
2. cans: 10 in stock
```

---
### View All Orders `view-orders`

Lists all orders.

**Format**: `view-orders`

**Sample Output**:

`> view-orders`

```
1. UUID: cec43f38-5c63-40b6-8964-00f8b4225c17
Type: PURCHASE
Creation Time: 2024-11-08T00:06:30.735047100
Fulfillment Time: null
Status: PENDING
Notes: null
Items: 
1. syringe: 100 in stock
2. cans: 10 in stock

2. UUID: b213353f-31d3-46db-a4e7-b2ee7542d18e
Type: DISPENSE
Creation Time: 2024-11-08T00:08:21.386002300
Fulfillment Time: null
Status: PENDING
Notes: null
Items: 
1. bottle: 10 in stock
```

---
### Fulfill Order: `fulfill-order`

**Format**: `fulfill-order ORDER_ID`

**Sample Output**:

`> fulfill-order 1`

```
Added the following item to the inventory: 
syringe: 100 in stock
Added the following item to the inventory: 
cans: 10 in stock
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

---

## Important Note

### Case Sensitivity

Our application is **case sensitive**. This means that item names must match exactly as they were entered. For example:

- `add Panadol` and `add PANADOL` will be treated as two different items.
- Similarly, commands such as `edit`, `delete` will only work if the case of the item name matches exactly as stored in the inventory.

Ensure you use the correct capitalization when interacting with items in the inventory.