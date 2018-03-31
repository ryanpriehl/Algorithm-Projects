# Algorithm-Projects
Assignments completed for Data Structures and Algorithms class. The original problem formulations are given in ```assignments.pdf```, so only the solution are explained below.

### [1. Pond Scum](#pondscum) 
Because the height of a pond is determined only by the heights of directly adjacent ponds, the problem can be set up as a system of linear equations based on these heights. In each linear equation, the coefficients of the variables are: 4 for the variable we are solving for (because it is the average of the 4 adjacent ponds), -1 for adjacent ponds of varying heights (because water will flow from this pond to those), 0 for non-adjacent ponds (since they don't affect this pond's height), and the solution is the sum of the heights of the adjacent, static ponds (because water will flow from those into this pond based on their heights). For example, the pond layout 
```
744 521 792 728 69 
81  !A  !B  !C  894 
923 !D  !E  455 481 
691 704 !F  304 228 
561 80  246 807 316
```
corresponds to the matrix
```
 4  -1   0  -1   0   0  602 
-1   4  -1   0  -1   0  792 
 0  -1   4   0   0   0  2077 
-1   0   0   4  -1   0  1627 
 0  -1   0  -1   4  -1  455 
 0   0   0   0  -1   4  1254 
```
By solving this matrix for the reduced row echelon form (using row reduction), the height of each pond is given in the final column of its corresponding row
```
1 0 0 0 0 0 177603/377 
0 1 0 0 0 0 234167/377 
0 0 1 0 0 0 254299/377 
0 0 0 1 0 0 249291/377 
0 0 0 0 1 0 206182/377 
0 0 0 0 0 1 169735/377 
```
So the heights ```177603/377, 234167/377, 254299/377, 249291/377, 206182/377, 169735/377``` are the final heights for ponds A-F respectively.

### 2. Party
A simple iterative approach: loop through the adjacency matrix removing people who don't meet the requirements and updating the matrix as they are removed. Continue until an iteration through the matrix results in no changes, at which point the people remaining are the solution set.

### 3. Hotlines
Constructs an adjacency matrix for all connections between vertices, then runs BFS on it to find all paths between 0 and N. (Warning: very slow and memory-heavy, could be improved by storing the connections in a list instead of an adjacency matrix.)

### 4. Mining
A network is set up so bricks with a nonnegative net value are connected to a source, bricks with a negative net value are connected to the sink, and bricks are connected to prerequisite bricks by an edge with infinite capacity. By finding the maximum flow through the network using the [Ford-Fulkerson algorithm](https://en.wikipedia.org/wiki/Ford%E2%80%93Fulkerson_algorithm), and then taking the set of projects on the source-side of a minimum cut the set of optimal bricks to remove is found. 

### 5. Racing Gems
A tree is constructed bades on the locations of the gems, where any gem that can possibly be reached from a given gem is a child of that gem. By removing the cycles from this tree, it can then be dynamically analyzed to determine the best path to take from any gem in the location, including the best path overall.

### 6. Mixtures
Similar solution to [1. Pond Scum](#pondscum): the available mixture ratios and the desired final ratio form the columns of a matrix, and reducing this matrix to row echelon form gives the ratio of mixtures to use. If the matrix can't be reduced to row echelon form with positive, integer solutions then there is no solution. (Note: this method only works for square matrices and some non-square matrices where the number of rows is greater than the number of columns)

### 7. Evolutionary Trees
A tree is created with the leaves filled in and internal nodes left empty. The internal nodes are filled in one letter at a time by running [Fitch's Algorithm](http://www.cs.ubc.ca/labs/beta/Courses/CPSC536A-01/Class10/class10-notes.html): internal nodes are solved recursively by taking the union or intersection of possible (or actual) values for the child nodes. These sets of values represent the letter choices that will maximize parsimony in the tree overall. 
