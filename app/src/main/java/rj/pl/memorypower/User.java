package rj.pl.memorypower;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert on 21.01.2018 - 01:29.
 */

public class User {

    public String name;
    public int score;
    public int time;

    public User() {
    }

    User(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("score", score);
        result.put("time", time);
        return result;
    }


}
