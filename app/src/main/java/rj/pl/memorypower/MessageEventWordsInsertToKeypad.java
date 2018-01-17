package rj.pl.memorypower;

/**
 * Created by Robert on 07.01.2018 - 13:01.
 */

@SuppressWarnings("ALL")
public class MessageEventWordsInsertToKeypad {
    String text;

    public MessageEventWordsInsertToKeypad(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
