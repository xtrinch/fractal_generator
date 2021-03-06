/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import helperFunctions.ComplexNumber;
import static helperFunctions.ComplexNumber.parseComplex;
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
public class NewtonFractal extends javax.swing.JPanel {

    ComplexNumber [] polynomialCoeffs;
    int polynomialCoeffCount;
    BufferedImage pic;
    float hue = 0f;
    float saturation = 1f;
    float brightness = 0.95f;
    int height;
    private static final int MAX_ITERATIONS = 32;
    private static final double EPSILON = 0.0000001;
    
    /**
     * Creates new form NewtonFractal
     */
    public NewtonFractal() {
        initComponents();
        polynomialCoeffCount = 6;
        x0FormattedField.setText("-1");
        x1FormattedField.setText("0");
        x2FormattedField.setText("0");
        x3FormattedField.setText("1");
        x4FormattedField.setText("5");
        x5FormattedField.setText("5");
        polynomialCoeffs = new ComplexNumber[polynomialCoeffCount];
        height = getHeight();
    }

    public ComplexNumber fx(ComplexNumber x) {
        ComplexNumber f = new ComplexNumber(0,0);
        for(int z=polynomialCoeffCount-1;z>=0;z--) {
          if (polynomialCoeffs[z].getRe() != 0.0 || polynomialCoeffs[z].getIm() != 0.0) {
              ComplexNumber fs;
              if (z!=0)
                  fs = ComplexNumber.pow(x,z);
              else
                  fs = new ComplexNumber(1,0);
              fs.multiply(polynomialCoeffs[z]);

              f.add(fs);
          }
        }
        
        return f;
    }
    
    public ComplexNumber dfx(ComplexNumber x) {
        ComplexNumber df = new ComplexNumber(0,0);
        for(int z=polynomialCoeffCount-2;z>=0;z--) {
          if (polynomialCoeffs[z+1].getRe() != 0.0 || polynomialCoeffs[z+1].getIm() != 0.0) {
              ComplexNumber fs;
              if (z!=0)
                  fs = ComplexNumber.pow(x,z);
              else
                  fs = new ComplexNumber(1,0);
              fs.multiply(polynomialCoeffs[z+1]);
              fs.multiply(new ComplexNumber(z+1,0));

              df.add(fs);
          }
        }   
        
        return df;
    }
    
    public boolean isRoot(ComplexNumber a) {
        return Math.abs(fx(a).mod()) < EPSILON;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        
        super.paintComponent(g); // Do the original draw   
        
        height = getHeight();
        if (this.pic != null) {
            g.drawImage(pic, (getWidth()-height)/2, 0, height, height, null);
            return;
        }
        polynomialCoeffs[0] = parseComplex(x0FormattedField.getText());
        polynomialCoeffs[1] = parseComplex(x1FormattedField.getText());
        polynomialCoeffs[2] = parseComplex(x2FormattedField.getText());
        polynomialCoeffs[3] = parseComplex(x3FormattedField.getText());
        polynomialCoeffs[4] = parseComplex(x4FormattedField.getText());
        polynomialCoeffs[5] = parseComplex(x5FormattedField.getText());
        pic = new BufferedImage(height, height, BufferedImage.TYPE_3BYTE_BGR );
        
        hue = 0f;
        
        for(int X=0; X<height; X++) {
          for(int Y=0; Y<height; Y++) {
            // Scaling to [-2.5,2.5]
            ComplexNumber a = new ComplexNumber((X-height/2.0)/(height/5.0), (Y-height/2.0)/(height/5.0));
            int iteration;
            for(iteration = 0; iteration<MAX_ITERATIONS; iteration++) {
   
              // Calculating f(x)
              ComplexNumber f = fx(a);
              
              // Calculating f'(x)
              ComplexNumber df = dfx(a);

              // a-f(a)/f'(a)
              a.subtract(ComplexNumber.divide(f,df));
              if(isRoot(a)) {
                break;
              }
            }
            // Mapping no. of iterations to hue
            hue = (float)iteration/MAX_ITERATIONS;
            pic.setRGB(X,Y, Color.getHSBColor(hue, saturation, brightness).getRGB());
          }
        }
        g.drawImage(pic, (getWidth()-height)/2, 0, height, height, null);

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
        jLabel1 = new javax.swing.JLabel();
        x3FormattedField = new javax.swing.JFormattedTextField();
        x2FormattedField = new javax.swing.JFormattedTextField();
        x1FormattedField = new javax.swing.JFormattedTextField();
        x0FormattedField = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        saveImageButton = new javax.swing.JButton();
        redrawImageButton = new javax.swing.JButton();
        x4FormattedField = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        x5FormattedField = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();

        jLabel1.setText("Polynomial f:");

        x3FormattedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x3FormattedFieldActionPerformed(evt);
            }
        });

        x2FormattedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x2FormattedFieldActionPerformed(evt);
            }
        });

        x1FormattedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x1FormattedFieldActionPerformed(evt);
            }
        });

        x0FormattedField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x0FormattedFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("x ");

        jLabel2.setText("x^2");

        jLabel4.setText("x^3");

        jLabel5.setText("+");

        jLabel6.setText("+");

        jLabel7.setText("+");

        saveImageButton.setText("Save image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });

        redrawImageButton.setText("Redraw image");
        redrawImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redrawImageButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("x^4");

        jLabel9.setText("+");

        jLabel10.setText("x^5");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setText("NEWTON FRACTAL");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel12.setText("y(z) = z + f(z)/f'(z)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(x5FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(redrawImageButton, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                                .addComponent(saveImageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(x4FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(x3FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(x0FormattedField, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(x1FormattedField)
                                            .addComponent(x2FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(x5FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(x4FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(x3FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(x2FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(x1FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(x0FormattedField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(redrawImageButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveImageButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void x3FormattedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3FormattedFieldActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_x3FormattedFieldActionPerformed

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

    private void x2FormattedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x2FormattedFieldActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_x2FormattedFieldActionPerformed

    private void x1FormattedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x1FormattedFieldActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_x1FormattedFieldActionPerformed

    private void x0FormattedFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x0FormattedFieldActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_x0FormattedFieldActionPerformed

    private void redrawImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redrawImageButtonActionPerformed
        this.pic = null;
        this.repaint();
    }//GEN-LAST:event_redrawImageButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JButton redrawImageButton;
    private javax.swing.JButton saveImageButton;
    private javax.swing.JFormattedTextField x0FormattedField;
    private javax.swing.JFormattedTextField x1FormattedField;
    private javax.swing.JFormattedTextField x2FormattedField;
    private javax.swing.JFormattedTextField x3FormattedField;
    private javax.swing.JFormattedTextField x4FormattedField;
    private javax.swing.JFormattedTextField x5FormattedField;
    // End of variables declaration//GEN-END:variables
}
