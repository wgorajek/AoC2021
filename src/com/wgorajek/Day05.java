package com.wgorajek;

import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

public class Day05 extends Solution {

        @Override
        public Object getPart1Solution() {
            final VentsMap map = getInput(false);

            return map.countOverlaps();
        }

        @Override
        public Object getPart2Solution() {
            final VentsMap map = getInput(true);

            return map.countOverlaps();
        }

        private static class VentsMap {
            public final Integer[][] map;
            public VentsMap(Integer[][] map){
                this.map = map;
            }

            public Integer countOverlaps() {
                Integer result = 0;
                for (var i = 0; i < map.length; i++) {
                    for (var j = 0;  j < map[0].length; j++) {
                        if (map[i][j] >= 2) {
                            result++;
                        }
                    }
                }
                return result;
            }
        }






        private VentsMap getInput(Boolean countDiagonal) {
            final List<String> input = getInputLines();
            Integer[][] map = new Integer[1000][1000];

            for (var i = 0; i < map.length; i++) {
                for (var j = 0;  j < map.length; j++) {
                    map[i][j] = 0;
                }
            }
            for (var line: input) {
                Matcher m = Pattern.compile("\\d+").matcher(line);
                m.find();
                Integer minX = Integer.parseInt(m.group());
                m.find();
                Integer minY = Integer.parseInt(m.group());
                m.find();
                Integer maxX = Integer.parseInt(m.group());
                m.find();
                Integer maxY = Integer.parseInt(m.group());

                Integer stepX = 0;
                if ((maxX-minX) != 0) {
                    stepX = (maxX - minX) / abs((maxX - minX));
                }
                Integer stepY = 0;
                if ((maxY-minY) != 0) {
                    stepY = (maxY - minY) / abs(maxY - minY);
                }
                var tmp = Math.max(abs(maxX - minX), abs(maxY - minY));

                if (countDiagonal || (stepX == 0) || (stepY ==0)) {
                    for (var i = 0; i <= tmp; i++) {
                        map[minX+stepX*i][minY+stepY*i] = map[minX+stepX*i][minY+stepY*i] + 1;
                    }
                }
            }

            return new VentsMap(map);
        }

}
