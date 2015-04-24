package model;

/**
 * 
 * @author Kyle Hodgetts
 * @author Peter Barta
 * @version 1.0
 * 
 * <p>FourPicturesOneWord, the game where four photos are displayed on the screen and the user must guess what the word
 * is based on the picture.</p>
 */
public class FourPictures{
	private String[] fileNames;		//Paths for the photos
	private String answer;			//Stores the full revealed answer
	private String selection;		//The choice of letters the user can pick from
	private String revealed;		//The letters that have been guessed correctly
	private boolean guessed;		//Has the word been fully guessed?
	
	/**
	 * <p>Initialises new game</p>
	 * @param fileNames		A string array of file paths for four images
	 * @param answer		The answer to the puzzle
	 * @param selection		The selection of letters the user has to choose from
	 */
	public FourPictures(String[] fileNames, String answer, String selection){
		this.fileNames = fileNames;
		this.answer = answer;
		this.selection = selection;
		this.revealed="";
		this.guessed = false;
		
		/*
		 * For each letter in the answer, append an underscore to the revealed field.
		 * Initially, no letters have been guessed, therfore all letters will be underscores.
		 */
		for(int i = 0; i<answer.length(); ++i){
			this.revealed += "_ ";
		}
	}
	
	/**
	 * <p>May be used to populate buttons with letter choices.</p>
	 * @return the selection of letters to choose from.
	 */
	public String getSelection(){
		return this.selection;
	}
	
	/**
	 * @return The letters that have been revealed so far and underscores for the letters that are yet to be guessed.
	 */
	public String toString(){
		return this.revealed;
	}
	
	/**
	 * 
	 * @return the full revealed answer
	 */
	public String getAnswer(){
		return this.answer;
	}
	
	/**
	 * 
	 * @return the file paths for the images on a round
	 */
	public String[] getPictures(){
		return this.fileNames;
	}
	
	/**
	 * 
	 * @return True if the word has been guessed. False otherwise
	 */
	public boolean isGuessed(){
		return this.guessed;
	}
	
	/**
	 * <p>Will reveal the letter at {@position} index with the {@letter} </p>
	 * @param letter
	 * @param position
	 */
	public void setRevealed(char letter, int position){
		StringBuilder sb = new StringBuilder(revealed);
		sb.setCharAt(position, letter);
		this.revealed = sb.toString();
		this.checkGuessed();
	}
	
	/**
	 * <p>Reveals a letter within the answer</p>
	 */
	public void hintReveal(){
		StringBuilder sb = new StringBuilder(revealed);
		for(int i = 0; i<this.getAnswer().length(); ++i){
			
			//If this letter hasn't been guessed yet
			if(this.toString().charAt(i) == '_'){
				
				//If the letter is the first letter
				if(i==0){sb.setCharAt(i, this.answer.charAt(i));}
				else{
					sb.setCharAt(i, this.answer.charAt(i-1));
				}
				break;
			}
		}
		this.revealed = sb.toString();
		this.checkGuessed();
	}
	
	private void checkGuessed(){
		//If there are no underscores left in the word, then it has been guessed
		if(!this.revealed.contains(String.valueOf("_"))){
			this.guessed = true;
		}
	}
}
