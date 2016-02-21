package com.robben1.crypto.stockade;

import java.util.*;

/**
 * Created by robben1 on 20.02.16.
 */
public class StockadeOut {

    public List<String> opentext;
    protected List<String> transit;
    public String closedtext;
    public Integer height;
    public Integer maxHeight;
    protected Integer pointerToCurrentSymbol;
    protected Integer pointerToLastSymbolLastTime;

    StockadeOut() {
        this("идркапл");
    }

    StockadeOut(String close) {
        this(close, 3);
    }

    StockadeOut(String close, Integer maxHeight) {
        this(close, maxHeight, maxHeight);
    }


    StockadeOut(String close, Integer maxHeight, Integer height) {
        this.opentext = new ArrayList<String>();
        this.height = height;
        this.maxHeight = maxHeight;
        this.closedtext = close;
        this.pointerToCurrentSymbol = 0;
        this.pointerToLastSymbolLastTime = 0;
        this.initializeTransit();
        this.makeOpenText();

    }

    /**
     * decodes string to human readable form
     * @return
     */
    protected List<String> makeOpenText() {

        for (; this.height <= this.maxHeight; this.height++) {
            String[] levels = initializeLevels();
            String result = decrypt(this.height, levels);
            opentext.add(result);
        }

        return opentext;
    }

    /**
     * decrypts
     * @param height - height of triangle
     * @param levels - array of strings
     * @return
     */
    protected String decrypt(int height, String[] levels) {
        int mode = 0;


        int numberOfTops = this.transit.size() / this.height;

//        try {

            for (int i = this.height - 1, k = 1; i >= 0; i--, k++) {

                int numOfNext = 0;

                switch (numberOfTops) {
                    case 1:
                        numOfNext = 2;
                        break;
                    case 2:
                        numOfNext = 3;
                        break;
                    default:
                        numOfNext = (numberOfTops - 1) * 2 + 1;
                }


                if (i == this.height - 1) {
                    for (; pointerToCurrentSymbol < numberOfTops * k; pointerToCurrentSymbol++) {
                        levels[i] += this.transit.get(pointerToCurrentSymbol);
                    }
                } else {
                    if (i < this.height - 1 && i != 0) {

                        for (; pointerToCurrentSymbol < pointerToLastSymbolLastTime + numOfNext; pointerToCurrentSymbol++) {
                            levels[i] += this.transit.get(pointerToCurrentSymbol);
                        }
                    } else {
                        int numOfLast = numOfNext - 1;
                        for (; pointerToCurrentSymbol < this.transit.size(); pointerToCurrentSymbol++) {
                            levels[i] += this.transit.get(pointerToCurrentSymbol);
                        }
                    }
                }
                for(String l : levels) {

                    System.out.println(l);
                }
                pointerToLastSymbolLastTime = pointerToCurrentSymbol;
            }

//        } catch (IndexOutOfBoundsException ignore) {

//        } finally {
            return serializeLevels(levels);
//        }

    }

    /**
     * creates and fills with "" array of strings
     * @return
     */
    protected String[] initializeLevels() {
        String[] levels = new String[this.height];
        Arrays.fill(levels, "");
        return levels;
    }

    /**
     * creates string for output to user
     * @param levels
     * @return
     */
    protected String serializeLevels(String[] levels) {
        String result = "";

//        String str = levels[levels.length].charAt(0) + levels[levels.length - 1].charAt(0) + ...

        int mode = 0;
        Map<String, Integer> counters = new HashMap<String, Integer>();

        for (String level : levels) {
            int value = 0;
            counters.put(level, 0);

        }

        int longest = compare(levels);


        try {
            for (int i = 0; i < longest; i++) {

                if (mode == 0) {
                    for (int j = 0; j < levels.length; j++) {
                        if (levels[j].length() > counters.get(levels[j])) {
                            result += levels[j].charAt(counters.get(levels[j]));
                            counters.put(levels[j], counters.get(levels[j]) + 1);
                        } else {
                        }
                    }

                    mode = 1;
                } else {
                    for (int j = this.height - 2; j >= 0; j--) {
                        result += levels[j].charAt(counters.get(levels[j]));
                        counters.put(levels[j], counters.get(levels[j]) + 1);
                    }

                    mode = 0;
                }
            }


        } catch (StringIndexOutOfBoundsException ignore) {

        } finally {
            System.out.println(result);
            return result;
        }
    }

    /**
     * compares levels to find the longest
     * @param levels
     * @return
     */
    protected int compare(String[] levels) {
        int longest = 0;
        for (int i = 0; i < levels.length; i++) {
            if (longest < levels[i].length()) longest = levels[i].length();
        }
        return longest;
    }

    protected String serializeResults() {
        return null;
    }

    /**
     * creates array of chars from input string
     * @return
     */
    protected boolean initializeTransit() {
        this.transit = new ArrayList<String>();
        for (int i = 0; i < closedtext.length(); i++) {
            this.transit.add(String.valueOf(closedtext.charAt(i)));
        }
        return true;
    }
}
