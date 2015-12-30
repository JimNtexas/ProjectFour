package com.grayraven.jokes;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Joker {
    private List<String> jokeList = new ArrayList<String>();
    private static final Logger Log = Logger.getLogger(Joker.class.getName());

    public Joker() {
        jokeList.add("I can't believe I got fired from the calendar factory. All I did was take a day off.");
        jokeList.add("I wanna make a joke about sodium, but Na..");
        jokeList.add("I'm reading a book about anti-gravity. It's impossible to put down.");
        jokeList.add("My math teacher called me average. How mean!");
    }

    /**
     * Return a random joke from jokeList
     */
    public String getJoke() {
        Random r = new Random();
        int rn = r.nextInt(jokeList.size());
        String result = jokeList.get(rn);
        Log.info("getJoke() returns " + result);
        return result;
    }

    /**
     * Return the joke at position i, or null String if i is out of range
     */
    public String getJoke(int i) {
        String result = "";
        if(i < jokeList.size()) {
            result = jokeList.get(i);
        } else {
            Log.warning("getJoke( " + i + ") out of range");
        }
        Log.info("getJoke(" + i + ") returns " + result);
        return result;
    }

    /**
     * return the number of jokes contained in this object
     */
    public int getListSize() {
        return jokeList.size();
    }
}
