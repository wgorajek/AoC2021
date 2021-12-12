package com.wgorajek;

import java.util.ArrayList;
import java.util.List;

public class Day03 extends Solution {
    @Override
    public Object getPart1Solution() {
        final List<String> commandList = getInputLines();
        Integer total = 0;
        Integer total3 = 0;
        Integer[] array = new Integer[12];
        for (var I = 0; I < commandList.get(0).length(); I++) {
            array[I] = 0;
            for (var line : commandList) {
                    if (line.toCharArray()[I] == '0') {
                        array[I] -= 1;
                    }
                    else if (line.toCharArray()[I] == '1') {
                        array[I] += 1;
                    }
            }
            if (array[I] > 0) {
                total = total*2 +1;
                total3 = total3*2;
            } else {
                total = total*2;
                total3 = total3*2 + 1;
            }
        }

        return total*total3;
    }

    @Override
    public Object getPart2Solution() {
        final List<String> commandList = getInputLines();

        List<String> myList = new ArrayList<String>();

        myList.addAll(commandList);
        Integer I = 0;
        while (myList.size() > 1) {
            myList = getRatingList(myList, I, 1);
            I++;
        }

        Integer total = 0;
        for (char c : myList.get(0).toCharArray()) {
            if (c == '0') {
                total = total * 2;
            } else {
                total = total * 2 + 1;
            }
        }

        myList.addAll(commandList);
        I = 0;
        while (myList.size() > 1) {
            myList = getRatingList(myList, I, -1);
            I++;
        }

        Integer total2 = 0;
        for (char c : myList.get(0).toCharArray()) {
            if (c == '0') {
                total2 = total2 * 2;
            } else {
                total2 = total2 * 2 + 1;
            }
        }

        return total*total2;
    }

    private List<String> getRatingList(List<String> input, Integer position, Integer type){
        final List<String> newList =new ArrayList<String>();


        Integer total = 0;
        for (var line : input) {
            if (line.toCharArray()[position] == '0') {
                total -= 1;
            }
            else if (line.toCharArray()[position] == '1') {
                total += 1;
            }
        }
        char bitSign;
        if ((total >= 0 && type ==1) || (total < 0 && type != 1)) {
            bitSign = '1';
        } else {
            bitSign = '0';
        }
        for (var line : input) {
            if (bitSign == line.toCharArray()[position]) {
                newList.add(line);
            }
        }
        return newList;
    }

}

