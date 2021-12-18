package com.wgorajek;

import java.util.HashMap;

public class Day14 extends Solution{


    @Override
    public Object getPart1Solution() {
        var polimerization = getInput();
        while(polimerization.step < 10) {
            polimerization.nextStep();
        }

        return polimerization.countDiffrence();
    }

    @Override
    public Object getPart2Solution() {
        var polimerization = getInput();
        while(polimerization.step < 40) {
            polimerization.nextStep();
        }

        return polimerization.countDiffrence();
    }

    private class Polimerization {
        HashMap<String, String> rules = new HashMap<String, String>();
        HashMap<String, Long> polimerMap = new HashMap<String, Long>();
        String polimer;
        Integer step;

        public Polimerization(String polimer, HashMap<String, String> rules ) {
            step = 0;
            this.polimer = polimer;
            this.rules.putAll(rules);
            for (var pair: rules.keySet()) {
                polimerMap.put(pair, 0L);
            }
            for (var i = 0; i < polimer.length() - 1; i++) {
                var pair = polimer.substring(i, i+2);
                polimerMap.put(pair, polimerMap.get(pair) + 1);
            }
        }

        public void nextStep() {
            HashMap<String, Long> newPolimerMap = new HashMap<String, Long>();
            for (var key: polimerMap.keySet()) {
                if (polimerMap.get(key) > 0) {
                    var newpair1 = key.substring(0, 1).concat(rules.get(key));
                    var newpair2 = rules.get(key).concat(key.substring(1));
                    newPolimerMap.put(newpair1, polimerMap.get(key) + newPolimerMap.getOrDefault(newpair1, 0L));
                    newPolimerMap.put(newpair2, polimerMap.get(key) + newPolimerMap.getOrDefault(newpair2, 0L));
                }
            }
            polimerMap = newPolimerMap;
            step++;
        }

        public Long countDiffrence() {
            var strCount = new HashMap<String, Long>();
            for (var pair: polimerMap.keySet()) {
                for (var str: pair.split("")) {
                    strCount.put(str, strCount.getOrDefault(str, 0L) + polimerMap.get(pair));
                }
            }
            var firstLetter = polimer.substring(0,1);
            var lastLetter = polimer.substring(polimer.length()-1);
            strCount.put(firstLetter, strCount.get(firstLetter) + 1);
            strCount.put(lastLetter, strCount.get(lastLetter) + 1);
            var min = 99999999999999L;
            var max = 0L;

            for(var value: strCount.values()) {
                min = Long.min(min, value);
                max = Long.max(max, value);
            }
            return (max-min) / 2;
        }
    }

    private Polimerization getInput() {
        var input = getInputLines();
        var rules = new HashMap<String, String>();
        var polimer = input.get(0);
        for(var i = 2; i < input.size(); i++) {
            var line = input.get(i);
            var inputPolimer = line.substring(0, 2);
            var outputPolimer = line.substring(line.length()-1);
            rules.put(inputPolimer, outputPolimer);
        }

        return new Polimerization(polimer, rules);
    }
}
