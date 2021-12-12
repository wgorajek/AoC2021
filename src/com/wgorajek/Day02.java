package com.wgorajek;

import java.util.ArrayList;
import java.util.List;


public class Day02 extends Solution {
    @Override
    public Object getPart1Solution() {
        final List<SubmarineCommand> commandList = getInput();
        Integer depth = 0;
        Integer forward = 0;

        for (var command : commandList) {
            if (command.direction.equals("forward")) {
                forward += command.length;
            }
            else if (command.direction.equals("down")) {
                depth += command.length;
            }
            else if (command.direction.equals("up")) {
                depth -= command.length;
            }
        }
        return depth * forward;
    }

    @Override
    public Object getPart2Solution() {
        final List<SubmarineCommand> commandList = getInput();
        Integer depth = 0;
        Integer forward = 0;
        Integer aim = 0;

        for (var command : commandList) {
            if (command.direction.equals("forward")) {
                forward += command.length;
                depth += aim * command.length;
            }
            else if (command.direction.equals("down")) {
                aim += command.length;
            }
            else if (command.direction.equals("up")) {
                aim -= command.length;
            }
        }
        return depth * forward;
    }


    private static class SubmarineCommand {
        public final int length;
        public final String direction;

        public SubmarineCommand(String command) {
            final String[] parts = command.split("[ ]");
            this.direction = parts[0];
            this.length = Integer.parseInt(parts[1]);
        }
    }

    private List<SubmarineCommand> getInput() {
        final List<String> input = getInputLines();
        final List<SubmarineCommand> commandList = new ArrayList<SubmarineCommand>();
        for (String i : input) {
            commandList.add(new SubmarineCommand(i));
        }

        return commandList;
    }
}
