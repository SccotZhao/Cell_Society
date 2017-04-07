# cellsociety

CompSci 308 Cell Society Project
People who worked on the project:  Alexander Zapata, Kyle Finke, Zhiyong Zhao
We started on January 27th and ended on February 14th
There was a total of approximately 60-80 man-hours put into this project.

Alex Zapata- Made everything in the CellPackage and refactored everything but the XML classes.
Kyle Finke- Made everything in the XMLpackage and made the grid. Also refactored everywhere.
Zhiyong Zhao- Made everything in the UserInterface package and Main in default package.

The only external resources was code provided us in the lab files for CompSci 308.

To start the project, go into source->default package->main.
The files that we made to test the project are in the data folder, and each simulation has its own folder within that
once the program is run, the system will open to this file-system, so just go in the folders and click one of the XML files.
There are no non-standard files.
So to use this program effectively, click the run button, choose the shape you want the grid to have (Sugarscape only works with rectangle by design) and then click choose simulation. After you choose a simulation, there will be different buttons to interact with the simulation- click those if you want. Close the program by closing the simulation, then closing the starting screen.

There is a bug where sometimes the program will not stop the animation of the previous simulation and this will effect the time-step speed. That is the main bug. Also, the user MUST choose a shape before the simulation because there is no set default really. The user should be able to write the states of the blocks within the program and submit. Zhiyong should discuss this in his analysis. The only major fixes that should be made in the future are really to help the user understand what to do and how to navigate the program and some error/exception-handling with such.