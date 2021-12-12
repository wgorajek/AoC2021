package com.wgorajek;

import java.util.ArrayList;
import java.util.List;

    public class Day01 extends Solution {
    @Override
    public Object getPart1Solution() {
        final List<Integer> ints = getInput();
        var J = 0;
        for(var I = 1; I < ints.size(); I++) {
                if (ints.get(I) > ints.get(I-1)){
                    J++;
                }
        }
        return J;
    }

    @Override
    public Object getPart2Solution() {
        final List<Integer> ints = getInput();
        var j = 0;
        for(var i = 3; i < ints.size(); i++) {
            if (ints.get(i-3) < ints.get(i)){
                j++;
            }
        }
        return j;

    }


    private List<Integer> getInput() {
        final List<String> input = getInputLines();
        final List<Integer> ints = new ArrayList<Integer>();
        for (String i : input) {
            ints.add(Integer.parseInt(i));
        }

        return ints;
    }
}
