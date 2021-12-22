package com.wgorajek;

public class Day20 extends Solution{
    @Override
    public Object getPart1Solution() {
        var image =  getInput();
        for (var i = 0; i < 2; i++) {
            image.applyAlgorithm();
        }
        return image.pointCount();
    }

    @Override
    public Object getPart2Solution() {
        var image =  getInput();
        for (var i = 0; i < 50; i++) {
            image.applyAlgorithm();
        }
        return image.pointCount();
    }

    private class EnchancementAlgorith {
        boolean[] algorithm = new boolean[512];

        public EnchancementAlgorith(String algorithm) {
            for (var i = 0; i < algorithm.length(); i++) {
                this.algorithm[i] = (algorithm.charAt(i) == '#');

            }
        }

        public boolean newValue(Integer number) {
            return algorithm[number];
        }
    }

    private class Image {
        boolean[][] image;
        int step = 1;

        EnchancementAlgorith algorithm;

        public Image(boolean[][] image, EnchancementAlgorith algorithm) {
            this.image = image;
            this.algorithm = algorithm;
        }

        public int pointCount() {
            var result = 0;
            for (var i = 0; i < 1000 ; i++) {
                for (var j = 0; j < 1000 ; j++) {
                    if (image[i][j]) {
                        result++;
                    }
                }
            }
            return result;
        }

        public void applyAlgorithm() {
            boolean[][] newImage = new boolean[1000][1000];
            for (var i = 0; i < 1000 ; i++) {
                for (var j = 0; j < 1000 ; j++) {
                    newImage[i][j] = step % 2 == 1;
                }
            }

            for (var i = 1; i < 999; i++) {
                for (var j = 1; j < 999 ; j++) {
                    newImage[i][j] = algorithm.newValue(getNeighboursSum(i, j));
                }
            }
            image = newImage;
            step ++;
        }


        private int getNeighboursSum(int i, int j) {
            int[] vector = {-1, 0, 1};
            var sum = 0;
            for (var vi: vector) {
                for (var vj: vector) {
                        sum *= 2;
                        if (image[i + vi][j + vj]) {
                            sum++;
                        }
                }
            }

            return sum;
        }
    }

    private Image getInput() {
        EnchancementAlgorith algorithm = null;
        var image = new boolean[1000][1000];
        var middle = 500;
        for (var i = 0; i < 1000 ; i++) {
            for (var j = 0; j < 1000 ; j++) {
                image[i][j] = false;
            }
        }
        var i = 0;
        for(var line: getInputLines()) {
            if (i == 0) {
                algorithm = new EnchancementAlgorith(line);
            }
            if (i  >= 2) {
                for (var j = 0 ; j < line.length(); j++) {
                    image[middle+i-2][middle+j] = (line.charAt(j) == '#');
                }
            }
            i++;
        }
        return new Image(image, algorithm);
    }
}
