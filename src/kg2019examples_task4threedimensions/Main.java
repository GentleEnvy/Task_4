/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kg2019examples_task4threedimensions;

import models.Gear;

import javax.swing.*;


/**
 @author Alexey */
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        frame.setSize(600, 600);
        
        var gear = new Gear(
            2, 1, 1, 0.5, 15
        );
        
        DrawPanel panel = new DrawPanel();
        
        panel.setModel(gear);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }
}
