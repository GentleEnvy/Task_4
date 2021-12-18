/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import kg2019examples_task4threedimensions.math.Vector3;
import kg2019examples_task4threedimensions.third.IModel;
import kg2019examples_task4threedimensions.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 Описывает параллелипипед по двум диагональным точкам
 
 @author Alexey */
public class Bevel
    implements IModel
{
    private int dotCount;
    private float offset;
    
    public float getOffset() {
        return offset;
    }
    
    public int getDotCount() {
        return dotCount;
    }
    
    public void setDotCount(int dotCount) {
        this.dotCount = dotCount;
    }
    
    public void setOffset(float offset) {
        this.offset = offset;
    }
    
    public Bevel() {
        dotCount = 6;
        offset = 0.2f;
    }
    
    public Vector3 interpolation(Vector3 v1, Vector3 v2, double dt) {
        double x = v1.getX() * (1.0f - dt) + v2.getX() * dt;
        double y = v1.getY() * (1.0f - dt) + v2.getY() * dt;
        double z = v1.getZ() * (1.0f - dt) + v2.getZ() * dt;
        return new Vector3(x, y, z);
    }
    
    private PolyLine3D genRound(Vector3 p1, Vector3 p2, Vector3 p3) {
        List<Vector3> points = new LinkedList<>();
        points.add(p1);
        for (int i = 0; i < dotCount; i++) {
            float t = (1.0f / dotCount) * i;
            Vector3 t1 = interpolation(p1, p2, t);
            Vector3 t2 = interpolation(p2, p3, t);
            points.add(interpolation(t1, t2, t));
        }
        points.add(p3);
        return new PolyLine3D(points, false);
    }
    
    private List<PolyLine3D> genPrim(float xPos) {
        Vector3 v1 = new Vector3(xPos, 1, 0);
        Vector3 v2 = new Vector3(xPos, 1, 1);
        Vector3 v3 = new Vector3(xPos, 0, 1);
        
        LinkedList<PolyLine3D> result = new LinkedList<>();
        
        Vector3 myOffset1 = interpolation(
            v1,
            v2,
            offset
        ); // Смещаем точеки от центральной
        Vector3 myOffset2 = interpolation(v3, v2, offset);
        
        result.add(new PolyLine3D(Arrays.asList(v1, myOffset1), false));
        result.add(genRound(myOffset1, v2, myOffset2));
        result.add(new PolyLine3D(Arrays.asList(v3, myOffset2), false));
        
        return result;
    }
    
    
    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> result = new LinkedList<>();
        
        List<PolyLine3D> leftGroup = genPrim(-1.0f);
        List<PolyLine3D> rightGroup = genPrim(1.0f);
        
        for (int pl = 0; pl < leftGroup.size(); pl++) {
            
            List<Vector3> leftPoints = leftGroup.get(pl).getPoints();
            List<Vector3> rightPoints = rightGroup.get(pl).getPoints();
            
            for (int i = 0; i < leftPoints.size(); i++) {
                
                Vector3 p1 = leftPoints.get(i);
                Vector3 p2 = rightPoints.get(i);
                
                result.add(new PolyLine3D(Arrays.asList(p1, p2), false));
            }
        }
        
        result.addAll(leftGroup);
        result.addAll(rightGroup);
        return result;
    }
}
