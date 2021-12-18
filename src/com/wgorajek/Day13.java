package com.wgorajek;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

public class Day13 extends Solution{
    public Object getPart1Solution() {
        var origami = getInput();
        origami.applyFirstRule();
        return origami.countPoints();
    }

    @Override
    public Object getPart2Solution() {
        var origami = getInput();
        origami.applyRules();
        origami.print();
        return 0;
    }



    private class Rule {
        String axis;
        Integer value;
        public Rule (String axis, Integer value) {
            this.axis = axis;
            this.value = value;
        }

        public void apply(Point point) {
            if (axis.equals("x")) {
                if (point.x > value) {
                    point.x = value * 2 - point.x;
                }
            }
            if (axis.equals("y")) {
                if (point.y > value) {
                    point.y = value * 2 - point.y;
                }
            }
        }
    }

    private class Origami {
        List<Point> points;
        List<Rule> rules;
        public Origami (List<Point> points, List<Rule> rules) {
            this.points = points;
            this.rules = rules;
        }

        public void applyRules() {
            for (var rule: rules) {
                for (var point: points) {
                    rule.apply(point);
                }
            }
        }

        public void applyFirstRule() {
            var rule = rules.get(0);
            for (var point: points) {
                rule.apply(point);
            }
        }

        public Integer countPoints() {
            var diffrentPoints = new HashSet<Point>();
            diffrentPoints.addAll(points);
            return diffrentPoints.size();
        }

        public void print() {
            System.out.println();
            var maxX = 0;
            var maxY = 0;
            for(var p: points) {
                maxX = Integer.max(p.x, maxX);
                maxY = Integer.max(p.y, maxY);
            }
            var output = new char[maxY+1][maxX+1];
            for(var y = 0; y < output.length; y++) {
                for(var x = 0; x < output.length; x++) {
                    output[y][x]=' ';
                }
            }
            for(var p: points) {
                output[p.y][p.x] = '#';
            }
            for(var y = 0; y < output.length; y++) {
                System.out.println(new String(output[y]));
            }
        }
    }


    private Origami getInput() {
        var input = getInputLines();
        var pointList = new ArrayList<Point>();
        var rules = new ArrayList<Rule>();

        var i = 0;
        while (!input.get(i).isEmpty()) {
            var line = input.get(i);
            var x = line.substring(0, line.indexOf(",") );
            var y = line.substring(line.indexOf(",") + 1);
            pointList.add(new Point(Integer.parseInt(x), Integer.parseInt(y)));
            i++;
        }
        i++;
        for (;i < input.size(); i++) {
            var line = input.get(i);
            line = line.replace("fold along ","");
            var axis = line.substring(0,1);
            var value = Integer.parseInt(line.substring(2));
            rules.add(new Rule(axis, value));
        }
        return new Origami(pointList, rules);
    }
}
