package main.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;

import com.sun.istack.internal.FinalArrayList;
import main.crypto.*;

public class MainWindow extends JFrame {

	public static final String RADIOBUTTON1 = "Szyfruj";
	public static final String RADIOBUTTON2 = "Deszyfruj";
	public static final String WORKBUTTON1 = "Szyfruj";
	public static final String WORKBUTTON2 = "Deszyfruj";
	public static final String OPENFILE = "Wybierz plik";
	public static final String SAVEFILE = "Zapisz plik";

	private JRadioButton encryptJRadioButton;
	private JRadioButton decryptJRadioButton;
	private ButtonGroup SourceRadioGroup;

	private String patchFile;
	private String trargetPatch;

	private JButton doWorkJButton;
	private JLabel scourceJLabel;
	private JLabel targetJLabel;

	private JFileChooser getJFileChooser;
	private JButton bFileChooserJButton;
	private JFileChooser saveJFileChooser;
	private JButton bSaveFileButton;
	private JPasswordField keyJPasswordField;
	private char[] keyByte;

	private JLabel statusJLabel;
	private JPanel statusJPanel;

	public MainWindow() {
		// TODO Auto-generated constructor stub
		setMainFrame();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	public void setMainFrame() {
		setLayout(new BorderLayout());

		

		

		
		setRightColumn();

		//add(doWorkJButton);
		//add(encryptJRadioButton);
		//add(decryptJRadioButton);
		//add(bFileChooserJButton);
		//add(scourceJLabel);
		//add(targetJLabel);
		//add(keyJPasswordField);
		//add(bSaveFileButton);
		setTitle("Crypto");
		setStatusBar();
		setRadioButtons();
		setLeftColumn();
		pack();
	}

	

	class FileChooser implements ActionListener {
		int returnVal;

		public FileChooser() {
			super();
			// TODO Auto-generated constructor stub
			getJFileChooser = new JFileChooser();

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			returnVal = getJFileChooser.showOpenDialog(MainWindow.this);
			getJFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				patchFile = getJFileChooser.getSelectedFile().getAbsolutePath();
				scourceJLabel.setText(patchFile);
				System.out.println(patchFile);
				trargetPatch = patchFile.replaceAll("\\.[a-zA-Z]+$", "."
						+ "crypto");
				System.out.println(trargetPatch);
				targetJLabel.setText(trargetPatch);
				String[] tmp = trargetPatch.split("\\\\");
				System.out.println(tmp[tmp.length - 1]);
				saveJFileChooser.setSelectedFile(new File(tmp[tmp.length - 1]));
				if (keyJPasswordField.getText().length() == 32
						&& (encryptJRadioButton.isSelected() || decryptJRadioButton
								.isSelected())) {
					doWorkJButton.setEnabled(true);
					System.out.println(encryptJRadioButton.isSelected()
							|| decryptJRadioButton.isSelected());
					System.out.println(encryptJRadioButton.isSelected());
					System.out.println(decryptJRadioButton.isSelected());
				}
			}
		}

	}

	class SaveChooser implements ActionListener {
		int returnVal;

		public SaveChooser() {
			super();
			saveJFileChooser = new JFileChooser();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (trargetPatch == null) {
				saveJFileChooser.setSelectedFile(new File(" " + ".crypto"));
			}

			returnVal = saveJFileChooser.showSaveDialog(MainWindow.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				trargetPatch = saveJFileChooser.getSelectedFile()
						.getAbsolutePath();
				targetJLabel.setText(trargetPatch);
			}
		}

	}

	void setStatusBar() {
		statusJLabel = new JLabel("Status");
		statusJPanel = new JPanel();
		statusJPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		statusJPanel.setPreferredSize(new Dimension(getWidth(), 16));
		statusJPanel.setLayout(new BoxLayout(statusJPanel, BoxLayout.X_AXIS));
		statusJPanel.add(statusJLabel);
		add(statusJPanel, BorderLayout.SOUTH);

	}

	class DecryptRadioButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			doWorkJButton.setText("Deszyfruj");
		}

	}

	class EncryptRadioButton implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			doWorkJButton.setText("Szyfruj");
		}

	}

	class CheckKey implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			final JPasswordField passF = (JPasswordField) e.getComponent();
			if (passF.getPassword().length == 32
					&& (encryptJRadioButton.isSelected() || decryptJRadioButton
							.isSelected())) {
				System.out.println(passF.getPassword().length);
				keyByte = passF.getPassword();
				doWorkJButton.setEnabled(true);

			} else {
				System.out.println(passF.getPassword().length);
				doWorkJButton.setEnabled(false);
			}
		}

	}

	void setRadioButtons() {
		JPanel radioJPanel = new JPanel();
		radioJPanel.setPreferredSize(new Dimension(170, 20));
		radioJPanel.setLayout(new GridLayout(1, 2));
		encryptJRadioButton = new JRadioButton(RADIOBUTTON1);
		decryptJRadioButton = new JRadioButton(RADIOBUTTON2);
		encryptJRadioButton.addActionListener(new EncryptRadioButton());
		decryptJRadioButton.addActionListener(new DecryptRadioButton());
		
		SourceRadioGroup = new ButtonGroup();
		SourceRadioGroup.add(decryptJRadioButton);
		SourceRadioGroup.add(encryptJRadioButton);
		
		radioJPanel.add(encryptJRadioButton);
		radioJPanel.add(decryptJRadioButton);
		
		
		
		
		encryptJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (trargetPatch != null
						&& keyJPasswordField.getPassword().length == 32)
					doWorkJButton.setEnabled(true);

			}
		});
		decryptJRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (trargetPatch != null
						&& keyJPasswordField.getPassword().length == 32)
					doWorkJButton.setEnabled(true);

			}
		});

		add(radioJPanel,BorderLayout.CENTER);
	}
	void setLeftColumn(){
		JPanel leftJPanel = new JPanel();
		leftJPanel.setPreferredSize(new Dimension(200,70));
		leftJPanel.setLayout(new BoxLayout(leftJPanel, BoxLayout.Y_AXIS));
		scourceJLabel = new JLabel("adsa");
		targetJLabel = new JLabel("asdsad");
		doWorkJButton = new JButton("Wybierz tryb");
		doWorkJButton.setMaximumSize(new Dimension(200, 30));
		doWorkJButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (encryptJRadioButton.isSelected())
					try {
						AES.encrypt(patchFile, keyJPasswordField.getPassword(),
								trargetPatch);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				if (decryptJRadioButton.isSelected())
					try {
						AES.decrypt(patchFile, keyJPasswordField.getPassword(),
								trargetPatch);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		doWorkJButton.setEnabled(false);
		leftJPanel.add(doWorkJButton);
		leftJPanel.add(scourceJLabel);
		leftJPanel.add(targetJLabel);
		add(leftJPanel,BorderLayout.WEST);
	}
	
	void setRightColumn() {
		JPanel rightJPanel =new JPanel();
		rightJPanel.setLayout(new BoxLayout(rightJPanel, BoxLayout.PAGE_AXIS));
		rightJPanel.setPreferredSize(new Dimension(270,90));
		bFileChooserJButton = new JButton(OPENFILE);
		bFileChooserJButton.setMaximumSize(new Dimension(270, 10));
		bFileChooserJButton.addActionListener(new FileChooser());
		bFileChooserJButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		bSaveFileButton = new JButton(SAVEFILE);
		bSaveFileButton.addActionListener(new SaveChooser());
		bSaveFileButton.setMaximumSize(new Dimension(270, 20));
		bSaveFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		keyJPasswordField = new JPasswordField(15);
		keyJPasswordField.addFocusListener(new CheckKey());
		keyJPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
		PlainDocument plainDocument = (PlainDocument) keyJPasswordField
				.getDocument();
		plainDocument.setDocumentFilter(new DocumentFilter() {

			@Override
			public void replace(DocumentFilter.FilterBypass fb, int offset,
					int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String string = fb.getDocument().getText(0,
						fb.getDocument().getLength())
						+ text;

				if (string.length() <= 32) {
					super.replace(fb, offset, length, text, attrs); 
					if (string.length() < 32)
						statusJLabel.setText("Klucz musi liczyæ 32 znaki!!"); 
																				
					else {
						statusJLabel.setText("D³ugoœæ klucza odpowiednia"); 
						if (patchFile != null
								&& (encryptJRadioButton.isSelected() || decryptJRadioButton
										.isSelected()))
							doWorkJButton.setEnabled(true);
					}
				}
			}

		});
		
		rightJPanel.add(bFileChooserJButton);
		rightJPanel.add(bSaveFileButton);
		rightJPanel.add(keyJPasswordField);
		add(rightJPanel,BorderLayout.EAST);
		
	}
}
