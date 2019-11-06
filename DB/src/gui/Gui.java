package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;

public class Gui extends JFrame implements Querys {

	private static final String SKRIPT = "Skript";
	private static final String ODBC_DATENBANK_IMPORTIEREN = "ODBC Datenbank Importieren";
	private static final String SPEICHERN_UNTER = "Speichern unter...";
	private static final String STAMMDATEN = "Stammdaten";
	private static final String NACHLASSSTUNDEN = "Nachlassstunden";
	private static final String LEHRER = "Lehrer";
	private static final String RÄUME = "Räume";
	private static final String KLASSEN = "Klassen";
	private static final String HILFE = "Hilfe";
	private static final String TABELLEN = "Tabellen";
	private static final String STATUS_READY = "Ready";
	private static final int ANZAHL_REIHEN_TABELLENLISTE = 15;

	private JPanel contentPane;
	private JMenuItem mntmSpeichernUnter;
	private JList<String> listTables;
	private JTable jTableDummy;
	private JLabel lblStatus;
	private JPanel panelTableList;
	private JPanel panelData;
	private JScrollPane scrollPaneData;
	private JPanel[] panelTables;
	private JPanel panelDummy;

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

		JMenuItem mntmImport = new JMenuItem(ODBC_DATENBANK_IMPORTIEREN);
		mnDatei.add(mntmImport);

		mntmSpeichernUnter = new JMenuItem(SPEICHERN_UNTER);
		mntmSpeichernUnter.setEnabled(false);
		mntmSpeichernUnter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clickSaveAs();

			}
		});
		mnDatei.add(mntmSpeichernUnter);

		JMenu mnStammdaten = new JMenu(STAMMDATEN);
		menuBar.add(mnStammdaten);

		JMenuItem mntmNachlassstunden = new JMenuItem(NACHLASSSTUNDEN);
		mnStammdaten.add(mntmNachlassstunden);

		JMenuItem mntmLehrer = new JMenuItem(LEHRER);
		mnStammdaten.add(mntmLehrer);

		JMenuItem mntmRume = new JMenuItem(RÄUME);
		mnStammdaten.add(mntmRume);

		JMenuItem mntmKlassen = new JMenuItem(KLASSEN);
		mnStammdaten.add(mntmKlassen);

		JMenu mnHilfe = new JMenu(HILFE);
		menuBar.add(mnHilfe);

		JMenuItem mntmSkript = new JMenuItem(SKRIPT);
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

		panelTableList = new JPanel();
		panelData = new JPanel();

		listTables = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(listTables);
		listTables.setBorder(new CompoundBorder());
		listTables.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listTables.setVisibleRowCount(ANZAHL_REIHEN_TABELLENLISTE);
		listTables.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {

					// Tabellenname der selektierten Tabelle der Liste
					String tableName = listTables.getSelectedValue();
					int tableIndex = listTables.getSelectedIndex();
					lblStatus.setText("Tabelle " + tableName + " gewählt");

					// Alle Daten der Tabelle anzeigen
					// jTable = null;
					// jTable = DBConnect.buildJTable(tableName);
					// jTable.revalidate();

					// testausgabe -> geht
					// JOptionPane.showMessageDialog(null, new JScrollPane(jTable));

					// Alle Tabellen aublenden
					panelDummy.setVisible(false);
					for (int i = 0; i < panelTables.length; i++) {
						panelTables[i].setVisible(false);
					}

					// Aktive Tabelle einblenden
					panelTables[tableIndex].setVisible(true);
				}
			}
		});

		panelTableList.setLayout(new BoxLayout(panelTableList, BoxLayout.X_AXIS));
		panelTableList.add(scrollPane);
		panelData.setLayout(new CardLayout(0, 0));

		// dummy table
		jTableDummy = new JTable();
		jTableDummy.setModel(new DefaultTableModel(
				new Object[][] { { null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null },
						{ null, null, null, null, null, null, null, null, null, null }, },
				new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" }));
		JScrollPane scrollPaneDummy = new JScrollPane(jTableDummy);
		panelDummy = new JPanel();
		panelDummy.setLayout(new BoxLayout(panelDummy, BoxLayout.X_AXIS));
		panelDummy.add(scrollPaneDummy);
		panelData.add(panelDummy);

		contentPane.add(panelData, BorderLayout.CENTER);
		contentPane.add(panelTableList, BorderLayout.WEST);

		JLabel lblNewLabel = new JLabel(TABELLEN);
		contentPane.add(lblNewLabel, BorderLayout.NORTH);
		contentPane.add(panelStatus, BorderLayout.SOUTH);
		panelStatus.setLayout(new BoxLayout(panelStatus, BoxLayout.X_AXIS));

		JLabel lblNewLabel_1 = new JLabel("Status:");
		panelStatus.add(lblNewLabel_1);

		lblStatus = new JLabel(STATUS_READY);
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

		// Alle Tabellen der DB auflisten
		DBConnect.listTables();
		String[] tables = DBConnect.getlistOfTables();
		listTables.setListData(tables);
		lblStatus.setText("DB geladen");

		/*
		 * Für jede Tabelle ein JScrollPanel inkl JTable erzeugen und dem Panel
		 * panelData hinzufügen Das panelData hat ein Cardlayout mit welchem später
		 * zwischen den Tabellen umegschaltet werden kann
		 */
		int anzahlTabellen = tables.length;
		panelTables = new JPanel[anzahlTabellen];

		for (int i = 0; i < tables.length; i++) {
			JTable newJTable = DBConnect.buildJTable(tables[i]);
			JScrollPane sp = new JScrollPane(newJTable);
			panelTables[i] = new JPanel();
			panelTables[i].setLayout(new BoxLayout(panelTables[i], BoxLayout.X_AXIS));
			panelTables[i].add(sp);
			panelData.add(panelTables[i]);

		}
		
		lblStatus.setText("Tabellenansichten erzeugt");
		

	}
}
