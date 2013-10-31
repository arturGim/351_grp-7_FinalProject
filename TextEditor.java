package notetakingNoTabs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;

import rtf.AdvancedRTFDocument;
import rtf.AdvancedRTFEditorKit;

public class TextEditor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// base variables//
	private JFileChooser fileDialog;
	private String curFile = "Untitled";
	private JTextPane txtPane = new JTextPane();
	private boolean changed = false;
	private static int windowNum = 0;
	private JLabel status;
	private List<String> keywordLst = new ArrayList<String>();
	private WrapEditorKit rtfKit = new WrapEditorKit();
	private JComboBox fontCombo;
	private JComboBox sizeCombo;

	private String previousWord = "";

	/** The list of possible font sizes. */
	private static final Integer[] SIZES = { 8, 9, 10, 11, 12, 13, 14, 16, 18,
			20, 24, 26, 28, 32, 36, 40, 48, 56, 64, 72 };

	/** The list of possible fonts. */
	private static final String[] FONTS = GraphicsEnvironment
			.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

	// base variables//

	// constructor//
	@SuppressWarnings("unused")
	public TextEditor() {
		// "reset" the look and feel to System default//
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// "reset" the look and feel to System default//

		windowNum++;

		// then we initialize the file chooser//
		fileDialog = new JFileChooser(System.getProperty("user.dir"));
		fileDialog.setAcceptAllFileFilterUsed(false);
		fileDialog.addChoosableFileFilter(new filter());
		// then we initialize the file chooser//

		// menu bar stuffs//
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		mb.add(file);
		mb.add(edit);
		// menu bar stuffs//

		// file menu stuffs//
		file.add(New);
		file.addSeparator();
		file.add(Open);
		file.addSeparator();
		file.add(Save);
		file.add(SaveAs);
		file.addSeparator();
		file.add(Quit);
		file.addSeparator();

		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		// file menu stuffs//

		// JToolBar//
		JToolBar tool = new JToolBar();
		add(tool, BorderLayout.NORTH);
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.add(SaveAs);
		tool.addSeparator();

		JButton cut = tool.add(Cut), cop = tool.add(Copy), pas = tool
				.add(Paste);

		cut.setText(null);
		cut.setIcon(new ImageIcon("icons/cut.png"));

		cop.setText(null);
		cop.setIcon(new ImageIcon("icons/copy.png"));
		cop.setFocusable(false);

		pas.setText(null);
		pas.setIcon(new ImageIcon("icons/paste.png"));

		tool.addSeparator();

		fontCombo = new JComboBox(FONTS);
		sizeCombo = new JComboBox(SIZES);
		fontCombo.setSelectedItem("Arial");
		sizeCombo.setSelectedItem(12);
		fontCombo.addActionListener(ChangeFont);
		sizeCombo.addActionListener(ChangeFont);
		tool.add(fontCombo);
		tool.add(sizeCombo);

		JButton bold = tool.add(new StyledEditorKit.BoldAction());
		bold.setIcon(new ImageIcon("icons/bold.png"));
		bold.setText(null);
		JButton italic = tool.add(new StyledEditorKit.ItalicAction());
		italic.setIcon(new ImageIcon("icons/italic.png"));
		italic.setText(null);
		JButton under = tool.add(new StyledEditorKit.UnderlineAction());
		under.setIcon(new ImageIcon("icons/underline.png"));
		under.setText(null);

		JButton leftAlign = tool.add(new StyledEditorKit.AlignmentAction(
				"ALIGN_LEFT", 0));
		leftAlign.setIcon(new ImageIcon("icons/left.png"));
		leftAlign.setText(null);
		JButton centerAlign = tool.add(new StyledEditorKit.AlignmentAction(
				"ALIGN_CENTER", 1));
		centerAlign.setIcon(new ImageIcon("icons/center.png"));
		centerAlign.setText(null);
		JButton rightAlign = tool.add(new StyledEditorKit.AlignmentAction(
				"ALIGN_RIGHT", 2));
		rightAlign.setIcon(new ImageIcon("icons/right.png"));
		rightAlign.setText(null);

		fontCombo.setFocusable(false);
		sizeCombo.setFocusable(false);

		bold.setFocusable(false);
		italic.setFocusable(false);
		under.setFocusable(false);

		leftAlign.setFocusable(false);
		centerAlign.setFocusable(false);
		rightAlign.setFocusable(false);

		tool.setFloatable(false);
		tool.setLayout(new FlowLayout());
		// JToolBar//

		// text pane//
		StyleContext context = new StyleContext();
		AdvancedRTFDocument document = new AdvancedRTFDocument(context);

		// -grab default style//
		Style dstyle = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setAlignment(dstyle, StyleConstants.ALIGN_LEFT);
		StyleConstants.setFontFamily(dstyle, "Arial");
		StyleConstants.setFontSize(dstyle, 12);
		StyleConstants.setSpaceAbove(dstyle, 4);
		StyleConstants.setSpaceBelow(dstyle, 4);
		document.setParagraphAttributes(0, 1, dstyle, false);

		txtPane.addCaretListener(refreshFontList);

		txtPane.setPreferredSize(new Dimension(500, 500));

		txtPane.setDocument(document);
		txtPane.setEditorKit(rtfKit);
		txtPane.addKeyListener(k1);
		txtPane.addMouseMotionListener(Mouse);
		// document.addUndoableEditListener(new Undo());

		// text pane//

		// keyboard list//

		keywordLst.add("henry");
		keywordLst.add("karen");
		keywordLst.add("ann");
		keywordLst.add("jennifer");
		keywordLst.add("cindy");
		keywordLst.add("chloe");
		keywordLst.add("russell");
		keywordLst.add("anais");
		keywordLst.add("artur");
		keywordLst.add("peter");
		keywordLst.add("rainbow");
		keywordLst.add("sarah");
		keywordLst.add("derek");
		keywordLst.add("kelly");
		keywordLst.add("jacky");
		keywordLst.add("eric");

		// scroll pane//
		JScrollPane scroll = new JScrollPane(txtPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll, BorderLayout.CENTER);
		// scroll pane//

		status = new JLabel("Fresh Start :D");
		add(status, BorderLayout.PAGE_END);

		addWindowListener(w);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		pack();
		setTitle("NoteTaking 351 - " + curFile);
		setVisible(true);
		// other initialization//
	}

	// constructor//

	// key listener//
	private KeyListener k1 = new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			changed = true;
			highlightWord(txtPane, keywordLst); // easter egg
		}
	};
	// key listener

	// mouse listener//
	private MouseMotionListener Mouse = new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			int offset = txtPane.viewToModel(arg0.getPoint());
			try {
				int left = Utilities.getWordStart(txtPane, offset);
				int right = Utilities.getWordEnd(txtPane, offset);
				String word = txtPane.getDocument().getText(left, right - left);
				checkWord(word);
			} catch (Exception ble) {
				System.out.println("you are not hovering");
			}
		}
	};

	// analysis word//
	private void checkWord(String word) {
		if (keywordLst.contains(word) && !word.equalsIgnoreCase(previousWord)) {
			System.out.println(word);
			previousWord = word;
		}
	}

	// window listener//
	private WindowListener w = new WindowAdapter() {
		public void windowClosed(WindowEvent e) {
			if (--windowNum == 0) {
				System.exit(0);
			}
		}
	};

	// font action//
	CaretListener refreshFontList = new CaretListener() {
		@Override
		public void caretUpdate(CaretEvent e) {
			// TODO Auto-generated method stub

		}
	};

	Action ChangeFont = new AbstractAction() {
		public void actionPerformed(ActionEvent e) {
			String family = fontCombo.getSelectedItem().toString();
			int size = Integer.parseInt(sizeCombo.getSelectedItem().toString());
			Style style = ((DefaultStyledDocument) txtPane.getDocument())
					.addStyle("font", null);
			StyleConstants.setFontFamily(style, family);
			StyleConstants.setFontSize(style, size);
			String tmp = txtPane.getSelectedText();
			txtPane.replaceSelection("");
			try {
				txtPane.getDocument().insertString(txtPane.getSelectionStart(),
						tmp, style);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	// font action//

	// new action//
	Action New = new AbstractAction("New", new ImageIcon("icons/newNote.png")) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			// increase the counter of windows//
			new TextEditor();
		}
	};

	// new action//

	// open action//
	Action Open = new AbstractAction("Open",
			new ImageIcon("icons/openNote.png")) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveOld();
			if (fileDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				readInFile(fileDialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);
		}
	};
	// open action//

	// save action//
	Action Save = new AbstractAction("Save",
			new ImageIcon("icons/saveNote.png")) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (!curFile.equals("Untitled")) {
				saveFile(curFile);
			} else {
				saveFileAs();
			}
		}
	};
	// save action//

	// save as action//
	Action SaveAs = new AbstractAction("Save as...", new ImageIcon(
			"icons/saveAs.png")) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	// save as action//

	// quit action//
	Action Quit = new AbstractAction("Exit", new ImageIcon("icons/exit.png")) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveOld();
			dispose();
		}
	};

	// quit action//

	// action mapping for cut, copy, and paste//
	ActionMap m = txtPane.getActionMap();
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);

	// action mapping for cut, copy, and paste//

	// highlight method//
	private void highlightWord(JTextComponent textComp, List<String> lst) {
		// remove existing highlights
		removeHighlights(textComp);

		// An instance of the private subclass of the default highlight painter
		// Highlighter.HighlightPainter highlightPainter = new
		// highlightPainter(Color.CYAN);

		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			for (int i = 0; i < lst.size(); i++) {
				String pattern = lst.get(i).toString();

				// search for pattern//
				while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(),
						pos)) >= 0) {
					hilite.addHighlight(pos, pos + pattern.length(),
							new highlightPainter(Color.cyan, text));
					pos += pattern.length();
				}
			}

		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	// highlight method//

	// Removes only our private highlights
	public void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();
		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof highlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	// A private subclass of the default highlight painter
	class highlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		String holder;

		public highlightPainter(Color color, String text) {
			super(color);
			holder = text;
		}
	}

	// save file as method//
	private void saveFileAs() {
		if (fileDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			saveFile(fileDialog.getSelectedFile().getAbsolutePath());
		}
	}

	// save file as method//

	// save old method//
	private void saveOld() {
		if (changed) {
			if (JOptionPane.showConfirmDialog(this, "Would you like to save "
					+ curFile + " ?", "Save", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				saveFile(curFile);
			}
		}
	}

	// save old method//

	// read in file method//
	private void readInFile(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			StyledDocument doc = (StyledDocument) txtPane.getDocument();
			doc.remove(0, doc.getLength());
			rtfKit.read(fis, doc, 0);
			fis.close();
			curFile = fileName;
			setTitle("NoteTaking 351 - " + curFile);
			status.setText("Opening : " + curFile);
			changed = false;
		} catch (IOException | BadLocationException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,
					"Editor can't find the file call " + fileName);
		}
	}

	// read in file method//

	// Overridden for word wrapping//
	class WrapEditorKit extends AdvancedRTFEditorKit {
		ViewFactory defaultFactory = new WrapColumnFactory();

		public ViewFactory getViewFactory() {
			return defaultFactory;
		}

	}

	class WrapColumnFactory implements ViewFactory {
		public View create(Element elem) {
			String kind = elem.getName();
			if (kind != null) {
				if (kind.equals(AbstractDocument.ContentElementName)) {
					return new WrapLabelView(elem);
				} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
					return new ParagraphView(elem);
				} else if (kind.equals(AbstractDocument.SectionElementName)) {
					return new BoxView(elem, View.Y_AXIS);
				} else if (kind.equals(StyleConstants.ComponentElementName)) {
					return new ComponentView(elem);
				} else if (kind.equals(StyleConstants.IconElementName)) {
					return new IconView(elem);
				}
			}

			// default to text display
			return new LabelView(elem);
		}
	}

	class WrapLabelView extends LabelView {
		public WrapLabelView(Element elem) {
			super(elem);
		}

		public float getMinimumSpan(int axis) {
			switch (axis) {
			case View.X_AXIS:
				return 0;
			case View.Y_AXIS:
				return super.getMinimumSpan(axis);
			default:
				throw new IllegalArgumentException("Invalid axis: " + axis);
			}
		}
	}

	// save file method//
	private void saveFile(String fileName) {
		if (!fileName.endsWith("page")) {
			fileName = fileName + ".page";
		}
		StyledDocument doc = (StyledDocument) txtPane.getDocument();
		try {
			rtfKit.write(fileName, doc);
		} catch (IOException | BadLocationException e1) {
			e1.printStackTrace();
		}
		curFile = fileName;
		setTitle("NoteTaking 351 - " + curFile);
		status.setText("Saved : " + curFile);
	}

	// save file method//

	// file filter//
	class filter extends javax.swing.filechooser.FileFilter {
		public boolean accept(File file) {
			String filename = file.getName();
			return filename.endsWith(".page");
		}

		public String getDescription() {
			return "*.page - a specialized note pages package";
		}
	}

	// file filter//

	// the main//
	public static void main(String[] arg) {
		new TextEditor();
	}
	// the main//
}
