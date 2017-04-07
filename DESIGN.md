Computer Science 308: Cell Society Design Plan
=====================================
Team 20: Alex Zapata, Zhiyong Zhao, Kyle Finke
	
# Introduction
	
   The problem our team is trying to solve is how best to implement multiple different grid based simulations that a   
user can interact with. The user will be able choose from a list of different simulations to determine which one will  
be displayed. The primary design goals of this project are to allow for the most flexibility that makes it easy to add 
new simulations to the program and increase the control afforded to the user of the program. While the ability to add  
new simulations will be open, the ability to alter existing simulations will be closed. Additionally, adding new 
functionalities for the user will be open as well but the updating of the grid layout that is displayed to the user  
will be closed to alteration.
	
#Overview
	  
   When our program is first started up, the first class that is called is perhaps the UserControl class. This class   
will be the class that sets up and handles the user interface of the program. Further, this class will allow the change  
of the program to different conditions, including the simulation type. Once all of these parameters are filled, the XMLReader 
will get the type of the simulation and read through the subsequent XML file, further passing the information of the initial  
conditions to the Grid class so that the scene can be created and the initial cells placed. The Grid class itself will then  
interact with the cells, which will have dedicated locations, states, and other internal values like the amount of neighbors  
they are near. The cell class is a stash for all of these characteristics that each of these cell-types could possibly hold.  
Each simulation will have its own type of cell with different internal states and methods for finding things like the amount  
of neighbors near them. Lastly, the cell class will interact with the CellHandler. The purpose of the CellHandler is to  
handle any of the mathematics and other semantic methods that will determine the state of each cell. The following is a  
map of how each class will interact. Further, this map shows all of the prospective methods of each class.
[ImageLink](https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team20/blob/master/resources/CellSociety.jpg)
	
#User Interface

   To start, the user interface will look similar to a splash screen. It will display a list of possible commands that the user  
may enter. The first thing they are prompted to enter when they start the program is which simulation file they would like to  
see. Then, they are told about a set of keys that they may press during the simulation, such as speed up, slow down, pause, start,  
end, and change simulation. If they change the simulation, then they will be taken back to the splash screen to enter the new  
simulation they would like to see. Everything is controlled using solely the keyboard keys, except the file name which is chosen  
using the console to enter a filename. If the user enters a filename that does not exist or contains improper inputs, they will  
be notified with the appropriate warning message and asked to enter a new, proper file name.
[Here is the picture of the screens used for user interface:](https://coursework.cs.duke.edu/CompSci308_2017Spring/cellsociety_team20/blob/master/resources/User_Interface_Image.png)
	
#Design Details
	   
   For our Cell Society program, we will have a Main class that will set up necessary parameters for the simulation and start  
the program. From this point, we intend to have five different fundamental classes (some being abstract, and some being regular)  
to handle user input and run the simulation correctly. 
	
## *UserControl
   
   One of these classes, perhaps the first class used in the program, is the UserControl class. This class will ultimately control  
the user interface, and more importantly allow the user to interact with the program. As described in the project specifications,  
the user will be able to start, pause, speed up, and slow down the simulation- as well as be able to choose the simulation that  
will be run. Once the program is initially run, or at the volition of the user, there will be an option to choose the simulation.  
Whenever the simulation is chosen, from a series of available XML files, there will be a call to a class called XMLReader through  
another class called the Grid class. 
	
## *XMLReader
	  
   This class has a plain and simple use in our Cell Society program. The XMLReader class reads in the appropriate XML file for  
the simulation chosen by the user. This file will contain all the necessary information to create the simulation including the  
simulation name, simulation author, settings for global configuration parameters, dimensions of the grid, and the initial configuration 
states of the cells. The XMLreader class will save these values as instance variables that can be accessed through getter methods. 
One possible way we could extend the XMLReader would be to add a method to it or add another class that could write its own initial 
conditions into the XMLReader that has random initial conditions for a certain type of simulation. This would be something that would 
ultimately be implemented after we know that the simulations run correctly so that we can be reasonably certain that those random 
conditions are being treated correctly.
	
## *Grid
	   
   The Grid class gets the information from the XMLReader class about the simulation type, grid size, and cells’ states and will use 
this information to construct the layout of the simulation that will be displayed to the user. The Grid class will construct the layout 
by creating a Cell object related to the type of simulation and store all of the cells in a 2D arrayList. Once the layout is constructed 
the Grid class will handle the updating of the Cells through the use of the CellHandler class, which is specific to each type of 
simulation. 
	
## *Cell
	   
   The Cell class is an abstract class that will hold the informations of cells that have been created. With this information, the grid 
will determine the state of the scene and what grid spaces are active. Each type of simulation will have its own type of cell that 
will have its respective characteristics.
	
## *CellHandler
	  
   The CellHandler class monitors the state of each Cell in the Grid and updates them as necessary based on the Cell’s current state, the 
states of its neighbors,  the possible odds of a change occurring, and whether it is an edge Cell.
	
## Use Cases
	
*Apply the rules to a middle cell: to set the next state of a middle Cell using the Game of Life rules, a CellHandler object call the 
changeState method on a Cell. The CellHandler method calls the  getNeighbors method which returns how many neighbors the current Cell has. 
Based on the number returned, the CellHandler will call the setState method on the Cell to change the Cell to dead or alive. 
Once the Cell is updated, the CellHandler will return control to the Grid class.
	
*Apply the rules to an edge cell: to set the next state of an edge Cell using the Game of Life rules, a CellHandler object call the 
changeState method on a Cell. The CellHandler method calls the  getNeighbors method which returns how many neighbors the current Cell has. 
The getNeighbor method does not check for neighbors on the side of the Cell that is the edge to avoid indexOutOfBound exceptions. To do 
this, the Cell object has instance variables for the size of the Grid, and if the Cell location is equal to an edge of the Grid, the 
getNeighbors method will know not to look for neighbors along that edge of the Cell. Based on the number returned, the CellHandler will 
call the setState method on the Cell to change the Cell to dead or alive. Once the Cell is updated, the CellHandler will return control 
to the Grid class. 
	
*Move to the next generation: The Grid class handles all the Cells that will be displayed to the screen in a 2D arrayList. First, the 
Grid class updates the Cells using the CellHandler’s changeState method. Then, the Grid class loops through all the updated Cells in the 
updateView method to replace the old images of the Cells with the updated Cell state images on the scene displayed to the user.
	
	
*Set a simulation parameter: to set a probCatch parameter for the Game of Life, the CellHandler class can use the getPercentage method 
to get the information about the cell, which will be used to compare with the probCatch. If the the return value of the getPercentage is 
larger than the proBCatch and the cell has tree, then in the next step, it will change the state to the Burning state through setState 
method in the Cell class. And if if there is no tree or the tree is Burning, then in the next step, there is no tree in the next step.  
If the return value of the getPercentage is less than the proBCatch, then the burning state will change to be no tree through the 
setState method in the Cell class.
	
*Switch simulations: The handleKeyInput method in the Main class will check during each update of the Grid if the P key is pressed. If P 
is pressed, the simulation is paused and control is passed to the UserControl class which allows the user to switch the simulation by 
entering the name of the XML file for that simulation which is handled in the chooseSimulation method of the UserControl class which 
passes the file name to the XMLReader class’s read method that reads the XML file to create the new simulation through the Grid class as 
explained above under the Grid class header.
	
#Design Considerations
	
*For the UserControl class, the question is how much flexibility that the user will get. If the user can control a lot of input parameters, 
then we need a XMLWriter class that will generate the required simulation grid. But the con is that this will bring a lot of burdens to the 
developers. If we limit the flexibility, the user will not get perfect experience from the simulation.
	
*For the Cell class, we want to make it to an abstract class. In this way, we can add a lot of different kinds of cells for the simulations 
without changing the signature of the methods in the Cell class.
	
*In the Grid class, we want to add a private XMLReader object, which can be used to get information of the XML file. Because the Grid class 
is responsible to setup the grid of the simulation and we need the information of the grid from the XML file, a private object from XMLReader 
is a best choice. The Grid class will store cells in a 2D arrayList in case that the user wants to resize the cell and the size of the arrayList 
will change. In this situation, an array is not efficient.
	
*To switch between different simulations, a menu bar would be a best possible choice to control the switch. The pro is that it is user-friendly 
for users but it bring a lot of burdens to the developers. We need add more control codes and setups to finish this control part.
	
## Team Responsibilities 
	
*Alex: Working on the Cell abstract class as well as the cells from each of the different types of simulations. Also will be working on the 
CellHandler abstract class, and all of the mathematical manipulations to determine the cell states.
	
*Kyle: Working on the XMLReader and the UserControl class. Will be doing research on the XML format and how to write files programmatically. 
	
*Zhiyong: Working on the Grid class and the Main class’s layout/functionality with step, setupGame,...