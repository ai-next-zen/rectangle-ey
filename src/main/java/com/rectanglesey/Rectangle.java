package com.rectanglesey;

import java.util.ArrayList;
import java.util.List;

public final class Rectangle {

    private Rectangle() {
        // Utility class
    }

    /**
     * 1. INTERSECTION
     * Determines if rectangles overlap and returns the intersection corner points.
     */
    public static List<String> getIntersectionPoints(double[] rect1, double[] rect2) {
        double[] a = normalize(rect1);
        double[] b = normalize(rect2);

        List<String> points = new ArrayList<>();

        if (a[0] < b[2] && a[2] > b[0] && a[1] < b[3] && a[3] > b[1]) {
            double interX1 = Math.max(a[0], b[0]);
            double interY1 = Math.max(a[1], b[1]);
            double interX2 = Math.min(a[2], b[2]);
            double interY2 = Math.min(a[3], b[3]);

            points.add(String.format("(%.1f, %.1f)", interX1, interY1));
            points.add(String.format("(%.1f, %.1f)", interX2, interY1));
            points.add(String.format("(%.1f, %.1f)", interX2, interY2));
            points.add(String.format("(%.1f, %.1f)", interX1, interY2));
        }

        return points;
    }

    /**
     * 2. CONTAINMENT
     * Returns true if rect1 is fully contained by rect2.
     */
    public static boolean isContainedBy(double[] rect1, double[] rect2) {
        double[] a = normalize(rect1);
        double[] b = normalize(rect2);

        return a[0] >= b[0] && a[2] <= b[2] && a[1] >= b[1] && a[3] <= b[3];
    }

    /**
     * 3. ADJACENCY
     * Returns one of: Proper, Sub-line, Partial, None
     */
    public static String detectAdjacency(double[] rect1, double[] rect2) {
        double[] a = normalize(rect1);
        double[] b = normalize(rect2);

        // If overlapping, not just adjacent
        if (a[0] < b[2] && a[2] > b[0] && a[1] < b[3] && a[3] > b[1]) {
            return "None";
        }

        boolean shareX = (a[0] == b[2] || a[2] == b[0]);
        boolean shareY = (a[1] == b[3] || a[3] == b[1]);

        if (shareX) {
            return validateSideSharing(a[1], a[3], b[1], b[3]);
        } else if (shareY) {
            return validateSideSharing(a[0], a[2], b[0], b[2]);
        }

        return "None";
    }

    private static String validateSideSharing(double min1, double max1, double min2, double max2) {
        if (min1 == min2 && max1 == max2) {
            return "Proper";
        }

        if ((min1 >= min2 && max1 <= max2) || (min2 >= min1 && max2 <= max1)) {
            return "Sub-line";
        }

        if (max1 > min2 && min1 < max2) {
            return "Partial";
        }

        return "None";
    }

    private static double[] normalize(double[] rect) {
        if (rect == null || rect.length != 4) {
            throw new IllegalArgumentException("Rectangle must be a double[4] {x1,y1,x2,y2}");
        }

        double x1 = Math.min(rect[0], rect[2]);
        double y1 = Math.min(rect[1], rect[3]);
        double x2 = Math.max(rect[0], rect[2]);
        double y2 = Math.max(rect[1], rect[3]);

        return new double[]{x1, y1, x2, y2};
    }

    public static void main(String[] args) {
        double[] r1 = new double[]{0, 0, 10, 10};
        double[] r2 = new double[]{10, 2, 15, 8};

        System.out.println("Adjacency: " + detectAdjacency(r1, r2));
        System.out.println("R2 contained in R1: " + isContainedBy(r2, r1));
    }
}
