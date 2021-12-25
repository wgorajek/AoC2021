package com.wgorajek;

public class Day25 extends Solution{
    @Override
    public Object getPart1Solution() {
        var sea = getInput();
        while (sea.nextStep()) {
        }

        return sea.step;
    }

    @Override
    public Object getPart2Solution() {
        return null;
    }



    private class Sea {
        char[][] map;
        Integer step = 0;
        public boolean nextStep() {
            step++;
            var somethingMoved = false;
            var newMap = new char[map.length][map[0].length];
            for (var i = 0; i < map.length; i++) {
                for (var j = 0; j < map[0].length; j++) {
                    newMap[i][j] = '.';
                }
            }

            for (var i = 0; i < map.length; i++) {
                for (var j = 0; j < map[0].length; j++) {
                    if (map[i][j] == '>') {
                        if (map[i][incY(j)] == '.') {
                            newMap[i][incY(j)] = '>';
                            somethingMoved = true;
                        }
                        else{
                            newMap[i][j] = '>';
                        }
                    }
                    else if (map[i][j] == 'v') {
                        newMap[i][j] = 'v';
                    }
                }
            }

            for (var i = 0; i < map.length; i++) {
                for (var j = 0; j < map[0].length; j++) {
                    map[i][j] = newMap[i][j];
                    newMap[i][j] = '.';
                }
            }

            for (var i = 0; i < map.length; i++) {
                for (var j = 0; j < map[0].length; j++) {
                    if (map[i][j] == 'v') {
                        if (map[incX(i)][j] == '.') {
                            newMap[incX(i)][j] = 'v';
                            somethingMoved = true;
                        }
                        else{
                            newMap[i][j] = 'v';
                        }
                    }
                    else if (map[i][j] == '>') {
                        newMap[i][j] = '>';
                    }
                }
            }

            for (var i = 0; i < map.length; i++) {
                for (var j = 0; j < map[0].length; j++) {
                    map[i][j] = newMap[i][j];
                }
            }


            return somethingMoved;
        }

        public Integer incX(Integer i) {
            return (i+1)%map.length;
        }

        public Integer incY(Integer i) {
            return (i+1)%map[0].length;
        }

    }

    private Sea getInput() {
        var sea = new Sea();
        var input = getInputLines();
        sea.map = new char[input.size()][input.get(0).length()];
        for (var i = 0; i < input.size(); i++) {
            for (var j = 0; j < input.get(0).length(); j++) {
                sea.map[i][j] = input.get(i).charAt(j);
            }
        }

        return sea;
    }
}
