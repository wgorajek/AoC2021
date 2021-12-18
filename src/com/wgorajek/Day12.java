package com.wgorajek;

import java.util.*;

public class Day12 extends Solution{
    public Object getPart1Solution() {
        var caveSystem = getInput();
        return caveSystem.countPaths(false);
    }


    @Override
    public Object getPart2Solution() {
        var caveSystem = getInput();
        return caveSystem.countPaths(true);
    }

    private class Cave {
        String name;
        List<Cave> neighbours;
        Boolean isSmall;

        public Cave(String name) {
            this.name = name;
            isSmall = Character.isLowerCase(name.charAt(0));
            neighbours = new ArrayList<Cave>();
        }
    }

    private class Path {
        public Object print;
        List<Cave> path;
        Cave lastCave;
        public Path(List<Cave> previousPath, Cave newCave) {
            path = new ArrayList<Cave>();
            path.addAll(previousPath);
            path.add(newCave);
            lastCave = newCave;
        }

        public Boolean visited(Cave cave) {
            return path.contains(cave);
        }

        public Boolean visitedTwice(Cave cave) {
            if (cave.name.equals("start")) {
                return true;
            }
            if (cave.isSmall && this.visited(cave)) {
                for (var c: path) {
                    if (c.isSmall && Collections.frequency(path, c) == 2) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private class CaveSystem {
        Cave startCave;
        Map<String, Cave> caves;
        public CaveSystem() {
            caves = new HashMap<String, Cave>();
        }

        public Integer countPaths(Boolean allowVisitTwice) {
            var pathStack = new Stack<Path>();
            var finishedPaths = new ArrayList<Path>();
            var startPath = new Path(new ArrayList<Cave>(), startCave);
            pathStack.push(startPath);
            while(!pathStack.isEmpty()) {
                var path = pathStack.pop();
                for (var neighbour : path.lastCave.neighbours) {
                    if (!neighbour.isSmall || (allowVisitTwice && !path.visitedTwice(neighbour)) || (!allowVisitTwice && !path.visited(neighbour))) {
                        if (neighbour.name.equals("end")) {
                            finishedPaths.add(new Path(path.path, neighbour));
                        }
                        else {
                            pathStack.push(new Path(path.path, neighbour));
                        }
                    }
                }
            }
            return finishedPaths.size();
        }
    }


    private CaveSystem getInput() {
        var input = getInputLines();
        var caveSystem = new CaveSystem();
        for (var line: input) {
            var pointA = line.substring(0, line.indexOf('-'));
            var pointB = line.substring(line.indexOf('-')+1);
            if (!caveSystem.caves.containsKey(pointA)) {
                caveSystem.caves.put(pointA, new Cave(pointA));
            }
            if (!caveSystem.caves.containsKey(pointB)) {
                caveSystem.caves.put(pointB, new Cave(pointB));
            }
            var caveA = caveSystem.caves.get(pointA);
            var caveB = caveSystem.caves.get(pointB);
            caveA.neighbours.add(caveB);
            caveB.neighbours.add(caveA);
        }

        for (var cave : caveSystem.caves.values()) {
            if (cave.name.equals("start")) {
                caveSystem.startCave = cave;
            }
        }

        return caveSystem;
    }
}
