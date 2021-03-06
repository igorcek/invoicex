/*
 * update.java
 *
 * Created on 22 marzo 2002, 21.05
 */

package it.tnx;
import java.io.*;
//import com.oroinc.net.ftp.*;
import java.util.jar.*;

/**
 *
 * @author  Administrator
 */

public class Update extends Thread {
    //public frmMenu parent;
    private String host;
    private String login;
    private String pwd;
    private String file;
    private String path;
    private javax.swing.JProgressBar progress;
    private javax.swing.JLabel label;
    
    public int ret = -1;
    public static final int RET_UPDATE_OK = 1;
    public static final int RET_UPDATE_KO = 0;
    
    public Update(String host, String login, String pwd, String file, String path, javax.swing.JProgressBar progress, javax.swing.JLabel label) {
        this.host = host;
        this.login = login;
        this.pwd = pwd;
        this.file = file;
        this.path = path;
        this.progress = progress;
        this.label = label;
        this.setPriority(Thread.NORM_PRIORITY - 1);
        this.start();
    }
    
    public void run() {
        //parent.butUpda.setEnabled(false);
        
        boolean ret = false;
        
        String surl = "http://www.tnx.it/pagine/javaupdate/" + file;
        System.out.println("collegamento a:" + surl);
        try {
            java.net.URL url = new java.net.URL(surl);
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            label.setText("aggiornamento : scaricamento file '" + file + "'");
            progress.setIndeterminate(true);
            byte[] buf = new byte[256];
            int contaByte = 0;
            while (bis.read(buf) >= 0) {
                contaByte += 256;
                System.out.println("letto:" + new String(buf));
                label.setText("aggiornamento : scaricamento file - " + (contaByte / 1024) + " kb");
            }
            label.setText("aggiornamento : in corso, decompressione dei files" );
            //decompressione !!!
            UnZip unzip = new UnZip();
            String[] args = {file};
            unzip.main(args);
            label.setText("aggiornamento : completato");
            javax.swing.JWindow frame = (javax.swing.JWindow)progress.getTopLevelAncestor();
            frame.dispose();
            int ret2 = javax.swing.JOptionPane.showConfirmDialog(progress.getTopLevelAncestor(),"Aggiornamento completato, le modifiche saranno apportate\n al prossimo avvio dell'applicazione.\nPremere OK per uscire.","Attenzione",javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (ret2 == javax.swing.JOptionPane.OK_OPTION) {
              this.destroy();
              //it.tnx.philogen.Main.exitMain();
            }
            
        } catch (Exception err) {
            err.printStackTrace();
        }
        
        /* SOSTIUISCO CON SCARICAMENTO DEL FILE VIA HTTTP */
        /*
        //accesso all'ftp
        com.oroinc.net.ftp.FTPClient ftp = new FTPClient();
        try {
          int reply;
          //parent.addStatus("Waiting connection for '" + main.iniHost + "'");
          label.setText("aggiornamento : collegamento in corso");
          System.out.println("Connecting to " + this.host);
          ftp.connect(this.host);
          //parent.addStatus("Connected to '" + main.iniHost + "'");
         
          System.out.println("Connected to " + this.host);
          System.out.print(ftp.getReplyString());
         
         
          // After connection attempt, you should check the reply code to verify
          // success.
          reply = ftp.getReplyCode();
          if(!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            //parent.addStatus("ERROR! FTP server refused connection");
            label.setText("aggiornamento : ERRORE, connessione impossibile");
         
            System.err.println("FTP server refused connection.");
         
            javax.swing.JWindow frame = (javax.swing.JWindow)progress.getTopLevelAncestor();
            frame.dispose();
            javax.swing.JOptionPane.showMessageDialog(progress.getTopLevelAncestor(),"ERRORE, Aggiornamento non riuscito");
            this.stop();
          }
         
          ftp.login(login,pwd);
          //FTPFile[] files = ftp.listFiles();
          String[] names = ftp.listNames();
         
          if (path.length() > 0) {
            ret = ftp.changeWorkingDirectory(path);
            //debug
            System.out.println("change dir:" + ret);
         
          }
          FTPFile[] files = ftp.listFiles(file);
         
          if (files == null) {
            javax.swing.JWindow frame = (javax.swing.JWindow)progress.getTopLevelAncestor();
            frame.dispose();
            javax.swing.JOptionPane.showMessageDialog(progress.getTopLevelAncestor(),"Non ci sono aggioranmenti da scaricare");
            this.stop();
          }
         
          //debug
          System.out.println("list file:" + files.length);
         
          boolean faccioDownload = true;
          File fileLocale = new File(file);
          if (fileLocale.exists()) {
            //parent.addStatus("Check if update or not");
            System.out.println("da finire controllo data");
          }
         
          //download
          if (faccioDownload == true) {
            ftp.setFileType(ftp.BINARY_FILE_TYPE) ;
            //parent.addStatus("aggiornamento : scaricamento file '" + files[0].getName() + "'");
            label.setText("aggiornamento : scaricamento file '" + files[0].getName() + "'");
            System.out.println("inizio dowload del file:" + files[0].getName() + " - " + files[0].getTimestamp());
         
            InputStream inFtp = ftp.retrieveFileStream(file);
         
            //parent.setPercMin(0);
            progress.setMinimum(0);
            //parent.setPercMax((int)(files[0].getSize() / 512));
            progress.setMaximum((int)(files[0].getSize() / 512));
            progress.setMaximum((int)(progress.getMaximum() + (progress.getMaximum() / 100 * 25)));
            File out = new File(file);
            FileOutputStream outFile = new FileOutputStream(out);
            try {
               // R E A D / W R I T E by chunks
               int chunkSize = 512;
               // code will work even when chunkSize = 0 or chunks = 0;
               // Even for small files, we allocate a big buffer, since we
               // don't know the size ahead of time.
               byte[] ba = new byte[chunkSize];
               // keep reading till hit eof
               int conta=0;
               while ( true ) {
                  int bytesRead = readBlocking (inFtp, ba, 0, chunkSize);
                  //debug
                  //System.out.println(bytesRead);
                  if ( bytesRead > 0 ) {
                     conta++;
                     //parent.setPerc(conta);
                     progress.setValue(conta);
                     outFile.write(ba,
                                  0,
                                  bytesRead);
                  } else {
                     break; // hit eof
                  }
               } // end while
               // C L O S E, done by caller if wanted.
            } catch ( IOException e ) {
              e.printStackTrace();
            }
            outFile.flush();
            outFile.close();
         
            //ftp.retrieveFile(main.iniFile,fileDownload);
            //download in corso
            //debug
            //parent.addStatus("Download of '" + files[0].getName() + "' completed");
            label.setText("aggiornamento : scaricamento completato del file '" + files[0].getName() + "'");
            System.out.println("fine dowload del file");
            //decompressione
            //parent.addStatus("Launching downloaded file");
            label.setText("aggiornamento : in corso, decompressione dei files" );
         
            //decompressione !!!
            //Runtime.getRuntime().exec(out.getAbsolutePath());
            UnZip unzip = new UnZip();
            //String[] args = {"gf.zip"};
            String[] args = {file};
            unzip.main(args);
         
            //parent.addStatus("Update completed");
            label.setText("aggiornamento : completato");
         
            javax.swing.JWindow frame = (javax.swing.JWindow)progress.getTopLevelAncestor();
            frame.dispose();
            int ret2 = javax.swing.JOptionPane.showConfirmDialog(progress.getTopLevelAncestor(),"Aggiornamento completato, le modifiche saranno apportate\n al prossimo avvio dell'applicazione.\nPremere OK per uscire.","Attenzione",javax.swing.JOptionPane.OK_CANCEL_OPTION);
            if (ret2 == javax.swing.JOptionPane.OK_OPTION) {
              this.destroy();
              //it.tnx.philogen.Main.exitMain();
            }
          }
        } catch(IOException err) {
          //parent.addStatus("ERRORE !!! " + err.toString());
          label.setText("aggiornamento : ERRORE, " + err.toString());
          System.err.println("Errore");
          if(ftp.isConnected()) {
            try {
              //parent.butUpda.setEnabled(true);
              ftp.disconnect();
            } catch(IOException f) {
              // do nothing
              //parent.butUpda.setEnabled(true);
            }
          }
          //parent.butUpda.setEnabled(true);
          System.err.println("Errore");
          err.printStackTrace();
          //System.exit(1);
         
          javax.swing.JWindow frame = (javax.swing.JWindow)progress.getTopLevelAncestor();
          frame.dispose();
          javax.swing.JOptionPane.showMessageDialog(progress.getTopLevelAncestor(),"ERRORE, Aggiornamento non riuscito\nControllare di essere collegati ad internet");
          this.stop();
        }
         */
    }
    
    /*
    public final int readBlocking(InputStream in,
            byte b[],
            long off,
            int len) throws IOException {
        int totalBytesRead = 0;
     
        while ( totalBytesRead < len ) {
            int bytesRead = in.read(b, (int)(off + totalBytesRead), len -
                    totalBytesRead);
            if ( bytesRead < 0 ) {
                break;
            }
            totalBytesRead += bytesRead;
        }
        return totalBytesRead;
    } // end readBlocking
     */
}