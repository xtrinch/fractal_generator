/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import java.awt.Graphics;

/**
 *
 * @author trinch
 */
public class KochSnowflake extends javax.swing.JPanel {

    /**
     * Creates new form KochSnowflake
     */
    public KochSnowflake(int level) {
        initComponents();
        this.level = level;
    }
    
    int height;
    int width;
    int level;
    
    @Override
    public void paint(Graphics g){
 
        height = this.getHeight() - this.getHeight()/4;
        width = this.getWidth();
        
        int xStart = width/2 - height/2;
        drawSnow(g, level, xStart + 20,             height - 20,   xStart + height - 20, height - 20);
        drawSnow(g, level, xStart + height - 20,    height - 20,   xStart + height/2,    20);
        drawSnow(g, level, xStart + height/2,       20,            xStart + 20,          height - 20);

    }
 
    private void drawSnow (Graphics g, int lev, int x1, int y1, int x5, int y5){
          int deltaX, deltaY, x2, y2, x3, y3, x4, y4;
 
          if (lev == 0){
 
              g.drawLine(x1, y1, x5, y5);
          }
          else{
                deltaX = x5 - x1;
                deltaY = y5 - y1;
 
                x2 = x1 + deltaX / 3;
                y2 = y1 + deltaY / 3;
 
                x3 = (int) (0.5 * (x1+x5) + Math.sqrt(3) * (y1-y5)/6);
                y3 = (int) (0.5 * (y1+y5) + Math.sqrt(3) * (x5-x1)/6);
 
                x4 = x1 + 2 * deltaX /3;
                y4 = y1 + 2 * deltaY /3;
 
                drawSnow (g,lev-1, x1, y1, x2, y2);
                drawSnow (g,lev-1, x2, y2, x3, y3);
                drawSnow (g,lev-1, x3, y3, x4, y4);
                drawSnow (g,lev-1, x4, y4, x5, y5);
            }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}