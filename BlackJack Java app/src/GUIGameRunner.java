/**
 * @author i7461730
 * Name: Daniel Dimanov
 * Date: 01.04.2016
 * Task: Assignment 2
 * Description: This is the GUI class which is essentially the whole game runner. This class has the interface of the game and uses the board for most of the 
 * back-end needed. Moreover it displays the messages to the user and is responsible for the whole front-end part of the program. It is also a controller between
 * the back and the front end, so it is a very important class. Not that any of the classes are not important, but this one implements all the others in a way 
 * and creates the GUI of the game.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
public class GUIGameRunner {
	private JFrame frame = new JFrame("Blackjack");
	private int JAnswer;
	private int bjC=0;
	private JLabel tempLbl;
	private JLabel scoreLbl,dealerLbl,cardBack;
	private JPanel panel,panelPlayer,panelDealer,panelBtn;
	private Object[] JOptions={"Yes","No"};
	private JButton btnDraw,btnStay;
	private ArrayList<JLabel> cardLbls=new ArrayList<JLabel>();
	private ArrayList<String> ids=new ArrayList<String>();
	private Board board;
	private boolean isPlayer;
	private int indexOfCardLbls;
	private ArrayList<ImageIcon> images=new ArrayList<ImageIcon>();
	private ImageIcon cardBackIcon;
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		         new GUIGameRunner();
		    }
		});

	}
	/**
	 * This is the constructor of the GUI. Here the frame, all the panels, labels and buttons are created. Moreover the setup of the game is made, which includes
	 * drawing of 2 cards for the player and 2 cards for the dealer, but the second one of the dealer is left invisible to the player until the it is time for the dealer to play.
	 */
	public GUIGameRunner(){
		createForm();
		createBoard(); 
		createImages();
		shuffleDecks();
		createFields();
		createButtons();
		panel.add(panelPlayer,BorderLayout.SOUTH);
		panel.add(panelDealer,BorderLayout.NORTH);
		giveColor();
		frame.add(panel);
		frame.setVisible(true);
	}
	/**
	 * This method simply gives background color to all the panels. The color I have chosen is green, because most casino tables for blackjack are green.
	 */
	public void giveColor(){
		panel.setBackground(Color.GREEN);
		panelPlayer.setBackground(Color.GREEN);
		panelDealer.setBackground(Color.GREEN);
		panelBtn.setBackground(Color.GREEN);
	}
	/**
	 * This method creates the buttons and is used by the constructor method.
	 */
	public void createButtons(){
		panelBtn=new JPanel(new FlowLayout());
		panelBtn.setLayout(new FlowLayout(FlowLayout.CENTER));
		btnDraw=new JButton("| Hit |");
		btnDraw.setBounds(100,100,100,100);
		btnDraw.addActionListener(new DrawHandler());
		btnDraw.setBackground(Color.WHITE);
		panelBtn.add(btnDraw);
		btnStay=new JButton("| Stay|");
		btnStay.setBounds(10,85, 100, 20);
		btnStay.addActionListener(new StayHandler());
		btnStay.setBackground(Color.WHITE);
		panelBtn.add(btnStay);
		panel.add(panelBtn,BorderLayout.CENTER);
	}
	class DrawHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(checkScore()){
				createLabelImage(board.draw(isPlayer),true);
				scoreLbl.setText(board.getPlayerScore()+"");
				editLabelScore(scoreLbl);
			}
			if(!checkScore()){
				scoreLbl.setText("Busted.");
				editLabelScore(scoreLbl);
				busted();
			}
		}
	}
	class StayHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e){
			btnDraw.setEnabled(false);
			dealerDraw();
		}
	}
	/**
	 * This method is evoked when the player is busted.
	 */
	public void busted(){
		btnDraw.setEnabled(false);
		btnStay.setEnabled(false);
		checkFinalScore();
	}
	/**
	 * This method adds the label, which is passed to it to the panel and then the panel is added to the frame to avoid glitches. This is the display of the score 
	 * of the player. 
	 * @param label the score of the player.
	 */
	public void editLabelScore(JLabel label){
		panel.add(label,BorderLayout.EAST);
		frame.add(panel);
		frame.setVisible(true);
	}
	/**
	 * This method adds the label, which is passed to it to the panel and then the panel is added to the frame to avoid glitches. This is the display of the score 
	 * of the dealer. 
	 * @param label the score of the dealer.
	 */
	public void editLabelDScore(JLabel label){
		panel.add(label,BorderLayout.WEST);
		frame.add(panel);
		frame.setVisible(true);
	}
	/**
	 * This is the method, which is responsible for the drawing of all the dealer cards and basically it is dealer playing the game.
	 */
	public void dealerDraw(){
		btnStay.setEnabled(false);
		panelDealer.remove(cardBack);
		while(board.getDealerScore()<17){
			createLabelImage(board.draw(false),false);
			dealerLbl.setText(board.getDealerScore()+"");
			editLabelDScore(dealerLbl);
		}
		checkFinalScore();
	}
	/**
	 * This method checks the score and basically applies the rules of blackjack.
	 * @return boolean whether to play on or stop.
	 */
	public boolean checkScore(){
		int score=board.getPlayerScore();
		if(score>21){
			return false;
		}
		else{
			checkBlackjack();
			return true;
		}
	}
	/**
	 * This method creates all the fields like labels, imageIcons, Label images and adds everything to the panel. This method is used by the constructor and when the
	 * new round is about to begin.
	 */
	public void createFields(){
		scoreLbl=new JLabel();
		dealerLbl=new JLabel();
		cardBack=new JLabel();
		cardBackIcon=new ImageIcon("images/CardBack.png");
		cardBack.setIcon(cardBackIcon);
		initializeIndexOfCardLbls(); 
		isPlayer=true;
		clearScores();
		createLabelImage(board.draw(isPlayer),true); 
		createLabelImage(board.draw(isPlayer),true);
		createLabelImage(board.draw(false),false);
		panelDealer.add(cardBack);
		scoreLbl.setText(board.getPlayerScore()+"  ");
		scoreLbl.setFont(new Font("Times New Roman",Font.BOLD,44));
		dealerLbl.setText(board.getDealerScore()+"  ");
		dealerLbl.setFont(new Font("Times New Roman",Font.BOLD,44));
		panel.add(scoreLbl,BorderLayout.EAST);
		panel.add(dealerLbl,BorderLayout.WEST);
		checkBlackjack();
	}
	/**
	 * This method checks if the player has got a blackjack and directly moves on to the dealer drawing the cards. Moreover it outputs in the console a text if the player has a blackjack.
	 */
	public void checkBlackjack(){
		if(board.getPlayerScore()==21){
			btnDraw.setEnabled(false);
			bjC++;
		}
	}
	/**
	 * This method is used to create the label images and then display them to the user. 
	 * @param card Card to be displayed
	 * @param isPlayer in which panel(the player one or the dealer one the card to be displayed in)
	 */
	public void createLabelImage(Card card,boolean isPlayer){
		createCardLabel(images.get(idToIndex(card.getId()))); 
		showCardLabel(isPlayer);
	}
	/**
	 * This method converts the id to index, which is then used to find the position of the card in the deck. 
	 * @param id the id of the cards with first symbol the symbol for the color(C,D,H,S) and then the number of the card(1-13)
	 * @return the index of the card in the deck. 
	 */
	public int idToIndex(String id){
		int indexOfId;
		switch(id.substring(0,1)){
		case "C": indexOfId=(Integer.parseInt(id.substring(1)))-1;break;
		case "D": indexOfId=(Integer.parseInt(id.substring(1))+13)-1;break;
		case "H": indexOfId=(Integer.parseInt(id.substring(1))+26)-1;break;
		case "S": indexOfId=(Integer.parseInt(id.substring(1))+39)-1;break;
		default: indexOfId=52;
		}
		return indexOfId;
	}
	/**
	 * This method creates the board on which the game will be played.
	 */
	public void createBoard(){
		board=new Board();
	}
	/**
	 * This method shuffles the decks. 
	 */
	public void shuffleDecks(){
		board.shuffleDecks();
	}
	/**
	 * This method sets up the whole environment of the GUI. The main panels and positions them in the right order. 
	 */
	public void createForm(){
		panel=new JPanel();
		panel.setLayout(new BorderLayout());
		panelPlayer=new JPanel();
		panelPlayer.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelDealer=new JPanel();
		panelDealer.setLayout(new FlowLayout(FlowLayout.CENTER));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setMinimumSize(new Dimension(800, 600));
	}
	/**
	 * This method initializes the index of the card labels.
	 */
	public void initializeIndexOfCardLbls(){
		indexOfCardLbls=0;
	}
	/**
	 * This method creates a label for the card to be added to. It is first inserted and constructed as a tempLbl and then it is added to the ArrayList of labels. 
	 * The right picture of the card in taken as a parameter.
	 * @param icon the picture, which corresponds to the card, which is to be shown.
	 */
	public void createCardLabel(ImageIcon icon){
		tempLbl=new JLabel();//(icon);
		tempLbl.setBounds(0, 0, 100, 100);
		tempLbl.setIcon(icon);
		cardLbls.add(tempLbl);
		indexOfCardLbls++;
		tempLbl.removeAll();
	}
	/**
	 * This method creates all the images of all the cards, which are then to be used in labels.
	 */
	public void createImages(){
		ids.addAll((board.getDeck(0)).getIds());
		for(int idCount=0;idCount<ids.size();idCount++){
			images.add(new ImageIcon("images/"+(ids.get(idCount)).substring(0,1)+(ids.get(idCount)).substring(1)+".png"));
		}
	}
	/**
	 * This method is responsible for the displaying of a label, which will contain a specific image for the specific card.
	 * The thing about this method is that if the cards on the table are more than 10 for the player they will shrink, so that
	 * all the cards can fit. One more shrink will be performed if the player draws 15 cards, which I have to say would happen really rarly, but 
	 * nevertheless there is such a chance! (6 decks= 24 aces, if the player draws 10 aces, then one 2 and then 8 aces he will have 19 cards on the table
	 * and 20 points, so if he decided that he will get one more ace and draws again, regardless of what he gets he will have 20 cards on the table. Thus 
	 * we need a way to handle 20 cards on the table. 
	 * @param isPlayer shows if it is the player or the dealer.
	 */
	public void showCardLabel(boolean isPlayer){
		if(!isPlayer){
			panelDealer.add(cardLbls.get(indexOfCardLbls-1));
		}else{if(indexOfCardLbls<=10){
			panelPlayer.add(cardLbls.get(indexOfCardLbls-1));
		}
		else {if(indexOfCardLbls<=15){
			panelPlayer.setLayout(new FlowLayout(FlowLayout.CENTER,-50,0));
			panelPlayer.add(cardLbls.get(indexOfCardLbls-1));
		}
		else{
			panelPlayer.setLayout(new FlowLayout(FlowLayout.CENTER,-80,0));
			panelPlayer.add(cardLbls.get(indexOfCardLbls-1));
		}
		}
		}
	}
	/**
	 * This is the method that decides the outcome of the game and displays the corresponding message to the user. Moreover it asks the user if s/he wants to continue and 
	 * allows him/her to do that.
	 */
	public void checkFinalScore(){
		if(board.getDealerScore()==board.getPlayerScore()){
			JAnswer=JOptionPane.showOptionDialog(frame, "Draw! You get your money back! Do you want to play again?", "Draw", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, JOptions, JOptions[0]);
		}
		else{if(board.getDealerScore()>board.getPlayerScore() && board.getDealerScore()<=21){
			JAnswer=JOptionPane.showOptionDialog(frame, "You loose! Do you want to play again?", "Dealer Wins!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, JOptions, JOptions[0]);
		}
		else{if(!(board.getPlayerScore()>21)){
			winAnswer();
		}else{if(board.getPlayerScore()>21){
			JAnswer=JOptionPane.showOptionDialog(frame, "You were busted! Do you want to play again?", "BUSTED!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, JOptions, JOptions[0]);
		}
		}
		}
		}
		decideOutcome();
	}
	/**
	 * This method is used to display the message in the player wins.
	 */
	public void winAnswer(){
		JAnswer=JOptionPane.showOptionDialog(frame, "You WIN! Do you want to play again?", "You Win!", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, JOptions, JOptions[0]);
	}
	/**
	 * This method is used to determine if the player wanted to play more or not and the corresponding action is done. If s/he wants to play the game
	 * is restarted, if s/he doesn't want to play, then a goodbye message is shown.
	 */
	public void decideOutcome(){
		if(JAnswer==0){
			resetGame();
		}
		if(JAnswer==1){
			JOptionPane.showMessageDialog(null, "Goodbye");
			System.out.println("For the record you got:"+bjC+" BlackJacks");
			System.exit(0);
		}
	}
	/**
	 * This method restarts all the labels and counters and ArrayLists, which needs to be cleared and restarted in order for the player to be able to play
	 * again.
	 */
	public void resetGame(){
		clearLbls();
		clearPanels();
		enableBtns();
		addAll();
	}
	/**
	 * This method is part of the cleaning up after the game has ended. It removes all the labels and clears up before the new round begin.
	 */
	public void clearLbls(){
		cardLbls.clear();
		scoreLbl.setText(" ");
		dealerLbl.setText(" ");
	}
	/**
	 * This method is part of the cleaning up after the game has ended. It removes all the panels
	 * which need to be removed and clears up before the new round begin.
	 */
	public void clearPanels(){
		panelPlayer.removeAll();
		panelPlayer.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelDealer.removeAll();
		panel.remove(panelPlayer);
		panel.remove(panelDealer);
		panel.remove(dealerLbl);
		panel.remove(scoreLbl);
	}
	/**
	 * This method uses a method in board to clear up the scores of the player and the dealer.
	 */
	public void clearScores(){
		board.newRound();
	}
	/**
	 * This method enables the button as they were disabled in the end of the last round.
	 */
	public void enableBtns(){
		btnDraw.setEnabled(true);
		btnStay.setEnabled(true);
	}
	/**
	 * This method just lets the game be constructed and start again.
	 */
	public void addAll(){
		frame.pack();
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		createFields();
		panel.add(panelPlayer,BorderLayout.SOUTH);
		panel.add(panelDealer,BorderLayout.NORTH);
		frame.add(panel);
		frame.setVisible(true);
	}
}

