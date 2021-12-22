package com.wgorajek;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day21 extends Solution{
    @Override
    public Object getPart1Solution() {
        var game = getInput();
        game.gameScoreLimit = 1000;
        while (!game.isFinished()) {
            game.play(0);
        }
        return game.getResult();
    }

    @Override
    public Object getPart2Solution() {
        var p1Won = 0l;
        var p2Won = 0l;
        var game = getInput();
        var universe = new Universe(game, 1);
        var universes = new Stack<Universe>();
        universes.push(universe);

        while (!universes.empty()) {
            var newUniverse = universes.pop();
            var splitUniverses = newUniverse.split();
            for (var u: splitUniverses) {
                if (u.game.isFinished()) {
                    if (u.game.scoreP1 >= u.game.gameScoreLimit) {
                        p1Won += u.count;
                    }
                    else {
                        p2Won += u.count;
                    }
                }
                else {
                    universes.push(u);
                }
            }
        }

        return Long.max(p1Won, p2Won);
    }


    private class Universe {
        long count = 0l;
        Game game;
        public Universe(Game game, long count) {
            this.game = game;
            this.count = count;
        }

        public int numberOfNewUniverses(int value) {
            switch(value) {
                case 3 : return 1;
                case 4 : return 3;
                case 5 : return 6;
                case 6 : return 7;
                case 7 : return 6;
                case 8 : return 3;
                case 9 : return 1;
            }
            return 0;
        }

        public List<Universe> split() {
            var universes = new ArrayList<Universe>();
            for (var i = 3; i <= 9; i++) {
                var newGame = new Game(game.player1, game.player2, game.scoreP1, game.scoreP2, game.nextPlayer1);
                newGame.play(i);
                var newUniverse = new Universe(newGame, count*numberOfNewUniverses(i));
                universes.add(newUniverse);
            }
            return universes;
        }
    }

    private class Dice {
        int diceNext = 0;
        int rolledCount = 0;
        public int roll() {
            diceNext = (diceNext) %100 + 1;
            rolledCount++;
            return diceNext;
        }
    }

    private class Game {
        int player1;
        int player2;
        int scoreP1 = 0;
        int scoreP2 = 0;
        boolean nextPlayer1;
        int gameScoreLimit;
        Dice dice;

        public Game(int player1, int player2, int scoreP1, int scoreP2, boolean nextPlayer1) {
            this.player1 = player1;
            this.player2 = player2;
            this.scoreP1 = scoreP1;
            this.scoreP2 = scoreP2;
            this.nextPlayer1 = nextPlayer1;
            gameScoreLimit = 21;
            dice = new Dice();
        }

        public void play(int rollValue) {
            if (rollValue == 0) {
                rollValue = dice.roll() + dice.roll() + dice.roll();
            }
            if (nextPlayer1) {
                player1 = (player1 - 1 + rollValue) % 10 + 1;
                scoreP1 += player1;
            }
            else
            {
                player2 = (player2 - 1 + rollValue) % 10 + 1;
                scoreP2 += player2;
            }
            nextPlayer1 = !nextPlayer1;
        }


        public boolean isFinished() {
            return scoreP1 >= gameScoreLimit || scoreP2 >= gameScoreLimit;
        }

        public int getResult() {
            if (scoreP2 >= scoreP1) {
                return dice.rolledCount * scoreP1;
            }
            else {
                return dice.rolledCount * scoreP2;
            }
        }
    }
    private Game getInput() {
        var input = getInputLines();

        var playerOne = input.get(0).substring(input.get(0).length() - 1);
        var playerTwo = input.get(1).substring(input.get(1).length() - 1);
        return new Game(Integer.parseInt(playerOne), Integer.parseInt(playerTwo), 0, 0, true);
    }

}
