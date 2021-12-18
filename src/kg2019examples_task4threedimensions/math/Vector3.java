/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.math;

/**
 Класс, хранящий трёхмерный вектор / точку в трёхмерном пространстве.
 
 @author Alexey */
public class Vector3 {
    private final double[] values; /*Значения хранятся в виде массива из трёх элементов*/
    
    /**
     Создаёт экземпляр вектора на основе значений трёх составляющих
     
     @param x
     первая составляющая, описывающая X-координату
     @param y
     вторая составляющая, описывающая Y-координату
     @param z
     третья составляющая, описывающая Z-координату
     */
    public Vector3(double x, double y, double z) {
        values = new double[] {x, y, z};
    }
    
    /**
     X-составляющая вектора
     
     @return X-составляющая вектора
     */
    public double getX() {
        return values[0];
    }
    
    public static Vector3 sum(Vector3 v1, Vector3 v2) {
        return new Vector3(
            v1.getX() + v2.getX(),
            v1.getY() + v2.getY(),
            v1.getZ() + v2.getZ()
        );
    }
    
    public static Vector3 div(Vector3 v, float divider) {
        return new Vector3(v.getX() / divider, v.getY() / divider, v.getZ() / divider);
    }
    
    /**
     Y-составляющая вектора
     
     @return Y-составляющая вектора
     */
    public double getY() {
        return values[1];
    }
    
    /**
     Z-составляющая вектора
     
     @return Z-составляющая вектора
     */
    public double getZ() {
        return values[2];
    }
    
    /**
     Метод, возвращающий составляющую вектора по порядковому номеру
     
     @param idx
     порядковый номер
     
     @return значение составляющей вектора
     */
    public double at(int idx) {
        return values[idx];
    }
    
    private static final double EPSILON = 1e-10f;
    
    /**
     Метод, возвращающий длину вектора
     
     @return длина вектора
     */
    public float length() {
        double lenSqr = values[0] * values[0]
                           + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON) {
            return 0;
        }
        return (float) Math.sqrt(lenSqr);
    }
}
