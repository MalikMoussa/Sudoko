# Sudoku Solver

## Author
   **-MOUSSA BOUDJEMAA Merwan Malik**

This program solves Sudoku puzzles using a series of deduction rules: DR1, DR2, and DR3. Each rule applies progressively more complex solving techniques to try and resolve the puzzle.

## How to Use

1. **Launch the Program**:
   1. ***Default Input File**:
   - By default, the program will look for a file named `sudoku_sample.txt` in the same directory as the JAR file. This file will serve as the input for the Sudoku grid.
   2. **Custom Input File**:
      - You can specify a custom file path as a command-line argument. For example:
        ```bash
        java -jar Sudoku(17).jar path/to/your/sudoku_grid.txt
        ```
2. **Program Flow**:
    - **Initial Attempt**: The program first tries to solve the Sudoku using **DR1**.
    - **Solved**: If **DR1** successfully solves the puzzle, a "Grid Solved" message appears, and the solution is displayed.
    - **Not Solved**: If **DR1** cannot fully solve the puzzle, you have two options:

3. **User Input Options**:
    - **Option M (Manual Input)**: You can manually assist **DR1** by entering a correct number into an empty cell:
        - If you choose **M**, you’ll be prompted to enter a number and a cell location.
        - **DR1** will reapply itself with the updated grid, now containing your manual input.
        - If the number you entered is incorrect, the solving process will stop, and you must restart from scratch.
    - **Option N (Next Rule)**: You can choose to progress to the next rule:
        - If you choose **N**, **DR2** will apply its more advanced techniques to try and solve the puzzle.
        - If **DR2** still cannot solve the puzzle, you will again have the **M** or **N** options.
        - **DR3** (backtracking) is the final rule, and if it cannot solve the puzzle, the program will conclude that the Sudoku is unsolvable.

4. **Difficulty Messages**:
    - When the grid is solved, the program will print a message indicating the difficulty level based on which rule solved the puzzle:
        - **DR1**: "Easy" level
        - **DR2**: "Medium" level
        - **DR3**: "Hard" level
        - If none of the rules can solve the grid, a message will indicate that the grid is **impossible to solve**.

### Input Format
- The input should be a plain text file where each line represents a row of the Sudoku puzzle.
- **Format Requirements**:
   - Each number is separated by a comma.
   - Use `0` to represent an empty cell.
   - There should be no spaces in the file.

- **Example Input**:
  ```plaintext
  3,8,0,1,0,0,5,9,0
  2,3,9,8,0,5,1,0,4
  0,0,1,0,3,9,8,2,5
  8,5,4,9,1,0,6,0,3
  0,0,0,0,0,0,0,0,0
  1,0,6,0,5,8,9,4,7
  0,6,3,0,0,0,4,1,0
  9,0,2,3,0,1,7,5,8
  0,1,8,0,0,4,0,6,9

