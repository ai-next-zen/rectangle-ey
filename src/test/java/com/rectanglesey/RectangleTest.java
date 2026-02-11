package com.rectanglesey;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RectangleTest {
    @Test
    void intersectionPoints_whenOverlapping_returnsFourCorners() {
        double[] r1 = new double[]{0, 0, 10, 10};
        double[] r2 = new double[]{5, 5, 15, 12};

        List<String> points = Rectangle.getIntersectionPoints(r1, r2);

        assertEquals(4, points.size());
        assertEquals("(5.0, 5.0)", points.get(0));
        assertEquals("(10.0, 5.0)", points.get(1));
        assertEquals("(10.0, 10.0)", points.get(2));
        assertEquals("(5.0, 10.0)", points.get(3));
    }

    @Test
    void intersectionPoints_whenNotOverlapping_returnsEmpty() {
        double[] r1 = new double[]{0, 0, 1, 1};
        double[] r2 = new double[]{2, 2, 3, 3};

        assertEquals(0, Rectangle.getIntersectionPoints(r1, r2).size());
    }

    @Test
    void containment_detectsContainedRectangle() {
        double[] outer = new double[]{0, 0, 10, 10};
        double[] inner = new double[]{2, 2, 3, 3};
        double[] notInner = new double[]{-1, 2, 3, 3};

        assertTrue(Rectangle.isContainedBy(inner, outer));
        assertFalse(Rectangle.isContainedBy(notInner, outer));
    }

    @Test
    void adjacency_detectsProper() {
        double[] r1 = new double[]{0, 0, 10, 10};
        double[] r2 = new double[]{10, 0, 20, 10};

        assertEquals("Proper", Rectangle.detectAdjacency(r1, r2));
    }

    @Test
    void adjacency_detectsSubLine() {
        double[] r1 = new double[]{0, 0, 10, 10};
        double[] r2 = new double[]{10, 2, 20, 8};

        assertEquals("Sub-line", Rectangle.detectAdjacency(r1, r2));
    }

    @Test
    void adjacency_detectsPartial() {
        double[] r1 = new double[]{0, 0, 10, 10};
        double[] r2 = new double[]{10, 8, 20, 12};

        assertEquals("Partial", Rectangle.detectAdjacency(r1, r2));
    }
}
