/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmAnagraficaEmissioneFattura.java
 *
 * Created on 21-dic-2011, 11.37.28
 */
package gestioneFatture;

import it.tnx.accessoUtenti.Permesso;
import it.tnx.commons.CastUtils;
import it.tnx.commons.SwingUtils;
import it.tnx.invoicex.InvoicexUtil;
import java.awt.Cursor;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Alessio
 */
public class frmAnagraficaUsers extends javax.swing.JInternalFrame {
    private boolean salvaPassword;
    
    /** Creates new form frmAnagraficaEmissioneFattura */
    public frmAnagraficaUsers() {
        initComponents();

        this.texCodi.setVisible(false);
        //associo il panel ai dati
        this.dati.dbNomeTabella = "accessi_utenti";
        Vector chiave = new Vector();
        chiave.add("id");
        this.dati.dbChiave = chiave;
        this.dati.butSave = this.butSave;
        this.dati.butUndo = this.butUndo;
        this.dati.butFind = this.butFind;
        this.dati.butNew = this.butNew;
        this.dati.butDele = this.butDele;
        this.dati.tipo_permesso = Permesso.PERMESSO_GESTIONE_UTENTI;

        this.texDbPassword.setVisible(false);
        this.comRoleId.dbOpenList(Db.getConn(), "SELECT descrizione, id FROM accessi_ruoli ORDER BY id");
        this.dati.dbOpen(Db.getConn(), "select * from " + this.dati.dbNomeTabella + " order by id");
        this.dati.dbRefresh();
        this.salvaPassword = false;
        
        //apro la griglia
        //this.griglia.dbEditabile = true;
        this.griglia.dbChiave = chiave;
        this.griglia.flagUsaThread = false;

        java.util.Hashtable colsWidthPerc = new java.util.Hashtable();
        colsWidthPerc.put("id", new Double(15));
        colsWidthPerc.put("username", new Double(85));
        this.griglia.columnsSizePerc = colsWidthPerc;
        this.griglia.dbOpen(Db.getConn(), "select id, username from " + this.dati.dbNomeTabella + " order by id");
        this.griglia.dbPanel = this.dati;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        butNew = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        butDele = new javax.swing.JButton();
        butFind = new javax.swing.JButton();
        jLabel131 = new javax.swing.JLabel();
        butFirs = new javax.swing.JButton();
        butPrev = new javax.swing.JButton();
        butNext = new javax.swing.JButton();
        butLast = new javax.swing.JButton();
        tabCent = new javax.swing.JTabbedPane();
        panDati = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        dati = new tnxbeans.tnxDbPanel();
        texDbPassword = new tnxbeans.tnxTextField();
        jLabel2111 = new javax.swing.JLabel();
        texCodi = new tnxbeans.tnxTextField();
        jLabel2113 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        texUsername = new tnxbeans.tnxTextField();
        texPasswordRepeat = new javax.swing.JPasswordField();
        texPassword = new javax.swing.JPasswordField();
        comRoleId = new tnxbeans.tnxComboField();
        btnAzzera = new javax.swing.JButton();
        panElen = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        griglia = new tnxbeans.tnxDbGrid();
        jPanel2 = new javax.swing.JPanel();
        butUndo = new javax.swing.JButton();
        butSave = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Anagrafica Utenti");

        butNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/document-new.png"))); // NOI18N
        butNew.setText("Nuovo");
        butNew.setBorderPainted(false);
        butNew.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNewActionPerformed(evt);
            }
        });
        jToolBar1.add(butNew);

        jLabel1.setText(" ");
        jToolBar1.add(jLabel1);

        jLabel11.setText(" ");
        jToolBar1.add(jLabel11);

        jLabel12.setText(" ");
        jToolBar1.add(jLabel12);

        jLabel13.setText(" ");
        jToolBar1.add(jLabel13);

        butDele.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/places/user-trash.png"))); // NOI18N
        butDele.setText("Elimina");
        butDele.setBorderPainted(false);
        butDele.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butDele.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDeleActionPerformed(evt);
            }
        });
        jToolBar1.add(butDele);

        butFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/edit-find.png"))); // NOI18N
        butFind.setText("Trova");
        butFind.setBorderPainted(false);
        butFind.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFindActionPerformed(evt);
            }
        });
        jToolBar1.add(butFind);

        jLabel131.setText(" ");
        jToolBar1.add(jLabel131);

        butFirs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/go-first.png"))); // NOI18N
        butFirs.setBorderPainted(false);
        butFirs.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butFirs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butFirsActionPerformed(evt);
            }
        });
        jToolBar1.add(butFirs);

        butPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/go-previous.png"))); // NOI18N
        butPrev.setBorderPainted(false);
        butPrev.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrevActionPerformed(evt);
            }
        });
        jToolBar1.add(butPrev);

        butNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/go-next.png"))); // NOI18N
        butNext.setBorderPainted(false);
        butNext.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butNextActionPerformed(evt);
            }
        });
        jToolBar1.add(butNext);

        butLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/go-last.png"))); // NOI18N
        butLast.setBorderPainted(false);
        butLast.setMargin(new java.awt.Insets(2, 2, 2, 2));
        butLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLastActionPerformed(evt);
            }
        });
        jToolBar1.add(butLast);

        tabCent.setName("dati"); // NOI18N

        panDati.setName("dati"); // NOI18N
        panDati.setLayout(new java.awt.BorderLayout());

        texDbPassword.setText("password");
        texDbPassword.setDbNomeCampo("password");
        texDbPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texDbPasswordActionPerformed(evt);
            }
        });

        jLabel2111.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2111.setText("password");

        texCodi.setText("id");
        texCodi.setDbNomeCampo("id");

        jLabel2113.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2113.setText("username");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("ripeti password");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("tipo utente");

        texUsername.setColumns(20);
        texUsername.setText("username");
        texUsername.setDbNomeCampo("username");
        texUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texUsernameActionPerformed(evt);
            }
        });

        texPasswordRepeat.setColumns(20);
        texPasswordRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texPasswordRepeatActionPerformed(evt);
            }
        });
        texPasswordRepeat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                texPasswordRepeatKeyReleased(evt);
            }
        });

        texPassword.setColumns(20);
        texPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texPasswordActionPerformed(evt);
            }
        });
        texPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                texPasswordKeyReleased(evt);
            }
        });

        comRoleId.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comRoleId.setDbNomeCampo("id_role");

        btnAzzera.setText("Azzera");
        btnAzzera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAzzeraActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout datiLayout = new org.jdesktop.layout.GroupLayout(dati);
        dati.setLayout(datiLayout);
        datiLayout.setHorizontalGroup(
            datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(datiLayout.createSequentialGroup()
                .addContainerGap()
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(datiLayout.createSequentialGroup()
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(comRoleId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, datiLayout.createSequentialGroup()
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, datiLayout.createSequentialGroup()
                                .add(jLabel2113)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(texUsername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.LEADING, datiLayout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(texPasswordRepeat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(datiLayout.createSequentialGroup()
                                .add(jLabel2111)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(texPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(texDbPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(texCodi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(btnAzzera))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        datiLayout.linkSize(new java.awt.Component[] {jLabel2, jLabel2111, jLabel2113, jLabel3}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        datiLayout.setVerticalGroup(
            datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(datiLayout.createSequentialGroup()
                .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(datiLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2113)
                            .add(texUsername, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(texCodi, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2111)
                            .add(texPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(texPasswordRepeat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(texDbPassword, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(datiLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(comRoleId, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(datiLayout.createSequentialGroup()
                        .add(38, 38, 38)
                        .add(btnAzzera)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(dati);

        panDati.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabCent.addTab("dati", panDati);

        panElen.setName("elenco"); // NOI18N
        panElen.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(griglia);

        panElen.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        tabCent.addTab("elenco", panElen);

        butUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/actions/edit-undo.png"))); // NOI18N
        butUndo.setText("Annulla");
        butUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butUndoActionPerformed(evt);
            }
        });
        jPanel2.add(butUndo);

        butSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/icons/tango-icon-theme-080/16x16/devices/media-floppy.png"))); // NOI18N
        butSave.setText("Salva");
        butSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSaveActionPerformed(evt);
            }
        });
        jPanel2.add(butSave);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, tabCent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabCent, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNewActionPerformed
        this.dati.dbNew();
        java.sql.Statement stat;
        ResultSet resu;

        //apre il resultset per ultimo +1
        try {
            stat = Db.getConn().createStatement();

            String sql = "select id from " + this.dati.dbNomeTabella + " order by id desc limit 1";
            resu = stat.executeQuery(sql);

            if (resu.next() == true) {
                this.texCodi.setText(String.valueOf(resu.getInt(1) + 1));
            } else {
                this.texCodi.setText("1");
            }
        } catch (Exception err) {
            javax.swing.JOptionPane.showMessageDialog(null, err.toString());
        }

        this.svuotaPassword();
        this.salvaPassword = true;
}//GEN-LAST:event_butNewActionPerformed

    private void butDeleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDeleActionPerformed
        if (CastUtils.toInteger0(this.texCodi.getText()) == 1) {
            SwingUtils.showInfoMessage(this, "Impossibile cancellare utente", "Impossibile proseguire");
            return;
        } else {
            int ret = JOptionPane.showConfirmDialog(this, "Sicuro di eliminare ?", "Attenzione", JOptionPane.YES_NO_OPTION);

            if (ret == JOptionPane.YES_OPTION) {
                this.dati.dbDelete();
                this.griglia.dbRefresh();
                this.griglia.dbSelezionaRiga();
            }
        }

        this.svuotaPassword();
}//GEN-LAST:event_butDeleActionPerformed

    private void butFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFindActionPerformed
        boolean ret = this.griglia.dbFindNext();

        if (ret == false) {
            int ret2 = JOptionPane.showConfirmDialog(this, "Posizione non trovata\nVuoi riprovare dall'inizio ?", "Attenzione", JOptionPane.YES_NO_OPTION);

            //JOptionPane.showMessageDialog(this,"?-:"+String.valueOf(i));
            if (ret2 == JOptionPane.OK_OPTION) {
                boolean ret3 = this.griglia.dbFindFirst();
            }
        }

        this.svuotaPassword();
}//GEN-LAST:event_butFindActionPerformed

    private void butFirsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butFirsActionPerformed
        // Add your handling code here:
        this.griglia.dbGoFirst();
        this.svuotaPassword();
}//GEN-LAST:event_butFirsActionPerformed

    private void butPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrevActionPerformed
        // Add your handling code here:
        this.griglia.dbGoPrevious();
        this.svuotaPassword();
}//GEN-LAST:event_butPrevActionPerformed

    private void butNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butNextActionPerformed
        // Add your handling code here:
        this.griglia.dbGoNext();
        this.svuotaPassword();
}//GEN-LAST:event_butNextActionPerformed

    private void butLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLastActionPerformed
        // Add your handling code here:
        this.griglia.dbGoLast();
        this.svuotaPassword();
}//GEN-LAST:event_butLastActionPerformed

    private void texDbPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texDbPasswordActionPerformed
}//GEN-LAST:event_texDbPasswordActionPerformed

    private void butUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butUndoActionPerformed
        dati.dbUndo();
        svuotaPassword();
}//GEN-LAST:event_butUndoActionPerformed

    private void svuotaPassword() {
        this.texPassword.setText("");
        this.texPasswordRepeat.setText("");
        this.salvaPassword = false;

        //cecca: non capisco ??? deve andare in base a chie utente è loggato non a cosa c'è scritto dentro il codice ???
//        if(this.texCodi.getText().equals("1")){
//            this.comRoleId.setEnabled(false);
//        } else {
//            this.comRoleId.setEnabled(true);
//        }
    }
    private void butSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSaveActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        String password = new String(this.texPassword.getPassword());
        String passwordRepeat = new String(this.texPasswordRepeat.getPassword());

        if(this.salvaPassword){
            if (password.equals(passwordRepeat)) {
                try {
                    this.texDbPassword.setText(InvoicexUtil.md5(password));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                SwingUtils.showErrorMessage(this, "Le due password inserite non coincidono, la nuova password non sarà salvata", "Password Errate");
            }
        }

        if(this.texUsername.getText().equals("") || CastUtils.toString(this.comRoleId.getSelectedItem()).equals("")){
            SwingUtils.showErrorMessage(this, "Inserire username e ruolo prima di salvare", "Impossibile salvare");
        } else {
            this.dati.dbSave();
            this.griglia.dbRefresh();
            this.svuotaPassword();
        }
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_butSaveActionPerformed

    private void texUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_texUsernameActionPerformed

    private void texPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texPasswordActionPerformed
        dati.dbForzaModificati();
        this.salvaPassword = true;
    }//GEN-LAST:event_texPasswordActionPerformed

    private void texPasswordRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texPasswordRepeatActionPerformed
        dati.dbForzaModificati();
        this.salvaPassword = true;
    }//GEN-LAST:event_texPasswordRepeatActionPerformed

    private void texPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_texPasswordKeyReleased
        dati.dbForzaModificati();
        this.salvaPassword = true;
    }//GEN-LAST:event_texPasswordKeyReleased

    private void texPasswordRepeatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_texPasswordRepeatKeyReleased
        dati.dbForzaModificati();
        this.salvaPassword = true;
    }//GEN-LAST:event_texPasswordRepeatKeyReleased

    private void btnAzzeraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAzzeraActionPerformed
        if(SwingUtils.showYesNoMessage(this, "Azzerando le password il tuo account non avrà più protezione, sicuro di proseguire?", "Azzeramento Password")){
            String sql = "UPDATE accessi_utenti SET password = " + Db.pc(InvoicexUtil.md5(""), Types.VARCHAR) + " WHERE id = " + Db.pc(this.texCodi.getText(), Types.INTEGER);
            Db.executeSql(sql);
            SwingUtils.showInfoMessage(this, "Password azzerata con successo", "Password Azzerata");
        }
    }//GEN-LAST:event_btnAzzeraActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new frmAnagraficaUsers().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAzzera;
    private javax.swing.JButton butDele;
    private javax.swing.JButton butFind;
    private javax.swing.JButton butFirs;
    private javax.swing.JButton butLast;
    private javax.swing.JButton butNew;
    private javax.swing.JButton butNext;
    private javax.swing.JButton butPrev;
    private javax.swing.JButton butSave;
    private javax.swing.JButton butUndo;
    private tnxbeans.tnxComboField comRoleId;
    private tnxbeans.tnxDbPanel dati;
    private tnxbeans.tnxDbGrid griglia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel2111;
    private javax.swing.JLabel jLabel2113;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel panDati;
    private javax.swing.JPanel panElen;
    private javax.swing.JTabbedPane tabCent;
    private tnxbeans.tnxTextField texCodi;
    private tnxbeans.tnxTextField texDbPassword;
    private javax.swing.JPasswordField texPassword;
    private javax.swing.JPasswordField texPasswordRepeat;
    private tnxbeans.tnxTextField texUsername;
    // End of variables declaration//GEN-END:variables
}