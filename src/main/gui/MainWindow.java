package main.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.AttributeSet;

import com.sun.istack.internal.FinalArrayList;

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
	private char [] keyByte;
	
	public MainWindow() {
		// TODO Auto-generated constructor stub
		setMainFrame();
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setMainFrame() {
		setLayout(new GridLayout(2,4));
		
		encryptJRadioButton = new JRadioButton(RADIOBUTTON1);
		decryptJRadioButton = new JRadioButton(RADIOBUTTON2);
		encryptJRadioButton.addActionListener(new EncryptRadioButton());
		decryptJRadioButton.addActionListener(new DecryptRadioButton());
		
		SourceRadioGroup = new ButtonGroup();
		SourceRadioGroup.add(decryptJRadioButton);
		SourceRadioGroup.add(encryptJRadioButton);
		
		scourceJLabel = new JLabel();
		targetJLabel = new JLabel();

		bFileChooserJButton = new JButton(OPENFILE);
		bFileChooserJButton.addActionListener(new FileChooser());
		
		bSaveFileButton = new JButton(SAVEFILE);
		bSaveFileButton.addActionListener(new SaveChooser());
		
		doWorkJButton = new JButton();
		
		keyJPasswordField = new JPasswordField(15);
		keyJPasswordField.addFocusListener(new CheckKey());
		
		PlainDocument plainDocument = (PlainDocument) keyJPasswordField.getDocument();
		plainDocument.setDocumentFilter(new DocumentFilter(){

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 32) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
		
		
		add(doWorkJButton);
		add(encryptJRadioButton);
		add(decryptJRadioButton);
		add(bFileChooserJButton);
		add(scourceJLabel);
		add(targetJLabel);
		add(keyJPasswordField);
		setTitle("Crypto");
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
			}
		}

	}
	
	class SaveChooser implements ActionListener{
		int returnVal;
		
		public SaveChooser(){
			super();
			saveJFileChooser = new JFileChooser();
			saveJFileChooser.setSelectedFile(new File("fileToSave.txt"));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			saveJFileChooser.setSelectedFile(new File(" "+".crypto"));
			returnVal = saveJFileChooser.showSaveDialog(MainWindow.this);
			//saveJFileChooser
			
			if(returnVal==JFileChooser.APPROVE_OPTION){
				
			}
		}
		
	}
	
	class DecryptRadioButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			doWorkJButton.setText("Deszyfruj");
		}
		
	}
	
	
	class EncryptRadioButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			doWorkJButton.setText("Szyfruj");
		}
		
	}
	class CheckKey implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			// TODO Auto-generated method stub
			final JPasswordField passF = (JPasswordField)e.getComponent();
			if(passF.getPassword().length==32){
				System.out.println(passF.getPassword().length);
				keyByte = passF.getPassword();
				doWorkJButton.setEnabled(true);
			}
			else{
				System.out.println(passF.getPassword().length);
				doWorkJButton.setEnabled(false);
			}
		}
		
	}
}
