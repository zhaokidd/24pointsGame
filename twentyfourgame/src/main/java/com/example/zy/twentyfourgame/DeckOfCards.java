package com.example.zy.twentyfourgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zy on 2015/3/7 0007.
 */
public class DeckOfCards {
    private final static int WIDTH=123;
    private final static int HEIGHT=158;
    /**
     arrayList To store the cards
     */
    private ArrayList<Card> arrayListOfSpade = new ArrayList<Card>(13);
    private ArrayList<Card> arrayListOfDiamond = new ArrayList<Card>(13);
    private ArrayList<Card> arrayListOfHeart = new ArrayList<Card>(13);
    private ArrayList<Card> arrayListOfClub = new ArrayList<Card>(13);
    private ArrayList<Card> randomCards     = new ArrayList<Card>(4);

    /**
     *constructor of the DeckOfCards
     * Give every card its image , numbe and type
     * Using project file path to get image Icon
     * For example number:1, image:"1.jpg", type:Spade
     */
    public DeckOfCards(){
        int value=R.drawable.a01;
        for(int i=1;i<=13;i++){
            arrayListOfSpade.add(new Card(i,"Spade",value-1+i));
        }

        for(int i=14;i<=26;i++){
            arrayListOfHeart.add(new Card(i-13,"Heart",value-1+i));
        }

        for(int i=27;i<=39;i++){
            arrayListOfClub.add(new Card(i-26,"Club", value-1+i));
        }

        for(int i=40;i<=52;i++){
            arrayListOfDiamond.add(new Card(i-39,"Diamond", value-1+i));
        }

        //generate four cards randomly

        randomAvailable();
    }

    /**
     *This is a method to generate four random numbers to decide the Cards displayed
     *
     */
    public void randomAvailable(){
        Random random = new Random();
        randomCards.add(arrayListOfSpade.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfDiamond.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfClub.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfHeart.get(random.nextInt(12)+1));
    }

    public void reRandomSelect(){
        randomCards.clear();
        Random random = new Random();
        randomCards.add(arrayListOfSpade.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfDiamond.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfClub.get(random.nextInt(12)+1));
        randomCards.add(arrayListOfHeart.get(random.nextInt(12)+1));
    }

    public ArrayList getRandomCards(){
        return this.randomCards;
    }
}
