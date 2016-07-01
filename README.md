
- The solution
To solve this I created a Stock Manager, that provides methods to handle both new orders and new stocks.

When a new *Order* is created, it will try to be fulfilled by existing stocks. These stocks are getted ordered by their
creation date.

When a new *Stock* is created, it will try to be fulfill as many order as possible. Orders to be fulfilled are getted ordered by their
creation date.

When there is a movement of items from Stock to Order, a StockOrderMovement is created to keep a trace of
which stocks fulfilled which orders and vice-versa.

When an *Order* is fulfilled, an email is sent to the User that created it.
When listing Orders, completed percentage is shown.

TODO
1. Change field names in views to be more human readable.
1. Move logic to get next order and next stock to a higher class, allowing it to be easily changed.
1. Make the @Transient annotation tag work on completedPercentage field on Order.
1. Add indexes to creationDate both for ORder and StockMovement
1. Usar synchronized nos handle do StockManager, para garantir que mesmo com várias Threads, este código funcionaria.



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

