import java.util.ArrayList;
import java.util.Collections;
/**
 * @author i7461730
 * Name: Daniel Dimanov
 * Date: 15.12.2015
 * Task: Assignment 2
 * Description: This class will be the blueprint for the 6 decks, which blackjack is played with. 
 * Every deck will have 52 cards and the deck will store all the ids and all the cards in the deck. 
 * Moreover the deck will actually create these ids and then create the cards using them.  
 */
public class Deck {
	private ArrayList<String> ids=new ArrayList<String>();
	private ArrayList<Card> cards=new ArrayList<Card>();
	/**
	 * This is the constructor method. It does not take any parameters, but rather uses the ArrayLists, which the whole class has access to.
	 * Thus it creates the 52 cards and stores it in the ArrayList and this is essentially the deck.
	 */
	public Deck(){
		sgId();
		for(int count=0;count<52;count++){
			cards.add(new Card(ids,count));
		}
	}
	/**
	 * In this method all the Ids are made. Integrated for loop in a for loop is used to determine the value and the general one is for the color(S(Spade),D(Diamond),H(Hearts),C(Clubs))
	 */
	public void sgId(){
		for(int countV=0;countV<4;countV++){ //V for value
			for(int countF=0;countF<13;countF++){//F for face
				switch(countV){
				case 0:ids.add("C"+(countF+1)); break;
				case 1:ids.add("D"+(countF+1)); break;
				case 2:ids.add("H"+(countF+1)); break;
				case 3:ids.add("S"+(countF+1)); break;
				}
			}
		}
	}
	/**
	 * Pretty straight- forward. This method shuffles the deck.
	 */
	public void shuffle(){
		Collections.shuffle(cards);
	}
	/**
	 * This method is mostly used for testing, but can be used to further develop the program and make on every 3 or 4 rounds the cards to be disposed and new cards to be used. Anyway not still done in the program. 
	 */
	public void disposeCards(){
		cards.clear();
	}
	/** 
	 * This method returns the Card at given index
	 * @param index used to give the right card
	 * @return the card  at the given index
	 */
	public Card getCard(int index){	
		return cards.get(index);
	}
	/**
	 * This method gives back all the ids.
	 * @return the ids which are really important for the GUI and the right pictures
	 */
	public ArrayList<String> getIds(){
		return ids;
	}
	/**
	 * This method is used mainly for testing and it just prints all the cards in a deck in the console. 
	 */
	public void printCards(){
		for(Card card:cards){
			System.out.println(card.getId());
			System.out.println(card.getValue());
		}
	}

}
