package a02_DBViewer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class IOFiles implements ErrorMessage {

	private static final String FILE_EXTENSION_MDB = "mdb";
	private static final String ACCESS_MDB_FILE = "Access *.mdb File";
	private static final String FILE_EXTENSUION_ACCDB = "accdb";
	private static final String ACCESS_ACCDB_FILE = "Access *.accdb File";
	
	static File fileDB;
	static Gui parent;

	public static File openFile(Gui parent) {
		IOFiles.parent = parent;

		// DB Datei auswaehlen Dialog
		JFileChooser fc = new JFileChooser();

		// Nur Access DB Dateien oeffnen
		FileFilter filterAccdb = new FileNameExtensionFilter(ACCESS_ACCDB_FILE, FILE_EXTENSUION_ACCDB);
		FileFilter filterMdb = new FileNameExtensionFilter(ACCESS_MDB_FILE, FILE_EXTENSION_MDB);
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
			JOptionPane.showMessageDialog(parent, ERROR_MESSAGE_FILE_NOT_FOUND, ERROR_TYPE_IO, JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			// EDIT: No checks needed here. Just a copy option in case the file already
			// exists
			Files.copy(pathFromIn, pathToOut, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, ERROR_MESSAGE_IO, ERROR_TYPE_IO, JOptionPane.ERROR_MESSAGE);
		}
	}

}
