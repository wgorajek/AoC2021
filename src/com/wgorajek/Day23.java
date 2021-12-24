package com.wgorajek;

import java.lang.reflect.Array;
import java.util.Stack;

public class Day23 extends Solution{
    @Override
    public Object getPart1Solution() {
        var burrow = getInput();
        var possibleMoves = new Stack<Burrow>();
        possibleMoves.push(burrow);
        var bestSolution = 999999999;
        while (!possibleMoves.empty()) {
            var actualBurrow = possibleMoves.pop();
            if (actualBurrow.isFinished()) {
                bestSolution = Integer.min(bestSolution, actualBurrow.cost);
            }
            else {
                if (actualBurrow.cost <= bestSolution) {
                    Integer[] array = {0, 1, 3, 5, 7, 9, 10};
                    //all moves from tunnel to end
                    for (var i : array) {
                        if (actualBurrow.tunnel[i] != '.') {
                            var letter = actualBurrow.tunnel[i];
                            if (actualBurrow.rooms[homeRoom(letter)].readyToMoveIn()) {
                                var length = actualBurrow.wayFromTunneltoRoomLength(i, homeRoom(letter));
                                if (length != -1) {
                                    var newBurrow = actualBurrow.makeCopy();
                                    newBurrow.cost += letterMoveCost(letter) * length;
                                    newBurrow.tunnel[i] = '.';
                                    newBurrow.rooms[homeRoom(letter)].moveIn();
                                    possibleMoves.push(newBurrow);
                                }
                            }
                        }
                    }
                    //all moves from room to end
                    for (var i = 0; i < 4; i++) {
                        if (!actualBurrow.rooms[i].isPartFinished()) {
                            var firstLetter = actualBurrow.rooms[i].firstLetter();
                            if (firstLetter != '.' && actualBurrow.rooms[homeRoom(firstLetter)].readyToMoveIn()) {
                                var length = actualBurrow.wayFromRoomToRoomLength(i, homeRoom(firstLetter));
                                if (length != -1) {
                                    var newBurrow = actualBurrow.makeCopy();
                                    newBurrow.cost += letterMoveCost(firstLetter) * length;
                                    newBurrow.rooms[i].moveOut();
                                    newBurrow.rooms[homeRoom(firstLetter)].moveIn();
                                    possibleMoves.push(newBurrow);
                                }
                            }
                        }
                    }
                    //all moves from room to tunnel
                    for (var i = 0; i < 4; i++) {
                        if (!actualBurrow.rooms[i].isPartFinished()) {
                            var firstLetter = actualBurrow.rooms[i].firstLetter();
                            if (firstLetter != '.') {
                                for (var j = 0; j < actualBurrow.tunnel.length; j++) {
                                    var length = actualBurrow.wayFromRoomToTunnelLength(i, j);
                                    if (length != -1) {
                                        var newBurrow = actualBurrow.makeCopy();
                                        newBurrow.cost += letterMoveCost(firstLetter) * length;
                                        newBurrow.rooms[i].moveOut();
                                        newBurrow.tunnel[j] = firstLetter;
                                        possibleMoves.push(newBurrow);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return bestSolution;
    }

    private int letterMoveCost(char letter) {
        return (int)Math.pow(10, homeRoom(letter));
    }

    @Override
    public Object getPart2Solution() {
        var burrow = getInputLarge();
        var possibleMoves = new Stack<Burrow>();
        possibleMoves.push(burrow);
        var bestSolution = 999999999;
        while (!possibleMoves.empty()) {
            var actualBurrow = possibleMoves.pop();
            if (actualBurrow.isFinished()) {
                bestSolution = Integer.min(bestSolution, actualBurrow.cost);
            }
            else {
                if (actualBurrow.cost <= bestSolution) {
                    Integer[] array = {0, 1, 3, 5, 7, 9, 10};
                    //all moves from tunnel to end
                    for (var i : array) {
                        if (actualBurrow.tunnel[i] != '.') {
                            var letter = actualBurrow.tunnel[i];
                            if (actualBurrow.rooms[homeRoom(letter)].readyToMoveIn()) {
                                var length = actualBurrow.wayFromTunneltoRoomLength(i, homeRoom(letter));
                                if (length != -1) {
                                    var newBurrow = actualBurrow.makeCopy();
                                    newBurrow.cost += letterMoveCost(letter) * length;
                                    newBurrow.tunnel[i] = '.';
                                    newBurrow.rooms[homeRoom(letter)].moveIn();
                                    possibleMoves.push(newBurrow);
                                }
                            }
                        }
                    }
                    //all moves from room to end
                    for (var i = 0; i < 4; i++) {
                        if (!actualBurrow.rooms[i].isPartFinished()) {
                            var firstLetter = actualBurrow.rooms[i].firstLetter();
                            if (firstLetter != '.' && actualBurrow.rooms[homeRoom(firstLetter)].readyToMoveIn()) {
                                var length = actualBurrow.wayFromRoomToRoomLength(i, homeRoom(firstLetter));
                                if (length != -1) {
                                    var newBurrow = actualBurrow.makeCopy();
                                    newBurrow.cost += letterMoveCost(firstLetter) * length;
                                    newBurrow.rooms[i].moveOut();
                                    newBurrow.rooms[homeRoom(firstLetter)].moveIn();
                                    possibleMoves.push(newBurrow);
                                }
                            }
                        }
                    }
                    //all moves from room to tunnel
                    for (var i = 0; i < 4; i++) {
                        if (!actualBurrow.rooms[i].isPartFinished()) {
                            var firstLetter = actualBurrow.rooms[i].firstLetter();
                            if (firstLetter != '.') {
                                for (var j = 0; j < actualBurrow.tunnel.length; j++) {
                                    var length = actualBurrow.wayFromRoomToTunnelLength(i, j);
                                    if (length != -1) {
                                        var newBurrow = actualBurrow.makeCopy();
                                        newBurrow.cost += letterMoveCost(firstLetter) * length;
                                        newBurrow.rooms[i].moveOut();
                                        newBurrow.tunnel[j] = firstLetter;
                                        possibleMoves.push(newBurrow);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return bestSolution;
    }

    private class Room {
        char homeLetter;
        char[] room;

        public Room(char homeLetter, boolean isLarge) {
            this.homeLetter = homeLetter;
            if (isLarge) {
                room = new char[4];
            }
            else {
                room = new char[2];
            }
        }

        public int costIn() {
            for (var i = room.length - 1; i >= 0; i--) {
                if (room[i] == '.') {
                    return i+1;
                }
            }
            return 1;
        }

        public int costOut() {
            for (var i = 0; i < room.length; i++) {
                if (room[i] != '.') {
                    return i+1;
                }
            }
            return room.length;
        }

        public boolean readyToMoveIn() {
            for (var i = 0; i< room.length; i++) {
                if (room[i] != homeLetter && room[i] != '.') {
                    return false;
                }
            }
            return true;
        }

        public void moveIn() {
            for (var i = room.length - 1; i >=0 ; i--) {
                if (room[i] == '.') {
                    room[i] = homeLetter;
                    break;
                }
            }
        }

        public char firstLetter() {
            for (var i = 0; i < room.length; i++) {
                if (room[i] != '.') {
                    return room[i];
                }
            }
            return '.';
        }

        public void moveOut() {
            for (var i = 0; i < room.length ; i++) {
                if (room[i] != '.') {
                    room[i] = '.';
                    break;
                }
            }
        }

        public boolean isFinished() {
            for (var i = 0; i < room.length ; i++) {
                if (room[i] != homeLetter) {
                    return false;
                }
            }
            return true;
        }

        public boolean isPartFinished() {
            for (var i = 0; i < room.length ; i++) {
                if (room[i] != homeLetter && room[i] != '.') {
                    return false;
                }
            }
            return true;
        }
    }

    private class Burrow{
        int cost = 0;
        char[] tunnel;
        Room[] rooms;
        public Burrow(boolean isLarge) {
            tunnel = new char[11];
            rooms = new Room[4];
            for (var i = 0 ; i < tunnel.length; i++) {
                tunnel[i] = '.';
            }
            for (var i = 0; i < 4; i++) {
                rooms[i] = new Room(homeLetter(i), isLarge);
            }
        }

        public int wayFromRoomToRoomLength(int startRoomNumber, int finishRoomNumber) {
            //-1 not possible
            var startRoomNumberExit = 2 * startRoomNumber + 2;
            var finishRoomNumberExit = 2 * finishRoomNumber + 2;
            var length = -1;
            for (var i = Math.min(finishRoomNumberExit, startRoomNumberExit) ; i <= Math.max(finishRoomNumberExit, startRoomNumberExit) ;i++) {
                if (tunnel[i] != '.') {
                    return -1;
                }
                length++;
            }
            length += rooms[finishRoomNumber].costIn();
            length += rooms[startRoomNumber].costOut();

            return length;
        }

        public int wayFromRoomToTunnelLength(int startRoomNumber, int finishTunnelNumber) {
            //-1 not possible
            var startRoomNumberExit = 2 * startRoomNumber + 2;

            var length = -1;
            for (var i = Math.min(finishTunnelNumber, startRoomNumberExit) ; i <= Math.max(finishTunnelNumber, startRoomNumberExit) ;i++) {
                if (tunnel[i] != '.') {
                    return -1;
                }
                length++;
            }
            length += rooms[startRoomNumber].costOut();

            return length;
        }

        public int wayFromTunneltoRoomLength(int startTunnel, int finishRoomNumber) {
            //-1 not possible
            var finishRoomNumberExit = 2 * finishRoomNumber + 2;
            var length = -1;
            for (var i = Math.min(finishRoomNumberExit, startTunnel); i <= Math.max(finishRoomNumberExit, startTunnel) ;i++) {
                if (i != startTunnel && (tunnel[i] != '.')) {
                    return -1;
                }
                length++;
            }
            length += rooms[finishRoomNumber].costIn();

            return length;
        }

        public Burrow makeCopy() {
            var newBurrow = new Burrow(rooms[0].room.length == 4);
            for (var i = 0; i < tunnel.length; i++) {
                newBurrow.tunnel[i] = tunnel[i];
            }
            for (var i = 0; i < 4; i++) {
                for (var j = 0; j < rooms[i].room.length; j++) {
                    newBurrow.rooms[i].room[j] = rooms[i].room[j];
                }
            }
            newBurrow.cost = cost;
            return newBurrow;
        }

        public boolean isFinished() {
            for (var i = 0; i < 4; i++) {
                if (!rooms[i].isFinished()) {
                    return false;
                }
            }
            return true;
        }
    }

    private char homeLetter(int i) {
        switch (i) {
            case 0 : return 'A';
            case 1 : return 'B';
            case 2 : return 'C';
            case 3 : return 'D';
        }
        return 'F';
    }

    private int homeRoom(char c) {
        switch (c) {
            case 'A' : return 0;
            case 'B' : return 1;
            case 'C' : return 2;
            case 'D' : return 3;
        }
        return -1;
    }

    private Burrow getInput() {
        var input = getInputLines();
        var burrow = new Burrow(false);

        var line = input.get(2);
        burrow.rooms[0].room[0] = line.charAt(3);
        burrow.rooms[1].room[0] = line.charAt(5);
        burrow.rooms[2].room[0] = line.charAt(7);
        burrow.rooms[3].room[0] = line.charAt(9);
        line = input.get(3);
        burrow.rooms[0].room[1] = line.charAt(3);
        burrow.rooms[1].room[1] = line.charAt(5);
        burrow.rooms[2].room[1] = line.charAt(7);
        burrow.rooms[3].room[1] = line.charAt(9);
        return burrow;
    }

    private Burrow getInputLarge() {
        var input = getInputLines();
        var burrow = new Burrow(true);

        var line = input.get(2);
        burrow.rooms[0].room[0] = line.charAt(3);
        burrow.rooms[1].room[0] = line.charAt(5);
        burrow.rooms[2].room[0] = line.charAt(7);
        burrow.rooms[3].room[0] = line.charAt(9);
        line = input.get(3);
        burrow.rooms[0].room[3] = line.charAt(3);
        burrow.rooms[1].room[3] = line.charAt(5);
        burrow.rooms[2].room[3] = line.charAt(7);
        burrow.rooms[3].room[3] = line.charAt(9);


        burrow.rooms[0].room[1] = 'D';
        burrow.rooms[1].room[1] = 'C';
        burrow.rooms[2].room[1] = 'B';
        burrow.rooms[3].room[1] = 'A';

        burrow.rooms[0].room[2] = 'D';
        burrow.rooms[1].room[2] = 'B';
        burrow.rooms[2].room[2] = 'A';
        burrow.rooms[3].room[2] = 'C';

        return burrow;
    }
}
