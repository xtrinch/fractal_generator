/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

/**
 *
 * @author trinch
 */
public class PythagorasTree extends javax.swing.JPanel {

    String seq = "0"; // axiom
    Stack stack;
    int scale = 2;
    
    /**
     * Creates new form PythagorasTree
     */
    public PythagorasTree() {
        initComponents();
        buildString(8);
        stack = new Stack();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        System.out.println("painting");
        super.paintComponent(g); // Do the original draw
        if(this.getHeight() <= 432) {
            this.scale = 1;
        } else {
            System.out.println("scaling");    
            this.scale = 2;
            System.out.println(this.getHeight());
        }
        drawString(g, this.getHeight(), this.getWidth());
    }
    
    public void drawString(Graphics g, int height, int width) {
        /*0: draw a line segment ending in a leaf
        1: draw a line segment
        [: push position and angle, turn left 45 degrees
        ]: pop position and angle, turn right 45 degrees*/
        
        int angle = 0;
        int x = width/2, y = height; // start drawing at bottom middle pixel of the screen
        int newx = 0, newy = 0;
        
        for(int i=0; i<seq.length(); i++) {
            if (seq.charAt(i) == '0') {
                if (angle == 0) {
                    newx = x;
                    newy = y - scale;
                } else if (angle == -45) {
                    newx = x - (int)(scale/2 * Math.sqrt(2));
                    newy = y - (int)(scale/2 * Math.sqrt(2));
                } else if (angle == 45) {
                    newx = x + (int)(scale/2 * Math.sqrt(2));
                    newy = y - (int)(scale/2 * Math.sqrt(2)); 
                } else if (angle == 90) {
                    newx = x + scale;
                    newy = y;
                } else if (angle == -90) {
                    newx = x - scale;
                    newy = y;
                } else if (angle == 180) {
                    newx = x;
                    newy = y + scale;
                } else if (angle == 135) {
                    newx = x + (int)(scale/2 * Math.sqrt(2));
                    newy = y + (int)(scale/2 * Math.sqrt(2)); 
                } else if (angle == -135) {
                    newx = x - (int)(scale/2 * Math.sqrt(2));
                    newy = y + (int)(scale/2 * Math.sqrt(2)); 
                }
                
                g.drawLine(x, y, newx, newy);
            } else if (seq.charAt(i) == '1') {
                if (angle == 0) {
                    newx = x;
                    newy = y - scale*2;
                } else if (angle == -45) {
                    newx = x - (int)(scale * Math.sqrt(2));
                    newy = y - (int)(scale * Math.sqrt(2));
                } else if (angle == 45) {
                    newx = x + (int)(scale * Math.sqrt(2));
                    newy = y - (int)(scale * Math.sqrt(2)); 
                } else if (angle == 90) {
                    newx = x + scale*2;
                    newy = y;
                } else if (angle == -90) {
                    newx = x - scale*2;
                    newy = y;
                } else if (angle == 180) {
                    newx = x;
                    newy = y + scale*2;
                } else if (angle == 135) {
                    newx = x + (int)(scale * Math.sqrt(2));
                    newy = y + (int)(scale * Math.sqrt(2)); 
                } else if (angle == -135) {
                    newx = x - (int)(scale * Math.sqrt(2));
                    newy = y + (int)(scale * Math.sqrt(2)); 
                }
                
                g.drawLine(x, y, newx, newy);
            } else if(seq.charAt(i) == ']') {
                // ]: pop position and angle, turn right 45 degrees
                angle = (int) this.stack.pop();
                newy = (int) this.stack.pop();
                newx = (int) this.stack.pop();
                
                angle += 45; 
                if (angle > 180)
                    angle = -135;
            } else if(seq.charAt(i) == '[') {
                // [: push position and angle, turn left 45 degrees
                this.stack.push(x);
                this.stack.push(y);
                this.stack.push(angle);
                
                angle -= 45; 
                if (angle <= -180)
                    angle = 180;
            }
            
            x = newx;
            y = newy;
        }
    }
    
    public void rewrite() {
        String newSeq = new String();
        
        // for every character execute a rewrite rule
        for(int i=0; i<seq.length(); i++) {
            if (seq.charAt(i) == '0') {
                newSeq = newSeq.concat("1[0]0");
            } else if(seq.charAt(i) == '1') {
                newSeq = newSeq.concat("11");
            } else {
                newSeq = newSeq.concat(Character.toString(seq.charAt(i)));
            }
        }
        
        this.seq = newSeq;
    }
    
    public void buildString(int n) {
        for(int i=0; i<n; i++) {
            rewrite();
            System.out.println(this.seq);
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

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

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

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        repaint();
        System.out.println("resized");
    }//GEN-LAST:event_formComponentResized


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}
