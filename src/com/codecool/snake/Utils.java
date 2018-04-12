package com.codecool.snake;

import javafx.geometry.Point2D;

import java.util.Random;

public class Utils {

    public static Random rand = new Random(System.nanoTime());
    public static int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
    public static double randDouble(double min, double max) {
        return rand.nextDouble()*(max-min)+min;
    }

    /*
    Converts a direction in degrees (0...360) to x and y coordinates.
    The length of this vector is the second parameter
    */
    public static Point2D directionToVector(double directionInDegrees, double length) {
        double directionInRadians = directionInDegrees / 180 * Math.PI;
        Point2D heading = new Point2D(length * Math.sin(directionInRadians), - length * Math.cos(directionInRadians));
        return heading;
    }
}
