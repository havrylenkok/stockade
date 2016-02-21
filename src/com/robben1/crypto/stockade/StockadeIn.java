package com.robben1.crypto.stockade;

import java.util.*;

/**
 * Created by robben1 on 19.02.16.
 */
public class StockadeIn {

    public String openText;
    public List<String> transit;
    public String closedText;
    public Integer height;
    public String[] levels;

    StockadeIn() {
        this("Приклад", 4);
    }

    StockadeIn(String closed, Integer height) {
        this.openText = closed;
        this.height = height;
        this.initializeLevels(height);
        this.initializeTransit();
        this.closedText = makeClosedText(this.openText);
    }


    /**
     * codes input string
     *
     * @param openText1
     * @return
     */
    String makeClosedText(String openText1) {
        try {

            int mode = 0;

            for (int i = 0; i < this.openText.length(); i += this.height - 1) {
                if (mode == 1) {
                    for (int j = this.height - 1, k = 0; j > 0; j--, k++) {
                        if (this.transit.get(i + k) != null) {
                            this.levels[j] += this.transit.get(i + k);
                        }

                    }
                    mode = 0;
                } else {
                    for (int j = 0; j < this.height - 1; j++) {

                        if (this.transit.get(i + j) != null) {
                            this.levels[j] += this.transit.get(i + j);
                        }

                    }
                    mode = 1;
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        } finally {

            return serializeLevels();

        }
    }

    /**
     * creates string for output
     *
     * @return
     */
    protected String serializeLevels() {
        String result = "";

        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < levels[i].length(); j++) {
                result += levels[i].charAt(j);
            }
        }

        return result;
    }

    /**
     * creates and fills with "" string array
     *
     * @param height
     * @return
     */
    protected boolean initializeLevels(int height) {
        this.levels = new String[height];
        Arrays.fill(this.levels, "");
        return true;
    }

    /**
     * creates and fills array of chars to work with from input string
     *
     * @return
     */
    protected boolean initializeTransit() {
        this.transit = new ArrayList<String>();
        for (int i = 0; i < openText.length(); i++) {
            this.transit.add(String.valueOf(openText.charAt(i)));
        }
        return true;
    }

}
