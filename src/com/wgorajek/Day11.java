package com.wgorajek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day11 extends Solution{

    public Object getPart1Solution() {
        var grid = getInput();
        for (var i = 1; i <= 100; i++) {
            grid.nextStep();
        }
        return grid.numberOfFlashes;
    }


    @Override
    public Object getPart2Solution() {
        var grid = getInput();
        var newflashes = 0;
        while (newflashes != grid.octopuses.size()) {
          newflashes = grid.nextStep();
        }
        return grid.step;
    }

    private class Octopus {
        Integer energy;
        Boolean flashed;
        List<Octopus> neighbours;

        public Octopus(Integer energy) {
            this.energy = energy;
            neighbours = new ArrayList<Octopus>();
            flashed = false;
        }

        public void increaseEnergy() {
            energy++;
            if (energy > 9 && !flashed) {
                flashed = true;
                for (var n: neighbours) {
                    n.increaseEnergy();
                }
            }
        }

        public void beforeNextStep() {
            if (flashed) {
                flashed = false;
                energy = 0;
            }
        }
    }

    private class Grid {
        Octopus[][] grid;
        List<Octopus> octopuses;
        Integer step = 0;
        Integer numberOfFlashes = 0;

        public Grid(Integer[][] inputgrid) {
            this.grid = new Octopus[inputgrid.length][inputgrid[0].length];
            octopuses = new ArrayList<Octopus>();

            for (var i = 0; i < grid.length; i++) {
                for (var j = 0; j < grid[i].length; j++) {
                    grid[i][j] = new Octopus(inputgrid[i][j]);
                    octopuses.add(grid[i][j]);
                }
            }
            int[] vector = {-1,0,1};
            for (var i = 0; i < grid.length; i++) {
                for (var j = 0; j < grid[i].length; j++) {
                    for (var vi: vector) {
                        for (var vj: vector) {
                            var x = i + vi;
                            var y = j + vj;
                            if ((vi != 0 || vj != 0) && validPoint(x, y)) {
                                grid[i][j].neighbours.add(grid[x][y]);
                            }
                        }
                    }
                }
            }
        }

        public Integer nextStep() {
            var numberOfNewFlashes = 0;
            for (var octopus: octopuses) {
                octopus.increaseEnergy();
            }
            for (var octopus: octopuses) {
                if (octopus.flashed) {
                    numberOfNewFlashes++;
                }
                octopus.beforeNextStep();
            }
            step++;
            numberOfFlashes += numberOfNewFlashes;
            return numberOfNewFlashes;
        }

        private boolean validPoint(int x, int y) {
            return (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length);
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
