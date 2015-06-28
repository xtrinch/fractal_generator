/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mandelbrotset;

import static java.awt.BorderLayout.CENTER;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author trinch
 */
public class MainProgram extends javax.swing.JFrame {

    public MainProgram() {
        initComponents();
        
        
        // centers the GUI window
       
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth()/2;
        int height = (int)screenSize.getHeight()/2;
        System.out.println("framesize (w,h): " + width + " " + height);
        this.setPreferredSize(new Dimension(width, height));
        this.pack();
        this.setVisible(true);
        int lebar = this.getWidth()/2;
        int tinggi = this.getHeight()/2;
        int x = width-lebar;
        int y = height-tinggi;
        this.setLocation(x, y);
        
        
        // gets container and sets the background color
        this.getContentPane().setBackground(Color.WHITE);
        
        
        // key bindings
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left");
        buttonPanel.getActionMap().put("left", leftAction);
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right");
        buttonPanel.getActionMap().put("right", rightAction);
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up");
        buttonPanel.getActionMap().put("up", upAction);
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down");
        buttonPanel.getActionMap().put("down", downAction);
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0), "zoom");
        buttonPanel.getActionMap().put("zoom", zoomAction);
        
        buttonPanel.getInputMap(JPanel.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), "zoomout");
        buttonPanel.getActionMap().put("zoomout", zoomoutAction);
    }

    Action leftAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        leftActionPerformed(e);
    }
   };

    Action rightAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        rightActionPerformed(e);
    }
   };    
    
    Action upAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        upActionPerformed(e);
    }
   };  
        
    Action downAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        downActionPerformed(e);
    }
   };  
            
    Action zoomAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        zoomActionPerformed(e);
    }
   };  
                
    Action zoomoutAction = new AbstractAction() {
    public void actionPerformed(ActionEvent e) {
        zoomoutActionPerformed(e);
    }
   };  
                    

    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        buttonPanel = new javax.swing.JPanel();
        zoom = new javax.swing.JButton();
        zoomout = new javax.swing.JButton();
        left = new javax.swing.JButton();
        up = new javax.swing.JButton();
        down = new javax.swing.JButton();
        right = new javax.swing.JButton();
        drawPanel1 = new mandelbrotset.drawPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        juliaSetMenuItem = new javax.swing.JMenuItem();
        mandelbrotSetMenuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        pythagorasTreeMenuItem = new javax.swing.JMenuItem();
        kochSnowflakeMenuItem = new javax.swing.JMenuItem();
        fractalPlantMenuItem = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        barnsleysFernMenuItem = new javax.swing.JMenuItem();
        fractalFlameMenuItem = new javax.swing.JMenuItem();
        sierpinskiGasketMenuItem = new javax.swing.JMenuItem();
        dragonCurveMenuItem = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        brownianTreeMenuItem = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonPanel.setBackground(new java.awt.Color(102, 102, 102));

        zoom.setText("+");
        zoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomActionPerformed(evt);
            }
        });
        buttonPanel.add(zoom);

        zoomout.setText("-");
        zoomout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomoutActionPerformed(evt);
            }
        });
        buttonPanel.add(zoomout);

        left.setText("<");
        left.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leftActionPerformed(evt);
            }
        });
        buttonPanel.add(left);

        up.setText("▲");
        up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upActionPerformed(evt);
            }
        });
        buttonPanel.add(up);

        down.setText("▼");
        down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downActionPerformed(evt);
            }
        });
        buttonPanel.add(down);

        right.setText(">");
        right.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rightActionPerformed(evt);
            }
        });
        buttonPanel.add(right);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout drawPanel1Layout = new javax.swing.GroupLayout(drawPanel1);
        drawPanel1.setLayout(drawPanel1Layout);
        drawPanel1Layout.setHorizontalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawPanel1Layout.setVerticalGroup(
            drawPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(drawPanel1, java.awt.BorderLayout.CENTER);

        jMenu2.setText("Escape-time fractals");

        juliaSetMenuItem.setText("Julia set");
        juliaSetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                juliaSetMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(juliaSetMenuItem);

        mandelbrotSetMenuItem.setText("Mandelbrot set");
        mandelbrotSetMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mandelbrotSetMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(mandelbrotSetMenuItem);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("L-systems");

        pythagorasTreeMenuItem.setText("Pythagoras Tree");
        pythagorasTreeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pythagorasTreeMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(pythagorasTreeMenuItem);

        kochSnowflakeMenuItem.setText("Koch Snowflake");
        kochSnowflakeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kochSnowflakeMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(kochSnowflakeMenuItem);

        fractalPlantMenuItem.setText("Fractal Plant");
        fractalPlantMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fractalPlantMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(fractalPlantMenuItem);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Iterated function systems");

        barnsleysFernMenuItem.setText("Barnsley's Fern");
        barnsleysFernMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                barnsleysFernMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(barnsleysFernMenuItem);

        fractalFlameMenuItem.setText("Fractal Flame");
        fractalFlameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fractalFlameMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(fractalFlameMenuItem);

        sierpinskiGasketMenuItem.setText("Sierpinski's Gasket");
        sierpinskiGasketMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sierpinskiGasketMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(sierpinskiGasketMenuItem);

        dragonCurveMenuItem.setText("Dragon Curve");
        dragonCurveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dragonCurveMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(dragonCurveMenuItem);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Random fractals");

        brownianTreeMenuItem.setText("Brownian tree");
        brownianTreeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                brownianTreeMenuItemActionPerformed(evt);
            }
        });
        jMenu5.add(brownianTreeMenuItem);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leftActionPerformed
        this.drawPanel1.left();
        this.repaint();
    }//GEN-LAST:event_leftActionPerformed

    private void zoomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomActionPerformed
        Container contain = getContentPane();
        JPanel panel = (JPanel) contain.getComponent(1);
        if (panel instanceof JuliaSet)
            ((JuliaSet)panel).zoomInc();
        if (panel instanceof drawPanel)
            ((drawPanel)panel).zoomInc();
        if (panel instanceof KochSnowflake)
            ((KochSnowflake)panel).level += 1;
        this.repaint();
    }//GEN-LAST:event_zoomActionPerformed

    private void zoomoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomoutActionPerformed
        Container contain = getContentPane();
        JPanel panel = (JPanel) contain.getComponent(1);
        if (panel instanceof JuliaSet)
            ((JuliaSet)panel).zoomDec();
            System.out.println("julia repaint");
        if (panel instanceof drawPanel)
            ((drawPanel)panel).zoomDec();
        if (panel instanceof KochSnowflake) 
            ((KochSnowflake)panel).level -= 1;
        this.repaint();
    }//GEN-LAST:event_zoomoutActionPerformed

    private void rightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rightActionPerformed
        this.drawPanel1.right();
        this.repaint();
    }//GEN-LAST:event_rightActionPerformed

    private void upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upActionPerformed
        this.drawPanel1.up();
        this.repaint();
    }//GEN-LAST:event_upActionPerformed

    private void downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downActionPerformed
        this.drawPanel1.down();
        this.repaint();
    }//GEN-LAST:event_downActionPerformed

    private void juliaSetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_juliaSetMenuItemActionPerformed
        changePanel(new JuliaSet());
    }//GEN-LAST:event_juliaSetMenuItemActionPerformed

    private void mandelbrotSetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mandelbrotSetMenuItemActionPerformed
        changePanel(new drawPanel());
    }//GEN-LAST:event_mandelbrotSetMenuItemActionPerformed

    private void pythagorasTreeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pythagorasTreeMenuItemActionPerformed
        changePanel(new PythagorasTree());
    }//GEN-LAST:event_pythagorasTreeMenuItemActionPerformed

    private void fractalPlantMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fractalPlantMenuItemActionPerformed
        changePanel(new FractalPlant());
    }//GEN-LAST:event_fractalPlantMenuItemActionPerformed

    private void barnsleysFernMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_barnsleysFernMenuItemActionPerformed
        changePanel(new BarnsleysFern());
    }//GEN-LAST:event_barnsleysFernMenuItemActionPerformed

    private void fractalFlameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fractalFlameMenuItemActionPerformed
        changePanel(new FractalFlame());
    }//GEN-LAST:event_fractalFlameMenuItemActionPerformed

    private void sierpinskiGasketMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sierpinskiGasketMenuItemActionPerformed
        changePanel(new SierpinskiGasket());
    }//GEN-LAST:event_sierpinskiGasketMenuItemActionPerformed

    private void kochSnowflakeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kochSnowflakeMenuItemActionPerformed
        changePanel(new KochSnowflake(3));
    }//GEN-LAST:event_kochSnowflakeMenuItemActionPerformed

    private void dragonCurveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dragonCurveMenuItemActionPerformed
        changePanel(new DragonCurve());
    }//GEN-LAST:event_dragonCurveMenuItemActionPerformed

    private void brownianTreeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_brownianTreeMenuItemActionPerformed
        changePanel(new BrownianTree());
    }//GEN-LAST:event_brownianTreeMenuItemActionPerformed

    private void changePanel(JPanel panel) {
        Container contain = getContentPane();
        contain.remove(1);
        contain.add(panel,1);
        validate();
        repaint();
        //System.out.println("in4");
        setVisible(true);    
    }
    public static void main(String args[]) {
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainProgram().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem barnsleysFernMenuItem;
    private javax.swing.JMenuItem brownianTreeMenuItem;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton down;
    private javax.swing.JMenuItem dragonCurveMenuItem;
    private mandelbrotset.drawPanel drawPanel1;
    private javax.swing.JMenuItem fractalFlameMenuItem;
    private javax.swing.JMenuItem fractalPlantMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JMenuItem juliaSetMenuItem;
    private javax.swing.JMenuItem kochSnowflakeMenuItem;
    private javax.swing.JButton left;
    private javax.swing.JMenuItem mandelbrotSetMenuItem;
    private javax.swing.JMenuItem pythagorasTreeMenuItem;
    private javax.swing.JButton right;
    private javax.swing.JMenuItem sierpinskiGasketMenuItem;
    private javax.swing.JButton up;
    private javax.swing.JButton zoom;
    private javax.swing.JButton zoomout;
    // End of variables declaration//GEN-END:variables
}
