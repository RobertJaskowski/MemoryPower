package rj.pl.memorypower;

/**
 * Created  by Robert on 11.01.2018 - 14:53.
 */

@SuppressWarnings("ALL")
public class MyTagTwo {
    String code;
    Integer image;
    String web_ref;

    public MyTagTwo() {
        code = null;
        image = null;
        web_ref = null;
    }

    public MyTagTwo(String cod, Integer img, String wref) {
        code = cod;
        image = img;
        web_ref = wref;
    }
}
