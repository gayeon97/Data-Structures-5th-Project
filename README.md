# Data-Structures-5th-Project
5th Java Project from Data Structures course

This project provide a tool for extraction of certain type of information about motor vehicle collisions in New York City. The
New York Police Department (NYPD) provides data regarding all motor vehicle collisions that occur on streets on NYC. This data can be downloaded from NYC Open Data website at
https://data.cityofnewyork.us/Public-Safety/NYPD-Motor-Vehicle-Collisions/h9gi-nx95.

The program prompts the user for the zip code, start date and end date. The program validates that the zip code is in a correct format. Valid zip codes have exactly 5 characters and all characters are digits. The program validates that the dates are in a correct format. The valid dates should be written as mm/dd/yyyy (the month and day can be either one or two digits long, the year has to be four digits long). The start date should be smaller than (earlier) than the end date. After the program displays the results, the user should be prompted for the zip code, start date and end date again. The program should terminate when the user enters ”quit” in place of the zip code.

The program is given the name of the input text file as its command line argument (the first and only argument used by this
program). Note: this implies the user should not be prompted for the name of the input file by the program itself.
If the filename is omitted from the command line, it is an error. The program should display an error message and terminate. The error message indicates what went wrong (i.e: ”Error: missing name of the input file”).
If the filename is given but the file does not exist or cannot be opened for reading by the program, for any reason, it is an error. The program displays an error message and terminate. The error message indicates what went wrong (i.e: ”Error: file collisions.csv does not exist.”).

The program does not hardcode the input filename in its own code; it is up to the user of the program to specify the name
of the input file. The program does not modify the name of the user-specified file (do not append anything to the name).
The program also does not read the input file more than once nor modify the input file.
