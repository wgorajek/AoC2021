package com.wgorajek;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.*;

public class Day22 extends Solution{
    @Override
    public Object getPart1Solution() {
        var reactor = getInput();

        reactor.executeInstructions(50);
        return reactor.countCubesOn();
    }

    @Override
    public Object getPart2Solution() {
        var reactor = getInput();

        reactor.executeInstructions(-1);
        return reactor.countCubesOn();
    }


    class Instruction {
        Cuboid cuboid;
        boolean turnOn;

        public Instruction(Cuboid cuboid, boolean turnOn) {
            this.cuboid = cuboid;
            this.turnOn = turnOn;
        }

        public List<Cuboid> execute(List<Cuboid> blocksOn) {
            var newList = new ArrayList<Cuboid>();

            for (var c: blocksOn) {
                if (!cuboid.hasCommonCubes(c)) {
                    newList.add(c);
                } else {
                    if (c.minX < cuboid.minX) {
                        var newMaX = cuboid.minX - 1;
                        newList.add(new Cuboid(c.minX, newMaX, c.minY, c.maxY, c.minZ, c.maxZ));
                    }
                    if (c.maxX > cuboid.maxX) {
                        var newMinX = cuboid.maxX + 1;
                        newList.add(new Cuboid(newMinX, c.maxX, c.minY, c.maxY, c.minZ, c.maxZ));
                    }
                    if (c.minY < cuboid.minY) {
                        var newMaxY = cuboid.minY - 1;
                        newList.add(new Cuboid(max(cuboid.minX, c.minX), min(cuboid.maxX, c.maxX), c.minY, newMaxY, c.minZ, c.maxZ));
                    }
                    if (c.maxY > cuboid.maxY) {
                        var newMinY = cuboid.maxY + 1;
                        newList.add(new Cuboid(max(cuboid.minX, c.minX), min(cuboid.maxX, c.maxX), newMinY, c.maxY, c.minZ, c.maxZ));
                    }
                    if (c.minZ < cuboid.minZ) {
                        var newMaxZ = cuboid.minZ - 1;
                        newList.add(new Cuboid(max(cuboid.minX, c.minX), min(cuboid.maxX, c.maxX), max(cuboid.minY, c.minY), min(c.maxY, cuboid.maxY), c.minZ, newMaxZ));
                    }
                    if (c.maxZ > cuboid.maxZ) {
                        var newMinZ = cuboid.maxZ + 1;
                        newList.add(new Cuboid(max(cuboid.minX, c.minX), min(cuboid.maxX, c.maxX), max(cuboid.minY, c.minY), min(c.maxY, cuboid.maxY), newMinZ, c.maxZ));
                    }
                }
            }
            return newList;

        }

        public boolean inLimit(int limit) {
            if (limit == -1) {
                return true;
            }
            return abs(cuboid.minX) <= limit && abs(cuboid.maxX) <= limit & abs(cuboid.minY) <= limit && abs(cuboid.maxY) <= limit & abs(cuboid.minZ) <= limit && abs(cuboid.maxZ) <= limit;
        }
    }

    class Reactor {
        List<Cuboid> blocksOn = new ArrayList<Cuboid>();
        List<Instruction> instructions = new ArrayList<Instruction>();

        public void executeInstructions(int limit) {
            for (var i: instructions) {
                if (i.inLimit(limit) ) {
                    blocksOn = i.execute(blocksOn);
                    if (i.turnOn) {
                        blocksOn.add(i.cuboid);
                    }
                }
            }
        }

        public long countCubesOn() {
            var count = 0l;
            for (var c: blocksOn) {
                count += c.blockCount();
            }
            return count;
        }
    }

    private class Cuboid {
        int minX;
        int minY;
        int minZ;
        int maxX;
        int maxY;
        int maxZ;

        public Cuboid(int minX, int maxX, int minY, int maxY, int minZ, int maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public List<Cuboid> split(Cuboid cuboid) {
            var list = new ArrayList<Cuboid>();
            return list;
        }

        public boolean hasCommonCubes(Cuboid cuboid) {
            return  (maxX >= cuboid.minX & minX <= cuboid.maxX) && (maxY >= cuboid.minY & minY <= cuboid.maxY) && (maxZ >= cuboid.minZ & minZ <= cuboid.maxZ);
        }

        public boolean coversAllcubes(Cuboid cuboid) {
            return (minX <= cuboid.minX) && (maxX >= cuboid.maxX) && (minY <= cuboid.minY) && (maxY >= cuboid.maxY) && (minZ <= cuboid.minZ) && (maxZ >= cuboid.maxZ);
        }

        public long blockCount() {
            return (maxX-minX + 1l) * (maxY - minY + 1l) * (maxZ - minZ + 1l);
        }
    }


    private Reactor getInput() {
        var input = getInputLines();
        var reactor = new Reactor();

        for (var line: input) {
            var isOn = line.substring(0, line.indexOf(" ")).equals("on");
            var str = line.substring(line.indexOf(" ")+1);
            var minX = Integer.parseInt(str.substring(2, str.indexOf("..")));
            var maxX = Integer.parseInt(str.substring(str.indexOf("..")+2, str.indexOf(",")));
            str = str.substring(str.indexOf(",")+1);
            var minY = Integer.parseInt(str.substring(2, str.indexOf("..")));
            var maxY = Integer.parseInt(str.substring(str.indexOf("..")+2, str.indexOf(",")));
            str = str.substring(str.indexOf(",")+1);
            var minZ = Integer.parseInt(str.substring(2, str.indexOf("..")));
            var maxZ = Integer.parseInt(str.substring(str.indexOf("..")+2));

            reactor.instructions.add(new Instruction(new Cuboid(minX, maxX, minY, maxY, minZ, maxZ), isOn));
        }

        return reactor;
    }
}

