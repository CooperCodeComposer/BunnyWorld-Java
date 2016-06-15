# BunnyWorld-Java
Java program simulating bunny population

Final project for UCLA Extension Java 1 course 

Program simulates a population of male and female bunnies which reproduce every year. 

##Command Line Arguments
+ Program accepts 2 parameters as command line arguments:
    + Initial number of bunnies 
    + Number of years to run the simulation 

+ For example to run program from terminal enter: java Simulator 6 20 
    + (This would give initial colony of 6 bunnies running the sim for 20 years)

##Simulation Rules:
+ When bunnies are born they are assigned a random color from either black, blue, gray, white or brown
+ When a bunny is born it will always be the same color as the mother
+ When a bunny is born it is assigned a random name (retrieved from a txt list of names from a url)
+ Once a name has been used it is removed from the list 
+ Bunnies have a maximum life expectancy of 6 years
+ If there are male bunnies and female bunnies both with age >= 2,then for every female bunny with age >= 2, a new bunny is created.
+ If the total population reaches 100, 50% of the bunnies will chosen at random to die of starvation
+ Simulation ends when there are no more bunnies or it has reached year the user specified number of years to run the sim


# Author
Alistair Cooper

[twitter](https://www.twitter.com/swiftcomposer.com)

[SwiftCodeComposer.com](https://www.swiftcodecomposer.com)

