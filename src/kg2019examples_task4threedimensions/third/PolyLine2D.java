/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.third;

import kg2019examples_task4threedimensions.math.Vector2;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class PolyLine2D {
    private final List<Vector2> points;
    private final boolean closed;
    
    public PolyLine2D(Collection<Vector2> points, boolean closed) {
        this.points = new LinkedList<>(points);
        this.closed = closed;
    }
    
    public boolean isClosed() {
        return closed;
    }
    
    public List<Vector2> getPoints() {
        return points;
    }
}
