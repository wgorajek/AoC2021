package com.wgorajek;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 extends Solution {
    public List<Integer> bingoNumbers;

    @Override
    public Object getPart1Solution() {
        final List<BingoBoard> boardList = getInput();
        for(var number: bingoNumbers) {
            for (var board: boardList) {
                board.addNumber(number);
                if (board.checkWin()) {
                    return board.boardValue() * number;
                }
            }
        }

        return null;
    }

    @Override
    public Object getPart2Solution() {
        final List<BingoBoard> boardList = getInput();
        Integer winningSum = 0;
        for(var number: bingoNumbers) {
            for (var board: boardList) {
                if (!board.won) {

                    board.addNumber(number);
                    if (board.checkWin()) {
                        winningSum = board.boardValue() * number;
                        board.won = true;
                    }
                }
            }
        }

        return winningSum;
    }


    private static class BingoBoard {
        public final Integer[][] board = new Integer[5][5];
        public final Boolean[][] checkedBoard = new Boolean[5][5];
        public Boolean won;

        public BingoBoard() {
            won = false;
            for(var i=0; i<5 ;i++) {
                for(var j=0; j<5 ;j++) {
                    checkedBoard[i][j] = false;
                }
            }
        }

        public Integer boardValue() {
            Integer value = 0;
            for(var i=0; i<5 ;i++) {
                for(var j=0; j<5 ;j++) {
                    if (!checkedBoard[i][j]) {
                        value += board[i][j];
                    }
                }
            }
            return value;
        }

        public void addNumber(Integer number) {
            for(var i=0; i<5 ;i++) {
                for(var j=0; j<5 ;j++) {
                    if (board[i][j] == number) {
                        checkedBoard[i][j] = true;
                    }
                }
            }
        }
        public Boolean checkWin(){
            for(var i=0; i<5 ;i++) {
                var allCheckedHorizontal = true;
                var allCheckedVertical = true;
                for(var j=0; j<5 ;j++) {
                    if (!checkedBoard[i][j]) {
                        allCheckedHorizontal = false;
                    }
                    if (!checkedBoard[j][i]) {
                        allCheckedVertical = false;
                    }
                }
                if (allCheckedHorizontal || allCheckedVertical) {
                    return true;
                }
            }
            return false;
        }
    }

    private List<BingoBoard> getInput() {
        final List<String> input = getInputLines();
        final List<BingoBoard> boardList = new ArrayList<BingoBoard>();
        bingoNumbers = new ArrayList<Integer>();

        for (var i = 0; i < input.size() ; i++) {
            if (i == 0) {
                var firstLine = input.get(i);

                for (var str : firstLine.split("[,]")) {
                    bingoNumbers.add(Integer.parseInt(str));
                }
            }
            else {
                if ((i-1)%6 == 0) {
                    boardList.add(new BingoBoard());
                }
                if ((i-1)%6 != 0) {
                    var lastBoard = boardList.get(boardList.size()-1);
                    var j = 0;

                    Matcher m = Pattern.compile("\\d+").matcher(input.get(i));
                    while (m.find()) {
                        lastBoard.board[j][(i-1)%6-1] = (Integer.parseInt(m.group()));
                        j++;
                    }
                }
            }

        }
        return boardList;
    }

}

