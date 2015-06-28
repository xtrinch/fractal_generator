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
public class JuliaSet extends javax.swing.JPanel {
    int MaxIterations = 300;
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
    double z0const_re = -0.7;
    double z0const_im = 0.27015;
    /**
     * Creates new form juliasetpanel
     */
    public JuliaSet() {
        initComponents();
        this.setBackground(Color.BLACK);
        zoom = 1;
        
        // for all corner pixels, we define what complex number they represent (later we interpolate the in-between pixels when calculating the set)
        minRe = -2.0;
        minIm = -1.3;
        maxRe = 2.0;
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

        System.out.println(zoom);
        
        height = this.getHeight();
        width = 3 * (height/2);//-100;s
        // some number is for maintaining aspect ratio, width of the blank space on the left/right
        int someNumber = (this.getWidth() - width) / 2;
        
        System.out.println("zomy: "+zoom+ " double: "+(double)zoom+"minRe:"+minRe+" maxRe:"+maxRe+" minim:"+minIm);
        
        // calculating automatically the equivalent maxIm according to the image dimension and the real part - maintaining aspect ratio
        maxIm = (minIm + (maxRe-minRe) * height / width);

        // pre-calculating factor of multiplication for one pixel
        re_factor = (maxRe-minRe)/(width-1);
        im_factor = (maxIm-minIm)/(height-1);
        
        super.paintComponent(g); // Do the original draw
        
        for(int y=0; y<height; y++)
        {
            // y goes down
            double c_im = minIm + (y)*im_factor;
            for(int x=someNumber + 0; x<someNumber + width; x++)
            {
                double c_re = minRe + (x-someNumber)*re_factor;

                
                // Calculate whether c belongs to the Mandelbrot set or
                // not and draw a pixel at coordinates (x,y) accordingly
                int b;
                if ( (b = belongsToSet(c_re, c_im)) < 0) {
                    g.setColor(Color.BLACK);
                    g.drawOval(x, y, 1, 1);
                } else {
                    /* belongsToSet function returns a number between 0 and max number of iterations,
                        that number tells us at which number the inner loop has ended, or in other words,
                        how fast does the complex number at a certain pixel tend to infinity
                        (the closer the complex number is to the border of the set, the longer it will
                        take for it to tend to infinity)
                    */
                    g.setColor(getShadingColor(b));                  
                    g.drawOval(x, y, 1, 1);
                }
            }
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
            z_im = 2*z_im*z_re + z0const_im;
            z_re = z_re*z_re - z_im_mid + z0const_re;
        }
        
        return -1;
    }

    private double imaginaryAbs(double c_re, double c_im) {
        return c_re * c_re + c_im * c_im;
    }

    private Color getShadingColor(int b) {
        // hue, saturation, brightness
        return Color.getHSBColor( (float)(b%256)/100, 1,  1 );
    }

}



