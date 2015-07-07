/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;


/**
 *
 * @author trinch
 */

class Terrain {

    int size;
    int max;
    // between 0-1, 0: smooth, 1: rough
    double roughness;
    double[][] map;
    double waterValue;
    
    public Terrain(int detail, double roughness) {
        size = (int) (Math.pow(2, detail) + 1);
        max = this.size - 1;
        map = new double[size][size];
        this.roughness = roughness;
        waterValue = this.size * 0.3;
        calculate();
    }
    
    public void calculate() {
        seedCorners();
        divide();
    }
    
    public void printTerrain() {
        for(int x=0; x<=max; x++) {
            for(int y=0; y<=max; y++) {
                System.out.print(map[x][y]+ " ");
            }
            System.out.println();
        }
    }
    
    // the corners half way up the cube
    public void seedCorners() {
        map[0][0] = max / 2;
        map[max][0] = max / 2;
        map[0][max] = max / 2;
        map[max][max] = max / 2;
    }
    
    // split the map
    public void divide() {
        int half = size / 2;
        double scale = roughness * (double)size;
        if(half < 1)
            return;
        
        for (int y = half; y < max; y += size) {
            for (int x = half; x < max; x += size) {
                square(y, x, half, (float) (Math.random() * scale * 2 - scale));
            }
        }
        
        for (int y = 0; y <= max; y += half) {
            for (int x = (y + half) % size; x <= max; x += size) {
                diamond(y, x, half, (float) (Math.random() * scale * 2 - scale));
            } 
        }

        size = size / 2;
        divide ();
    }
    
    public void square(int y, int x, int half, double offset) {
        
        double x1 = max/2, x2 = max/2, x3 = max/2, x4 = max/2;
        if ((y - half) >= 0 && (x - half) >= 0)
            x1 = map[y - half][x - half]; // top
        if ((y + half) <= max && (x + half) <= max)
            x2 = map[y + half][x + half]; // right
        if ((y + half) <= max && (x - half) >= 0)
            x3 = map[y + half][x - half]; // bottom
        if ((y - half) >= 0 && (x + half) <= max)
            x4 = map[y - half][x + half]; // left
        
        double avg = x1 + x2 + x3 + x4;
        avg = avg / 4;
        
        // average of square corners + random offset
        map[y][x] = avg + offset;        
    }
    
    public void diamond(int y, int x, int half, float offset) {
        double x1 = max/2, x2 = max/2, x3 = max/2, x4 = max/2;
        
        if ((x - half) >= 0)
            x1 = map[y][x - half]; // top
        if ((y + half) <= max)
            x2 = map[y + half][x]; // right
        if ((x + half) <= max)
            x3 = map[y][x + half]; // bottom
        if ((y - half) >= 0)
            x4 = map[y - half][x]; // left
        
        double avg = x1 + x2 + x3 + x4;
        avg = avg / 4;
        
        // average of diamond corners + random offset
        map[y][x] = avg + offset;
    }
    
    
    
}

public class FractalTerrain extends javax.swing.JPanel {
    
    Terrain mountains;
    BufferedImage pic;
    int width;
    int height;
    boolean redraw;
    /**
     * Creates new form FractalTerrain
     */
    public FractalTerrain() {
        initComponents();
        this.redraw = false;
        mountains = new Terrain(9, 0.7);
        System.out.println(mountains.max);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (pic != null && width == getWidth() && height == getHeight() && redraw == false) {
            g.drawImage(pic, 0, 0, width, height, null);
            return;
        }
        if(redraw == true) {
            mountains = new Terrain(9, 0.7);
        }
        width = getWidth();
        height = getHeight();
        System.out.println("width: "+width+" height: "+height + " redraw:"+redraw);
        
        pic = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR );
        
        // nested loops that draw rectangles from the 'back' (y=0) to the 'front' (y=size) of map
        for(int y = 1; y < mountains.max; y++) {
            for(int x = 1; x < mountains.max; x++) {
                double value = mountains.map[x][y];
                double top[] = project (x, y, value, width, height);
                double bottom[] = project (x+1, y, 0, width, height);
                double water[] = project (x, y, mountains.waterValue, width, height);

                Color style = brightness (x, y, mountains.map[x + 1][y] - value);
         
                for(int k = (int) top[0]; k < bottom[0]; k++) {
                    for (int j = (int) top[1]; j < bottom[1]; j++) {
                        if ( j < height && k < width && j > 0 && k > 0) {
                            pic.setRGB(k, j, style.getRGB());
                            if (x == mountains.max-1) {
                                pic.setRGB(k, j, new Color(30, 30, 30, 100).getRGB());
                            }
                            if (y == mountains.max-1) {
                                pic.setRGB(k, j, new Color(18, 18, 18, 100).getRGB());
                            }
                        }
                        

                    }
                }
                
                for(int k = (int) water[0]; k < bottom[0]; k++) {
                    for (int j = (int) water[1]; j < bottom[1]; j++) {
                        if ( j < height && k < width && j > 0 && k > 0 && y != mountains.max-1 && x != mountains.max-1) {
                            pic.setRGB(k, j, new Color(50, 150, 200, (int)(0.15*255)).getRGB());
                        }
                        
                        if ((x == mountains.max-1) && value < mountains.waterValue && j < height && k < width && j > 0 && k > 0)
                            pic.setRGB(k, j, new Color(30, 30, 30, (int)(0.15*255)).getRGB());
                        if (( y == mountains.max-1) && value < mountains.waterValue && j < height && k < width && j > 0 && k > 0)
                            pic.setRGB(k, j, new Color(18, 18, 18, (int)(0.15*255)).getRGB());
                        
                    }
                }
            }
        }
        System.out.println("Trying to draw image");
        g.drawImage(pic, 0, 0, width, height, null);
        this.redraw = false;
    }
    
    private double[] project(int flatx, int flaty, double flatz, int width, int height) {
        double point[] = iso(flatx, flaty); 
        double x0 = (int) (width * 0.5);
        double y0 = (int) (height * 0.2);
        double z = (mountains.max+1) * 0.5 - flatz + point[1] * 0.75;
        double x = (point[0] - ((mountains.max+1) * 0.5)) * 6;
        double y = ((mountains.max+1) - point[1]) * 0.005 + 1;
        
        return new double[] {
            x0 + x / y,
            y0 + z / y
        };
    }
    
    private Color brightness(int x, int y, double slope) {
        if (x == mountains.max || y == mountains.max) {
            return new Color(0,0,0);
        }
        
        int b = (int)Math.floor(slope * 50) + 128;
        if (b > 255)
            b = 255;
        if (b < 0)
            b = 0;
        return new Color(b,b,b,1);
    }

    private double[] iso(int x, int y) {
        return new double[] {
            0.5 * (mountains.max + 1 + x - y),
            0.5 * (y + x)
        };
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        redrawImageButton = new javax.swing.JButton();
        saveImageButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        redrawImageButton.setText("Redraw image");
        redrawImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redrawImageButtonActionPerformed(evt);
            }
        });

        saveImageButton.setText("Save image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("FRACTAL TERRAIN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(redrawImageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveImageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(230, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(redrawImageButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveImageButton)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void redrawImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redrawImageButtonActionPerformed
        this.pic = null;
        this.redraw = true;
        this.repaint();
    }//GEN-LAST:event_redrawImageButtonActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton redrawImageButton;
    private javax.swing.JButton saveImageButton;
    // End of variables declaration//GEN-END:variables


}
