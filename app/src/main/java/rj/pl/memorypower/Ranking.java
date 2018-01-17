package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Ranking extends Activity {

    @BindView(R.id.buttonTEMP)
    Button buttonAdd;

    @BindView(R.id.buttonUpdate)
    Button buttonUpdate;

    @BindView(R.id.ranking)
    ListView listView;

    @BindView(R.id.scoreTEMP)
    EditText scoreTemp;


    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ButterKnife.bind(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://memorypower-3ada5.firebaseio.com/");


    }


    @OnClick(R.id.buttonTEMP)
    void addScore() {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
        String key = myRef.child("numbers").push().getKey();
        testObj testObj = new testObj("Rob", 5, 35);
        myRef.child("numbers").child(key).setValue(testObj);
    }

    @OnClick(R.id.buttonUpdate)
    void UpdateScore(){
        Toast.makeText(this,"updatin",Toast.LENGTH_SHORT).show();

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

}