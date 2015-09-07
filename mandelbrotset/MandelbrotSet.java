/*
    done by using the tutorial on http://warp.povusers.org/Mandelbrot/
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
public class MandelbrotSet extends javax.swing.JPanel {
    int MaxIterations = 30;
    int zoom;
    double minRe;
    double minIm;
    double maxRe;
    double maxIm;
    double re_factor;
    double im_factor;
    int height;
    int width;
    double zoomFactor = 4/5.;
    double widthToHeightRatio = 3/2.;
    BufferedImage pic;
    /**
     * Creates new form drawPanel
     */
    public MandelbrotSet() {
        initComponents();
        this.setBackground(Color.BLACK);
        zoom = 1;
        
        // for all corner pixels, we define what complex number they represent (later we interpolate the in-between pixels when calculating the set)
        minRe = -2.0;
        minIm = -1.0;
        maxRe = 1.0;
        this.setBackground(Color.BLACK);
    }

    public void zoomInc() {
        int heightZoom = (int)(zoomFactor * height);
        int widthZoom = (int)(widthToHeightRatio * heightZoom);
        
        // pixel coordinates of borders of the new zoomed image
        int xZoom = width/2 - (widthZoom/2);
        int yZoom = height/2 - (heightZoom/2);
        
        double zoom_re = minRe + (xZoom)*re_factor;
        maxRe = maxRe - Math.abs(zoom_re - minRe);
        minRe = zoom_re;
        minIm = minIm + yZoom*im_factor;
    }
    
    public void zoomDec() {
               
        int heightZoom = (int)(1/zoomFactor * height);
        int widthZoom = (int)(widthToHeightRatio * heightZoom);
        
        int xZoom = width/2 - (widthZoom/2);
        int yZoom = height/2 - (heightZoom/2);
        System.out.println("xzoom: "+xZoom);
        
        double zoom_re = minRe + (xZoom)*re_factor;
        maxRe = maxRe + Math.abs(zoom_re - minRe);
        minRe = zoom_re;
        minIm = minIm + yZoom*im_factor;
    }
    
    public void left() {
        int xZoom = -100;
        System.out.println("xzoom: "+xZoom);
        
        double zoom_re = minRe + (xZoom)*re_factor;
        maxRe = maxRe + (xZoom)*re_factor;
        minRe = zoom_re;
    }
    
    public void right() {
        int xZoom = 100;
        System.out.println("xzoom: "+xZoom);
        
        double zoom_re = minRe + (xZoom)*re_factor;
        maxRe = maxRe + (xZoom)*re_factor;
        minRe = zoom_re;
    }
    
    public void up() {
        int yZoom = -100;
        minIm = minIm + yZoom*im_factor;
    }
    
    public void down() {
        int yZoom = 100;
        minIm = minIm + yZoom*im_factor;
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Do the original draw
        
        height = this.getHeight();
        width = 3 * (height/2);//-100;s
        
        if (this.pic != null) {
            g.drawImage(pic, (getWidth()-width)/2, 0, width, height, null);
            return;
        }
        pic = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR );
        
        System.out.println(zoom);
        
        // some number is for maintaining aspect ratio, width of the blank space on the left/right
        int someNumber = (this.getWidth() - width) / 2;
        
        System.out.println("zomy: "+zoom+ " double: "+(double)zoom+"minRe:"+minRe+" maxRe:"+maxRe+" minim:"+minIm);
        
        // calculating automatically the equivalent maxIm according to the image dimension and the real part - maintaining aspect ratio
        maxIm = (minIm + (maxRe-minRe) * height / width);

        // pre-calculating factor of multiplication for one pixel
        re_factor = (maxRe-minRe)/(width-1);
        im_factor = (maxIm-minIm)/(height-1);
        
        
        
        for(int y=0; y<height; y++)
        {
            // y goes down
            double c_im = minIm + (y)*im_factor;
            for(int x=0; x<width; x++)
            {
                double c_re = minRe + x*re_factor;

                
                // Calculate whether c belongs to the Mandelbrot set or
                // not and draw a pixel at coordinates (x,y) accordingly
                int b;
                if ( (b = belongsToSet(c_re, c_im)) < 0) {
                    pic.setRGB(x,y, Color.BLACK.getRGB());
                } else {
                    /* belongsToSet function returns a number between 0 and max number of iterations,
                        that number tells us at which number the inner loop has ended, or in other words,
                        how fast does the complex number at a certain pixel tend to infinity
                        (the closer the complex number is to the border of the set, the longer it will
                        take for it to tend to infinity)
                    */
                    pic.setRGB(x,y, getShadingColor(b).getRGB());
                }
            }
        }
        g.drawImage(pic, someNumber, 0, width, height, null);  
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        saveImageButton = new javax.swing.JButton();
        zoomInButton = new javax.swing.JButton();
        zoomOutButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        saveImageButton.setText("Save image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });

        zoomInButton.setText("Zoom in");
        zoomInButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInButtonActionPerformed(evt);
            }
        });

        zoomOutButton.setText("Zoom out");
        zoomOutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("MANDELBROT SET");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("f(z) = z^2 + c; z0 = 0");

        jButton1.setText("Left");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Right");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Down");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Up");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(0, 225, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveImageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zoomInButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(zoomOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zoomOutButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(zoomInButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveImageButton)
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

    private void zoomInButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInButtonActionPerformed
        this.pic=null;
        zoomInc();
        repaint();
    }//GEN-LAST:event_zoomInButtonActionPerformed

    private void zoomOutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutButtonActionPerformed
        this.pic=null;
        zoomDec();
        repaint();
    }//GEN-LAST:event_zoomOutButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.pic=null;
        left();
        repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.pic=null;
        up();
        repaint();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.pic=null;
        down();
        repaint();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.pic=null;
        right();
        repaint();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JButton saveImageButton;
    private javax.swing.JButton zoomInButton;
    private javax.swing.JButton zoomOutButton;
    // End of variables declaration//GEN-END:variables

    private int belongsToSet(double c_re, double c_im) {
        
        double z_re = c_re, z_im = c_im;
        
        boolean isInside = true;
        for(int n=0; n<MaxIterations; n++) {
            // if the absolute value of the imaginary number goes over two, it tends toward the attractor of the mandelbrot set, infinity (not inside the set)
            if(imaginaryAbs(z_re, z_im) > 4) {
                return n;
            }
            /* Z = Z2 + c */
            double z_im_mid = z_im * z_im;
            z_im = 2*z_im*z_re + c_im;
            z_re = z_re*z_re - z_im_mid + c_re;
        }
        
        return -1;
    }

    private double imaginaryAbs(double c_re, double c_im) {
        return c_re * c_re + c_im * c_im;
    }

    private Color getShadingColor(int b) {
        // hue, saturation, brightness
        return Color.getHSBColor( 30/(float)100, 100/(float)100,  (float)b / (float)MaxIterations );
    }

}
