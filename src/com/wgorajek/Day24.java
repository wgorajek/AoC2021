package com.wgorajek;

import java.util.*;

public class Day24 extends Solution{
    @Override
    public Object getPart1Solution() {
//        var posibleStates = new HashMap<Long, Long>();
//        long[] digits = {1l,2l,3l,4l,5l,6l,7l,8l,9l};
//
//        var alu = getInput();
//
//        var initState = new AluState(-1, 0);
//
//        var states = new Stack<AluState>();
//        states.push(initState);
//
//        for (var i = 0; i < 14; i++) {
//            System.out.println(i);
//            while (!states.empty()) {
//                var state = states.pop();
//                for (var d: digits) {
//                    alu.load(state);
//                    alu.executeOneNumber(d);
//                    var newState = alu.save();
//                    if (!posibleStates.containsKey(newState.z)) {
//                        posibleStates.put(newState.z, newState.inputNumber);
//                    }
//                    else {
////                        posibleStates.put(newState.z, Long.max(newState.inputNumber, posibleStates.get(newState.z)));
//                        posibleStates.put(newState.z, Long.min(newState.inputNumber, posibleStates.get(newState.z)));
//                    }
//                }
//            }
//            if (i == 13) {
//                return posibleStates.get(0l);
//            }
//            else {
//                for (var z : posibleStates.keySet()) {
//                    states.push(new AluState(posibleStates.get(z), z));
//                }
//                posibleStates.clear();
//            }
//        }
        return getSolution(true);

    }

    @Override
    public Object getPart2Solution() {
        return getSolution(false);
    }


    public long getSolution(boolean largest) {
        var posibleStates = new HashMap<Long, Long>();
        long[] digits = {1l,2l,3l,4l,5l,6l,7l,8l,9l};

        var alu = getInput();

        var initState = new AluState(-1, 0);

        var states = new Stack<AluState>();
        states.push(initState);

        for (var i = 0; i < 14; i++) {
            while (!states.empty()) {
                var state = states.pop();
                for (var d: digits) {
                    alu.load(state);
                    alu.executeOneNumber(d);
                    var newState = alu.save();
                    if (!posibleStates.containsKey(newState.z)) {
                        posibleStates.put(newState.z, newState.inputNumber);
                    }
                    else {
                        if (largest) {
                        posibleStates.put(newState.z, Long.max(newState.inputNumber, posibleStates.get(newState.z)));
                        }
                        else {
                            posibleStates.put(newState.z, Long.min(newState.inputNumber, posibleStates.get(newState.z)));
                        }
                    }
                }
            }
            if (i == 13) {
                return posibleStates.get(0l);
            }
            else {
                for (var z : posibleStates.keySet()) {
                    states.push(new AluState(posibleStates.get(z), z));
                }
                posibleStates.clear();
            }
        }
        return 1;
    }

    private class Instruction{
        String operation;
        int variableIndex1;
        int variableIndex2;
        long value;

        public Instruction(String operation) {
            this.operation = operation;
            variableIndex2 = -1;
        }

        public long variableOrValue(long[] variables) {
            if (variableIndex2 != -1) {
                return variables[variableIndex2];
            }
            return value;
        }
    }

    private class AluState{
        long z = 0l;
        long inputNumber = 0l;
        public AluState(long inputNumber, long z) {
            this.inputNumber = inputNumber;
            this.z = z;
        }

        public boolean equals (Object obj) {
            return ((AluState)obj).z == z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(z);
        }
    }

    private class Alu {
        long[] variables = new long[4];
        List<Instruction> instructions = new ArrayList<Instruction>();
        List<List<Instruction>> instructionsSets = new ArrayList<List<Instruction>>();
        long inputNumber = 0l;

        public Alu() {
            for (var i = 0; i < variables.length; i++) {
                variables[i] = 0l;
            }
        }

        public void load(AluState aluState) {
            this.inputNumber = aluState.inputNumber;
            for (var i = 0; i < variables.length; i++) {
                variables[i] = 0;
            }
            variables[3] = aluState.z;
        }

        public AluState save() {
            return new AluState(inputNumber, variables[3]);
        }

        public void loadInstructionSets() {
            for (var i = 0; i< 14; i++) {
                instructionsSets.add(new ArrayList<Instruction>());
            }
            var j = -1;
            for (var i = 0; i < instructions.size(); i++) {
                if (instructions.get(i).operation.equals("inp")) {
                    j++;
                }
                instructionsSets.get(j).add(instructions.get(i));
            }
        }

        public void executeOneNumber(long inputDigit) {
            if (inputNumber == -1l) {
                inputNumber = inputDigit;
            }
            else {
                inputNumber = inputNumber * 10 + inputDigit;
            }
            var indexOfInstructionSet = Long.toString(inputNumber).length()-1;

            var instructionSet = instructionsSets.get(indexOfInstructionSet);
            for (var i: instructionSet) {
                if (i.operation.equals("inp")) {
                    variables[i.variableIndex1] = inputDigit;
                }
                else if (i.operation.equals("add")) {
                    variables[i.variableIndex1] = variables[i.variableIndex1] + i.variableOrValue(variables);
                }
                else if (i.operation.equals("mul")) {
                    variables[i.variableIndex1] = variables[i.variableIndex1] * i.variableOrValue(variables);
                }
                else if (i.operation.equals("div")) {
                    variables[i.variableIndex1] = (long)(variables[i.variableIndex1] / i.variableOrValue(variables));
                }
                else if (i.operation.equals("mod")) {
                    variables[i.variableIndex1] = (variables[i.variableIndex1] % i.variableOrValue(variables));
                }
                else if (i.operation.equals("eql")) {
                    if (variables[i.variableIndex1] == i.variableOrValue(variables)) {
                        variables[i.variableIndex1] = 1;
                    }
                    else {
                        variables[i.variableIndex1] = 0;
                    }
                }
            }
        }
    }


    private Alu getInput() {
        var input = getInputLines();
        var alu = new Alu();
        for (var line: input) {
           var instructionCode = line.substring(0, 3);
            Instruction instruction = new Instruction(instructionCode);

            var variable = line.substring(4,5);
            instruction.variableIndex1 = variableIndex(variable);
            if (!instructionCode.equals("inp") ) {
                var variable2 = line.substring(6);
                if (variable2.equals("w") || variable2.equals("x") || variable2.equals("y") || variable2.equals("z")) {
                    instruction.variableIndex2 = variableIndex(variable2);
                }
                else  {
                    instruction.value = Long.parseLong(variable2);
                }
            }
            alu.instructions.add(instruction);

        }

        alu.loadInstructionSets();
        return alu;
    }

    private int variableIndex(String variable) {
        switch(variable) {
            case "w" : return 0;
            case "x" : return 1;
            case "y" : return 2;
            case "z" : return 3;
        }
        return -1;
    }
}
