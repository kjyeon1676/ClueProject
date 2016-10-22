package Model.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hyun on 2015-01-26.
 */
public class  CardList implements Serializable{

    private ArrayList<Card> weaponCard;
    private ArrayList<Card> roomCard;
    private ArrayList<Card> personCard;

    public CardList()
    {
       weaponCard = new ArrayList<Card>();
       roomCard = new ArrayList<Card>();
       personCard = new ArrayList<Card>();
    }
    public ArrayList<Card> getWeaponCard() {
        return weaponCard;
    }

    public void addWeaponCard(Card weaponCard) {
        this.weaponCard.add(weaponCard);
    }

    public ArrayList<Card> getRoomCard() {
        return roomCard;
    }

    public void addRoomCard(Card roomCard)
    {
        this.roomCard.add(roomCard);
    }

    public ArrayList<Card> getPersonCard() {
        return personCard;
    }

    public void addPersonCard(Card personCard) {
        this.personCard.add(personCard);
    }


}
