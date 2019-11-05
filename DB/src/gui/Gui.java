package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.BevelBorder;

public class Gui extends JFrame implements Querys{

	private JPanel contentPane;
	private JMenuItem mntmSpeichernUnter;
	private JList<String> listTables;
	private JTable table;
	private JLabel lblStatus;

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

		setLookAndFeel();

		// Vollbild
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

		setTitle("SSDB 0.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 301);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenuItem mntmImport = new JMenuItem("ODBC Datenbank Importieren");
		mnDatei.add(mntmImport);

		mntmSpeichernUnter = new JMenuItem("Speichern unter...");
		mntmSpeichernUnter.setEnabled(false);
		mntmSpeichernUnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clickSaveAs();

			}
		});
		mnDatei.add(mntmSpeichernUnter);

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
		panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		
		listTables = new JList<String>();
		listTables.setBorder(new CompoundBorder());
		listTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTables.setVisibleRowCount(15);
		JPanel panelTables = new JPanel();
		panelTables.setLayout(new BoxLayout(panelTables, BoxLayout.X_AXIS));
		JScrollPane scrollPane = new JScrollPane(listTables);
		//scrollPane.getViewport().setView(listTables);
		panelTables.add(scrollPane);
		
		
		JPanel panelData = new JPanel();
		panelData.setLayout(new BoxLayout(panelData, BoxLayout.X_AXIS));
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"
			}
		));
		JScrollPane scrollPaneData = new JScrollPane(table);
		panelData.add(scrollPaneData);
		
		contentPane.add(panelData, BorderLayout.CENTER);
		contentPane.add(panelTables, BorderLayout.WEST);
		
		JLabel lblNewLabel = new JLabel("Tabellen");
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		contentPane.add(panelStatus, BorderLayout.SOUTH);
		panelStatus.setLayout(new BoxLayout(panelStatus, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel("Status:");
		panelStatus.add(lblNewLabel_1);
		
		lblStatus = new JLabel("Ready");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		panelStatus.add(lblStatus);
		
		

	}

	private void setLookAndFeel() {
		// System Look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	protected void clickSaveAs() {

	}

	protected void clickImport() {
		// DB öffnen
		File fileDB = MyFiles.openFile(this);

		// Dialog save as anbieten
		mntmSpeichernUnter.setEnabled(true);

		// Verbindung zur Datenbank aufbauen
		String path = fileDB.getAbsolutePath();
		Connection con = DBConnect.getConnection(path);

		//Alle Tabellen der DB auflisten
		DBConnect.listTables();
		listTables.setListData(DBConnect.getlistOfTables());
	    lblStatus.setText("DB geladen");

	}
}
