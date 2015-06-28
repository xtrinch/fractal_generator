/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author trinch
 */
public class BarnsleysFern extends javax.swing.JPanel {

    /**
     * Creates new form SinusoidalFlame
     */
    public BarnsleysFern() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Do the original draw
        
        int height = this.getHeight();
        // aspect ratio
        int width = height;
        int blankSpace = (this.getWidth()-width) / 2;
        //System.out.println("Height: "+height+", width: "+width+", blank space: "+blankSpace);

        
        /* 
            Pseudo code:
            (x, y)= a random point in the bi-unit square
            iterate { 
                i = a random integer from 0 to n − 1 inclusive
                (x, y) = Fi(x, y)
                plot(x, y) except during the first 20 iterations
            }
        */
        
        int i=0, numIterations=2000000;
        // map x and y to [-1, 1]
        double x = 0, y = 0, prevx = -1;

        while (i < numIterations) {
            // choosing which function to use
            int randNum = (int)(Math.random() * (100) + 1), fun;

            if (randNum <= 85)
                fun = 1;
            else if(randNum > 85 && randNum <= 92)
                fun = 3;
            else if(randNum > 92 && randNum <= 99)
                fun = 2;
            else
                fun = 0;
            // recalculate x and y coordinates
            prevx = x;
            x = x(x, y, fun);
            y = y(y, prevx,  fun);
            //System.out.println(x);
            //System.out.println(y);
            
            if (i > 19)
                plot (x, y, height, width, blankSpace, g);
            
            i++;
            
            //.out.println("Going through "+i+"th iteration...");
        }
        
    } 
    
    private double x(double x, double y, int fun) {
        switch(fun) {
            case 0:
                return 0;
            case 1:
                return (0.85 * x) + (0.04 * y);
            case 2:
                return (0.2 * x) - (0.26 * y);
            case 3:
                return (-0.15 * x) + (0.28 * y);
        }
        
        return -2;
    }

    private double y(double y, double x, int fun) {
        switch(fun) {
            case 0:
                return 0.16*y;
            case 1:
                return (-0.04 * x) + (0.85 * y) + 1.6;
            case 2:
                return (0.23 * x) + (0.22 * y) + 1.6;
            case 3:
                return (0.26 * x) + (0.24 * y) + 0.44;
                
        }
        
        return -2;
    }

    private void plot(double x, double y, int height, int width, int blankSpace, Graphics g) {
        //System.out.println(x);
        //System.out.println(y);
        // get only positive values
        x = x+2.18201;
        
        // multiply to get pixels, add or subtract to center it
        x = x*(height/4.8378);
        y = y*(width/9.9983);
        
        //System.out.println("Drawing x,y: "+(int)(x+blankSpace)+ ","+ (int)y);
        g.setColor(Color.BLACK);
        g.drawOval((int)(x+blankSpace), (int)(height-y), 1, 1);
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
