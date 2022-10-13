# CSCE331-Project-2

## Team Members

- Alex Warren
- Elijah Sanders
- Kevin Brown
- Sydney Beeler
- Isidora Wright

## Project Description

Point of sale system for Spin 'n Stone. Implemented using JDBC and Swing.

## Getting Started

assuming intellij:
- file> new>project from version control
- top right near build/run bar add configuration. Add a application type run/debug configuration and set the class to edu.tamu.spinnston.App
- right click the resources folder in the project directory view. mark directory as resource root.  
- open settings and go to the GUI Designer section. make sure generate java source code is selected
- should be good to go, click play button in top right corner

### Phase 2 demo
- check the stock of a few products (ingredients/drinks)
- create an order that contains those products
- record the total and checkout
- look at the order (should be the highest id) total should match
- using that order id run query 6 from queries.sql
- the product from this order should match and the quantity in stock should have updated

### notes to developers

We are adding code regions. If you are using vscode, you can collapse a region by hovering over the line number next to the //region start and clicking the arrow. This is useful for reducing the on-screen clutter when working on a specific part of the code.

You can also open the command palette (ctrl+shift+p) and type "fold" to see the fold commands. Fold/Unfold all regions are particularly useful commands.

https://web.mit.edu/6.005/www/sp14/psets/ps4/java-6-tutorial/components.html

https://docs.oracle.com/javase/tutorial/uiswing/layout/box.html
https://docs.oracle.com/javase/tutorial/uiswing/layout/border.html
https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html
https://docs.oracle.com/javase/tutorial/uiswing/layout/flow.html
https://docs.oracle.com/javase/tutorial/uiswing/layout/card.html