package com.wgorajek;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18 extends Solution{
    @Override
    public Object getPart1Solution() {
        var homeworkList = getInput();

        var solution = homeworkList.get(0);
        solution.reduce();
        for (var i = 1; i < homeworkList.size(); i++) {
            solution.add(homeworkList.get(i).homework.toString());
            solution.reduce();
        }
        return solution.getMagnitude();
    }

    @Override
    public Object getPart2Solution() {
        var homeworkList = getInput();
        var maxValue = 0;

        for (var i = 0; i < homeworkList.size(); i++) {
            for (var j = 0; j < homeworkList.size(); j++) {
                if (i != j) {
                    var solution = new SnailfishHomeWork(homeworkList.get(i).homework.toString());
                    solution.add(homeworkList.get(j).homework.toString());
                    solution.reduce();
                    maxValue = Integer.max(solution.getMagnitude(), maxValue);
                }
            }
        }
        return maxValue;
    }

    private class SnailfishHomeWork {
        StringBuilder homework;
        public SnailfishHomeWork(String homework) {
            this.homework = new StringBuilder(homework);
        }

        public void reduce() {
            var checkExplode = true;
            var checkSplit = true;
            while (checkExplode || checkSplit) {
                if (checkExplode) {
                    checkExplode = explodeIfNecessary();
                    checkSplit = true;
                }
                else {
                    checkSplit = splitIfNecessary();
                    if (checkSplit) {
                        checkExplode = true;
                    }
                }
            }

        }

        private Boolean explodeIfNecessary() {
            var position = 0;
            var nestedDepth = 0;
            while (position < homework.length()) {
                if(homework.charAt(position) == '[') {
                    nestedDepth++;
                    if (nestedDepth == 5) {
                        explode(position);
                        return true;
                    }
                }
                else if(homework.charAt(position) == ']') {
                    nestedDepth--;
                }
                position++;
            }
            return false;
        }

        private void explode(int position) {
            Pattern pattern = Pattern.compile("\\[(\\d+),(\\d+).*");

            Matcher m = pattern.matcher(homework.substring(position));
            m.matches();
            var firstNumberStr = m.group(1);
            var secondNumberStr = m.group(2);
            var firstNumber = Integer.parseInt(firstNumberStr);
            var secondNumber = Integer.parseInt(secondNumberStr);

            var i = position + firstNumberStr.length() + secondNumberStr.length() + 3;
            while (!Character.isDigit(homework.charAt(i)) && i < homework.length() - 1) {
                i++;
            }
            if (i < homework.length() - 1) {
                var j = i + 1;
                if (Character.isDigit(homework.charAt(j))) {
                    j++;
                }
                Integer rightDigit = Integer.parseInt(homework.substring(i, j)) + secondNumber;
                homework.replace(i, j, rightDigit.toString());
            }

            homework.replace(position, position + 3 + secondNumberStr.length() + firstNumberStr.length(), "0");

            i = position - 1;
            while (!Character.isDigit(homework.charAt(i)) && i > 0) {
                i--;
            }
            if (i > 0) {
                var j = i;
                if (Character.isDigit(homework.charAt(i-1))) {
                    j = i-1;
                }
                Integer leftDigit = Integer.parseInt(homework.substring(j, i + 1)) + firstNumber;
                homework.replace(j, i + 1, leftDigit.toString());
            }
        }

        private Boolean splitIfNecessary() {
            Pattern pattern = Pattern.compile("(\\d{2})");

            Matcher m = pattern.matcher(homework);
            if(m.find()) {
                var splitedNumberStr = m.group(1);

                var start  = homework.indexOf(splitedNumberStr);

                var splitedNumber = Integer.parseInt(splitedNumberStr);
                var leftNumber = (int) Math.floor(splitedNumber/2.0);
                var rightNumber = (int) Math.ceil(splitedNumber/2.0);
                var str = new StringBuilder("");
                str.append("[").append(leftNumber).append(",").append(rightNumber).append("]");
                homework.replace(start, start + splitedNumberStr.length(), str.toString());
                return true;
            }
            return false;
        }

        public int getMagnitude() {
            var position = 0;
            var depth = 0;
            Boolean[] leftSide = {true, true, true, true, true};
            var value = 0;
            while (position < homework.length()) {
                var str = homework.substring(position, position + 1);
                if (str.equals("[")) {
                    depth++;
                }
                else if (str.equals("]")) {
                    leftSide[depth] = true;
                    depth--;
                }
                else if (str.equals(",")) {
                    leftSide[depth] = false;
                }
                else {
                    var newValue = Integer.parseInt(str);
                    for (var i = 1; i <= depth; i++) {
                        if (leftSide[i]) {
                            newValue *= 3;
                        }
                        else {
                            newValue *= 2;
                        }
                    }
                    value += newValue;
                }
                position++;

            }
            return value;
        }

        public void add(String newNumber) {
            var str = new StringBuilder("[");
            str.append(homework.toString()).append(",").append(newNumber).append("]");
            homework = str;
        }
    }

    private List<SnailfishHomeWork> getInput() {
        var input = getInputLines();
        var list = new ArrayList<SnailfishHomeWork>();
        for (var line : input) {
            list.add(new SnailfishHomeWork(line));
        }
        return list;
    }
}
