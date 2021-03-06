package it.tnx;

import javax.swing.*;
/*
 * JFrameMessage.java
 *
 * Created on October 13, 2003, 9:08 AM
 */

/**
 *
 * @author  marco
 */
public class JFrameMessage extends javax.swing.JFrame {

    /** Creates new form JFrameMessage */
    public JFrameMessage() {
        initComponents();        
    }

    public JTextArea getTextArea() {
        return this.texMessage;
    }

    public JButton getPulsanteChiudi() {
        return this.butChiudi;
    }

    public JProgressBar getAvanzamento() {
        return this.proAvanzamento;
    }

    public JScrollPane getTextAreaScrollPane() {
        return this.scrTextArea;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        scrTextArea = new javax.swing.JScrollPane();
        texMessage = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        proAvanzamento = new javax.swing.JProgressBar();
        panbasso = new javax.swing.JPanel();
        butChiudi = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        texMessage.setEditable(false);
        scrTextArea.setViewportView(texMessage);

        jPanel1.add(scrTextArea, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.add(proAvanzamento);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        butChiudi.setText("Chiudi");
        butChiudi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butChiudiActionPerformed(evt);
            }
        });
        panbasso.add(butChiudi);

        getContentPane().add(panbasso, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void butChiudiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butChiudiActionPerformed
      this.dispose();
  }//GEN-LAST:event_butChiudiActionPerformed

    /** Exit the Application */
  private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
      //System.exit(0);
  }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new JFrameMessage().show();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butChiudi;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPanel panbasso;
    private javax.swing.JProgressBar proAvanzamento;
    private javax.swing.JScrollPane scrTextArea;
    private javax.swing.JTextArea texMessage;
    // End of variables declaration//GEN-END:variables
}
