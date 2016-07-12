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
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

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
    int n = 4;          /* number of equations */
    int sup = 1;		/* super sample value  */
    int samples = 10000;	/* number of flame samples */
    int iterations = 400; /* number of iterations per sample */
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
    int currVariation;
    BufferedImage pic;
    int width;
    int height;
    /**
     * Creates new form FractalFlame
     */
    public FractalFlame() {
        initComponents();
         
        width = getWidth();
        height = getHeight();   
        
        currVariation = 1;
        
        f0_1.setText("0");
        f0_2.setText("1");
        f0_3.setText("-0.8517534479826052");
        f0_4.setText("0.23804495861929253");
        f0_5.setText("1.4171662300346934");
        f0_6.setText("-0.7925003271873968");
        f1_1.setText("0.9543612354680828");
        f1_2.setText("1");
        f1_3.setText("1.095236586337871");
        f1_4.setText("0.7198013022733969");
        f1_5.setText("0.20883176427885686");
        f1_6.setText("1.0896293070775722");
        f2_1.setText("-1.2335901437430397");
        f2_2.setText("-1.4397194877322943");
        f2_3.setText("-0.9030202103386588");
        f2_4.setText("1");
        f2_5.setText("-0.39318861417528694");
        f2_6.setText("1.473195787865186");
        f3_1.setText("0.5");
        f3_2.setText("0.49519717460936175");
        f3_3.setText("-1.1714647509170342");
        f3_4.setText("-1");
        f3_5.setText("-0.98057301606437");
        f3_6.setText("1.2296082012444334");
        System.out.println("a");
        fractal = new Fractal();
      //  -0.41808795992893755,  0.3125598757670647,  -0.8517534479826052,  0.23804495861929253,  1.4171662300346934,  -0.7925003271873968
//0.9543612354680828,  1.048032479988401,  1.095236586337871,  0.7198013022733969,  0.20883176427885686,  1.0896293070775722
//-1.2335901437430397,  -1.4397194877322943,  -0.9030202103386588,  -0.6183062662064422,  -0.39318861417528694,  1.473195787865186
//0.17155274365131956,  0.49519717460936175,  -1.1714647509170342,  1.2449334575942075,  -0.98057301606437,  1.2296082012444334
        
        
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g); // Do the original draw
        
        if (this.pic != null && width == getWidth() && height == getHeight()) {
            g.drawImage(pic, 0, 0, width, height, null);
            return;
        }
        
        width = getWidth();
        height = getHeight();

        pic = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);
        
        Graphics2D g2d = pic.createGraphics();
        
        fractal.xres = this.getWidth();
        fractal.yres = this.getHeight();
        initialize_coefficients();
        buffer_init();
        
        
        double r, theta, x, y, c, f, b, e;
        double newx, newy, pa1, pa2, pa3, pa4;
        double P0, P1, prefix, t;
        int i, k, s;
        k=currVariation;
        long tran = 0; // unsigned int
  
        for(int num=0; num<fractal.samples; num++) {
            newx = rand (fractal.xmin, fractal.xmax);
            newy = rand (fractal.ymin, fractal.ymax);
            
            for(int step = -20; step<fractal.iterations; step++) {

                //k = fractal.transformations[(int)(tran % fractal.transformations.length)];
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
        render(g2d);
        g.drawImage(pic, 0, 0, width, height, null);

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
        fractal.coarray[0] = new Coeff();
        fractal.coarray[0].ac = Double.parseDouble(f0_1.getText());
        fractal.coarray[0].bc = Double.parseDouble(f0_2.getText());
        fractal.coarray[0].cc = Double.parseDouble(f0_3.getText());
        fractal.coarray[0].dc = Double.parseDouble(f0_4.getText());
        fractal.coarray[0].ec = Double.parseDouble(f0_5.getText());
        fractal.coarray[0].fc = Double.parseDouble(f0_6.getText());
        fractal.coarray[1] = new Coeff();
        fractal.coarray[1].ac = Double.parseDouble(f1_1.getText());
        fractal.coarray[1].bc = Double.parseDouble(f1_2.getText());
        fractal.coarray[1].cc = Double.parseDouble(f1_3.getText());
        fractal.coarray[1].dc = Double.parseDouble(f1_4.getText());
        fractal.coarray[1].ec = Double.parseDouble(f1_5.getText());
        fractal.coarray[1].fc = Double.parseDouble(f1_6.getText());
        fractal.coarray[2] = new Coeff();
        fractal.coarray[2].ac = Double.parseDouble(f2_1.getText());
        fractal.coarray[2].bc = Double.parseDouble(f2_2.getText());
        fractal.coarray[2].cc = Double.parseDouble(f2_3.getText());
        fractal.coarray[2].dc = Double.parseDouble(f2_4.getText());
        fractal.coarray[2].ec = Double.parseDouble(f2_5.getText());
        fractal.coarray[2].fc = Double.parseDouble(f2_6.getText());
        fractal.coarray[3] = new Coeff();
        fractal.coarray[3].ac = Double.parseDouble(f3_1.getText());
        fractal.coarray[3].bc = Double.parseDouble(f3_2.getText());
        fractal.coarray[3].cc = Double.parseDouble(f3_3.getText());
        fractal.coarray[3].dc = Double.parseDouble(f3_4.getText());
        fractal.coarray[3].ec = Double.parseDouble(f3_5.getText());
        fractal.coarray[3].fc = Double.parseDouble(f3_6.getText());
        
        for(int i=0; i<fractal.n; i++) {
            
            
            //fractal.coarray[i].ac = rand(-1.5, 1.5);
            /*fractal.coarray[i].ac = rand(-1.5, 1.5);
            fractal.coarray[i].bc = rand(-1.5, 1.5);
            fractal.coarray[i].cc = rand(-1.5, 1.5);
            fractal.coarray[i].dc = rand(-1.5, 1.5);
            fractal.coarray[i].ec = rand(-1.5, 1.5); 
            fractal.coarray[i].fc = rand(-1.5, 1.5);*/
            
            fractal.coarray[i].pa1 = rand(-2, 2);
            fractal.coarray[i].pa2 = rand(-2, 2);
            fractal.coarray[i].pa3 = rand(-2, 2);
            fractal.coarray[i].pa4 = rand(-2, 2);
            
            fractal.coarray[i].r = (char) (rand(64, 256));
            fractal.coarray[i].g = (char) (rand(64, 256));
            fractal.coarray[i].b = (char) (rand(64, 256));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jFileChooser1 = new javax.swing.JFileChooser();
        f0_2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        f0_3 = new javax.swing.JTextField();
        f0_4 = new javax.swing.JTextField();
        f0_5 = new javax.swing.JTextField();
        f0_6 = new javax.swing.JTextField();
        f0_1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        f1_3 = new javax.swing.JTextField();
        f1_2 = new javax.swing.JTextField();
        f1_6 = new javax.swing.JTextField();
        f1_1 = new javax.swing.JTextField();
        f1_4 = new javax.swing.JTextField();
        f1_5 = new javax.swing.JTextField();
        f2_5 = new javax.swing.JTextField();
        f2_4 = new javax.swing.JTextField();
        f2_1 = new javax.swing.JTextField();
        f2_6 = new javax.swing.JTextField();
        f2_2 = new javax.swing.JTextField();
        f2_3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        f3_5 = new javax.swing.JTextField();
        f3_4 = new javax.swing.JTextField();
        f3_1 = new javax.swing.JTextField();
        f3_6 = new javax.swing.JTextField();
        f3_2 = new javax.swing.JTextField();
        f3_3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboBoxVar = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        redrawImageButton = new javax.swing.JButton();
        saveImageButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();

        f0_2.setBorder(null);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("IFS system function coefficients:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("a         b          c          d          e          f");

        f0_3.setBorder(null);

        f0_4.setBorder(null);

        f0_5.setBorder(null);

        f0_6.setBorder(null);

        f0_1.setBorder(null);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("f1");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("f2");

        f1_3.setBorder(null);

        f1_2.setBorder(null);

        f1_6.setBorder(null);

        f1_1.setBorder(null);

        f1_4.setBorder(null);

        f1_5.setBorder(null);

        f2_5.setBorder(null);

        f2_4.setBorder(null);

        f2_1.setBorder(null);

        f2_6.setBorder(null);

        f2_2.setBorder(null);

        f2_3.setBorder(null);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("f3");

        f3_5.setBorder(null);

        f3_4.setBorder(null);

        f3_1.setBorder(null);

        f3_6.setBorder(null);

        f3_2.setBorder(null);

        f3_3.setBorder(null);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("f4");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Variation used:");

        comboBoxVar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Linear", "Sinusoidal", "Spherical", "Swirl", "Horseshoe", "Polar", "Handkerchief", "Heart", "Disk", "Spiral", "Hyperbolic", "Diamond", "Ex", "Julia", "Bent", "Waves", "Fisheye", "Popcorn", "Exponential", "Power", "Cosine", "Rings", "Fan", "Eyefish", "Bubble", "Cylinder", "Tangent", "Cross", "Colatz", "Mobius", "Blob", "Noise", "Blur", "Square", "Not broken waves" }));
        comboBoxVar.setSelectedIndex(1);
        comboBoxVar.setBorder(null);
        comboBoxVar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxVarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("FRACTAL FLAMES");

        redrawImageButton.setText("Redraw image");
        redrawImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redrawImageButtonActionPerformed(evt);
            }
        });

        saveImageButton1.setText("Save image");
        saveImageButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("f(x, y) =");

        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("<html> a b <br/> c d </html>");

        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("<html> x <br/> y </html>");

        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("+");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setText("[");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("]");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setText("[");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("]");

        jLabel17.setForeground(new java.awt.Color(204, 204, 204));
        jLabel17.setText("<html> e <br/> f </html>");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(204, 204, 204));
        jLabel18.setText("]");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 204, 204));
        jLabel20.setText("[");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(saveImageButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(redrawImageButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                        .addGap(5, 5, 5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(651, 651, 651)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(f1_1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(f1_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(f1_3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(f1_4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(f1_5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(f1_6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(f0_1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(f0_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(f0_3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(f0_4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(f0_5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(f0_6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(f2_1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f2_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f2_3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f2_4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f2_5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f2_6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(f3_1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f3_2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f3_3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f3_4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f3_5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(f3_6, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(comboBoxVar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(5, 5, 5))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(f0_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f0_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f0_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f0_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f0_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f0_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(f1_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f1_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(f2_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f2_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(f3_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3_6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(f3_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(12, 12, 12)
                .addComponent(comboBoxVar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(redrawImageButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveImageButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void comboBoxVarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxVarActionPerformed
        currVariation = comboBoxVar.getSelectedIndex();
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_comboBoxVarActionPerformed

    private void redrawImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redrawImageButtonActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_redrawImageButtonActionPerformed

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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox comboBoxVar;
    private javax.swing.JTextField f0_1;
    private javax.swing.JTextField f0_2;
    private javax.swing.JTextField f0_3;
    private javax.swing.JTextField f0_4;
    private javax.swing.JTextField f0_5;
    private javax.swing.JTextField f0_6;
    private javax.swing.JTextField f1_1;
    private javax.swing.JTextField f1_2;
    private javax.swing.JTextField f1_3;
    private javax.swing.JTextField f1_4;
    private javax.swing.JTextField f1_5;
    private javax.swing.JTextField f1_6;
    private javax.swing.JTextField f2_1;
    private javax.swing.JTextField f2_2;
    private javax.swing.JTextField f2_3;
    private javax.swing.JTextField f2_4;
    private javax.swing.JTextField f2_5;
    private javax.swing.JTextField f2_6;
    private javax.swing.JTextField f3_1;
    private javax.swing.JTextField f3_2;
    private javax.swing.JTextField f3_3;
    private javax.swing.JTextField f3_4;
    private javax.swing.JTextField f3_5;
    private javax.swing.JTextField f3_6;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton redrawImageButton;
    private javax.swing.JButton saveImageButton1;
    // End of variables declaration//GEN-END:variables

}
