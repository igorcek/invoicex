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
package reports;

import gestioneFatture.*;
import gestioneFatture.logic.clienti.Cliente;
import gestioneFatture.logic.documenti.*;
import it.tnx.commons.CastUtils;
import it.tnx.commons.DbUtils;
import it.tnx.commons.FormatUtils;
import it.tnx.invoicex.InvoicexUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.*;
import java.util.prefs.Preferences;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.groovy.ant.FileScanner;

public class JRDSInvoice extends JRDSBase
        implements net.sf.jasperreports.engine.JRDataSource {

    private int conta = 0;
    String sql = "";
    ResultSet rDocu = null;
    ResultSet rCliente = null;
    private Documento doc;
    DettaglioIva diva = null;
    String serie = "";
    int numero = 1;
    int anno = 2004;
    String banca_sede;
    String banca_solo_sede;
    String banca_agenzia;
    String banca_iban;
    String banca_SWIFT;
    //iva
    String iva_codice_1 = "";
    String iva_desc_1 = "";
    String iva_imp_1 = "";
    String iva_perc_1 = "";
    String iva_imposta_1 = "";
    String iva_codice_2 = "";
    String iva_desc_2 = "";
    String iva_imp_2 = "";
    String iva_perc_2 = "";
    String iva_imposta_2 = "";
    String iva_codice_3 = "";
    String iva_desc_3 = "";
    String iva_imp_3 = "";
    String iva_perc_3 = "";
    String iva_imposta_3 = "";
    String iva_codice_4 = "";
    String iva_desc_4 = "";
    String iva_imp_4 = "";
    String iva_perc_4 = "";
    String iva_imposta_4 = "";
    String scadenze = "";
    Vector scadenze_date = new Vector();
    Vector scadenze_importi = new Vector();
    String intestazione1 = "";
    String intestazione2 = "";
    String intestazione3 = "";
    String intestazione4 = "";
    String intestazione5 = "";
    String intestazione6 = "";
    String etichettaCliente = "";
    String etichettaDestinazione = "";
    String etichettaCliente_eng = "";
    String etichettaDestinazione_eng = "";
    public String nomeClienteFile;
    Preferences preferences = Preferences.userNodeForPackage(main.class);
    boolean stampa_dest_div = false;
    String ragione_sociale_1 = "";
    String indirizzo_1 = "";
    String cap_loc_prov_1 = "";
    String piva_cfiscale_desc_1 = "";
    String recapito_1 = "";
    String email_1 = "";
    String ragione_sociale_2 = "";
    String indirizzo_2 = "";
    String cap_loc_prov_2 = "";
    String piva_cfiscale_desc_2 = "";
    String piva_cfiscale_desc_2_sotto = "";
    String email_2 = "";
    int numRighe = 0;
    String recapito_2 = "";
    String recapito_2_sotto = "";
    boolean perEmail = false;
    public Integer codiceCliente = null;
    boolean italian = true;
    String valuta = it.tnx.Util.EURO;
    Double marca_da_bollo;
//    String valuta = "?";
    String notePiede = "";
    boolean stampaInvoicexRiga = true;
    boolean prezzi_ivati = false;
    static int contalogo;

    /** Creates a new instance of JRDSInvoice */
    public JRDSInvoice(Connection conn, String serie, int numero, int anno, Integer id) {
        init(conn, serie, numero, anno, false, id);
    }

    public JRDSInvoice(Connection conn, String serie, int numero, int anno, boolean perEmail, Integer id) {
        init(conn, serie, numero, anno, perEmail, id);
    }

    private void init(Connection conn, String serie, int numero, int anno, boolean perEmail, Integer id) {
        this.serie = serie;
        this.numero = numero;
        this.anno = anno;
        this.perEmail = perEmail;
        doc = new Documento();
        doc.load(Db.INSTANCE, numero, serie, anno, Db.TIPO_DOCUMENTO_FATTURA, id);
        doc.calcolaTotali();
        doc.visualizzaCastellettoIva();

        int ivaSize = doc.dettagliIva.size();

        if (ivaSize > 4) {
            javax.swing.JOptionPane.showMessageDialog(null, "Ci sono piu' di 4 tipi di iva ma ne verranno stampati solo 4 !!!");
        }

        if (doc.dettagliIva.size() > 0) {
            diva = (DettaglioIva) doc.dettagliIva.get(0);
            iva_codice_1 = diva.getCodice();
            iva_desc_1 = diva.getDescrizione();
            iva_imp_1 = it.tnx.Util.formatValutaEuro(diva.getImponibile());
            iva_perc_1 = it.tnx.Util.formatNumero0Decimali(diva.getPercentuale());
            iva_imposta_1 = it.tnx.Util.formatValutaEuro(diva.getImposta());
        }

        if (ivaSize >= 2) {
            diva = (DettaglioIva) doc.dettagliIva.get(1);
            iva_codice_2 = diva.getCodice();
            iva_desc_2 = diva.getDescrizione();
            iva_imp_2 = it.tnx.Util.formatValutaEuro(diva.getImponibile());
            iva_perc_2 = it.tnx.Util.formatNumero0Decimali(diva.getPercentuale());
            iva_imposta_2 = it.tnx.Util.formatValutaEuro(diva.getImposta());
        }

        if (ivaSize >= 3) {
            diva = (DettaglioIva) doc.dettagliIva.get(2);
            iva_codice_3 = diva.getCodice();
            iva_desc_3 = diva.getDescrizione();
            iva_imp_3 = it.tnx.Util.formatValutaEuro(diva.getImponibile());
            iva_perc_3 = it.tnx.Util.formatNumero0Decimali(diva.getPercentuale());
            iva_imposta_3 = it.tnx.Util.formatValutaEuro(diva.getImposta());
        }

        if (ivaSize >= 4) {
            diva = (DettaglioIva) doc.dettagliIva.get(3);
            iva_codice_4 = diva.getCodice();
            iva_desc_4 = diva.getDescrizione();
            iva_imp_4 = it.tnx.Util.formatValutaEuro(diva.getImponibile());
            iva_perc_4 = it.tnx.Util.formatNumero0Decimali(diva.getPercentuale());
            iva_imposta_4 = it.tnx.Util.formatValutaEuro(diva.getImposta());
        }

        sql = "select test_fatt.*,righ_fatt.*,clie_forn.*, clie_forn.codice as codice_cliente2, clie_forn_dest.*, agenti.nome as agente_nome \n"
                + " , articoli.codice_a_barre \n"
                + " , articoli.codice_fornitore \n"
                + " , articoli.immagine1 \n"
                + " from ((test_fatt left join righ_fatt on test_fatt.id = righ_fatt.id_padre and righ_fatt.anno = " + anno + " ) \n"
                + " left join clie_forn on test_fatt.cliente = clie_forn.codice) \n"
                + " left join clie_forn_dest on test_fatt.cliente = clie_forn_dest.codice_cliente and test_fatt.cliente_destinazione = clie_forn_dest.codice \n"
                + " left join agenti on test_fatt.agente_codice = agenti.id \n"
                + " left join articoli on righ_fatt.codice_articolo = articoli.codice \n"
                + " where test_fatt.tipo_fattura != 7 and test_fatt.serie = " + Db.pc(serie, Types.VARCHAR) + " and test_fatt.numero = " + numero + " and test_fatt.anno = " + anno + " order by righ_fatt.riga";

        //debug
        System.out.println("jasper sql:" + sql);

        try {

            Statement stat = conn.createStatement();
            rDocu = stat.executeQuery(sql);

            numRighe = 0;
            while (rDocu.next()) {
                numRighe++;
            }

            rDocu.beforeFirst();
            Statement statCliente = conn.createStatement();

            //trovo dati banca
            banca_sede = "";
            banca_solo_sede = "";
            banca_agenzia = "";
            banca_iban = "";
            banca_SWIFT = "";
            rDocu.next();
            try {
                marca_da_bollo = rDocu.getDouble("marca_da_bollo");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Preferences preferences = java.util.prefs.Preferences.userNodeForPackage(main.class);
            if (main.fileIni.getValueBoolean("pref", "soloItaliano", true)) {
                italian = true;
            } else {
                codiceCliente = rDocu.getInt("cliente");
                Cliente cliente = new Cliente(codiceCliente);
                italian = cliente.isItalian();
            }

            String prezzi_ivati_s = CastUtils.toString(rDocu.getString("prezzi_ivati"));
            if (prezzi_ivati_s.equalsIgnoreCase("S")) {
                prezzi_ivati = true;
            }

            //seleziono i dati del cliente
            rCliente = statCliente.executeQuery("select * from clie_forn where codice = " + rDocu.getInt("cliente"));
            rCliente.next();
            System.out.println("rdocue.next");
            nomeClienteFile = rCliente.getString("ragione_sociale");

            if (rDocu.getString("test_fatt.banca_abi") != null && rDocu.getString("test_fatt.banca_abi").length() > 0) {
                banca_sede += "ABI " + rDocu.getString("test_fatt.banca_abi");
                banca_solo_sede = "";
                banca_agenzia += "CAB " + rDocu.getString("test_fatt.banca_cab");

                String sql = "";
                sql += "select *";
                sql += " from banche_abi";
                sql += " where abi = " + Db.pc(rDocu.getString("test_fatt.banca_abi"), "VARCHAR");

                try {

                    Statement sAbi = conn.createStatement();
                    ResultSet rAbi = sAbi.executeQuery(sql);

                    if (rAbi.next()) {
                        banca_sede += " - " + rAbi.getString(2);
                        banca_solo_sede += rAbi.getString(2);
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }

                sql = "";
                sql += "select banche_cab.cap,";
                sql += " banche_cab.indirizzo,";
                sql += " comuni.comune,";
                sql += " comuni.provincia";
                sql += " from banche_cab left join comuni on banche_cab.codice_comune = comuni.codice";
                sql += " where banche_cab.abi = " + Db.pc(rDocu.getString("test_fatt.banca_abi"), "VARCHAR");
                sql += " and banche_cab.cab = " + Db.pc(rDocu.getString("test_fatt.banca_cab"), "VARCHAR");

                try {

                    Statement sCab = conn.createStatement();
                    ResultSet rCab = sCab.executeQuery(sql);

                    if (rCab.next()) {
                        banca_agenzia += " - Fil. " + Db.nz(rCab.getString(1), "") + " " + Db.nz(rCab.getString(2), "") + ", " + Db.nz(rCab.getString(3), "") + " (" + Db.nz(rCab.getString(4), "") + ")";
                    }
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }

            if (rDocu.getString("test_fatt.banca_iban") != null && rDocu.getString("test_fatt.banca_iban").length() > 0) {
                banca_iban = "IBAN " + rDocu.getString("test_fatt.banca_iban");
            }

            if (rDocu.getString("test_fatt.SWIFT") != null && rDocu.getString("test_fatt.SWIFT").length() > 0) {
                banca_SWIFT = "" + rDocu.getString("test_fatt.SWIFT");
            }

            //dati per dest diversa
            if (StringUtils.isNotBlank(rDocu.getString("dest_ragione_sociale")) || StringUtils.isNotBlank(rDocu.getString("dest_indirizzo"))) {
                stampa_dest_div = true;
            } else {
                stampa_dest_div = false;
            }

            if (main.fileIni.getValueBoolean("pref", "stampaDestDiversaSotto", false) && stampa_dest_div) {
                ragione_sociale_1 = rCliente.getString("ragione_sociale");
                indirizzo_1 = rCliente.getString("indirizzo");
                String capLocProv = "";
                capLocProv += it.tnx.Db.nz(rCliente.getString("cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rCliente.getString("localita"), "");
                if (it.tnx.Db.nz(rCliente.getString("provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rCliente.getString("provincia"), "") + ")";
                }
                cap_loc_prov_1 = capLocProv;

                if (italian) {
                    if (!StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && !StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "P.IVA " + rCliente.getString("piva_cfiscale") + " Cod. Fisc. " + rCliente.getString("cfiscale");
                    } else if (StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && !StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "Cod. Fisc. " + rCliente.getString("cfiscale");
                    } else if (!StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "P.IVA " + rCliente.getString("piva_cfiscale");
                    }
                } else {
                    if (!StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && !StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "Vat no. " + rCliente.getString("piva_cfiscale") + " Vat Code " + rCliente.getString("cfiscale");
                    } else if (StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && !StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "Vat Code " + rCliente.getString("cfiscale");
                    } else if (!StringUtils.isEmpty(rCliente.getString("piva_cfiscale")) && StringUtils.isEmpty(rCliente.getString("cfiscale"))) {
                        piva_cfiscale_desc_1 = "Vat no. " + rCliente.getString("piva_cfiscale");
                    }
                }
                try {
                    recapito_1 = Db.lookUp(rCliente.getString("paese"), "codice1", "stati").getString("nome");
                } catch (Exception ex) {
                    recapito_1 = "";
                }
                if (!main.pluginAttivi.contains("pluginToysforyou")) {
                    if ("ITALY".equals(recapito_1)) {
                        recapito_1 = "";
                    }
                }
                recapito_1 = InvoicexUtil.aggiungi_recapiti(recapito_1, false, rCliente, rDocu);

                ragione_sociale_2 = rDocu.getString("dest_ragione_sociale");
                indirizzo_2 = rDocu.getString("dest_indirizzo");
                capLocProv = "";
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_localita"), "");
                if (it.tnx.Db.nz(rDocu.getString("dest_provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rDocu.getString("dest_provincia"), "") + ")";
                }
                cap_loc_prov_2 = capLocProv;
                piva_cfiscale_desc_2 = "";
                try {
                    recapito_2 = Db.lookUp(rDocu.getString("dest_paese"), "codice1", "stati").getString("nome");
                } catch (Exception ex) {
                    recapito_2 = "";
                }
                if (!main.pluginAttivi.contains("pluginToysforyou")) {
                    if ("ITALY".equals(recapito_2)) {
                        recapito_2 = "";
                    }
                }
                recapito_2 = InvoicexUtil.aggiungi_recapiti(recapito_2, true, rCliente, rDocu);
                recapito_2_sotto = recapito_2;
                email_2 = CastUtils.toString(rDocu.getString("email"));
                if (email_2.equals("")) {
                    email_2 = "";
                } else {
                    email_2 = "<br>Email: " + email_2;
                }
            } else {
                ragione_sociale_2 = rCliente.getString("ragione_sociale");
                indirizzo_2 = rCliente.getString("indirizzo");
                String capLocProv = "";
                capLocProv += it.tnx.Db.nz(rCliente.getString("cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rCliente.getString("localita"), "");
                if (it.tnx.Db.nz(rCliente.getString("provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rCliente.getString("provincia"), "") + ")";
                }
                cap_loc_prov_2 = capLocProv;

                // dm-edit-20081215 --- [start]:
                String piva_lbl = "";
                String cfisc_lbl = "";
                String piva_txt = rCliente.getString("piva_cfiscale");
                String cfisc_txt = rCliente.getString("cfiscale");
                if (italian) {
                    piva_lbl = "P.IVA";
                    cfisc_lbl = "Cod. Fisc.";
                } else {
                    piva_lbl = "Vat no.";
                    cfisc_lbl = "Vat Code";
                }
                piva_cfiscale_desc_2 = "";
                if (!StringUtils.isEmpty(piva_txt)) {
                    piva_cfiscale_desc_2 += piva_lbl + " " + piva_txt;
                }
                if (!StringUtils.isEmpty(cfisc_txt)) {
                    piva_cfiscale_desc_2 += (StringUtils.isBlank(piva_txt) ? "" : " ") + cfisc_lbl + " " + cfisc_txt;
                }
                piva_cfiscale_desc_2_sotto = piva_cfiscale_desc_2;
                // --- [end] dm-edit-20081215

                try {
                    recapito_2 = Db.lookUp(rCliente.getString("paese"), "codice1", "stati").getString("nome");
                } catch (Exception ex) {
                    recapito_2 = "";
                }
                if (!main.pluginAttivi.contains("pluginToysforyou")) {
                    if ("ITALY".equals(recapito_2)) {
                        recapito_2 = "";
                    }
                }
                recapito_2 = InvoicexUtil.aggiungi_recapiti(recapito_2, false, rCliente, rDocu);
                recapito_2_sotto = recapito_2;
                email_2 = CastUtils.toString(rCliente.getString("email"));
                if (email_2.equals("")) {
                    email_2 = "";
                } else {
                    email_2 = "<br>Email: " + email_2;
                }

                ragione_sociale_1 = rDocu.getString("dest_ragione_sociale");
                indirizzo_1 = rDocu.getString("dest_indirizzo");                
                capLocProv = "";
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_localita"), "");
                if (it.tnx.Db.nz(rDocu.getString("dest_provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rDocu.getString("dest_provincia"), "") + ")";
                }
                cap_loc_prov_1 = capLocProv;
                piva_cfiscale_desc_1 = "";
                try {
                    recapito_1 = Db.lookUp(rDocu.getString("dest_paese"), "codice1", "stati").getString("nome");
                } catch (Exception ex) {
                    recapito_1 = "";
                }
                if (!main.pluginAttivi.contains("pluginToysforyou")) {
                    if ("ITALY".equals(recapito_1)) {
                        recapito_1 = "";
                    }
                }
                recapito_1 = InvoicexUtil.aggiungi_recapiti(recapito_1, true, rCliente, rDocu);
            }
            if (!main.fileIni.getValue("pref", "tipoStampa", "").equalsIgnoreCase("fattura_mod2_piva_default.jrxml")) {
                if (!StringUtils.isBlank(recapito_2)) {
                    String temp = piva_cfiscale_desc_2;
                    piva_cfiscale_desc_2 = recapito_2;
                    recapito_2 = temp;
                }
                if (!StringUtils.isBlank(recapito_1)) {
                    String temp = piva_cfiscale_desc_1;
                    piva_cfiscale_desc_1 = recapito_1;
                    recapito_1 = temp;
                }
            }

            //--------------------------
            System.out.println("rdocue.prev");
            rDocu.previous();
        } catch (Exception err) {
            err.printStackTrace();
        }

        //seleziono le scadenze
        sql = "" + "select *" + " from scadenze" + " "
                + "where documento_serie = " + Db.pc(serie, Types.VARCHAR) + " "
                + "and documento_numero = " + numero + " "
                + "and documento_anno = " + anno + " "
                + "and documento_tipo = 'FA' "
                + "order by data_scadenza";

        try {

            Statement sScad = conn.createStatement();
            ResultSet rScad = sScad.executeQuery(sql);
            int contaScad = 0;

            while (rScad.next()) {
                contaScad++;
                scadenze_date.add(it.tnx.Util.formatDataItalian(rScad.getDate("data_scadenza")));
                scadenze_importi.add(valuta + "  " + it.tnx.Util.format2Decimali(rScad.getDouble("importo")));
            }

//            if (contaScad > 7) {
//                javax.swing.JOptionPane.showMessageDialog(null, "Le scadenze sono " + contaScad + " mentre in stampa ne entrano al massimo 7");
//            }
        } catch (Exception err) {
            err.printStackTrace();
        }

        //carico dati intestazione
        try {
            String sqlForn = "select clie_forn.*, fornitore from clie_forn join test_fatt on clie_forn.codice = test_fatt.fornitore";
            sqlForn += " where test_fatt.serie = '" + this.serie + "' and test_fatt.anno = " + this.anno + " and test_fatt.numero = " + this.numero;
            ResultSet rsForn = Db.openResultSet(sqlForn);
            boolean daforn = false;
            if (rsForn.next()) {
                daforn = true;
            }
            if (daforn && rsForn.getInt("fornitore") > 0) {
                Vector v = new Vector();
                String tempInt = "";
                if (!rsForn.getString("ragione_sociale").equals("")) {
                    tempInt = rsForn.getString("ragione_sociale"); // INTESTAZIONE 1
                    v.add(tempInt);
                }

                if (!rsForn.getString("indirizzo").equals("")) {
                    tempInt = rsForn.getString("indirizzo"); // INTESTAZIONE 2
                    v.add(tempInt);
                }

                if (!rsForn.getString("localita").equals("")) {
                    tempInt = rsForn.getString("localita"); // INTESTAZIONE 3 - PARTE 1
                }

                if (!rsForn.getString("cap").equals("")) {
                    tempInt += " (" + rsForn.getString("cap") + ")"; // INTESTAZIONE 3 - PARTE 2
                }

                if (!rsForn.getString("provincia").equals("")) {
                    tempInt += " " + rsForn.getString("provincia"); // INTESTAZIONE 3 - PARTE 3
                }

                if (!tempInt.equals("")) {
                    v.add(tempInt); // INTESTAZIONE 3 - INSERIMENTO!
                }
                Vector intestazioneTmp = new Vector();
                if (!rsForn.getString("piva_cfiscale").equals("")) {
                    intestazioneTmp.add("PI: " + rsForn.getString("piva_cfiscale")); // INTESTAZIONE 4
                }

                if (!rsForn.getString("piva_cfiscale").equals("")) {
                    intestazioneTmp.add("CF: " + rsForn.getString("cfiscale")); // INTESTAZIONE 4
                }

                if (!tempInt.equals("")) {
                    v.add(StringUtils.join(intestazioneTmp, " - "));
                }

                intestazioneTmp.clear();
                if (!rsForn.getString("telefono").equals("")) {
                    intestazioneTmp.add("Tel. " + rsForn.getString("telefono")); // INTESTAZIONE 5 - PARTE 1
                }

                if (!rsForn.getString("cellulare").equals("")) {
                    intestazioneTmp.add("Cell. " + rsForn.getString("cellulare")); // INTESTAZIONE 5 - PARTE 2
                }

                if (!rsForn.getString("fax").equals("")) {
                    intestazioneTmp.add("Fax. " + rsForn.getString("fax")); // INTESTAZIONE 5 - PARTE 3
                }

                if (!tempInt.equals("")) {
                    v.add(StringUtils.join(intestazioneTmp, " - "));
                }

                intestazioneTmp.clear();
                if (!rsForn.getString("email").equals("")) {
                    intestazioneTmp.add(rsForn.getString("email")); // INTESTAZIONE 6 - PARTE 1
                }

                if (!rsForn.getString("web").equals("")) {
                    intestazioneTmp.add(rsForn.getString("web")); // INTESTAZIONE 6 - PARTE 2
                }

                if (!tempInt.equals("")) {
                    v.add(StringUtils.join(intestazioneTmp, " - "));
                }

                try {
                    intestazione1 = (String) v.get(0);
                    intestazione2 = (String) v.get(1);
                    intestazione3 = (String) v.get(2);
                    intestazione4 = (String) v.get(3);
                    intestazione5 = (String) v.get(4);
                    intestazione6 = (String) v.get(5);
//                    etichettaCliente = "Fornitore";
//                    etichettaDestinazione = "Destinazione Merce";
                } catch (Exception e) {
                }

                rsForn.close();

                try {
                    Statement sDatiAzienda = conn.createStatement();
                    ResultSet rDatiAzienda = sDatiAzienda.executeQuery("select " + main.campiDatiAzienda + " from dati_azienda");
                    if (rDatiAzienda.next()) {
                        etichettaCliente = rDatiAzienda.getString("label_cliente");
                        etichettaDestinazione = rDatiAzienda.getString("label_destinazione");
                        etichettaCliente_eng = rDatiAzienda.getString("label_cliente_eng");
                        etichettaDestinazione_eng = rDatiAzienda.getString("label_destinazione_eng");
                        notePiede = rDatiAzienda.getString("testo_piede_fatt_v");
                        stampaInvoicexRiga = rDatiAzienda.getInt("stampa_riga_invoicex") == 1;
                    }
                    rDatiAzienda.close();
                    sDatiAzienda.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Statement sDatiAzienda = conn.createStatement();
                ResultSet rDatiAzienda = sDatiAzienda.executeQuery("select " + main.campiDatiAzienda + " from dati_azienda");
                if (rDatiAzienda.next()) {
                    intestazione1 = rDatiAzienda.getString("intestazione_riga1");
                    intestazione2 = rDatiAzienda.getString("intestazione_riga2");
                    intestazione3 = rDatiAzienda.getString("intestazione_riga3");
                    intestazione4 = rDatiAzienda.getString("intestazione_riga4");
                    intestazione5 = rDatiAzienda.getString("intestazione_riga5");
                    intestazione6 = rDatiAzienda.getString("intestazione_riga6");
                    etichettaCliente = rDatiAzienda.getString("label_cliente");
                    etichettaDestinazione = rDatiAzienda.getString("label_destinazione");
                    etichettaCliente_eng = rDatiAzienda.getString("label_cliente_eng");
                    etichettaDestinazione_eng = rDatiAzienda.getString("label_destinazione_eng");
                    notePiede = rDatiAzienda.getString("testo_piede_fatt_v");
                    stampaInvoicexRiga = rDatiAzienda.getInt("stampa_riga_invoicex") == 1;
                }
                rDatiAzienda.close();
                sDatiAzienda.close();
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public Object getFieldValue(net.sf.jasperreports.engine.JRField jRField)
            throws net.sf.jasperreports.engine.JRException {

//        System.out.println("jrfield: " + jRField.getName());
        
        try {
            // dest diversa
            if (jRField.getName().equalsIgnoreCase("ragione_sociale1")) {
                return ragione_sociale_1;
            } else if (jRField.getName().equalsIgnoreCase("indirizzo1")) {
                return indirizzo_1;
            } else if (jRField.getName().equalsIgnoreCase("cap_loc_prov1")) {
                return cap_loc_prov_1;
            } else if (jRField.getName().equalsIgnoreCase("piva_cfiscale_desc1")) {
                return piva_cfiscale_desc_1;
            } else if (jRField.getName().equalsIgnoreCase("recapito1")) {
                return recapito_1;
            } else if(jRField.getName().equalsIgnoreCase("email_1")) {
                return email_1;
            }

            if (jRField.getName().equalsIgnoreCase("ragione_sociale2")) {
                return ragione_sociale_2;
            } else if (jRField.getName().equalsIgnoreCase("indirizzo2")) {
                return indirizzo_2;
            } else if (jRField.getName().equalsIgnoreCase("cap_loc_prov2")) {
                return cap_loc_prov_2;
            } else if (jRField.getName().equalsIgnoreCase("piva_cfiscale_desc2")) {
                return piva_cfiscale_desc_2;
            } else if (jRField.getName().equalsIgnoreCase("piva_cfiscale_desc_2_sotto")) {
                if (recapito_2_sotto.length() > 0 && !recapito_2_sotto.endsWith("<br>") && !main.fileIni.getValueBoolean("pref", "stampaPivaSotto", false)) {
                    return "<br>" + piva_cfiscale_desc_2_sotto;
                } else {
                    return piva_cfiscale_desc_2_sotto;
                }
            } else if (jRField.getName().equalsIgnoreCase("recapito2")) {
                return recapito_2;
            } else if (jRField.getName().equalsIgnoreCase("recapito_2_sotto")) {
                return recapito_2_sotto;
            } else if(jRField.getName().equalsIgnoreCase("email_2")) {
                return email_2;
            }

            if (jRField.getName().equalsIgnoreCase("stampa_dest_div")) {
                return stampa_dest_div;
            } else if (jRField.getName().equalsIgnoreCase("etichetta_int1")) {
                if (main.fileIni.getValueBoolean("pref", "stampaDestDiversaSotto", false) && stampa_dest_div) {
                    if (italian) {
                        return etichettaCliente;
                    } else {
                        return etichettaCliente_eng;
                    }
                } else {
                    if (italian) {
                        return etichettaDestinazione;

                    } else {
                        return etichettaDestinazione_eng;
                    }
                }
            } else if (jRField.getName().equalsIgnoreCase("etichetta_int2")) {
                if (main.fileIni.getValueBoolean("pref", "stampaDestDiversaSotto", false) && stampa_dest_div) {
                    if (italian) {
                        return etichettaDestinazione;
                    } else {
                        return etichettaDestinazione_eng;
                    }
                } else {
                    if (italian) {
                        return etichettaCliente;
                    } else {
                        return etichettaCliente_eng;
                    }
                }
            }
            // -------------------

            //debug
            //System.out.println("conta:" + conta + " campo:" + jRField.getName() + " valore:" + rDocu.getString(jRField.getName()));
            if (jRField.getName().equalsIgnoreCase("ragione_sociale")) {
                return rCliente.getString(jRField.getName());
            } else if (jRField.getName().equalsIgnoreCase("indirizzo")) {
                return rCliente.getString(jRField.getName());
            } else if (jRField.getName().equalsIgnoreCase("piva_cfiscale")) {
                return rCliente.getString(jRField.getName());
            } else if (jRField.getName().equalsIgnoreCase("piva_cfiscale_desc")) {
                if (italian) {
                    return "P.IVA " + rCliente.getString("piva_cfiscale") + " Cod. Fisc. " + rCliente.getString("cfiscale");
                } else {
                    return "VAT no. " + rCliente.getString("piva_cfiscale") + " VAT Code " + rCliente.getString("cfiscale");
                }

            } else if (jRField.getName().equalsIgnoreCase("cap_loc_prov")) {
                String capLocProv = "";
                capLocProv += it.tnx.Db.nz(rCliente.getString("cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rCliente.getString("localita"), "");
                if (it.tnx.Db.nz(rCliente.getString("provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rCliente.getString("provincia"), "") + ")";
                }
                return capLocProv;
            } else if (jRField.getName().equalsIgnoreCase("dest_cap_loc_prov")) {
                String capLocProv = "";
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_cap"), "");
                if (capLocProv.length() > 0) {
                    capLocProv += " ";
                }
                capLocProv += it.tnx.Db.nz(rDocu.getString("dest_localita"), "");
                if (it.tnx.Db.nz(rDocu.getString("dest_provincia"), "").length() > 0) {
                    capLocProv += " (" + it.tnx.Db.nz(rDocu.getString("dest_provincia"), "") + ")";
                }
                return capLocProv;
            } else if (jRField.getName().equalsIgnoreCase("recapito")) {

                if (main.fileIni.getValueBoolean("pref", "stampaCellulare", true) == false) {
                    return "";
                }
                String recapito = "";

                if (it.tnx.Db.nz(rCliente.getString("telefono"), "").length() > 0) {
                    recapito += "Tel. " + rCliente.getString("telefono");
                }

                if (it.tnx.Db.nz(rCliente.getString("cellulare"), "").length() > 0) {

                    if (recapito.length() > 0) {
                        recapito += " ";
                    }
                    if (italian) {
                        recapito += "Cell. " + rCliente.getString("cellulare");
                    } else {
                        recapito += "Mobile " + rCliente.getString("cellulare");
                    }
                }

                return recapito;
            } else if (jRField.getName().equalsIgnoreCase("dest_recapito")) {

                if (main.fileIni.getValueBoolean("pref", "stampaCellulare", true) == false) {
                    return "";
                }
                String recapito = "";

                if (it.tnx.Db.nz(rDocu.getString("dest_telefono"), "").length() > 0) {
                    recapito += "Tel. " + rDocu.getString("dest_telefono");
                }

                if (it.tnx.Db.nz(rDocu.getString("dest_cellulare"), "").length() > 0) {

                    if (recapito.length() > 0) {
                        recapito += " ";
                    }
                    if (italian) {
                        recapito += "Cell. " + rDocu.getString("dest_cellulare");
                    } else {
                        recapito += "Mobile " + rCliente.getString("cellulare");
                    }
                }

                return recapito;
            } else if (jRField.getName().equalsIgnoreCase("numero_fattura")) {

                String num = "";

                if (rDocu.getInt("tipo_fattura") == dbFattura.TIPO_FATTURA_NOTA_DI_CREDITO) {
                    if (!main.fileIni.getValueBoolean("pref", "numerazioneNoteCredito", false)) {
                        if (it.tnx.Db.nz(rDocu.getString("serie"), "").length() > 0) {
                            num += rDocu.getString("serie") + "/";
                        }
                    }
                } else {
                    if (it.tnx.Db.nz(rDocu.getString("serie"), "").length() > 0) {
                        num += rDocu.getString("serie") + "/";                         
                    }
                }

                num += rDocu.getString("numero");
                
                try {
                    if (rDocu.getInt("anno") >= 2013) {
                        if (InvoicexUtil.getTipoNumerazione() == InvoicexUtil.TIPO_NUMERAZIONE_ANNO) {
                            num += "/" + rDocu.getString("anno");
                        } else if (InvoicexUtil.getTipoNumerazione() == InvoicexUtil.TIPO_NUMERAZIONE_ANNO_2CIFRE) {
                            num += "/" + StringUtils.right(rDocu.getString("anno"), 2);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // dm-edit-20081215:
                if (!main.fileIni.getValueBoolean("pref", "inclNumProForma", true)) {
                    if (rDocu.getInt("tipo_fattura") == dbFattura.TIPO_FATTURA_PROFORMA) {
                        num = "";
                    }
                }

                return num;
            } else if (jRField.getName().equalsIgnoreCase("sconti")) {
                String sconti = "";
                if (rDocu.getDouble("righ_fatt.sconto1") != 0) {
                    sconti = it.tnx.Util.formatNumero2Decimali(rDocu.getDouble("righ_fatt.sconto1"));
                    if (rDocu.getDouble("righ_fatt.sconto2") != 0) {
                        sconti += " + " + it.tnx.Util.formatNumero2Decimali(rDocu.getDouble("righ_fatt.sconto2"));
                    }
                }
                return sconti;
            } else if (jRField.getName().equalsIgnoreCase("s_quantita")) {

                String ret = "";

                if (rDocu.getDouble("quantita") != 0) {
                    ret = it.tnx.Util.formatNumero5Decimali(rDocu.getDouble("quantita"));
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_numcasse")) {
                String ret = "";

                if (rDocu.getDouble("numero_casse") != 0) {
                    ret = it.tnx.Util.int2str(rDocu.getInt("numero_casse"));
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_prezzo")) {

                String ret = "";

                if (!prezzi_ivati) {
                    if (rDocu.getDouble("prezzo") != 0) {
                        ret = Db.formatDecimal5(rDocu.getDouble("prezzo"));
                    }
                } else {
                    if (rDocu.getDouble("prezzo_ivato") != 0) {
                        ret = Db.formatDecimal5(rDocu.getDouble("prezzo_ivato"));
                    }
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("is_descrizione")) {
                return Db.nz(rDocu.getString("is_descrizione"), "N").equals("S");
            } else if (jRField.getName().equalsIgnoreCase("s_prezzo_netto")) {

                String ret = "";
                double importo = 0;
                double sconto1 = 0;
                double sconto2 = 0;
                double quantita = 0;

                try {
                    sconto1 = rDocu.getDouble("righ_fatt.sconto1");
                } catch (Exception err1) {
                    err1.printStackTrace();
                }

                try {
                    sconto2 = rDocu.getDouble("righ_fatt.sconto2");
                } catch (Exception err2) {
                    err2.printStackTrace();
                }

                try {
                    quantita = rDocu.getDouble("quantita");
                } catch (Exception err3) {
                    err3.printStackTrace();
                }

                try {
                    importo = rDocu.getDouble("prezzo");
                } catch (Exception err4) {
                    err4.printStackTrace();
                }

                importo = importo - (importo / 100 * sconto1);
                importo = importo - (importo / 100 * sconto2);

                if (importo != 0) {
                    ret = it.tnx.Util.format2Decimali(importo);
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_importo")) {
                String ret = "";
                if (!prezzi_ivati) {
                    if (rDocu.getDouble("righ_fatt.totale_imponibile") != 0 || rDocu.getDouble("righ_fatt.sconto1") == 100 || rDocu.getDouble("quantita") != 0) {
                        ret = it.tnx.Util.format2Decimali(rDocu.getDouble("righ_fatt.totale_imponibile"));
                    }
                } else {
                    if (rDocu.getDouble("righ_fatt.totale_ivato") != 0 || rDocu.getDouble("righ_fatt.sconto1") == 100 || rDocu.getDouble("quantita") != 0) {
                        ret = it.tnx.Util.format2Decimali(rDocu.getDouble("righ_fatt.totale_ivato"));
                    }
                }
                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_prezzo_scontato")) {

                String ret = "";
                double importo = 0;
                double sconto1 = 0;
                double sconto2 = 0;

                try {
                    sconto1 = rDocu.getDouble("righ_fatt.sconto1");
                } catch (Exception err1) {
                    err1.printStackTrace();
                }

                try {
                    sconto2 = rDocu.getDouble("righ_fatt.sconto2");
                } catch (Exception err2) {
                    err2.printStackTrace();
                }

                try {
                    importo = rDocu.getDouble("prezzo");
                } catch (Exception err4) {
                    err4.printStackTrace();
                }

                importo = importo - (importo / 100d * sconto1);
                importo = importo - (importo / 100d * sconto2);

                if (importo != 0) {
                    ret = it.tnx.Util.format2Decimali(importo);
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_iva")) {
                return rDocu.getString("iva");
            } else if (jRField.getName().equalsIgnoreCase("s_iva_old")) {
                String ret = "";
                if (rDocu.getDouble("iva") != 0) {
                    ret = it.tnx.Util.formatNumero0Decimali(rDocu.getDouble("iva"));
                }
                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_banca_sede")) {
                return banca_sede;
            } else if (jRField.getName().equalsIgnoreCase("s_banca_solo_sede")) {
                return banca_solo_sede;
            } else if (jRField.getName().equalsIgnoreCase("s_banca_agenzia")) {

                return banca_agenzia;
            } else if (jRField.getName().equalsIgnoreCase("s_banca_agenzia")) {

                return banca_agenzia;
            } else if (jRField.getName().equalsIgnoreCase("s_banca_iban")) {
                return banca_iban;

            }  else if (jRField.getName().equalsIgnoreCase("s_banca_swift")) {
                return banca_SWIFT;

            } else if (jRField.getName().equalsIgnoreCase("s_spese_trasporto")) {

                String ret = "";

                if (rDocu.getDouble("spese_trasporto") != 0) {
                    ret = valuta + " " + it.tnx.Util.format2Decimali(rDocu.getDouble("spese_trasporto"));
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_spese_incasso")) {

                String ret = "";

                if (rDocu.getDouble("spese_incasso") != 0) {
                    ret = valuta + " " + it.tnx.Util.format2Decimali(rDocu.getDouble("spese_incasso"));
                }

                return ret;
            } else if (jRField.getName().equalsIgnoreCase("s_sconti")) {
                String ret = "";
                if (rDocu.getDouble("test_fatt.sconto1") != 0) {
                    ret = it.tnx.Util.format2Decimali(rDocu.getDouble("test_fatt.sconto1"));
                    if (rDocu.getDouble("test_fatt.sconto2") != 0) {
                        ret += " + " + it.tnx.Util.format2Decimali(rDocu.getDouble("test_fatt.sconto2"));
                    }
                    if (rDocu.getDouble("test_fatt.sconto3") != 0) {
                        ret += " + " + it.tnx.Util.format2Decimali(rDocu.getDouble("test_fatt.sconto3"));
                    }
                }
                if (ret.length() > 0 && rDocu.getDouble("test_fatt.sconto") > 0) {
                    ret += ",  ";
                }
                if (rDocu.getDouble("test_fatt.sconto") > 0) {
                    ret += " - € " + FormatUtils.formatEuroIta(rDocu.getDouble("test_fatt.sconto"));
                }
                return ret;
            } else if (jRField.getName().equalsIgnoreCase("messaggio")) {
                String ret = main.fileIni.getValue("varie", "messaggioStampa");
                return ret;
            } else if (jRField.getName().equalsIgnoreCase("iva_codice_1")) {

                return iva_codice_1;
            } else if (jRField.getName().equalsIgnoreCase("nume_righe")) {

                return numRighe;
            } else if (jRField.getName().equalsIgnoreCase("iva_desc_1")) {

                return iva_desc_1;
            } else if (jRField.getName().equalsIgnoreCase("iva_imp_1")) {

                return iva_imp_1;
            } else if (jRField.getName().equalsIgnoreCase("iva_perc_1")) {

                return iva_perc_1;
            } else if (jRField.getName().equalsIgnoreCase("iva_imposta_1")) {

                return iva_imposta_1;
            } else if (jRField.getName().equalsIgnoreCase("iva_codice_2")) {

                return iva_codice_2;
            } else if (jRField.getName().equalsIgnoreCase("iva_desc_2")) {

                return iva_desc_2;
            } else if (jRField.getName().equalsIgnoreCase("iva_imp_2")) {

                return iva_imp_2;
            } else if (jRField.getName().equalsIgnoreCase("iva_perc_2")) {

                return iva_perc_2;
            } else if (jRField.getName().equalsIgnoreCase("iva_imposta_2")) {

                return iva_imposta_2;
            } else if (jRField.getName().equalsIgnoreCase("iva_codice_3")) {
                return iva_codice_3;
            } else if (jRField.getName().equalsIgnoreCase("iva_desc_3")) {
                return iva_desc_3;
            } else if (jRField.getName().equalsIgnoreCase("iva_imp_3")) {
                return iva_imp_3;
            } else if (jRField.getName().equalsIgnoreCase("iva_perc_3")) {
                return iva_perc_3;
            } else if (jRField.getName().equalsIgnoreCase("iva_imposta_3")) {
                return iva_imposta_3;
            } else if (jRField.getName().equalsIgnoreCase("iva_codice_4")) {
                return iva_codice_4;
            } else if (jRField.getName().equalsIgnoreCase("iva_desc_4")) {
                return iva_desc_4;
            } else if (jRField.getName().equalsIgnoreCase("iva_imp_4")) {
                return iva_imp_4;
            } else if (jRField.getName().equalsIgnoreCase("iva_perc_4")) {
                return iva_perc_4;
            } else if (jRField.getName().equalsIgnoreCase("iva_imposta_4")) {
                return iva_imposta_4;
            } else if (jRField.getName().startsWith("scadenze_data_")) {
                int i = CastUtils.toInteger0(StringUtils.substringAfterLast(jRField.getName(), "_"));
                if (scadenze_date.size() >= i) {
                    return scadenze_date.get(i - 1);
                } else {
                    return "";
                }
            } else if (jRField.getName().startsWith("scadenze_importo_")) {
                int i = CastUtils.toInteger0(StringUtils.substringAfterLast(jRField.getName(), "_"));
                if (scadenze_importi.size() >= i) {
                    return scadenze_importi.get(i - 1);
                } else {
                    return "";
                }
            } else if (jRField.getName().equalsIgnoreCase("s_totale_imponibile")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotaleImponibile());
            } else if (jRField.getName().equalsIgnoreCase("s_marca_da_bollo")) {
                if (main.getPersonalContain("bollo")) {
                    return valuta + "  " + it.tnx.Util.format2Decimali(marca_da_bollo);
                } else {
                    return valuta + "  " + it.tnx.Util.format2Decimali(0);
                }
            } else if (jRField.getName().equalsIgnoreCase("s_totale_imponibile_parziale")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotaleImponibileParziale());
            } else if (jRField.getName().equalsIgnoreCase("s_totale_iva")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotaleIva());
            } else if (jRField.getName().equalsIgnoreCase("s_totale")) {
                if (main.getPersonalContain("bollo")) {
                    return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotale() + marca_da_bollo);
                } else {
                    return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotale());
                }
            } else if (jRField.getName().equalsIgnoreCase("s_totale_ritenuta")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotale_ritenuta());
            } else if (jRField.getName().equalsIgnoreCase("s_totale_pagare")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotale_da_pagare());
            } else if (jRField.getName().equalsIgnoreCase("flag_ritenuta")) {
                if (doc.getRitenuta() == 0) {
                    return false;
                } else {
                    return true;
                }
            } else if (jRField.getName().equalsIgnoreCase("s_ritenuta")) {
                return doc.getRitenuta_descrizione();
            } else if (jRField.getName().equalsIgnoreCase("s_rivalsa")) {
                return doc.getRivalsa_inps_descrizione();
            } else if (jRField.getName().equalsIgnoreCase("s_totale_rivalsa")) {
                return valuta + "  " + it.tnx.Util.format2Decimali(doc.getTotale_rivalsa());
            } else if (jRField.getName().equalsIgnoreCase("file_logo")) {
                return getImg(true, false);
            } else if (jRField.getName().equalsIgnoreCase("file_logo_input")) {
                return getImg(true, true);
            } else if (jRField.getName().equalsIgnoreCase("file_sfondo_input")) {
                return getImg(false, true);
            } else if (jRField.getName().equalsIgnoreCase("acquisto")) {
                return false;
            } else if (jRField.getName().equalsIgnoreCase("intestazione1")) {
                return intestazione1;
            } else if (jRField.getName().equalsIgnoreCase("intestazione2")) {
                return intestazione2;
            } else if (jRField.getName().equalsIgnoreCase("intestazione3")) {
                return intestazione3;
            } else if (jRField.getName().equalsIgnoreCase("intestazione4")) {
                return intestazione4;
            } else if (jRField.getName().equalsIgnoreCase("intestazione5")) {
                return intestazione5;
            } else if (jRField.getName().equalsIgnoreCase("intestazione6")) {
                return intestazione6;
            } else if (jRField.getName().equalsIgnoreCase("note_da_impostazioni")) {
                return notePiede;
            } else if (jRField.getName().equalsIgnoreCase("stampa_riga_aggiuntiva")) {
                return stampaInvoicexRiga;
            } else if (jRField.getName().equalsIgnoreCase("totale_quantita")) {
                return Double.valueOf(this.doc.getTotaleQuantita());
            } else if (jRField.getName().equalsIgnoreCase("pagamento")) {
                if (main.getPersonalContain("pagamento_stampa_codice")) {
                    return Db.nz(rDocu.getString(jRField.getName()), "");
                } else {
                    try {
                        String codicePagamento = Db.nz(rDocu.getObject(jRField.getName()), "");
                        String descPagamento = String.valueOf(DbUtils.getObject(Db.getConn(), "SELECT descrizione FROM pagamenti WHERE codice = " + Db.pc(codicePagamento, Types.VARCHAR)));
                        if (StringUtils.isEmpty(descPagamento)) return codicePagamento;
                        return descPagamento;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Db.nz(rDocu.getString(jRField.getName()), "");
                    }
                }
            } else if (jRField.getName().equalsIgnoreCase("int_dest_1")) {
                String s = main.fileIni.getValue("varie", "int_dest_1");
                System.out.println("int_dest_1 prima = " + s);
                s = sostituisci(s);
                System.out.println("int_dest_1 dopo = " + s);
                return s;
            } else if (jRField.getName().equalsIgnoreCase("int_dest_2")) {
                String s = main.fileIni.getValue("varie", "int_dest_2");
                System.out.println("int_dest_2 prima = " + s);
                s = sostituisci(s);
                System.out.println("int_dest_2 dopo = " + s);
                return s;
//campi direttametne dal db
            } else if (jRField.getValueClassName().equals("java.lang.String")) {
//                System.out.println("campo da db string: = " + jRField.getName() + ":" + Db.nz(rDocu.getString(jRField.getName()), ""));
                return Db.nz(rDocu.getString(jRField.getName()), "");
            } else if (jRField.getValueClassName().equals("java.lang.Object")) {
//                System.out.println("campo da db object: = " + jRField.getName() + ":" + Db.nz(rDocu.getObject(jRField.getName()), ""));
                return Db.nz(rDocu.getObject(jRField.getName()), "");
            } else if (jRField.getValueClassName().equals("java.util.Date")) {
                return rDocu.getDate(jRField.getName());
            } else if (jRField.getValueClassName().equals("java.lang.Double")) {
                return new Double(rDocu.getDouble(jRField.getName()));
            } else if (jRField.getValueClassName().equals("java.lang.Integer")) {
                return new Integer(rDocu.getInt(jRField.getName()));
            } else if (jRField.getValueClassName().equals("java.lang.Long")) {
                return new Long(rDocu.getLong(jRField.getName()));
            } else {
                System.out.println("campo da db else: = " + jRField.getName() + ":" + rDocu.getObject(jRField.getName()));
                return rDocu.getObject(jRField.getName());
            }
        } catch (Exception err) {
            System.err.println("Errore nel campo: " + jRField.getName());
            err.printStackTrace();
        }

        return null;
    }

    private Object getImg(boolean isLogo, boolean isInputStream) {
        return getImg(isLogo, isInputStream, serie, numero, anno, perEmail, false, "test_fatt");
    }

    public static Object getImg(boolean isLogo, boolean isInputStream, String serie, int numero, int anno, boolean perEmail, boolean acquisto, String tab_t) {
        String varnon, file = null, fileFornitore = null;

//        System.out.println("getImg: debug: main.iniPercorsoSfondoStampePdf:" + main.iniPercorsoSfondoStampePdf);
//        System.out.println("getImg: debug: main.iniPercorsoSfondoStampe:" + main.iniPercorsoSfondoStampe);

        if (isLogo && isInputStream && main.fileIni.getValue("pref", "flag_immagini_da_db_per_client_manager", "S").equalsIgnoreCase("S")) {
            ResultSet r = Db.openResultSet("select logo from dati_azienda");
            try {
                if (r.next()) {
                    Blob blob = r.getBlob("logo");
                    return blob.getBinaryStream();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //cerco logo fornitore
        if (isLogo && !acquisto && tab_t.equals("test_ordi")) {
            try {
                String sql = "select logo, t.* from clie_forn join " + tab_t + " t on t.fornitore = clie_forn.codice "
                        + "where t.serie = '" + serie + "' and t.anno = " + anno + " and t.numero = " + numero + " and t.fornitore > 0";
                ResultSet forn = Db.openResultSet(sql);
                if (forn.next()) {
                    fileFornitore = forn.getString("logo");
                    if (fileFornitore != null && fileFornitore.length() > 0) {
                        if (isInputStream) {
                            try {
                                return new FileInputStream(fileFornitore);
                            } catch (Exception ex0) {
                                System.out.println("getImg:" + ex0);
                                ex0.printStackTrace();
                                return null;
                            }
                        } else {
                            return fileFornitore;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("errore in riceca logo fornitore JRDSInvoice getImg : " + e.getMessage());
//                e.printStackTrace();
            }
        }

        String campo = null;
        if (isLogo) {
            if (perEmail) {
                varnon = "non_stampare_logo_pdf";
                campo = "logo_email";
            } else {
                varnon = "non_stampare_logo";
                campo = "logo";
            }
        } else {
            if (perEmail) {
                varnon = "non_stampare_sfondo_pdf";
                file = main.iniPercorsoSfondoStampePdf;
                campo = "sfondo_email";
                System.out.println("getImg: debug: 3");
            } else {
                varnon = "non_stampare_sfondo";
                file = main.iniPercorsoSfondoStampe;
                campo = "sfondo";
                System.out.println("getImg: debug: 4");
            }
        }

        String nonStampare = main.fileIni.getValue("varie", varnon);
        if (nonStampare != null && nonStampare.equalsIgnoreCase("si")) {
            System.out.println("getImg: return null per non stampare");
            return null;
        } else {
//            File ftest = new File(file);
//            System.out.println("getImg: ftest:" + ftest.getAbsolutePath());
//            System.out.println("getImg: ftest:" + ftest.getAbsolutePath() + " ftest.exists():" + ftest.exists());
//            if (ftest.exists()) {
            if (isInputStream) {
                try {
//                        System.out.println("getImg:" + ftest);
//                        return new FileInputStream(ftest);
                    ResultSet r = Db.openResultSet("select " + campo + " from dati_azienda");
                    if (r.next()) {
                        Blob blob = r.getBlob(campo);
                        if (blob == null) {
                            return null;
                        }
                        return blob.getBinaryStream();
                    }
                } catch (Exception ex0) {
                    System.out.println("getImg:" + ex0);
                    ex0.printStackTrace();
                    return null;
                }
            } else {
                System.out.println("getImg: return file:" + file);
                //scarico img in tmp e passo
                File logotmp = new File(System.getProperty("user.home") + File.separator + ".invoicex" + File.separator + "tmp" + File.separator + campo);
                logotmp.getParentFile().mkdirs();
                try {
                    if (!filescaricati.containsKey(campo) || (Long)filescaricati.get(campo) < System.currentTimeMillis() - 30000) {
                        IOUtils.copy(InvoicexUtil.caricaLogoDaDb(Db.getConn(), campo), new FileOutputStream(logotmp));
                        filescaricati.put(campo, System.currentTimeMillis());
                        contalogo++;
                        System.out.println("contalogo = " + contalogo + " " + campo);
                    }
                    System.out.println("logotmp.getAbsolutePath() = " + logotmp.getAbsolutePath());
                    return logotmp.getAbsolutePath();
                } catch (NullPointerException e) {
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
//            } else {
//                System.out.println("getImg: return null, " + ftest + " non esiste");
//                return null;
//            }
        }
        return null;
    }

    public boolean next()
            throws net.sf.jasperreports.engine.JRException {
        conta++;

        try {

            boolean ret = rDocu.next();

            return ret;
        } catch (Exception err) {
            err.printStackTrace();
        }

        return false;
    }

    public static void main(String[] args) {

//        testReport t = new testReport();

        double t = 85.1d * 8.95d;
        System.out.println(t);
        System.out.println(it.tnx.Util.round(t, 2));

//        System.out.println(it.tnx.Util.round2(t, 2));
//        System.out.println(it.tnx.Util.round3(t, 2));

//        System.out.println("-----");
//
        t = 34.62d * 1.04d;
        System.out.println(t);
        System.out.println(it.tnx.Util.round(t, 2));
//        System.out.println(it.tnx.Util.round2(t, 2));
//        System.out.println(it.tnx.Util.round3(t, 2));

    }


}
