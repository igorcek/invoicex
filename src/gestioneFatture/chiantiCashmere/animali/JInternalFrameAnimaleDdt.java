/**
 * Invoicex
 * Copyright (c) 2005,2006,2007 Marco Ceccarelli, Tnx snc
 *
 * Questo software è soggetto, e deve essere distribuito con la licenza  
 * GNU General Public License, Version 2. La licenza accompagna il software
 * o potete trovarne una copia alla Free Software Foundation http://www.fsf.org .
 *
 * This software is subject to, and may be distributed under, the
 * GNU General Public License, Version 2. The license should have
 * accompanied the software or you may obtain a copy of the license
 * from the Free Software Foundation at http://www.fsf.org .
 * 
 * --
 * Marco Ceccarelli (m.ceccarelli@tnx.it)
 * Tnx snc (http://www.tnx.it)
 *
 */



/*

 * JInternalFrameDatiBolle.java

 *

 * Created on 20 maggio 2003, 22.53

 */



package gestioneFatture.chiantiCashmere.animali;



/**

 *

 * @author  marco ceccarelli

 */

public class JInternalFrameAnimaleDdt extends javax.swing.JInternalFrame {

  InterfaceAnimale form;

  Animale animale;

  /** Creates new form JInternalFrameDatiBolle */

  public JInternalFrameAnimaleDdt(InterfaceAnimale form, Animale animale) {

    initComponents();

    

    this.comSesso.dbAddElement("Maschio", "M");

    this.comSesso.dbAddElement("Femmina", "F");

    

    this.form = form;

    this.animale = animale;

    if (animale.caricato == true) {

      this.texNumeroAllevamento.setText(animale.numeroAllevamento);

      this.texNumeroMicrochip.setText(animale.numeroMicrochip);

      this.texNoteAllegatoUsl.setText(animale.noteUsl);

      this.comSesso.dbTrovaKey(animale.sesso);

    }

  }

  

  /** This method is called from within the constructor to

   * initialize the form.

   * WARNING: Do NOT modify this code. The content of this method is

   * always regenerated by the Form Editor.

   */

  private void initComponents() {//GEN-BEGIN:initComponents

    jPanel1 = new javax.swing.JPanel();

    texNumeroAllevamento = new tnxbeans.tnxTextField();

    jLabel1 = new javax.swing.JLabel();

    jLabel2 = new javax.swing.JLabel();

    texNumeroMicrochip = new tnxbeans.tnxTextField();

    jLabel3 = new javax.swing.JLabel();

    jLabel4 = new javax.swing.JLabel();

    texNoteAllegatoUsl = new tnxbeans.tnxTextField();

    comSesso = new tnxbeans.tnxComboField();

    jPanel2 = new javax.swing.JPanel();

    jPanel3 = new javax.swing.JPanel();

    jButton1 = new javax.swing.JButton();

    jButton2 = new javax.swing.JButton();



    setClosable(true);

    setIconifiable(true);

    setMaximizable(true);

    setResizable(true);

    setTitle("Dati Animali");

    jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());



    texNumeroAllevamento.setmaxChars(50);

    jPanel1.add(texNumeroAllevamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 25, 265, 20));



    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    jLabel1.setText("Numero Allevamento");

    jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 25, -1, 20));



    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    jLabel2.setText("Numero Microchip");

    jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 120, 20));



    texNumeroMicrochip.setmaxChars(50);

    jPanel1.add(texNumeroMicrochip, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 50, 265, 20));



    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    jLabel3.setText("Sesso");

    jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-5, 75, 125, 20));



    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    jLabel4.setText("Note allegato USL");

    jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 120, 20));



    texNoteAllegatoUsl.setmaxChars(255);

    jPanel1.add(texNoteAllegatoUsl, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 100, 265, 20));



    comSesso.setEditable(false);

    jPanel1.add(comSesso, new org.netbeans.lib.awtextra.AbsoluteConstraints(125, 75, 165, 20));



    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);



    getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);



    jButton1.setText("Annulla");

    jButton1.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(java.awt.event.ActionEvent evt) {

        jButton1ActionPerformed(evt);

      }

    });



    jPanel3.add(jButton1);



    jButton2.setText("Conferma");

    jButton2.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(java.awt.event.ActionEvent evt) {

        jButton2ActionPerformed(evt);

      }

    });



    jPanel3.add(jButton2);



    getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);



    pack();

  }//GEN-END:initComponents



  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    this.dispose();

  }//GEN-LAST:event_jButton1ActionPerformed



  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

    this.animale.numeroAllevamento = this.texNumeroAllevamento.getText();

    this.animale.numeroMicrochip = this.texNumeroMicrochip.getText();

    this.animale.sesso = this.comSesso.getSelectedKey().toString();

    this.animale.noteUsl = this.texNoteAllegatoUsl.getText();

    form.confermaAnimale(this.animale);

    this.dispose();

  }//GEN-LAST:event_jButton2ActionPerformed

  

  

  // Variables declaration - do not modify//GEN-BEGIN:variables

  private tnxbeans.tnxComboField comSesso;

  private javax.swing.JButton jButton1;

  private javax.swing.JButton jButton2;

  private javax.swing.JLabel jLabel1;

  private javax.swing.JLabel jLabel2;

  private javax.swing.JLabel jLabel3;

  private javax.swing.JLabel jLabel4;

  private javax.swing.JPanel jPanel1;

  private javax.swing.JPanel jPanel2;

  private javax.swing.JPanel jPanel3;

  private tnxbeans.tnxTextField texNoteAllegatoUsl;

  private tnxbeans.tnxTextField texNumeroAllevamento;

  private tnxbeans.tnxTextField texNumeroMicrochip;

  // End of variables declaration//GEN-END:variables

  

}

