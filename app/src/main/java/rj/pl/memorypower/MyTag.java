package rj.pl.memorypower;

/**
 * Created  by Robert on 11.01.2018 - 12:38.
 */

public class MyTag {
    String code;
    Integer image;
    String web_ref;

    public MyTag() {
        code = null;
        image = null;
        web_ref = null;
    }

    public MyTag(String cod, Integer img, String wref) {
        code = cod;
        image = img;
        web_ref = wref;
    }
}
