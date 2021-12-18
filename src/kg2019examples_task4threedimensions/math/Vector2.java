/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.math;

public class Vector2 {
    private final double[] values;
    
    public Vector2(double x, double y) {
        values = new double[] {x, y};
    }
    
    public double getX() {
        return values[0];
    }
    
    public double getY() {
        return values[1];
    }
}
