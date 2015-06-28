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

/* default values */



class Coeff {
    double ac, bc, cc, dc, ec, fc; /* set of coefficients a-f */
    double pa1, pa2, pa3, pa4; /* external parameters */
    char r, g, b; /* color content of a pixel: RBG channels */
}

class Pixel {
    int counter = 0; /* number of times pixel has been incremented */
    float normal; /* normalized value at pixel */
    char r, g, b; /* color content of a pixel: RGB channels */
}

class Fractal { /* all components of a flame fractal */
    
    int xres = 1920, yres = 1080; /* x and y resolution of image */
    double xmin = -1.777, ymin = -1.0, xmax = 1.777, ymax = 1.0; /* axis bounds */
    double ranx, rany; /* numerical range of x/y axis */
    int R = -1,G = -1, B = -1;		/* fixed color channels */
    int n = 16;          /* number of equations */
    int sup = 1;		/* super sample value  */
    int samples = 20000;	/* number of flame samples */
    int iterations = 1000; /* number of iterations per sample */
    int invert = 0;		/* use inverse colors? 0 false, else true */
    int symmetry = 1;	/* use symmetrical rotation axis? set to greater than 1 */
    int seed = 1;		/* random seed */
    //int num_threads; /* number of threads to use in render */
    double gamma = 2;	/* gamma correction factor */
    Coeff coarray[]; /* array of coefficients */
    Pixel pixels[][]; /* image buffer */
    int transformations[];	/* transformations to use [choice.length: number of transformations available]*/

}

public class FractalFlame extends javax.swing.JPanel {

    Fractal fractal;
    
    /**
     * Creates new form FractalFlame
     */
    public FractalFlame() {
        initComponents();
        fractal = new Fractal();

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Do the original draw
        
        fractal.xres = this.getWidth();
        fractal.yres = this.getHeight();
        initialize_coefficients();
        buffer_init();
        
        
        double r, theta, x, y, c, f, b, e;
        double newx, newy, pa1, pa2, pa3, pa4;
        double P0, P1, prefix, t;
        int i, k, s;
        long tran = 0; // unsigned int
  
        for(int num=0; num<fractal.samples; num++) {
            newx = rand (fractal.xmin, fractal.xmax);
            newy = rand (fractal.ymin, fractal.ymax);
            
            for(int step = -20; step<fractal.iterations; step++) {

                k = fractal.transformations[(int)(tran % fractal.transformations.length)];
                tran++;
                
                i = (int)(Math.random() * (fractal.n));
                
                pa1 = fractal.coarray[i].pa1;
                pa2 = fractal.coarray[i].pa2;
                pa3 = fractal.coarray[i].pa3;
                pa4 = fractal.coarray[i].pa4;

                c = fractal.coarray[i].cc;
                f = fractal.coarray[i].fc;
                b = fractal.coarray[i].bc;
                e = fractal.coarray[i].ec;
                
                x =
                    fractal.coarray[i].ac * newx + fractal.coarray[i].bc * newy +
                    fractal.coarray[i].cc;
                y =
                    fractal.coarray[i].dc * newx + fractal.coarray[i].ec * newy +
                    fractal.coarray[i].fc;
                
                switch (k) {
                    case 0:		/* Linear */
                      newx = x;
                      newy = y;
                      break;
                    case 1:		/* Sinusoidal */
                      newx = Math.sin (x);
                      newy = Math.sin (y);
                      break;
                    case 2:		/* Spherical */
                      r = 1.0 / (x * x + y * y);
                      newx = r * x;
                      newy = r * y;
                      break;
                    case 3:		/* Swirl */
                      r = x * x + y * y;
                      newx = x * Math.sin (r) - y * Math.cos (r);
                      newy = x * Math.cos (r) + y * Math.sin (r);
                      break;
                    case 4:		/* Horseshoe */
                      r = 1.0 / Math.sqrt (x * x + y * y);
                      newx = r * (x - y) * (x + y);
                      newy = r * 2.0 * x * y;
                      break;
                    case 5:		/* Polar */
                      newx = Math.atan2 (y, x) / Math.PI;
                      newy = Math.sqrt (x * x + y * y) - 1.0;
                      break;
                    case 6:		/* Handkerchief */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = r * Math.sin (theta + r);
                      newy = r * Math.cos (theta - r);
                      break;
                    case 7:		/* Heart */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = r * Math.sin (theta * r);
                      newy = -r * Math.cos (theta * r);
                      break;
                    case 8:		/* Disk */
                      r = Math.sqrt (x * x + y * y) * Math.PI;
                      theta = Math.atan2 (y, x) / Math.PI;
                      newx = theta * Math.sin (r);
                      newy = theta * Math.cos (r);
                      break;
                    case 9:		/* Spiral */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = (1.0 / r) * (Math.cos (theta) + Math.sin (r));
                      newy = (1.0 / r) * (Math.sin (theta) - Math.cos (r));
                      break;
                    case 10:		/* Hyperbolic */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = Math.sin (theta) / r;
                      newy = r * Math.cos (theta);
                      break;
                    case 11:		/* Diamond */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = Math.sin (theta) * Math.cos (r);
                      newy = Math.cos (theta) * Math.sin (r);
                      break;
                    case 12:		/* Ex */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      P0 = Math.sin (theta + r);
                      P0 = P0 * P0 * P0;
                      P1 = Math.cos (theta - r);
                      P1 = P1 * P1 * P1;
                      newx = r * (P0 + P1);
                      newy = r * (P0 - P1);
                      break;
                    case 13:		/* Julia */
                      r = Math.sqrt (Math.sqrt (x * x + y * y));
                      theta = Math.atan2 (y, x) * .5;
                      if (Math.round(Math.random()) == 0)
                        theta += Math.PI;
                      newx = r * Math.cos (theta);
                      newy = r * Math.sin (theta);
                      break;
                    case 14:		/* Bent */
                      if (x >= 0.0 && y >= 0.0)
                        {
                          newx = x;
                          newy = y;
                        }
                      else if (x < 0.0 && y >= 0.0)
                        {
                          newx = 2.0 * x;
                          newy = y;
                        }
                      else if (x >= 0.0 && y < 0.0)
                        {
                          newx = x;
                          newy = y * .5;
                        }
                      else if (x < 0.0 && y < 0.0)
                        {
                          newx = 2.0 * x;
                          newy = y * .5;
                        }
                      break;
                    case 15:		/* Waves */
                      newx = x + pa1 * Math.sin (y / (pa2 * pa2));
                      newy = y + pa3 * Math.sin (x / (pa4 * pa4));
                      break;
                    case 16:		/* Fisheye */
                      r = 2.0 / (1. + Math.sqrt (x * x + y * y));
                      newx = r * y;
                      newy = r * x;
                      break;
                    case 17:		/* Popcorn */
                      newx = x + c * Math.sin (Math.tan (3.0 * y));
                      newy = y + f * Math.sin (Math.tan (3.0 * x));
                      break;
                    case 18:		/* Exponential */
                      newx = Math.exp (x - 1.0) * Math.cos (Math.PI * y);
                      newy = Math.exp (x - 1.0) * Math.sin (Math.PI * y);
                      break;
                    case 19:		/* Power */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx = Math.pow (r, Math.sin (theta)) * Math.cos (theta);
                      newy = Math.pow (r, Math.sin (theta)) * Math.sin (theta);
                      break;
                    case 20:		/* Cosine */
                      newx = Math.cos (Math.PI * x) * Math.cosh (y);
                      newy = -Math.sin (Math.PI * x) * Math.sinh (y);
                      break;
                    case 21:		/* Rings */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      prefix = (r + pa2 * pa2) % ((2.0 * pa2 * pa2) - (pa2 * pa2) + (r * (1.0 - pa2 * pa2)));
                      newx = prefix * Math.cos (theta);
                      newy = prefix * Math.sin (theta);
                      break;
                    case 22:		/* Fan */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      t = Math.PI * c * c;
                      if ((theta % t) > (t * .5))
                        {
                          newx = r * Math.cos (theta - (t * .5));
                          newy = r * Math.sin (theta - (t * .5));
                        }
                      else
                        {
                          newx = r * Math.cos (theta + (t * .5));
                          newy = r * Math.sin (theta + (t * .5));
                        }
                      break;
                    case 23:		/* Eyefish */
                      r = 2.0 / (1. + Math.sqrt (x * x + y * y));
                      newx = r * x;
                      newy = r * y;
                      break;
                    case 24:		/* Bubble */
                      r = 4 + x * x + y * y;
                      newx = (4.0 * x) / r;
                      newy = (4.0 * y) / r;
                      break;
                    case 25:		/* Cylinder */
                      newx = Math.sin (x);
                      newy = y;
                      break;
                    case 26:		/* Tangent */
                      newx = Math.sin (x) / Math.cos (y);
                      newy = Math.tan (y);
                      break;
                    case 27:		/* Cross */
                      r = Math.sqrt (1.0 / ((x * x - y * y) * (x * x - y * y)));
                      newx = x * r;
                      newy = y * r;
                      break;
                    case 28:		/* Collatz */
                      newx = .25 * (1.0 + 4.0 * x - (1.0 + 2.0 * x) * Math.cos (Math.PI * x));
                      newy = .25 * (1.0 + 4.0 * y - (1.0 + 2.0 * y) * Math.cos (Math.PI * y));
                      break;
                    case 29:		/* Mobius */
                      t = (pa3 * x + pa4) * (pa3 * x + pa4) + pa3 * y * pa3 * y;
                      newx =
                        ((pa1 * x + pa2) * (pa3 * x + pa4) + pa1 * pa3 * y * y) / t;
                      newy =
                        (pa1 * y * (pa3 * x + pa4) - pa3 * y * (pa1 * x + pa2)) / t;
                      break;
                    case 30:		/* Blob */
                      r = Math.sqrt (x * x + y * y);
                      theta = Math.atan2 (y, x);
                      newx =
                        r * (pa2 +
                             0.5 * (pa1 - pa2) * (Math.sin (pa3 * theta) +
                                                  1)) * Math.cos (theta);
                      newy =
                        r * (pa2 +
                             0.5 * (pa1 - pa2) * (Math.sin (pa3 * theta) +
                                                  1)) * Math.sin (theta);
                      break;
                    case 31:		/* Noise */
                      theta = rand(0, 1.);
                      r = rand(0, 1.);
                      newx = theta * x * Math.cos (2 * Math.PI * r);
                      newy = theta * y * Math.sin (2 * Math.PI * r);
                      break;
                    case 32:		/* Blur */
                      theta = rand(0, 1.);
                      r = rand(0, 1.);
                      newx = theta * Math.cos (2 * Math.PI * r);
                      newy = theta * Math.sin (2 * Math.PI * r);
                      break;
                    case 33:		/* Square */
                      newx = rand (0, 1.) - 0.5;
                      newy = rand (0, 1.) - 0.5;
                      break;
                    case 34:		/* Not Broken Waves */
                      newx = x + b * Math.sin (y / Math.pow (c, 2.0));
                      newy = y + e * Math.sin (x / Math.pow (f, 2.0));
                      break;
                    case 35:		/* something something */
                      newx = y;
                      newy = Math.sin (x);
                      break;
                    default:
                      break;
                    }

                    if (step > 0)
                        {
                          long x1 = 0, y1 = 0; // unsigned int
                          char red, green, blue;
                          //Pixel point = new Pixel();
                          double theta2, x_rot, y_rot;

                          theta2 = 0.0;

                          for (s = 0; s < fractal.symmetry; s++)
                            {

                              theta2 += ((2 * Math.PI) / (fractal.symmetry));
                              x_rot = newx * Math.cos (theta2) - newy * Math.sin (theta2);
                              y_rot = newx * Math.sin (theta2) + newy * Math.cos (theta2);

                              if (x_rot >= fractal.xmin && x_rot <= fractal.xmax
                                  && y_rot >= fractal.ymin && y_rot <= fractal.ymax)
                                {
                                  x1 =
                                    fractal.xres -
                                    (int) (((fractal.xmax -
                                             x_rot) / fractal.ranx) * fractal.xres);
                                  y1 =
                                    fractal.yres -
                                    (int) (((fractal.ymax -
                                             y_rot) / fractal.rany) * fractal.yres);

                                  if (x1 >= 0 && x1 < fractal.xres && y1 >= 0
                                      && y1 < fractal.yres)
                                    {


                                      if (fractal.pixels[(int)y1][(int)x1].counter == 0)
                                        {
                                          fractal.pixels[(int)y1][(int)x1].r = fractal.coarray[i].r;
                                          fractal.pixels[(int)y1][(int)x1].g = fractal.coarray[i].g;
                                          fractal.pixels[(int)y1][(int)x1].b = fractal.coarray[i].b;
                                        }
                                      else
                                        {
                                          red =
                                            (char) ((fractal.pixels[(int)y1][(int)x1].r +
                                                     fractal.coarray[i].r) / 2);
                                          
                                            if (red < 0)
                                                red = 0;

                                            if (red > 255)
                                                red = 255;
                                          
                                          fractal.pixels[(int)y1][(int)x1].r = red;
                                          green =
                                            (char) ((fractal.pixels[(int)y1][(int)x1].g +
                                                     fractal.coarray[i].g) / 2);
                                          
                                            if (green < 0)
                                                green = 0;

                                            if (green > 255)
                                                green = 255;
                                          fractal.pixels[(int)y1][(int)x1].g = green;
                                          blue =
                                            (char) ((fractal.pixels[(int)y1][(int)x1].b +
                                                     fractal.coarray[i].b) / 2);
                                            if (blue < 0)
                                                blue = 0;

                                            if (blue > 255)
                                                blue = 255;
                                          fractal.pixels[(int)y1][(int)x1].b = blue;
                                        }
                                      fractal.pixels[(int)y1][(int)x1].counter++;
                                      //System.out.println("saving pixel at "+ y1 + ", "+ x1 + " with value "+ (int)fractal.pixels[y1][x1].r + " " + (int)fractal.pixels[y1][x1].g + " "+(int)fractal.pixels[y1][x1].b);
                                
                                    }
                                }
                            
                            }
                          
                          
                        }

                
                
                
            }
        }

        gamma_log();
        render(g);

    }
    
    public void render(Graphics g) {
        System.out.println("Inside render!" + fractal.yres+ " " + fractal.xres);
        int invert = fractal.invert;
        for (int row = 0; row < fractal.yres; row++)
        {
          for (int col = 0; col < fractal.xres; col++)
            {

                if (fractal.pixels[row][col].r < 0)
                    fractal.pixels[row][col].r = 0;
                if (fractal.pixels[row][col].g < 0)
                    fractal.pixels[row][col].g = 0;
                if (fractal.pixels[row][col].b < 0)
                    fractal.pixels[row][col].b = 0;
                
                if (fractal.pixels[row][col].r > 255)
                    fractal.pixels[row][col].r = 255;
                if (fractal.pixels[row][col].g > 255)
                    fractal.pixels[row][col].g = 255;
                if (fractal.pixels[row][col].b > 255)
                    fractal.pixels[row][col].b = 255;
                
                
                //System.out.println("printing pixel at "+ row + ", "+ col + " with value "+ g.getColor().toString());
                g.setColor(new Color(fractal.pixels[row][col].r, fractal.pixels[row][col].g, fractal.pixels[row][col].b));
                g.drawOval(col, row, 1, 1);
                
            }
        }
    }
    
    public void gamma_log () {
        double max;
        double gamma = fractal.gamma;
        System.out.println("Gamma log resolution: " +fractal.yres + " "+ fractal.xres);
        max = 0.0;
        for (int row = 0; row < fractal.yres; row++)
          {
            for (int col = 0; col < fractal.xres; col++)
              {
                if (fractal.pixels[row][col].counter != 0)
                  {
                    fractal.pixels[row][col].normal =
                      (float) (Math.log10((double) fractal.pixels[row][col].counter));
                    if (fractal.pixels[row][col].normal > max)
                      max = fractal.pixels[row][col].normal;
                  }
              }
          }

        for (int row = 0; row < fractal.yres; row++)
          {
            for (int col = 0; col < fractal.xres; col++)
              {
                fractal.pixels[row][col].normal /= max;
                // applying gamma to each individual channel
                fractal.pixels[row][col].r =
                  (char) (((float) (fractal.pixels[row][col].r)) *  Math.pow (fractal.pixels[row][col].normal, (1.0 / gamma)));
                fractal.pixels[row][col].g =
                  (char) (((float) (fractal.pixels[row][col].g)) *  Math.pow (fractal.pixels[row][col].normal, (1.0 / gamma)));
                fractal.pixels[row][col].b =
                  (char) (((float) (fractal.pixels[row][col].b)) *  Math.pow (fractal.pixels[row][col].normal, (1.0 / gamma)));
              }
          }

    }
    
    public void initialize_coefficients() {

        int file_r, file_g, file_b;
        double fa, fb, fc, fd, fe, ff;
        
        fractal.coarray = new Coeff[fractal.n];
        
        for(int i=0; i<fractal.n; i++) {
            fractal.coarray[i] = new Coeff();
            
            fractal.coarray[i].ac = rand(-1.5, 1.5);
            fractal.coarray[i].bc = rand(-1.5, 1.5);
            fractal.coarray[i].cc = rand(-1.5, 1.5);
            fractal.coarray[i].dc = rand(-1.5, 1.5);
            fractal.coarray[i].ec = rand(-1.5, 1.5); 
            fractal.coarray[i].fc = rand(-1.5, 1.5);
            
            fractal.coarray[i].pa1 = rand(-2, 2);
            fractal.coarray[i].pa2 = rand(-2, 2);
            fractal.coarray[i].pa3 = rand(-2, 2);
            fractal.coarray[i].pa4 = rand(-2, 2);
            
            fractal.coarray[i].r = (char) (rand(64, 256));
            fractal.coarray[i].g = (char) (rand(64, 256));
            fractal.coarray[i].b = (char) (rand(64, 256));
            System.out.print((int)fractal.coarray[i].r);
            System.out.println();
        }    
        
        for (int i = 0; i < fractal.n; i++)
        {
            System.out.println(fractal.coarray[i].ac + ",  " + fractal.coarray[i].bc + ",  " + fractal.coarray[i].cc + ",  " + fractal.coarray[i].dc + ",  " + fractal.coarray[i].ec + ",  " + fractal.coarray[i].fc);
        }
        
    }
    
    public void buffer_init() {
        fractal.ranx = fractal.xmax - fractal.xmin;
        fractal.rany = fractal.ymax - fractal.ymin;
        
        fractal.xres *= fractal.sup;
        fractal.yres *= fractal.sup;
        
        fractal.pixels = new Pixel[fractal.yres][fractal.xres];
        for(int i=0; i< fractal.yres; i++) {
            for(int j=0; j<fractal.xres; j++) {
                fractal.pixels[i][j] = new Pixel();
            }
        }
        
        fractal.transformations = new int[1];
        fractal.transformations[0] = 5;
        
        fractal.seed = (int)rand(1, Integer.MAX_VALUE-1);
    }
    
    private double rand(double min, double max) {
        return min + Math.random() * (max - min);
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
