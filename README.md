# Sorting Visualizer Project

This programs visualizes several sorting algorithms on a canvas.

## Built With:

- Java, JavaFX
- Eclipse

### Build and Run

```
git clone https://github.com/MattBunch/Sorting-Visualization.git
cd Sorting-Visualization
gradle run
```

## Devlog:

### 25/11/2020

Core components built, bubblesort implemented.

### 6/01/2021

Added some other sorts with varying levels of functionality. 
Need to make parts of some sorts like Insertion Sort and Selection Sort, need to convert Heapsort to main class. 

### 7/01/2011

Need to fix the timeline from not starting again when starting the program over again.
When t.stop() is called, the program will show a blank screen on the main scene instead of the graph animation
If t.stop() is commented out, the program will double in speed.
Insertion Sort and Selection Sort partially implemented but there is still one for loop or while loop that is not animated in either.
Heap Sort needs to be put into main class.

# Extensions for future versions:

- Insertion sort
- Heap sort
- Having the entire program in one stage where the visualization and menu options are in the same screen
- more options eg. array size, speed, etc.