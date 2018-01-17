package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ranking extends Activity {

    @BindView(R.id.buttonTEMP1)
    Button buttonAddOne;

    @BindView(R.id.buttonTEMP2)
    Button buttonAddTwo;

    @BindView(R.id.buttonUpdate)
    Button buttonUpdate;


    @BindView(R.id.scoreTEMP)
    EditText scoreTempUp;


    @BindView(R.id.buttonTEMP3)
    Button buttonUpdateOne;

    @BindView(R.id.buttonTEMP4)
    Button buttonUpdateTwo;

    @BindView(R.id.buttonUpdate2)
    Button buttonUpdate2;


    @BindView(R.id.scoreTEMP2)
    EditText scoreTempDown;


    @BindView(R.id.ranking)
    ListView listView;

    private DatabaseReference myRef;

    private boolean dataCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://memorypower-3ada5.firebaseio.com/");


    }


    @OnClick(R.id.buttonTEMP1)
    void addScoreToNumbers() {


        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        String key = myRef.child("numbers").push().getKey();
        testObj testObj = new testObj("Rob", 5, 35);
        myRef.child("numbers").child(key).setValue(testObj);

    }

    @OnClick(R.id.buttonTEMP2)
    void addScoreToWords() {

        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        String key = myRef.child("words").push().getKey();
        testObj testObj = new testObj("Rob", 5, 35);
        myRef.child("words").child(key).setValue(testObj);
    }

    @OnClick(R.id.buttonTEMP3)
    void UpdateScoreNumbers() {
        Toast.makeText(this, "updatin numbers", Toast.LENGTH_SHORT).show();
        String key = myRef.child("numbers").getKey();
        String userKey = myRef.child("numbers").push().getKey();
        testObj testObj = new testObj("Tom", 6, 56);
        Map<String, Object> postValues = testObj.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put(key+ "/" + userKey, postValues); //creates new in numbers with key > data
//        childUpdates.put(key, postValues); // this delates old from numbers but doesnt create user folder,  numbers> data
        childUpdates.put("/"+ key+"/",postValues);


        myRef.updateChildren(childUpdates);


//        if (checkIfFirebaseHasData(key)){
//            Toast.makeText(this,"has data",Toast.LENGTH_SHORT).show();
//        }


    }

    boolean checkIfFirebaseHasData(String key) {


        myRef.child("numbers").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    dataCheck = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return dataCheck;

    }

}

class testObj {
    public String name;
    public int score;
    public int time;

    public testObj() {
    }

    public testObj(String name, int score, int time) {
        this.name = name;
        this.score = score;
        this.time = time;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("score", score);
        result.put("time", time);
        return result;
    }

}