package com.wgorajek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day08 extends Solution{

    public List<Long> bingoNumbers;

    @Override
    public Object getPart1Solution() {
        var signals = getInput();
        var totalCount = 0;
        for (var signal: signals) {
            totalCount += signal.digitCount(2) + signal.digitCount(3) + signal.digitCount(4) + signal.digitCount(7);
        }

        return totalCount;
    }


    @Override
    public Object getPart2Solution() {
        var signals = getInput();
        var totalCount = 0;
        for (var signal: signals) {
            totalCount += signal.getDecodedSignal();
        }

        return totalCount;
    }

    private static class Signal {
        List<String> outputSignal;
        List<String> pattern;
        HashSet<String> digitsSet;
        HashMap<String, Integer> decodedDigits;
        HashMap<Integer, String> decodedDigitsReversed;


        public Signal(List<String> pattern, List<String> outputSignal) {
            this.outputSignal = outputSignal;
            this.pattern = pattern;
            decodedDigits = new HashMap<String, Integer>();
            decodedDigitsReversed = new HashMap<Integer, String>();
            digitsSet = getAllDigits();
        }

        private HashSet<String> getAllDigits() {
            var allDigits = new HashSet<String>();
            allDigits.addAll(pattern);
            allDigits.addAll(outputSignal);
            return allDigits;
        }

        public Integer digitCount(int legnth) {
            var count = 0;
            for (var digit: outputSignal) {
                if (digit.length() == legnth) {
                    count += 1;
                }

            }
            return count;
        }

        private boolean stringContainsChars(String str, char[] charArray) {
            var result = true;
            for (var c: charArray) {
                result &= str.contains(String.valueOf(c));
            }
            return result;
        }

        private void decodeDigits() {
            for (var digit: digitsSet) {
                if (digit.length() == 2) {
                    decodedDigits.put(digit, 1);
                    decodedDigitsReversed.put(1, digit);
                }
                if (digit.length() == 3) {
                    decodedDigits.put(digit, 7);
                    decodedDigitsReversed.put(7, digit);
                }
                if (digit.length() == 4) {
                    decodedDigits.put(digit, 4);
                    decodedDigitsReversed.put(4, digit);
                }
                if (digit.length() == 7) {
                    decodedDigits.put(digit, 8);
                    decodedDigitsReversed.put(8, digit);
                }
            }
            for (var digit: digitsSet) {
                if ((digit.length() == 6) && !stringContainsChars(digit, decodedDigitsReversed.get(1).toCharArray())) {
                    decodedDigits.put(digit, 6);
                    decodedDigitsReversed.put(6, digit);
                }
                if ((digit.length() == 6) && stringContainsChars(digit, decodedDigitsReversed.get(4).toCharArray())) {
                    decodedDigits.put(digit, 9);
                    decodedDigitsReversed.put(9, digit);
                }
                if ((digit.length() == 5) && stringContainsChars(digit, decodedDigitsReversed.get(1).toCharArray())) {
                    decodedDigits.put(digit, 3);
                    decodedDigitsReversed.put(3, digit);
                }
            }
            for (var digit: digitsSet) {
                if ((digit.length() == 6) && (!decodedDigits.containsKey(digit))) {
                    decodedDigits.put(digit, 0);
                    decodedDigitsReversed.put(0, digit);
                }
                if ((digit.length() == 5) && stringContainsChars(decodedDigitsReversed.get(6), digit.toCharArray())) {
                    decodedDigits.put(digit, 5);
                    decodedDigitsReversed.put(5, digit);
                }
            }
            for (var digit: digitsSet) {
                if (!decodedDigits.containsKey(digit)) {
                    decodedDigits.put(digit, 2);
                    decodedDigitsReversed.put(2, digit);
                }
            }
        }

        public Integer getDecodedSignal() {
            decodeDigits();
            var decodedSignal = "";
            for (var str: outputSignal) {
                decodedSignal += decodedDigits.get(str).toString();
            }
            return Integer.parseInt(decodedSignal);
        }
    }

    private String sortLetters(String word){
        for (var i = 0; i < word.length(); i++) {
            for (var j = 0; j < word.length(); j++) {
                if (i != j){
                    if (word.charAt(i) < word.charAt(j)) {
                        StringBuilder newWord = new StringBuilder(word);
                        var tmpChar = newWord.charAt(i);
                        newWord.setCharAt(i, newWord.charAt(j));
                        newWord.setCharAt(j, tmpChar);
                        word = newWord.toString();
                    }
                }
            }
        }
        return word;
    }

    private List<Signal> getInput() {
        final List<String> input = getInputLines();
        List<Signal> signals = new ArrayList<Signal>();
        for (var str : input) {
            List<String> patternList = new ArrayList<String>();
            List<String> outputSignalList = new ArrayList<String>();

            var pattern = str.substring(0, str.indexOf("|")-1);
            var outputSignal = str.substring(str.indexOf("|")+2);

            for (var number:  pattern.split("[ ]")) {
                patternList.add(sortLetters(number));
            }
            for (var number:  outputSignal.split("[ ]")) {
                outputSignalList.add(sortLetters(number));
            }
            signals.add(new Signal(patternList, outputSignalList));
        }
        return signals;
    }

}