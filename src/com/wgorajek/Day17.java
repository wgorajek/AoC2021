package com.wgorajek;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.StrictMath.abs;

public class Day17 extends Solution{
    @Override
    public Object getPart1Solution() {
        var shootingrange = getInput();
        return shootingrange.highestShot();
    }

    @Override
    public Object getPart2Solution() {
        var shootingrange = getInput();
        return shootingrange.numberOfHits();
    }

    private class Target{
        Integer minX;
        Integer maxX;
        Integer minY;
        Integer maxY;

        public Boolean isHit(Point point) {
            return point.x <= maxX && point.x >= minX && point.y <= maxY && point.y >= minY;
        }
    }

    private class Shot {
        Point velocity;
        public Shot(Point velocity) {
            this.velocity = velocity;
        }

        public Boolean hitTarget(Target target) {
            var location = new Point(0,0);
            while (location.y >= target.minY && location.x <= target.maxX) {
                if (target.isHit(location)) {
                    return true;
                }
                else
                {
                    location.x += velocity.x;
                    location.y += velocity.y;
                    velocity.x = Integer.max(velocity.x - 1, 0 );
                    velocity.y--;
                }
            }
            return false;
        }
    }

    private class ShootingRange {
        Target target;
        public ShootingRange(Target target) {
            this.target = target;
        }

        public Integer highestShot() {
            var height = 0;
            for (var i = 0 ; i < abs(target.minY); i++) {
                height += i;
            }
            return height;
        }

        public Integer highestShotVelocity() {
            var velocity = 0;
            for (var i = 0 ; i < abs(target.minY); i++) {
                velocity += i;
            }
            return velocity;
        }

        public Integer slowestShot() {
            var velocity = 0;
            var distance = 0;
            for (var i = 0; distance < abs(target.minX); i++) {
                distance += i;
                velocity++;
            }
            return velocity-1;
        }

        public Integer numberOfHits () {
            var shotHit = 0;
            for (var x = slowestShot(); x <= target.maxX ; x++) {
                for (var y = target.minY; y <= highestShotVelocity() ; y++) {
                    var shot = new Shot(new Point(x, y));
                    if (shot.hitTarget(target)) {
                        shotHit++;
                    }
                }
            }
            return shotHit;
        }
    }

    public ShootingRange getInput() {
        var input = getInputString();
        var target = new Target();

        Pattern pattern = Pattern.compile(".*x=(\\d+)..(\\d+), y=-(\\d+)..-(\\d+).*");

        Matcher m = pattern.matcher(input);
        if(m.matches()) {
            target.minX = Integer.parseInt(m.group(1));
            target.maxX = Integer.parseInt(m.group(2));
            target.minY = -Integer.parseInt(m.group(3));
            target.maxY = -Integer.parseInt(m.group(4));
        }

        return new ShootingRange(target);
    }


}
