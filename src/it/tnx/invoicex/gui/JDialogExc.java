/**
 * Invoicex
 * Copyright (c) 2005,2006,2007,2008,2009 Marco Ceccarelli, Tnx snc
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
package it.tnx.invoicex.gui;

import gestioneFatture.main;
import it.tnx.commons.SwingUtils;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang.StringUtils;
import sun.misc.BASE64Encoder;

/**
 *
 * @author mceccarelli
 */
public class JDialogExc extends javax.swing.JDialog {

    Throwable e = null;

    /** Creates new form JDialogExc */
    public JDialogExc(java.awt.Frame parent, boolean modal, Throwable e) {
        super(parent, modal);
        this.e = e;
        initComponents();        
        try {
            ((java.awt.Frame)getOwner()).setIconImage(main.getLogoIcon());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public JDialogExc(java.awt.Dialog parent, boolean modal, Throwable e) {
        super(parent, modal);
        this.e = e;
        initComponents();
        try {
            ((java.awt.Frame)getOwner()).setIconImage(main.getLogoIcon());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labInt = new javax.swing.JLabel();
        labe = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jXHyperlink1 = new org.jdesktop.swingx.JXHyperlink();

        FormListener formListener = new FormListener();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invoicex - Errore");

        labInt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/22x22/actions/process-stop.png"))); // NOI18N
        labInt.setText("Sì è verificato il seguente problema:");

        labe.setFont(labe.getFont().deriveFont(labe.getFont().getStyle() | java.awt.Font.BOLD));
        labe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        if (e != null) labe.setText(StringUtils.abbreviate(e.getLocalizedMessage(), 100));

        jButton1.setFont(jButton1.getFont());
        jButton1.setText("Invia segnalazione errore");
        jButton1.addActionListener(formListener);

        jButton2.setText("Ignora");
        jButton2.addActionListener(formListener);

        jXHyperlink1.setText("Contatti per assistenza");
        jXHyperlink1.addActionListener(formListener);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(labe, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jXHyperlink1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton2))
                    .add(labInt, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(labInt)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labe)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 72, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2)
                    .add(jXHyperlink1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.ActionListener {
        FormListener() {}
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            if (evt.getSource() == jButton1) {
                JDialogExc.this.jButton1ActionPerformed(evt);
            }
            else if (evt.getSource() == jButton2) {
                JDialogExc.this.jButton2ActionPerformed(evt);
            }
            else if (evt.getSource() == jXHyperlink1) {
                JDialogExc.this.jXHyperlink1ActionPerformed(evt);
            }
        }
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SwingUtils.mouse_wait(this);
        URL url;
        try {
//            StringWriter sw = new StringWriter();
//            PrintWriter pw = new PrintWriter(sw);
//            e.printStackTrace(pw);
//            sw.toString();
//            url = new URL("http://www.tnx.it/pagine/invoicex_server/e.php?e=" + URLEncoder.encode(e.toString()) + "&v=" + URLEncoder.encode(main.version + " " + main.build) + "&i=" + main.attivazione.getIdRegistrazione() + "&est=" + URLEncoder.encode(sw.toString()));
//            System.err.println("url = " + url);
//
//            URLConnection conn = url.openConnection();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                System.err.println("in:" + line);
//            }

            //invio segnalazione con log zippato
            String swe = "";
            if (e != null) {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                swe = sw.toString();
            } else {
                swe = labInt.getText() + "\n" + labe.getText();
            }

            String data = URLEncoder.encode("e", "UTF-8") + "=" + URLEncoder.encode(e == null ? "exc vuota" : e.toString(), "UTF-8");
            data += "&" + URLEncoder.encode("v", "UTF-8") + "=" + URLEncoder.encode(main.version + " " + main.build, "UTF-8");
            data += "&" + URLEncoder.encode("i", "UTF-8") + "=" + main.attivazione.getIdRegistrazione();
            data += "&" + URLEncoder.encode("est", "UTF-8") + "=" + URLEncoder.encode(swe, "UTF-8");
            File f = new File(System.getProperty("user.home") + File.separator + ".invoicex" + File.separator + "invoicex.log");
            if (f.exists()) {
                byte[] buf = new byte[1024];
                StringWriter sw = new StringWriter();
                ByteArrayOutputStream ba = new ByteArrayOutputStream();
                ZipOutputStream out = new ZipOutputStream(ba);
                FileInputStream in = new FileInputStream(f);
                out.putNextEntry(new ZipEntry(f.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                out.close();
                in.close();
                byte[] bazip = ba.toByteArray();
                BASE64Encoder b64e = new BASE64Encoder();
                String zip_enc_b64 = b64e.encode(bazip);
                zip_enc_b64 = URLEncoder.encode(zip_enc_b64, "UTF-8");
                data += "&log=" + zip_enc_b64;
            }

            // Create a socket to the host
//            url = new URL("http://www.tnx.dyndns.org/invoicex/e2.php");
            url = new URL("http://www.tnx.it/pagine/invoicex_server/e2.php");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            // Send header
            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF8"));
//            wr.write("POST /invoicex/e2.php HTTP/1.0\r\n");
            wr.write("POST /pagine/invoicex_server/e2.php HTTP/1.0\r\n");
            wr.write("Content-Length: " + data.length() + "\r\n");
            wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
            wr.write("\r\n");

            // Send data
            wr.write(data);
            wr.flush();

            // Get response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            System.out.println("ritorno dal post:");
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }
            wr.close();
            rd.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SwingUtils.mouse_def(this);
        SwingUtils.showInfoMessage(this, "Segnalazione inviata, grazie.");
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jXHyperlink1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jXHyperlink1ActionPerformed
        try {
            SwingUtils.openUrl(new URL("http://www.invoicex.it/Supporto-e-Assistenza-Informazioni/"));
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jXHyperlink1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton1;
    public javax.swing.JButton jButton2;
    public org.jdesktop.swingx.JXHyperlink jXHyperlink1;
    public javax.swing.JLabel labInt;
    public javax.swing.JLabel labe;
    // End of variables declaration//GEN-END:variables

    static public void showExc(Frame comp, boolean modal, Exception e) {
        JDialogExc de = new JDialogExc(comp, true, e);
        de.setLocationRelativeTo(null);
        de.pack();
        Toolkit.getDefaultToolkit().beep();
        de.setVisible(true);
    }

    static public void showExc(Frame comp, boolean modal, Exception e, String intestazione) {
        JDialogExc de = new JDialogExc(comp, true, e);
        de.labInt.setText(intestazione);
        de.labInt.setFont(de.labInt.getFont().deriveFont(Font.BOLD, de.labInt.getFont().getSize()+4));
        de.setLocationRelativeTo(null);
        de.pack();
        Toolkit.getDefaultToolkit().beep();
        de.setVisible(true);
    }

    static public void showExc(Dialog comp, boolean modal, Exception e) {
        JDialogExc de = new JDialogExc(comp, true, e);
        de.setLocationRelativeTo(null);
        de.pack();
        Toolkit.getDefaultToolkit().beep();
        de.setVisible(true);
    }
}