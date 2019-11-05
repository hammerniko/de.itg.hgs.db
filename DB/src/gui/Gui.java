package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.CardLayout;
import java.awt.Panel;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTabbedPane;


public class Gui extends JFrame {

	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		//Vollbild
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		setTitle("SSDB 0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 301);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);
		
		JMenuItem mntmImport = new JMenuItem("Import ");
		mnDatei.add(mntmImport);
		
		JMenu mnStammdaten = new JMenu("Stammdaten");
		menuBar.add(mnStammdaten);
		
		JMenuItem mntmNachlassstunden = new JMenuItem("Nachlassstunden");
		mnStammdaten.add(mntmNachlassstunden);
		
		JMenuItem mntmLehrer = new JMenuItem("Lehrer");
		mnStammdaten.add(mntmLehrer);
		
		JMenuItem mntmRume = new JMenuItem("Räume");
		mnStammdaten.add(mntmRume);
		
		JMenuItem mntmKlassen = new JMenuItem("Klassen");
		mnStammdaten.add(mntmKlassen);
		
		JMenu mnHilfe = new JMenu("Hilfe");
		menuBar.add(mnHilfe);
		
		JMenuItem mntmSkript = new JMenuItem("Skript");
		mnHilfe.add(mntmSkript);
		
		
		mntmImport.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				clickImport();			
			}
		});
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		final JPanel panelStatus = new JPanel();
		contentPane.add(panelStatus, BorderLayout.SOUTH);
	
	}

	

	protected void clickImport() {
		//DB öffnen
		File fileDB = MyFiles.openFile(this);
		Path pathQuellen = FileSystems.getDefault().getPath("src", "quellen");
		
		//Kopie der Datei lokal speichern
		MyFiles.saveFile(fileDB, pathQuellen.toString());
		
		//Verbindung zur lokalen DB herstellen
		
	
	}
}
