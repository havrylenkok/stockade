package com.robben1.crypto.stockade;

public class Main {

    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }
        if (args.length < 1) {
            System.out.println("Usage: stockade in word height\nUsage: stockade out word [maxHeight] [knownHeight]");
            return;
        }

        if (args[1].equals("in")) {
            String word = args[2];
            Integer height = Integer.parseInt(args[3]);
            StockadeIn stock = new StockadeIn(word, height);
            System.out.println(stock.closedText);
        } else if (args[1].equals("out")) {
            String word = args[2];
            Integer maxHeight = Integer.parseInt(args[3]);
            if (args.length > 2) {
                StockadeOut stock = new StockadeOut(word, maxHeight);
                System.out.println(stock.opentext);
            } else if (args.length > 3){
                Integer knownHeight = Integer.parseInt(args[4]);
                StockadeOut stock = new StockadeOut(word, maxHeight, knownHeight);
                System.out.println(stock.opentext);
            } else {
                StockadeOut stock = new StockadeOut(word);
                System.out.println(stock.opentext);
            }
        }


    }
}
