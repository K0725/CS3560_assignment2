package User;

import java.util.Iterator;
import java.util.List;

public class TweetAnalyzer {
    public TweetAnalyzer() {
    }

    public int getTotalPositiveWords(List<User> userList) {
        int totalPositiveWords = 0;
        Iterator var3 = userList.iterator();

        while (var3.hasNext()) {
            User user = (User) var3.next();
            Iterator var5 = user.getNewsFeed().iterator();

            while (var5.hasNext()) {
                String tweet = (String) var5.next();
                if (this.containsPositiveWords(tweet)) {
                    ++totalPositiveWords;
                }
            }
        }

        return totalPositiveWords;
    }

    private boolean containsPositiveWords(String tweet) {
        return tweet.toLowerCase().contains("good") || tweet.toLowerCase().contains("great") || tweet.toLowerCase().contains("Excellent") ;
    }

    public int getTotalWords(List<User> userList) {
        int totalWords = 0;
        Iterator var3 = userList.iterator();

        while (var3.hasNext()) {
            User user = (User) var3.next();

            String tweet;
            for (Iterator var5 = user.getNewsFeed().iterator(); var5.hasNext(); totalWords += this.countWords(tweet)) {
                tweet = (String) var5.next();
            }
        }

        return totalWords;
    }

    private int countWords(String text) {
        String[] words = text.split("\\s+");
        return words.length;
    }

    public double calculatePositivePercentage(List<User> userList) {
        int totalPositiveWords = this.getTotalPositiveWords(userList);
        int totalWords = this.getTotalWords(userList);
        return totalWords == 0 ? 0.0 : (double) totalPositiveWords / (double) totalWords * 100.0;
    }
}