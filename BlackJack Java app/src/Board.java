/**
 * @author i7461730
 * Name: Daniel Dimanov
 * Date: 03.01.2016
 * Task: Assignment 2
 * Description: This class is going to be the blueprint for the board on which the game is going to be played. In this class is most of the back-end programming of the game
 * coming from and the GUI controller is kind of only making it appear in GUI, but actually the GUI is handling some of the functionality as well since the game was meant to be
 * GUI oriented. Nevertheless this class is the backbone of the whole program and it is making 6 decks in its constructor and then it is initiating the conters and sums, which will
 * be then used to retrieve the right cards and calculate the scores and results. Then it is basically responsible for the whole back-end game play. 
 */
import java.util.*;
public class Board {
	private ArrayList<Deck> decks=new ArrayList<Deck>();
	private ArrayList<Card> cardsOnBoardP=new ArrayList<Card>();
	private ArrayList<Card> cardsOnBoardD=new ArrayList<Card>();
	private Card tempCard;
	private int cardIndex;
	private int sumPlayer;
	private int sumDealer;
	private Random rand=new Random();
	/**
	 * This is the constructor method of the class. It takes no parameters, but
	 * creates 6 decks and adds them to an ArrayList of decks. Then it starts up the counters.
	 * And the score sums.
	 */
	public Board(){
		for(int deckC=0;deckC<6;deckC++){
			Deck deck=new Deck();
			decks.add(deck);
		}
		restartIndex();
		restartSums();
	}
	/**
	 * This method is mostly used for testing and what it does is that it actually removes all decks from the board. This can be used also it some other game is to be played.
	 * My initial idea was to make a game in which blackjack to be only a minigame, but the time and the work I had to do seriously affected my plans and ambitions. However this method is still
	 * useful for further development of the program.
	 */
	public void disposeDeck(){
		decks.clear();
	}
	/**
	 * This method restarts the scores of the player and the dealer. This is necessary since you want the player to be able to play more than just one game. 
	 */
	public void restartSums(){
		sumPlayer=0;
		sumDealer=0;
	}
	/**
	 * This method gets rid of all the cards on the table both for the player and the dealer, thus allows the new "round to begin".
	 */
	public void restartCoB(){
		cardsOnBoardP.clear();
		cardsOnBoardD.clear();
	}
	/**
	 * This method restarts the index of the card to be drawn next.
	 */
	public void restartIndex(){
		cardIndex=0;
	}
	/**
	 * This method clears the scores and the cards on board and allows the next deal to be made.
	 */
	public void newRound(){
		restartSums();
		restartCoB();
	}
	/**
	 * This method shuffles all the decks.
	 */
	public void shuffleDecks(){
		for(Deck deck:decks){
			deck.shuffle();
		}
	}
	/**
	 * @param index the index of the deck to be returned
	 * @return the deck at the position index
	 */
	public Deck getDeck(int index){
		return decks.get(index);
	}
	/**
	 * This method checks if 30 cards have been used and if so, then it shuffles all the decks and starts again from the top. 
	 * This is done to mimic a casino actually doing exactly the same thing maybe in more on less cards, but it doesn't really matter, because in DD casino it is happening on the 30th card.
	 */
	public void checkShuffle(){
		if(cardIndex>30){
			for(Deck deck:decks){
				deck.shuffle();
			}
			restartIndex();
		}
	}
	/**
	 * This just makes the custom pointer- cardIndex to move one position.
	 */
	public void cardIndexIncrement(){
		cardIndex++;
	}
	/**
	 * This method adds the card drawn to the score of the player.
	 * @param cardValue the value of the card drawn by the player.
	 */
	public void addPlayerCard(int cardValue){
		addToPScore(cardValue);
	}
	/**
	 * This method adds the card drawn to the score of the dealer.
	 * @param cardValue the value of the card drawn by the dealer.
	 */
	public void addDealerCard(int cardValue){
		addToDScore(cardValue);
	}
	/**
	 * This is the actual method of how the cards are drawn each time.
	 * @param isPlayer this shows if the player is drawing or the dealer.
	 * @return the card drawn.
	 */
	public Card draw(boolean isPlayer){
		checkShuffle();
		tempCard=(decks.get(rand.nextInt(6))).getCard(cardIndex);
		cardIndexIncrement();
		if(isPlayer){
			addPlayerCard(tempCard.getValue());
			cardsOnBoardP.add(tempCard);
			setScoreP(fixAces(getPlayerScore(),cardsOnBoardP));
		}
		else{
			addDealerCard(tempCard.getValue());
			cardsOnBoardD.add(tempCard);
			setScoreD(fixAces(getDealerScore(),cardsOnBoardD));
		}
		return tempCard;
	}
	/**
	 * This method is used to fix the value of the Ace card if needed. Thus to change it from 1 to 11 or 11 to 1.
	 * @param score the score, which can be changed.
	 * @param cards the cards of the player or the dealer, which will be checked for aces and if they are not actually busted.
	 * @return the new score of the player or dealer, depending on which one was passed to the method.
	 */
	public int fixAces(int score,ArrayList<Card> cards){
		if(score>21){
			for(Card card:cards){
				if(score>21){
					if(card.getValue()==11){
						card.setValue(1);
						score-=10;
					}
				}
			}
		}else{if(score<=11){
			for(Card card:cards){
				if(card.getValue()==1){
					card.setValue(11);
					score+=10;
				}
			}
		}
		}
		return score;
	}
	/**
	 * This method adds the value of the given card to the score of the player.
	 * @param value the value of the card to be added to the score.
	 */
	public void addToPScore(int value){
		sumPlayer+=value;
	}
	/**
	 * This method adds the value of the given card to the score of the dealer.
	 * @param value the value of the card to be added to the score.
	 */
	public void addToDScore(int value){
		sumDealer+=value;
	}
	/**
	 * This method sets/changes the score of the player.
	 * @param value the value of the new score. (This is mainly used from the method fixAces)
	 */
	public void setScoreP(int value){
		sumPlayer=value;
	}
	/**
	 * This method sets/changes the score of the dealer.
	 * @param value the value of the new score. (This is mainly used from the method fixAces)
	 */
	public void setScoreD(int value){
		sumDealer=value;
	}
	/**
	 * This method is a getter for the player's score
	 * @return the score of the player's points
	 */
	public int getPlayerScore(){
		return sumPlayer;
	}
	/**
	 * This method is a getter for the dealer's score
	 * @return the score of the dealer's points
	 */
	public int getDealerScore(){
		return sumDealer;
	}
}

