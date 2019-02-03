/**
 * @author i7461730
 * Name: Daniel Dimanov
 * Date: 11.12.2015
 * Task: Assignment 2
 * Description: I use this class to construct all the cards in the decks that I'm going to use for my simple blackjack game. 
 * So this is a really important class since if a mistake is to be found in here it will affect all the other classes. 
 */
import java.util.ArrayList;
import java.util.Random;
public class Card {
	private String id;
	private int value;
	Random rand;
	/**
	 * This method is the constructor method of the class Card. It essentially is the constructor for the blueprint of all the cards 
	 * @param ids    this is the ArrayList containing all the ids of the cards, which are for example S1(for Ace of Spades) D12(for the Queen of Diamond) and so on
	 * @param index  the index is the index of the specific card to be constructed
	 */
	public Card(ArrayList<String> ids,int index){
		this.id=ids.get(index);
		this.value=initializeValue(id);
	}
	/**
	 * This method gives back the value of the given card
	 * @return value  the value of the card. The points which is worth.
	 */
	public int getValue(){
		return value;
	}
	/**
	 * This method gives back the id of the given card
	 * @return id    the id of the card. The unique identifier of the card, which is used to further connect with the image of this card.
	 */
	public String getId(){
		return id;
	}
	/**
	 * 
	 * @param id The id of the card(formed with a letter for the color and then the value 1-13)
	 * @return the value of the card after all the Js,Qs and Ks are made 10s and the Ace is made 11
	 */
	public int initializeValue(String id){
		int tempValue=Integer.parseInt(id.substring(1));
		if(tempValue>10){
			tempValue=10;
		}
		if(tempValue==1){
			tempValue=11;
		}
		return tempValue;
	}
	/**
	 * The method is used to manipulate the value of the cards and it is needed, because the ability of the Ace to be 1 and 11. Or for other games to change to something else.
	 * @param tempValue used to change the value of the card itself.
	 */
	public void setValue(int tempValue){
		value=tempValue;
	}

}
