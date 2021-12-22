package com.wgorajek;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;


public class Day19 extends Solution{

    @Override
    public Object getPart1Solution() {
        var test = getInput();
        test.matchScanners();
        return test.countAllPoints();
    }

    @Override
    public Object getPart2Solution() {
        var test = getInput();
        test.matchScanners();
        return test.maxDistance();
    }

    private class Scanner{
        Integer number;
        ArrayList<Point3d> points;
        Point3d vectorVsCenter;
        public Scanner(Integer number) {
            this.number = number;
            points = new ArrayList<Point3d>();
        }
        public boolean isSynchronized() {
            return vectorVsCenter != null;
        }

        public void trySynchronize(Scanner foundScanner) {
            for (var i = 1; i <= 24; i++) {
                for (var p: foundScanner.points) {
                    if (!isSynchronized()) {
                        for (var p2 : points) {
                            if (!isSynchronized()) {
                                var vector = Point3d.substract(p, p2.transform(i));
                                var isVectorGood = true;
                                var numberOfPointsFound = 0;
                                for (var p3 : foundScanner.points) {
                                    if (isVectorGood) {
                                        var testedPoint = Point3d.substract(p3, vector);
                                        if (testedPoint.inRange(1000)) {
                                            var pointFound = false;
                                            for (var p4 : points) {

                                                if (p4.transform(i).equals(testedPoint)) {
                                                    numberOfPointsFound++;
                                                    pointFound = true;
                                                }
                                            }
                                            if (!pointFound) {
                                                isVectorGood = false;
                                            }
                                        }
                                    }
                                }
                                if (isVectorGood && numberOfPointsFound >= 12) {
                                    vectorVsCenter = Point3d.add(vector, foundScanner.vectorVsCenter);
                                    for (var p4 : points) {
                                        var t = p4.transform(i);
                                        p4.x = t.x;
                                        p4.y = t.y;
                                        p4.z = t.z;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private class Region {
        List<Scanner> scanners;

        public Region(List<Scanner> scanners) {
            this.scanners = scanners;
        }


        private boolean allScannersfound() {
            var allFound = true;
            for (var scanner : scanners) {
                allFound &= scanner.isSynchronized();
            }
            return allFound;
        }


        public int maxDistance() {
            var max = 0;
            for (var s: scanners) {
                for (var s2: scanners) {
                    max = Integer.max(max, s.vectorVsCenter.distance(s2.vectorVsCenter));
                }
            }
            return max;
        }


        public int countAllPoints() {
            var allPoints = new HashSet<Point3d>();
            for (var s : scanners) {
                for (var p : s.points) {
                    var contain = false;
                    for (var all: allPoints) {
                        if (all.equals(Point3d.add(p, s.vectorVsCenter))) {
                            contain = true;
                        }
                    }
                    if (!contain) {
                        allPoints.add(Point3d.add(p, s.vectorVsCenter));
                    }
                }
            }
            return allPoints.size();
        }

        public void matchScanners() {
            var foundedScanners = new Stack<Scanner>();
            foundedScanners.push(scanners.get(0));
            scanners.get(0).vectorVsCenter = new Point3d(0, 0, 0);
            while (!allScannersfound()) {
                var foundScanner = foundedScanners.pop();
                for (var scanner : scanners) {
                    if (!scanner.isSynchronized()) {
                        scanner.trySynchronize(foundScanner);
                        if (scanner.isSynchronized()) {
                            foundedScanners.push(scanner);
                        }
                    }
                }
            }
        }
    }


    private static class Point3d{
        int x;
        int y;
        int z;
        public Point3d(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static Point3d substract(Point3d p1, Point3d p2) {
            return new Point3d(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
        }

        public static Point3d add(Point3d p1, Point3d p2) {
            return new Point3d(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
        }

        public boolean equals (Object obj) {
            return ((Point3d) obj).x == x && ((Point3d) obj).y == y && ((Point3d) obj).z == z;
        }

        public Point3d transform(int type) {
            switch (type) {
                case 1: return new Point3d(x, y, z);
                case 2: return new Point3d(x, -z, y);
                case 3: return new Point3d(x, -y, -z);
                case 4: return new Point3d(x, z, -y);
                case 5: return new Point3d(-x, -y, z);
                case 6: return new Point3d(-x, -z, -y);
                case 7: return new Point3d(-x, y, -z);
                case 8: return new Point3d(-x, z, y);
                case 9: return new Point3d(y, -x, z);
                case 10: return new Point3d(y, -z, -x);
                case 11: return new Point3d(y, x, -z);
                case 12: return new Point3d(y, z, x);
                case 13: return new Point3d(-y, -x, -z);
                case 14: return new Point3d(-y, z, -x);
                case 15: return new Point3d(-y, x, z);
                case 16: return new Point3d(-y, -z, x);
                case 17: return new Point3d(z, -y, x);
                case 18: return new Point3d(z, -x, -y);
                case 19: return new Point3d(z, y, -x);
                case 20: return new Point3d(z, x, y);
                case 21: return new Point3d(-z, -y, -x);
                case 22: return new Point3d(-z, x, -y);
                case 23: return new Point3d(-z, y, x);
                case 24: return new Point3d(-z, -x, y);
            }
            return null;
        }

        public boolean inRange(int i) {
            return Math.abs(x) <= i && Math.abs(y) <= i && Math.abs(z) <= i;
        }

        public int distance(Point3d point) {
            return abs(x-point.x) + abs(y-point.y) + abs(z-point.z);
        }
    }

    private Region getInput() {
        var input=  getInputLines();
        Scanner scanner = null;
        var list = new ArrayList<Scanner>();
        for(var line: input) {
            if (line.isEmpty()) {}
            else if (line.substring(0,3).equals("---")) {
                Pattern pattern = Pattern.compile("(\\d+)");
                Matcher m = pattern.matcher(line);
                m.find();
                scanner = new Scanner(Integer.parseInt(m.group(1)));
                list.add(scanner);
            } else if (!line.isEmpty()) {
                var inputArray = line.split(",");
                scanner.points.add(new Point3d(Integer.parseInt(inputArray[0]), Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
            }
        }
        return new Region(list);
    }
}
