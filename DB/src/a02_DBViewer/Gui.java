package a02_DBViewer;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.FlowLayout;

public class Gui extends JFrame implements Querys {
	
	DBConnect db;

	private static final String DATENBANK_GESCHLOSSEN = "Datenbank geschlossen";
	private static final String DATENBANK_SCHLIESSEN = "Datenbank schliessen";
	private static final String DATEI = "Datei";
	private static final String TITEL = "SSDB 0.1";
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;

	private static final String ODBC_DATENBANK_IMPORTIEREN = "ODBC Datenbank Importieren";
	private static final String SPEICHERN_UNTER = "Speichern unter...";
	private static final String TABELLEN = "Tabellen";
	private static final String STATUS_READY = "Ready";
	private static final int ANZAHL_REIHEN_TABELLENLISTE = 15;

	private JPanel contentPane;
	private JMenuItem mntmSpeichernUnter;
	private JMenuItem mntmDatenbankSchliessen;
	private JMenuItem mntmImport;
	private JList<String> listTables;
	private JLabel lblStatus;
	private JPanel panelTableList;
	private JPanel panelData;
	private JPanel[] panelTables;

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

		db = new DBConnect(this);
		
		setLookAndFeel();
		setSize(WIDTH, HEIGHT);
		setTitle(TITEL);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 871, 301);

		setMenu();
		buildContenpane();
		addListeners();
		setContentPane(contentPane);

		JPanel panelTop = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelTop.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelTop, BorderLayout.NORTH);

		JLabel lblTabellen = new JLabel(TABELLEN);
		panelTop.add(lblTabellen);

	}

	private void buildContenpane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

		listTables = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(listTables);
		listTables.setBorder(new CompoundBorder());
		listTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTables.setVisibleRowCount(ANZAHL_REIHEN_TABELLENLISTE);

		panelTableList = new JPanel();
		panelTableList.setLayout(new BoxLayout(panelTableList, BoxLayout.X_AXIS));
		panelTableList.add(scrollPane);

		panelData = new JPanel();
		panelData.setLayout(new CardLayout(0, 0));

		final JPanel panelStatus = new JPanel();
		panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panelStatus.setLayout(new BoxLayout(panelStatus, BoxLayout.X_AXIS));
		JLabel lblNewLabel_1 = new JLabel("Status:");
		lblStatus = new JLabel(STATUS_READY);
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		panelStatus.add(lblNewLabel_1);
		panelStatus.add(lblStatus);
		contentPane.add(panelStatus, BorderLayout.SOUTH);
		contentPane.add(panelData, BorderLayout.CENTER);
		contentPane.add(panelTableList, BorderLayout.WEST);

	}

	private void addListeners() {

		mntmImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clickImport();
			}
		});

		mntmDatenbankSchliessen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickCloseDB();
			}
		});

		listTables.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					// Tabellenname der selektierten Tabelle der Liste
					String tableName = listTables.getSelectedValue();
					int tableIndex = listTables.getSelectedIndex();
					lblStatus.setText("Tabelle " + tableName + " gewählt");

					// Alle Tabellen aublenden
					for (int i = 0; i < panelTables.length; i++) {
						panelTables[i].setVisible(false);
					}

					// Aktive Tabelle einblenden
					panelTables[tableIndex].setVisible(true);

				}
			}
		});

		mntmSpeichernUnter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clickSaveAs();
			}
		});
	}

	protected void clickCloseDB() {
		// alle Panels aus panelData entfernen
		panelData.removeAll();
		panelTableList.removeAll();
		listTables = null;
		contentPane.removeAll();

		buildContenpane();
		addListeners();
		setContentPane(contentPane);
		revalidate();

		mntmDatenbankSchliessen.setEnabled(false);
		mntmImport.setEnabled(true);
		lblStatus.setText(DATENBANK_GESCHLOSSEN);

	}

	private void setMenu() {
		final JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		final JMenu mnDatei = new JMenu(DATEI);
		menuBar.add(mnDatei);

		mntmImport = new JMenuItem(ODBC_DATENBANK_IMPORTIEREN);
		mnDatei.add(mntmImport);

		mntmDatenbankSchliessen = new JMenuItem(DATENBANK_SCHLIESSEN);
		mnDatei.add(mntmDatenbankSchliessen);
		mntmDatenbankSchliessen.setEnabled(false);

		mntmSpeichernUnter = new JMenuItem(SPEICHERN_UNTER);
		mntmSpeichernUnter.setEnabled(false);

		mnDatei.add(mntmSpeichernUnter);

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
		File fileDB = IOFiles.openFile(this);

		// Prüfen ob File vorhanden, abbrechen wenn nicht
		if (fileDB == null) {
			return;
		}

		// Konifguriere Menüeinträge
		mntmDatenbankSchliessen.setEnabled(true);
		mntmImport.setEnabled(false);
		mntmSpeichernUnter.setEnabled(true);

		// Verbindung zur Datenbank aufbauen
		String path = fileDB.getAbsolutePath();
		Connection con = db.getConnection(path);

		// Alle Tabellen der DB auflisten
		db.listTables();
		String[] tables = db.getlistOfTables();

		/*
		 * Für jede Tabelle ein JPanel mit JScrollpane inkl JTable erzeugen und dem
		 * Panel panelData hinzufügen Das panelData hat ein Cardlayout mit welchem
		 * später zwischen den Tabellen umegschaltet werden kann
		 */
		int anzahlTabellen = tables.length;
		panelTables = new JPanel[anzahlTabellen];

		for (int i = 0; i < tables.length; i++) {
			JTable newJTable = db.buildJTable(tables[i]);
			JScrollPane sp = new JScrollPane(newJTable);
			panelTables[i] = new JPanel();
			panelTables[i].setLayout(new BoxLayout(panelTables[i], BoxLayout.X_AXIS));
			panelTables[i].add(sp);
			panelData.add(panelTables[i]);
		}

		// Tabellenliste anzeigen
		listTables.setListData(tables);

		// Status aktualisieren
		lblStatus.setText("Datenbank " + fileDB.getName() + " importiert.");

	}
}
