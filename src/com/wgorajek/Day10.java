package com.wgorajek;

import java.util.*;

public class Day10 extends Solution {
    @Override
    public Object getPart1Solution() {
        var lines = getInput();
        var score = 0;
        for (var line : lines) {
            score += line.countErrorScore();
        }
        return score;
    }


    @Override
    public Object getPart2Solution() {
        var lines = getInput();
        var scoreList = new ArrayList<Long>();
        for (var line : lines) {
            var score = line.countMissingCharacters();
            if (score != 0) {
                scoreList.add(score);
            }
        }
        Collections.sort(scoreList);
        return scoreList.get((scoreList.size() - 1) /2);
    }

    private class Line {
        String chunks;

        public Line(String chunks) {
            this.chunks = chunks;
        }

        public Integer countErrorScore() {
            var chunkStack = new Stack<String>();
            for (var chunk : chunks.split("")) {
                if ("([{<".contains(chunk)) {
                    chunkStack.push(chunk);
                } else {
                    var openingchunk = chunkStack.pop();
                    if (!chunkMatch(chunk, openingchunk)) {
                        return errorValue(chunk);
                    }
                }
            }

            return 0;
        }

        public Long countMissingCharacters() {
            var chunkStack = new Stack<String>();
            var score = 0L;
            for (var chunk : chunks.split("")) {
                if ("([{<".contains(chunk)) {
                    chunkStack.push(chunk);
                } else {
                    var openingchunk = chunkStack.pop();
                    if (!chunkMatch(chunk, openingchunk)) {
                        return 0L;
                    }
                }
            }

            while (!chunkStack.empty()) {
                score = 5 * score + chunkValue(chunkStack.pop());
            }
            return score;
        }

        private int chunkValue(String chunk) {
            var value = 0;
            switch (chunk) {
                case "(": value = 1; break;
                case "[": value = 2; break;
                case "{": value = 3; break;
                case "<": value = 4; break;
            }
            return value;
        }

        private Integer errorValue(String chunk) {
            var value = 0;
            switch (chunk) {
                case ")": value = 3; break;
                case "]": value = 57; break;
                case "}": value = 1197; break;
                case ">": value = 25137; break;
            }
            return value;
        }

        private boolean chunkMatch(String chunk, String openingchunk) {
            var bothchunks = openingchunk.concat(chunk);
            return ((bothchunks.equals("()")) || (bothchunks.equals("{}")) || (bothchunks.equals("[]")) || (bothchunks.equals("<>")));
        }
    }

    private List<Line> getInput() {
        var input = getInputLines();
        var lineList = new ArrayList<Line>();

        for (var str : input) {
            lineList.add(new Line(str));
        }
        return lineList;
    }
}
