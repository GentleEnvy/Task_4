package models;

import kg2019examples_task4threedimensions.math.Vector2;
import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine2D;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Gear
    implements IModel
{
    public double externalRadius;
    public double internalRadius;
    public double weight;
    public double toothLength;
    public int n;
    
    public Gear(
        final double externalRadius,
        final double internalRadius,
        final double weight,
        final double toothLength,
        final int n
    ) {
        this.externalRadius = externalRadius;
        this.internalRadius = internalRadius;
        this.weight = weight;
        this.toothLength = toothLength;
        this.n = n;
    }
    
    @Override
    public List<PolyLine3D> getLines() {
        var z = weight / 2;
        var lines = new ArrayList<PolyLine3D>();
        for (var line2D : getFlat()) {
            var upperPoints3D = new ArrayList<Vector3>();
            var lowerPoints3D = new ArrayList<Vector3>();
            for (var point2D : line2D.getPoints()) {
                var upperPoint3D = new Vector3(point2D.getX(), point2D.getY(), z);
                var lowerPoint3D = new Vector3(point2D.getX(), point2D.getY(), -z);
                upperPoints3D.add(upperPoint3D);
                lowerPoints3D.add(lowerPoint3D);
                lines.add(new PolyLine3D(
                    List.of(upperPoint3D, lowerPoint3D), false)
                );
            }
            lines.add(new PolyLine3D(upperPoints3D, true));
            lines.add(new PolyLine3D(lowerPoints3D, true));
        }
        return lines;
    }
    
    public List<PolyLine2D> getFlat() {
        var lines = new ArrayList<PolyLine2D>();
        
        var pointsExternalBase = new ArrayList<Vector2>();
        var pointsInternalBase = new ArrayList<Vector2>();
        double angle = 0;
        double deltaAngle = Math.PI / n;
        
        Vector2 prevV = null;
        for (int i = 0; i < 2 * n; ++i) {
            var externalV = new Vector2(
                externalRadius * Math.cos(angle), externalRadius * Math.sin(angle)
            );
            pointsExternalBase.add(externalV);
            
            var internalV = new Vector2(
                internalRadius * Math.cos(angle), internalRadius * Math.sin(angle)
            );
            pointsInternalBase.add(internalV);
            
            lines.add(new PolyLine2D(List.of(externalV, internalV), false));
            
            angle += deltaAngle;
            
            if (prevV == null) {
                prevV = externalV;
            } else {
                var toothVertexes = getToothVertexes(prevV, externalV);
                var a = toothVertexes[0];
                var d = toothVertexes[1];
                
                var pointsTooth = new ArrayList<>(
                    Arrays.asList(prevV, a, d, externalV)
                );
                lines.add(new PolyLine2D(pointsTooth, false));
                
                prevV = null;
            }
        }
        lines.add(new PolyLine2D(pointsExternalBase, true));
        lines.add(new PolyLine2D(pointsInternalBase, true));
        
        return lines;
    }
    
    private Vector2[] getToothVertexes(Vector2 v1, Vector2 v2) {
        var bx = (v1.getX() + v2.getX()) / 2;
        var by = (v1.getY() + v2.getY()) / 2;
        var r = Math.sqrt(bx * bx + by * by);
        var cx = bx * (1 + toothLength / r);
        var cy = by * (1 + toothLength / r);
        
        var ax = v1.getX() + cx - bx;
        var ay = v1.getY() + cy - by;
        var dx = v2.getX() + cx - bx;
        var dy = v2.getY() + cy - by;
        return new Vector2[] {new Vector2(ax, ay), new Vector2(dx, dy)};
    }
}
