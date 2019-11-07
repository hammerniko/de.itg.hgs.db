package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFiles {

	private static final String ERROR_TYPE_IO = "IO Error";
	private static final String ERROR_MESSAGE_IO = "Schreib-Lese Fehler";
	private static final String ERROR_MESSAGE_FILE_NOT_FOUND = "File not found";
	static File fileDB;
	static Gui parent;

	public static File openFile(Gui parent) {
		MyFiles.parent = parent;

		// DB Datei auswählen Dialog
		JFileChooser fc = new JFileChooser();

		// Nur Access DB Dateien oeffnen
		FileFilter filterAccdb = new FileNameExtensionFilter("Access *.accdb File", "accdb");
		FileFilter filterMdb = new FileNameExtensionFilter("Access *.mdb File", "mdb");
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(filterAccdb);
		fc.addChoosableFileFilter(filterMdb);
		

		// Dialog zum Oeffnen von Dateien anzeigen
		int returnValue = fc.showOpenDialog(parent);

		/* Abfrage, ob auf oeffnen" geklickt wurde */
		if (returnValue == JFileChooser.APPROVE_OPTION) {

			fileDB = fc.getSelectedFile();
		}

		return fileDB;
	}

	public static void saveFile(File f, String path) {
		Path pathToOut = Paths.get(path);
		Path pathFromIn = Paths.get(f.getAbsolutePath());
		if (!Files.exists(pathFromIn)) {
			JOptionPane.showMessageDialog(parent, "File not found", "IO Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			// EDIT: No checks needed here. Just a copy option in case the file already
			// exists
			Files.copy(pathFromIn, pathToOut, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Schreib-Lese Fehler", "IO Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
