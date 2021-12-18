package com.wgorajek;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Day15 extends Solution{

    @Override
    public Object getPart1Solution() {
        var cave = getInput();
        return cave.findBestPath().totalRisk;
    }

    @Override
    public Object getPart2Solution() {
        var cave = getInputLarge();
        return cave.findBestPath().totalRisk;
    }

    private class CavePoint {
        Point point;
        Integer riskLevel;
        List<CavePoint> neighbours;
        public CavePoint(Point point, Integer riskLevel) {
            this.point = point;
            this.riskLevel = riskLevel;
            neighbours = new ArrayList<CavePoint>();
        }
    }

    private class Path {
        Integer totalRisk;
        CavePoint lastPoint;

        public Path(Integer totalRisk, CavePoint lastPoint) {
            this.totalRisk = totalRisk;
            this.lastPoint = lastPoint;
        }

        public void addNewPoint(CavePoint point) {
            lastPoint = point;
            totalRisk += point.riskLevel;
        }
    }

    private class Cave {
        CavePoint[][] cave;
        CavePoint finishPoint;

        public Cave(Integer[][] caveMap) {
            cave = new CavePoint[caveMap.length][caveMap[0].length];
            for (var i = 0; i < caveMap.length; i++) {
                for (var j = 0; j < caveMap[0].length; j++) {
                    cave[i][j] = new CavePoint(new Point(i, j), caveMap[i][j]);
                }
            }
            int[] vector = {-1, 0, 1};
            for (var i = 0; i < cave.length; i++) {
                for (var j = 0; j < cave[i].length; j++) {
                    for (var vi: vector) {
                        for (var vj: vector) {
                            var x = i + vi;
                            var y = j + vj;
                            if ((vi == 0 ^ vj == 0) && validPoint(x, y)) {
                                cave[i][j].neighbours.add(cave[x][y]);
                            }
                        }
                    }
                }
            }
            finishPoint = cave[cave.length-1][cave[cave.length-1].length-1];
        }

        private boolean validPoint(int x, int y) {
            return (x >= 0 && y >= 0 && x < cave.length && y < cave[0].length);
        }

        public Path findBestPath() {
            var bestPathMap = new HashMap<CavePoint, Path>();
            Integer bestPathLength = 9999999;
            var pathStack = new Stack<Path>();
            var firstPath = new Path(0, cave[0][0]);
            pathStack.push(firstPath);
            bestPathMap.put(firstPath.lastPoint, firstPath);
            while (!pathStack.isEmpty()) {
                var path = pathStack.pop();
                for(var neighbour: path.lastPoint.neighbours) {
                    var newPath = new Path(path.totalRisk, path.lastPoint);
                    newPath.addNewPoint(neighbour);
                    var bestToThisPoint = bestPathMap.get(neighbour);
                    if (bestToThisPoint == null || bestToThisPoint.totalRisk > newPath.totalRisk) {
                        bestPathMap.put(newPath.lastPoint, newPath);
                        if (newPath.lastPoint != finishPoint) {
                            if (newPath.totalRisk < bestPathLength) {
                                pathStack.push(newPath);
                            }
                        }
                        else
                        {
                            bestPathLength = Integer.min(bestPathLength, newPath.totalRisk);
                        }
                    }
                }
            }

            return bestPathMap.get(finishPoint);
        }


    }

    private Cave getInput() {
        var input = getInputLines();
        var caveMap = new Integer[input.size()][input.get(0).length()];
        for (var i = 0; i < input.size() ; i++) {
            var line = input.get(i);
            for (var j =0; j < line.length(); j++) {
                caveMap[i][j] = Integer.parseInt(line.substring(j,j+1));
            }
        }
        return new Cave(caveMap);
    }

    private Cave getInputLarge() {
        var input = getInputLines();
        var sizeX = input.size();
        var sizeY = input.get(0).length();
        var caveMap = new Integer[input.size()*5][input.get(0).length()*5];

        for (var k = 0; k < 5; k++) {
            for (var l = 0; l < 5; l++) {
                for (var i = 0; i < input.size() ; i++) {
                    var line = input.get(i);
                    for (var j =0; j < line.length(); j++) {
                        var value = Integer.parseInt(line.substring(j,j+1));
                        value += k + l;
                        if (value > 9) {
                            value = value - 9;
                        }
//2835
                        caveMap[sizeX * k + i][sizeY * l + j] = value;
                    }
                }
            }
        }
        return new Cave(caveMap);
    }
}
