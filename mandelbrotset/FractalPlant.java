/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author trinch
 */
public class FractalPlant extends javax.swing.JPanel {
    
    String seq = "X"; // axiom
    Stack stack;
    int scale = 6;
    BufferedImage pic;
    int width;
    int height;
    /**
     * Creates new form FractalPlant
     */
    public FractalPlant() {
        initComponents();
        buildString(6);
        stack = new Stack();
        
    }
   

    
    @Override
    public void paintComponent(Graphics g)
    {
        
        if (this.pic != null && width == getWidth() && height == getHeight()) {
            g.drawImage(pic, 0, 0, width, height, null);
            return;
        }
        width = getWidth();
        height = getHeight();
        pic = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);

        Graphics2D g2d = pic.createGraphics();
        //g2d.setBackground(Color.WHITE);
        //g2d.clearRect(0, 0, width, height);
        g2d.setColor(Color.GREEN);
        System.out.println("painting");
        super.paintComponent(g); // Do the original draw
        scale = (int) Math.round( (this.getHeight() / 150.0) );
        drawString(g2d, height, width);
        g.drawImage(pic, 0, 0, width, height, null);  
    }
    
    public void drawString(Graphics g, int height, int width) {
        /*0: draw a line segment ending in a leaf
        1: draw a line segment
        [: push position and angle, turn left 45 degrees
        ]: pop position and angle, turn right 45 degrees*/
        
        
        /* 
           F means "draw forward"
           − means "turn left 25°"
           + means "turn right 25°"
           X does not correspond to any drawing action
           [ corresponds to saving the current values for position and angle
           [ corresponds to restoring the current values for position and angle
        */
        int angle = 0;
        int x = 3*width/7, y = height; // start drawing at bottom middle pixel of the screen
        int newx = 0, newy = 0;
        
        for(int i=0; i<seq.length(); i++) {
            if (seq.charAt(i) == 'F') {
                
                newx = x + (int)(scale * Math.sin(((2*Math.PI)/360) * angle));// (int)(scale/2 * Math.sqrt(2));
                newy = y - (int)(scale * Math.cos(((2*Math.PI)/360) * angle));
                
                g.drawLine(x, y, newx, newy);
                
            } else if(seq.charAt(i) == '+') {
                
                angle += 25; 
                if (angle > 180)
                    angle = - (360 - angle);
                
            } else if(seq.charAt(i) == '-') {
                System.out.println("-25");
                angle -= 25; 
                if (angle <= -180)
                    angle = (360 + angle);
                
            } else if(seq.charAt(i) == ']') {
                
                // ]: pop position and angle
                angle = (int) this.stack.pop();
                newy = (int) this.stack.pop();
                newx = (int) this.stack.pop();

            } else if(seq.charAt(i) == '[') {
                
                // [: push position and angle
                this.stack.push(x);
                this.stack.push(y);
                this.stack.push(angle);
            }
            
            x = newx;
            y = newy;
        }
    }
    
    public void rewrite() {
        String newSeq = new String();
        
        // for every character execute a rewrite rule
        for(int i=0; i<seq.length(); i++) {
            if (seq.charAt(i) == 'X') {
                newSeq = newSeq.concat("F-[[X]+X]+F[+FX]-X");
            } else if(seq.charAt(i) == 'F') {
                newSeq = newSeq.concat("FF");
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

        saveImageButton = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel4 = new javax.swing.JLabel();
        saveImageButton1 = new javax.swing.JButton();

        saveImageButton.setText("Save image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("FRACTAL PLANT");

        saveImageButton1.setText("Save image");
        saveImageButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(256, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveImageButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                .addComponent(saveImageButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void saveImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageButtonActionPerformed

        // Show the file chooser and get the value returned.
        int returnVal = jFileChooser1.showOpenDialog(this);
        String image_name = new String();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            image_name = jFileChooser1.getSelectedFile().getPath();
        }

        try {
            File outputfile = new File(image_name + ".png");
            ImageIO.write(pic, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_saveImageButtonActionPerformed

    private void saveImageButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageButton1ActionPerformed

        // Show the file chooser and get the value returned.
        int returnVal = jFileChooser1.showOpenDialog(this);
        String image_name = new String();
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            image_name = jFileChooser1.getSelectedFile().getPath();
        }

        try {
            File outputfile = new File(image_name + ".png");
            ImageIO.write(pic, "png", outputfile);
        } catch (IOException e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_saveImageButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton saveImageButton;
    private javax.swing.JButton saveImageButton1;
    // End of variables declaration//GEN-END:variables
}
