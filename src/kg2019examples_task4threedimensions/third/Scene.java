/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions.third;

import kg2019examples_task4threedimensions.draw.IDrawer;
import kg2019examples_task4threedimensions.math.Vector3;
import models.Line3D;

import java.util.*;


/**
 Описывает трёхмерную со всеми объектами на ней
 
 @author Alexey */
public class Scene {
    private final List<IModel> models = new ArrayList<>();
    
    public List<IModel> getModelsList() {
        return models;
    }
    
    private final int backgroundColor;
    
    /**
     Создаём сцену с заданным фоном
     
     @param backgroundColorRGB
     цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }
    
    private boolean showAxes;
    
    public boolean isShowAxes() {
        return showAxes;
    }
    
    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }
    
    public void showAxes() {
        this.showAxes = true;
    }
    
    public void hideAxes() {
        this.showAxes = false;
    }
    
    private static final List<Line3D> axes = Arrays.asList(
        new Line3D(new Vector3(0, 0, 0), new Vector3(1, 0, 0)),
        new Line3D(new Vector3(0, 0, 0), new Vector3(0, 1, 0)),
        new Line3D(new Vector3(0, 0, 0), new Vector3(0, 0, 1))
    );
    
    /**
     Рисуем сцену со всеми моделями
     
     @param drawer
     то, с помощью чего будем рисовать
     @param cam
     камера для преобразования координат
     */
    public void drawScene(IDrawer drawer, ICamera cam) {
        List<PolyLine3D> lines = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/
        if (isShowAxes()) {
            allModels.add(axes);
        }
        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels) {
            for (IModel m : mc) {
                for (PolyLine3D pl : m.getLines()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 v : pl.getPoints()) {
                        points.add(cam.w2s(v));
                    }
                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    lines.add(new PolyLine3D(points, pl.isClosed()));
                }
            }
        }
        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.draw(lines);
    }
}
