package com.wgorajek;

import java.util.List;

import static java.lang.Math.abs;

public class Day06 extends Solution{

    @Override
    public Object getPart1Solution() {
        var fishCount = getInput();
        for (var i = 0; i < 80; i++) {
            fishCount.nextDay();
        }

        return fishCount.getFishCount();
    }



    @Override
    public Object getPart2Solution() {
        var fishCount = getInput();
        for (var i = 0; i < 256; i++) {
            fishCount.nextDay();
        }
        return fishCount.getFishCount();
    }

    private static class Fishes {
        public final Long[] fishCount;
        public Fishes(Long[] fishCount){
            this.fishCount = fishCount;
        }

        public void nextDay() {
            var newFishCount = new Long[9];
            for (var i = 0; i <= 7; i++) {
                newFishCount[i] = fishCount[i+1];
            }
            newFishCount[8] = fishCount[0];
            newFishCount[6] += fishCount[0];
            for (var i =0; i < fishCount.length; i++) {
                fishCount[i] = newFishCount[i];
            }
        }
        public Long getFishCount() {
            Long totalFishes = 0L;
            for (var i =0; i < fishCount.length; i++) {
                totalFishes += fishCount[i];
            }
            return totalFishes;
        }
    }






    private Fishes getInput() {
        final String input = getInpuString();
        Long[] fishCount = new Long[9];
        for (var i =0; i < fishCount.length; i++) {
            fishCount[i] = 0L;
        }
        for (var str: input.split(",")) {
            fishCount[Integer.parseInt(str)]++;
        }


        return new Fishes(fishCount);
    }

}
