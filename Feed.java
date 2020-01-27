package rssfeed;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Feed {

    //Third party for read rss
    /*
      <dependency>
            <groupId>rome</groupId>
            <artifactId>rome</artifactId>
            <version>1.0</version>
        </dependency>
    */
  static   URL feedSource;

   static Runnable fetch = new Runnable() {
        public void run() {
            while (true) {

                try {
                    feedSource = new URL("http://rss.cnn.com/rss/edition.rss");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


  static   Runnable read = new Runnable() {
        public void run() {
            while (true) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed  feed= null;
                try {
                    feed = input.build(new XmlReader(feedSource));
                } catch (FeedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(feed.getImage().getUrl());

                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("/home/coder/Desktop/output.txt", true));
                    writer.append(' ');
                    writer.append(feed.getImage().getUrl());
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(20000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static void main(String[] args) {
    File myObj = new File("/home/coder/Desktop/output.txt");
        try {
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(fetch);
        thread.start();
        Thread thread1 = new Thread(read);
        thread1.start();




    }

}



