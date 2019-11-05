package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFiles {

	static File fileDB;
	static Gui parent;

	public static File openFile(Gui parent) {
		MyFiles.parent = parent;
		
		// DB Datei auswÃ¤hlen Dialog
		JFileChooser fc = new JFileChooser();

		// Nur Access DB Dateien oeffnen
		FileFilter filter = new FileNameExtensionFilter("accdb", "mdb");
		fc.addChoosableFileFilter(filter);

		// Dialog zum Oeffnen von Dateien anzeigen
		int returnValue = fc.showOpenDialog(parent);

		/* Abfrage, ob auf oeffnen" geklickt wurde */
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			fileDB = fc.getSelectedFile();
		}

		return fileDB;
	}

	public static void saveFile(File f, String path) {

		try {
			File fSrc = f; // Quelldatei
			File fDes = new File(path); // Zieldatei
			
			FileInputStream fis;

			fis = new FileInputStream(fSrc);
			// Stream fuer Quelldatei
			FileOutputStream fos = new FileOutputStream(fDes); // Stream fuer
																// Zieldatei

			byte buf[] = new byte[1024]; // Buffer für gelesene Daten
			while (fis.read(buf) != -1) { // solange lesen, bis EOF
				fos.write(buf); // Inhalt schreiben
			}
			fis.close();
			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "File not found", "IO Error", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Schreib-Lese Fehler", "IO Error", JOptionPane.ERROR_MESSAGE);
			
			
		}
	}

}
