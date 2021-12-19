package com.wgorajek;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Day07 extends Solution{

    @Override
    public Object getPart1Solution() {
        var crabList = getInput();
        var max = 0;
        var result = 0;
        for (var i: crabList) {
            max = Integer.max(max, i);
            result += i;
        }

        for (var tmp = 0; tmp <= max;tmp++) {
            var tmpResult = 0;
            for (var i : crabList) {
                tmpResult += abs(i - tmp);
            }
            if (tmpResult < result) {
                result = tmpResult;
            }
        }

        return result;
    }



    @Override
    public Object getPart2Solution() {
        var crabList = getInput();
        var max = 0;
        var result = 0;
        for (var i: crabList) {
            max = Integer.max(max, i);
            var diffrence = abs(i);
            result += (diffrence * (diffrence + 1))/2;
        }

        for (var tmp = 0; tmp <= max;tmp++) {
            var tmpResult = 0;
            for (var i : crabList) {
                var diffrence = abs(i - tmp);
                tmpResult += (diffrence * (diffrence + 1))/2;
            }
            if (tmpResult < result) {
                result = tmpResult;
            }
        }
        return result;
    }




    private List<Integer> getInput() {
        final String input = getInputString();
        List<Integer> crabList = new ArrayList<Integer>();
        for (var str: input.split(",")) {
            crabList.add(Integer.parseInt(str));
        }
        return crabList;
    }

}
