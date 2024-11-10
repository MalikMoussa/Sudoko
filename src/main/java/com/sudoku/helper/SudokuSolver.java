package com.sudoku.helper;

import com.sudoku.deductionrules.*;
import com.sudoku.observers.Observer;
import com.sudoku.utils.UserInputHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class SudokuSolver {
    private SudokuGrid grid;
    private List<DeductionRule> rules;
    int currentRule = 0;
    private Observer observer;
    private RuleFactory ruleFactory;

    public SudokuSolver() {
        grid = SudokuGrid.getInstance();
        ruleFactory = new RuleFactory();
        rules = new ArrayList<>();
        rules.add(ruleFactory.createRule("DR1"));
        rules.add(ruleFactory.createRule("DR2"));
        rules.add(ruleFactory.createRule("DR3"));
    }

    public void solve() {
        while (currentRule < rules.size()) {
            DeductionRule rule = rules.get(currentRule);
            if (applyRuleAndCheckCompletion(rule)) {
                return;
            }
            if(currentRule == rules.size())
                break;
            promptUserToContinue();
        }

    }

    private boolean applyRuleAndCheckCompletion(DeductionRule rule) {
        rule.applyRule(grid);
        if (grid.isSolved()) {
            String difficulty = getDifficultyByRule(currentRule);
            observer = rule;
            notifyObservers(grid, difficulty);
            return true;
        }
        if(currentRule == 2 && !grid.isSolved()){
            this.currentRule++;
            return false;
        }

        System.out.println("Rule DR" + (currentRule + 1) + " did apply successfully but did not solved the Sudoku !");
        grid.display("Grid after applying DR" + (currentRule + 1) + " : ");
        return false;
    }

    private void promptUserToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Do you want to complete it manually (M) and start again the current rule \"" + rules.get(currentRule).getClass().getSimpleName() + "\" or apply the next rule \"" + rules.get(currentRule + 1).getClass().getSimpleName() + "\" (N) ?");
        String response = scanner.nextLine().trim().toUpperCase();
        if (response.equals("M")) {
            UserInputHandler.promptUserForInput(grid);
        } else if (response.equals("N")) {
            grid.resetGrid();
            grid.display("Grid Restarted : ");
            this.currentRule++;
        } else {
            System.out.println("Invalid input. Please enter 'M' for manual or 'N' for the next rule.");
            promptUserToContinue();
        }
    }
    private void notifyObservers(SudokuGrid grid, String difficulty) {
        observer.update(grid, difficulty);
    }

    private String getDifficultyByRule(int ruleIndex) {
        switch (ruleIndex) {
            case 0:
                return "Easy";
            case 1:
                return "Medium";
            case 2:
                return "Hard";
            default:
                return "Unknown";
        }
    }
}