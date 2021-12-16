package com.wgorajek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Day09 extends Solution {

    @Override
    public Object getPart1Solution() {
        var grid = getInput();
        return grid.getLowPointScore();
    }


    @Override
    public Object getPart2Solution() {
        var grid = getInput();
        return grid.getBasinScore();
    }

    private class GridPoint{
        Integer height;
        GridPoint up;
        GridPoint down;
        GridPoint left;
        GridPoint right;
        public GridPoint(Integer height) {
            this.height = height;
        }

        private List<GridPoint> neighbours() {
            var neighboursList = new ArrayList<GridPoint>();
            if (left != null) {
                neighboursList.add(left);
            }
            if (right != null) {
                neighboursList.add(right);
            }
            if (up != null) {
                neighboursList.add(up);
            }
            if (down != null) {
                neighboursList.add(down);
            }
            return neighboursList;
        }

        private boolean isLowPoint() {
            for (var point: neighbours()) {
                if (point.height <= this.height) {
                    return false;
                }
            }
            return true;
        }


        public Integer basinSize() {
            var score = 0;
            List<GridPoint> basinPoints = new ArrayList<GridPoint>();
            Stack<GridPoint> basinPointsToCheck = new Stack<GridPoint>();
            basinPoints.add(this);
            basinPointsToCheck.add(this);
            while(!basinPointsToCheck.empty()) {
                var point = basinPointsToCheck.pop();
                for (var neighbour: point.neighbours()) {
                    if (neighbour.height < 9 && !basinPoints.contains(neighbour)) {
                        basinPoints.add(neighbour);
                        basinPointsToCheck.push(neighbour);
                    }
                }
            }
            return basinPoints.size();
        }
    }

    private class Grid {
        Integer[][] grid;
        GridPoint[][] grid2;
        List<GridPoint> pointsList;

        public Grid(Integer[][] grid) {
            this.grid = grid;
            pointsList = new ArrayList<GridPoint>();
            createGrid(grid);
        }

        private void createGrid(Integer[][] grid) {
            grid2 = new GridPoint[grid.length][grid[0].length];
            for (var i = 0; i < grid.length; i++) {
                for (var j = 0; j < grid[0].length; j++) {
                    grid2[i][j] = new GridPoint(grid[i][j]);
                    pointsList.add(grid2[i][j]);
                }
            }
            for (var i = 0; i < grid.length; i++) {
                for (var j = 0; j < grid[0].length; j++) {
                    var point = grid2[i][j];
                    if (i >= 1) {
                        point.up = grid2[i-1][j];
                    }
                    if (i < grid.length - 1) {
                        point.down = grid2[i+1][j];
                    }
                    if (j >= 1) {
                        point.left = grid2[i][j-1];
                    }
                    if (j < grid[0].length - 1) {
                        point.right = grid2[i][j+1];
                    }
                }
            }
        }

        public Integer getLowPointScore() {
            var score = 0;

            for (var point: pointsList) {
                if (point.isLowPoint()) {
                    score += point.height + 1;
                }
            }
            return score;
        }

        public Integer getBasinScore() {
            var score = 0;
            List<Integer> basinSizes = new ArrayList<Integer>();

            for (var point: pointsList) {
                if (point.isLowPoint()) {
                    basinSizes.add(point.basinSize());
                }
            }
            Collections.sort(basinSizes);
            var size = basinSizes.size();
            return basinSizes.get(size - 1) * basinSizes.get(size - 2) * basinSizes.get(size - 3);
        }
    }



    private Grid getInput() {
        var input = getInputLines();
        Integer[][] gridArray = new Integer[input.size()][input.get(0).length()];
        for (var i = 0; i < input.size() ; i++) {
            var line = input.get(i);
            for (var j =0; j < line.length(); j++) {
                gridArray[i][j] = Integer.parseInt(line.substring(j,j+1));
            }
        }
        return new Grid(gridArray);
    }

}
