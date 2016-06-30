This is a simple test, an order manager on Play Framework

Requirements:
playFramework 1.2.5
MySQL

Entities
Item
    name

StockMovement
    creationDate
    Item
    quantity

Order
    creationDate
    Item
    quantity
    User (who created the order)

User
    name
    email

Specification
The system should be able to provide the following features:
- CRUD and List for all entities
- when an order is created, it should try to satisfy it with the current stock.
- when a stock movement is created, the system should try to attribute it to an order that isn't complete.
- when an order is complete, send a notification by email to the user that created it
- trace the list of stock movements that were used to complete the order, and vice-versa
- show current completion of each order
- design/ux will not be valued
- for simplification purposes you can assume that stockmovements are only positive.