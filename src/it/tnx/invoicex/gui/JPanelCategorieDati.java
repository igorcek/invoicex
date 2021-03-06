/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelCategorieDati.java
 *
 * Created on 28-dic-2010, 16.03.22
 */

package it.tnx.invoicex.gui;

import it.tnx.Db;
import gestioneFatture.ImagePreviewPanel;
import gestioneFatture.main;
import it.tnx.commons.CastUtils;
import it.tnx.commons.ImgUtils;
import it.tnx.commons.SwingUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Types;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingworker.SwingWorker;
import tnxbeans.DbEvent;
import tnxbeans.DbListener;
import tnxbeans.tnxDbPanel;

/**
 *
 * @author mceccarelli
 */
public class JPanelCategorieDati extends javax.swing.JPanel {

    /** Creates new form JPanelCategorieDati */
    public JPanelCategorieDati() {
        initComponents();

        labStatusImgWeb.setVisible(false);

        List langs = Arrays.asList("it", "en", "fr");
        dati.setLang(langs);
        categoria.setLang(langs);
        descrizione.setLang(langs);

        dati.dbNomeTabella = "categorie";

        Vector chiave = new Vector();
        chiave.add("id");
        dati.dbChiave = chiave;
        dati.butSave = this.butSave;
        dati.butUndo = this.butUndo;

        dati.addDbListener(new DbListener() {

            public void statusFired(DbEvent event) {
                if (event.getStatus() == tnxDbPanel.STATUS_REFRESHING || event.getStatus() == tnxDbPanel.STATUS_ADDING) {
                    try {
                        dati.getCampiAggiuntivi().remove("immagine1");
                    } catch (Exception e) {
                    }
                    refreshImmagine();
                }
                if (event.getStatus() == tnxDbPanel.STATUS_ADDING) {
                    labImmagine.setText("Nessuna immagine");
                    labImmagine.setIcon(null);
                }
            }
        });

    }

    public void load(Integer id) {
        dati.dbOpen(Db.getConn(), "select * from categorie where id = " + id);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupImmagine = new javax.swing.JPopupMenu();
        menScegli = new javax.swing.JMenuItem();
        menElimina = new javax.swing.JMenuItem();
        dati = new tnxbeans.tnxDbPanel();
        categoria = new tnxbeans.tnxTextFieldLang();
        jLabel1 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        labImmagine = new javax.swing.JLabel();
        labStatusImgWeb = new org.jdesktop.swingx.JXBusyLabel();
        jLabel29 = new javax.swing.JLabel();
        descrizione = new tnxbeans.tnxMemoFieldLang();
        tnxCheckBox1 = new tnxbeans.tnxCheckBox();
        jPanel1 = new javax.swing.JPanel();
        butUndo = new javax.swing.JButton();
        butSave = new javax.swing.JButton();

        menScegli.setText("Scegli Immagine");
        menScegli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menScegliActionPerformed(evt);
            }
        });
        popupImmagine.add(menScegli);

        menElimina.setText("Elimina");
        menElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menEliminaActionPerformed(evt);
            }
        });
        popupImmagine.add(menElimina);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        categoria.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        categoria.setDbNomeCampo("nome");

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Categoria");

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel38.setText("immagine");

        labImmagine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labImmagine.setText("Nessuna immagine");
        labImmagine.setToolTipText("Clicca con il tasto destro per le opzioni");
        labImmagine.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));
        labImmagine.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labImmagineMouseClicked(evt);
            }
        });

        labStatusImgWeb.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        labStatusImgWeb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labStatusImgWeb.setText("...");
        labStatusImgWeb.setBusy(true);
        labStatusImgWeb.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel29.setText("Descrizione");

        descrizione.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        descrizione.setDbNomeCampo("descrizione");
        descrizione.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                descrizioneFocusLost(evt);
            }
        });

        tnxCheckBox1.setText("Visibile solo se utente registrato");
        tnxCheckBox1.setDbNomeCampo("visibile_se_registrato");

        org.jdesktop.layout.GroupLayout datiLayout = new org.jdesktop.layout.GroupLayout(dati);
        dati.setLayout(datiLayout);
        datiLayout.setHorizontalGroup(
            datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(datiLayout.createSequentialGroup()
                .addContainerGap()
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(datiLayout.createSequentialGroup()
                        .add(jLabel38, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(datiLayout.createSequentialGroup()
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                        .add(datiLayout.createSequentialGroup()
                            .add(jLabel29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 99, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(6, 6, 6))))
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(labImmagine, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, tnxCheckBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, descrizione, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, categoria, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .add(datiLayout.createSequentialGroup()
                        .add(379, 379, 379)
                        .add(labStatusImgWeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 62, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        datiLayout.setVerticalGroup(
            datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(datiLayout.createSequentialGroup()
                .addContainerGap()
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(categoria, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel29)
                    .add(descrizione, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, datiLayout.createSequentialGroup()
                        .add(tnxCheckBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(labImmagine, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                    .add(jLabel38, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(labStatusImgWeb, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(dati, java.awt.BorderLayout.CENTER);

        butUndo.setText("Annulla");
        butUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUndoActionPerformed(evt);
            }
        });

        butSave.setText("Conferma");
        butSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSaveActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(434, Short.MAX_VALUE)
                .add(butUndo)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(butSave)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(butSave)
                    .add(butUndo))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void labImmagineMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labImmagineMouseClicked
        if (evt.getClickCount() >= 2) {
            menScegliActionPerformed(null);
        }
        if (SwingUtilities.isRightMouseButton(evt)) {
            popupImmagine.show(labImmagine, evt.getX(), evt.getY());
        }
}//GEN-LAST:event_labImmagineMouseClicked

    private void menScegliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menScegliActionPerformed
        JFileChooser fileChoose = new JFileChooser();

        FileFilter filter1 = new FileFilter() {

            public boolean accept(File pathname) {
                if (pathname.getAbsolutePath().toLowerCase().endsWith(".jpg")
                        || pathname.getAbsolutePath().toLowerCase().endsWith(".jpeg")
                        || pathname.getAbsolutePath().toLowerCase().endsWith(".gif")
                        || pathname.getAbsolutePath().toLowerCase().endsWith(".bmp")
                        || pathname.getAbsolutePath().toLowerCase().endsWith(".png")
                        || pathname.isDirectory()) {
                    return true;
                } else {
                    return false;
                }
            }

            public String getDescription() {
                return "File Immagine (*.jpg, *.jpeg, *.gif, *.png, *.bmp)";
            }

        };
        fileChoose.addChoosableFileFilter(filter1);
        ImagePreviewPanel preview = new ImagePreviewPanel();
        fileChoose.setAccessory(preview);
        fileChoose.addPropertyChangeListener(preview);
        String path = main.prefs.get("articoli_path_chooser_immagine", null);
        if (path != null) {
            fileChoose.setCurrentDirectory(new File(path));
        }
        int ret = fileChoose.showOpenDialog(this);
        if (ret == javax.swing.JFileChooser.APPROVE_OPTION) {
            //apro il file
            String file = fileChoose.getSelectedFile().getAbsolutePath();
            if (dati.getCampiAggiuntivi() == null) {
                dati.setCampiAggiuntivi(new Hashtable());
            }
            file = StringUtils.replace(file, "\\", "\\\\");
            dati.getCampiAggiuntivi().put("immagine1", Db.pc(file, Types.VARCHAR));
            dati.dbForzaModificati();
            refreshImmagine();
            uploadImmagine(file);
        }
        //memorizzo path
        main.prefs.put("articoli_path_chooser_immagine", fileChoose.getCurrentDirectory().getAbsolutePath());
}//GEN-LAST:event_menScegliActionPerformed

    private void menEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menEliminaActionPerformed
        labImmagine.setText("Nessuna immagine");
        labImmagine.setIcon(null);

        labImmagine.setText("Nessuna immagine");
        labImmagine.setIcon(null);

        if (dati.getCampiAggiuntivi() == null) {
            dati.setCampiAggiuntivi(new Hashtable());
        }
        dati.getCampiAggiuntivi().put("immagine1", "null");
        dati.dbForzaModificati();
}//GEN-LAST:event_menEliminaActionPerformed

    private void butSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSaveActionPerformed
        if (dati.dbSave()) {
            dispose();
        }
    }//GEN-LAST:event_butSaveActionPerformed

    private void butUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUndoActionPerformed
        dispose();
    }//GEN-LAST:event_butUndoActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        refreshImmagine();
    }//GEN-LAST:event_formComponentShown

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        refreshImmagine();
    }//GEN-LAST:event_formComponentResized

    private void descrizioneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_descrizioneFocusLost

}//GEN-LAST:event_descrizioneFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butSave;
    private javax.swing.JButton butUndo;
    private tnxbeans.tnxTextFieldLang categoria;
    private tnxbeans.tnxDbPanel dati;
    private tnxbeans.tnxMemoFieldLang descrizione;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labImmagine;
    public org.jdesktop.swingx.JXBusyLabel labStatusImgWeb;
    private javax.swing.JMenuItem menElimina;
    private javax.swing.JMenuItem menScegli;
    private javax.swing.JPopupMenu popupImmagine;
    private tnxbeans.tnxCheckBox tnxCheckBox1;
    // End of variables declaration//GEN-END:variables


    public void refreshImmagine() {
        int w0 = labImmagine.getWidth();
        int h0 = labImmagine.getHeight();
        if (w0 == 0 && h0 == 0) {
            w0 = 420;
            h0 = 155;
        }
        final int w = w0;
        final int h = h0;
        if (w > 0 && h > 0) {
            Thread t = new Thread("refresh immagine") {

                @Override
                public void run() {
                    try {
                        String immagine = null;
                        try {
                            immagine = StringUtils.replace(StringUtils.remove((String) dati.getCampiAggiuntivi().get("immagine1"), "'"), "\\\\", "\\");
                        } catch (Exception e) {
                        }
                        if (immagine == null || immagine.length() == 0) {
                            immagine = CastUtils.toString(dati.dbGetField("immagine1"));
                        }
                        if (StringUtils.isEmpty(immagine)) {
                            SwingUtils.inEdt(new Runnable() {

                                public void run() {
                                    labImmagine.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));
                                    labImmagine.setIcon(null);
                                    labImmagine.setText("Nessuna immagine");
                                }
                            });
                        } else {
                            //ridimensiono
                            SwingUtils.inEdtWait(new Runnable() {

                                public void run() {
                                    labImmagine.setBorder(null);
                                    labImmagine.setIcon(null);
                                    labImmagine.setText("... caricamento ...");
                                }
                            });
                            BufferedImage i = ImageIO.read(new File(immagine));
                            final BufferedImage ir = ImgUtils.applyFrame3(ImgUtils.resizeQuality(i, ImgUtils.getDimension(i.getWidth(), i.getHeight(), w, h)), labImmagine.getBackground());
                            SwingUtils.inEdt(new Runnable() {

                                public void run() {
                                    labImmagine.setText("");
                                    labImmagine.setIcon(new ImageIcon(ir));
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        SwingUtils.inEdt(new Runnable() {

                            public void run() {
                                labImmagine.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.shadow")));
                                labImmagine.setIcon(null);
                                labImmagine.setText("Nessuna immagine");
                            }
                        });
                    }
                }
            };
            t.start();
        }

    }

    public void dispose() {
    }

    public static void main(String[] args) {
        JPanelCategorie.main(args);
    }

    private void uploadImmagine(final String file) {
        labStatusImgWeb.setVisible(true);
        labStatusImgWeb.setText("upload in corso...");

        SwingWorker worker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                try {

                    //controllo dimensioni
                    File fupload = null;
                    BufferedImage image = ImageIO.read(new File(file));
                    File temp = null;
                    if (image.getWidth() > 1400 || image.getHeight() > 1200) {
                        //rimpicciolisco
                        temp = new File(main.wd + "temp_img_categorie_upload/" + new File(file).getName());
                        temp.getParentFile().mkdirs();
                        temp.delete();
                        image = ImgUtils.resizeQuality(image, ImgUtils.getDimension(image.getWidth(), image.getHeight(), 1400, 1200));
                        ImgUtils.writeJpeg(image, temp.getAbsolutePath(), 0.9f);
                        fupload = temp;
                    } else {
                        fupload = new File(file);
                    }

                    HttpClient httpclient = new HttpClient();
//                    PostMethod post = new PostMethod("http://indi.tnx.dyndns.org/perle_gitane/invoicex.php");
                    PostMethod post = new PostMethod("http://www.perlegitane.it/invoicex.php");
                    File f = new File(file);
                    Part[] parts = {
                        new StringPart("upload_img", "1"),
                        new StringPart("categoria", categoria.getText("it")),
                        new FilePart(f.getName(), fupload)
                    };
                    post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));

                    int ret = httpclient.executeMethod(post);

                    System.out.println("ret = " + ret);

                    String resp = post.getResponseBodyAsString();

                    System.out.println("resp = " + resp);

                    post.releaseConnection();

                    if (temp != null) {
                        temp.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                labStatusImgWeb.setVisible(false);
            }

        };
        worker.execute();
    }
}
