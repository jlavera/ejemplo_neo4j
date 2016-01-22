package com.company;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> accunts = new LinkedList<String>() {{
            add("mterstegen1");
            add("teammessi");
            add("ivanrakitic");
            add("luissuarez9");
            add("Mascherano");
            add("JordiAlba");
            add("DaniAlvesD2");
            add("3gerardpique");
            add("neymarjr");
        }};

        try {
            System.out.println("Starting");

            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("97p6JnsqyhPTSKEDth3KKvIUb")
                    .setOAuthConsumerSecret("TuP9cY380bSX3u4dnauuiYuETQMxzTAeVa3VrSwKgVuaRmXXvH")
                    .setOAuthAccessToken("2796153780-9yWxtA0XU0Zp9e7yHArt0EH7r7Uoi7WoIGoBxk6")
                    .setOAuthAccessTokenSecret("wN6zjq38oYN0lu4abhbXoUETMUIcbFxkpGuOhV2SNFa7D");

            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();

            for (String account : accunts) {
                account = accunts.get(0);
                System.out.println("[" + account + "] Fetching");

                long cursor = -1;
                boolean full = false;
                FileWriter writer = new FileWriter("output/" + account + ".csv", false);

                PagableResponseList<User> pager = null;
                int cont = 0;
                do {
                    full = false;

                    try {
                        pager = twitter.getFriendsList(account, cursor, 200);
                        for (User user : pager) {
                            writer.write(account + ";" + user.getScreenName() + '\n');
                            System.out.println("user [" + cont++ + "]: " + user.getScreenName());
                        }
                    } catch (TwitterException te) {
                        System.out.println("Error [" + account + "]: " + te.getMessage());
                        full = true;
                    }

                } while ((cursor = pager.getNextCursor()) != 0 && !full);
                writer.close();
            }

            System.out.println("[Done Loading]");
        } catch (IOException io){
            System.out.println(io.getMessage());
        }

    }
}
