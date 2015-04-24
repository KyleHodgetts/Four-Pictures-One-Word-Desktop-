package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.FourPictures;

/**
 * 
 * @author Kyle Hodgetts
 * @author Peter Barta
 * @version 1.0
 * 
 * <p>Interface for FourPicturesOneWordGame. Displays four images and a range of letters to choose from.
 * Once the word has been guessed, the buttons will be replaced by a label that states that the word has been guessed.
 * </p>
 *
 */
public class GameWindow extends JFrame implements ActionListener {
	
	private JPanel guessPanel;
	private JLabel lblGuess;
	private JLabel lblLetters;
	private JButton btnNext;
	private JButton btnHint;
	
	private ArrayList<FourPictures> game;
	private String[] roundImg;
	private int roundCounter;
	
	private JPanel picPanel;
	private JPanel choicePanel;
	private JPanel winPanel;
	
	/**
	 * Takes a game as an argument and constructs the layout with images and choice letters
	 * @param game
	 * @throws MalformedURLException 
	 */
	public GameWindow(ArrayList<FourPictures> game) throws MalformedURLException{
		super("Four Pictures One Word");
		this.game = game;
		this.roundCounter = -1;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		this.createComponents();
		this.pack();
	}
	
	private void createComponents() throws MalformedURLException{
		++roundCounter;
		guessPanel = new JPanel(new FlowLayout());
		lblGuess = new JLabel("GUESSED: ");
		lblLetters = new JLabel(game.get(roundCounter).toString());
		btnNext = new JButton("-->");
		btnNext.setEnabled(false);
		btnNext.addActionListener(this);
		guessPanel.add(lblGuess);
		guessPanel.add(lblLetters);
		guessPanel.add(btnNext);
		
		picPanel = new JPanel(new GridLayout(2,2));
		roundImg = game.get(roundCounter).getPictures();
		
		for(int i = 0; i < roundImg.length; ++i){
			ImageIcon pic = new ImageIcon(new URL(roundImg[i]));
			Image image = pic.getImage(); // Get the Image to resizeit
			Image newPic = image.getScaledInstance(200, 150,  java.awt.Image.SCALE_SMOOTH); // scale it to a normal size
			pic = new ImageIcon(newPic);  // Make it an image icon again
			picPanel.add(new JLabel(pic));
		}
		
		choicePanel = new JPanel(new GridLayout(2,6));
		for(int k=0; k<game.get(roundCounter).getSelection().length(); ++k){
			JButton btn = new JButton(""+game.get(roundCounter).getSelection().charAt(k));
			btn.addActionListener(this);
			choicePanel.add(btn);
		}
		btnHint = new JButton("?");
		btnHint.addActionListener(this);
		choicePanel.add(btnHint);
		
		this.add(guessPanel, BorderLayout.NORTH);
		this.add(picPanel, BorderLayout.CENTER);
		this.add(choicePanel, BorderLayout.SOUTH);
		
	}

	/**
	 * <p>Called when a letter button is clicked. Prevents the button from being clicked again and checks to see if the letter is in the word.
	 * If so, the label is updated to include the guessed letter.
	 * </p>
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		source.setEnabled(false);
		
		if(source.getText().equals("-->")){
			this.nextRound();
		}
		
		if(source.getText().equals("?")){
			game.get(roundCounter).hintReveal();
			this.refreshWindow();
		}
		else{
			char btnValue = source.getText().toCharArray()[0];

			if(game.get(roundCounter).getAnswer().contains(String.valueOf(btnValue))){
				for(int i = 0; i<game.get(roundCounter).getAnswer().length(); ++i){
					if(btnValue == game.get(roundCounter).getAnswer().charAt(i)){
						game.get(roundCounter).setRevealed(game.get(roundCounter).getAnswer().charAt(i), 2*i);
					}
				}
				this.refreshWindow();
			}
		}
		
		if(game.get(roundCounter).isGuessed()){
			this.remove(choicePanel);
			btnNext.setEnabled(true);
			winPanel = new JPanel(new BorderLayout());
			JLabel lblWin = new JLabel("Correct!");
			winPanel.add(lblWin, BorderLayout.CENTER);
			this.add(winPanel, BorderLayout.SOUTH);
			this.refreshWindow();
		}
	}
	
	private void refreshWindow(){
		lblLetters.setText(game.get(roundCounter).toString());
		this.repaint();
	}
	
	private void nextRound(){
		try{
			this.remove(winPanel);
			this.remove(picPanel);
			++roundCounter;
			btnNext.setEnabled(false);
			btnHint.setEnabled(true);
			
			picPanel = new JPanel(new GridLayout(2,2));
			roundImg = game.get(roundCounter).getPictures();
			this.add(picPanel, BorderLayout.CENTER);
			
			choicePanel = new JPanel(new GridLayout(2,6));
			for(int k=0; k<game.get(roundCounter).getSelection().length(); ++k){
				JButton btn = new JButton(""+game.get(roundCounter).getSelection().charAt(k));
				btn.addActionListener(this);
				choicePanel.add(btn);
			}
			choicePanel.add(btnHint);
			this.add(choicePanel, BorderLayout.SOUTH);
			for(int i = 0; i < roundImg.length; ++i){
				ImageIcon pic = new ImageIcon(new URL(roundImg[i]));
				picPanel.add(new JLabel(pic));
			}
			this.refreshWindow();
		}
		catch(MalformedURLException me)
		{
			JOptionPane.showMessageDialog(this, "Images not found", "ERROR", JOptionPane.OK_OPTION);
		}
		catch(IndexOutOfBoundsException iobe){
			JOptionPane.showMessageDialog(this, "Game Completed!", "Congratulations", JOptionPane.PLAIN_MESSAGE);
			this.dispose();
			System.exit(0);
		}

		
	}
}
