package com.sudoku.deductionrules;

public class RuleFactory {

    public DeductionRule createRule(String ruleType) {
        switch (ruleType) {
            case "DR1":
                return new DR1();
            case "DR2":
                return new DR2();
            case "DR3":
                return new DR3();
            default:
                throw new IllegalArgumentException("Unknown rule type: " + ruleType);
        }
    }
}

