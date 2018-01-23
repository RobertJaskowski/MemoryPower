package rj.pl.memorypower;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

    @BindView(R.id.rankingSpinnerMonth)
    Spinner rankingSpinnerMonth;


    @BindView(R.id.rankingSpinnerType)
    Spinner rankingSpinnerType;


    @BindView(R.id.ranking)
    ListView listView;

    private DatabaseReference myRef;

    private boolean dataCheck;

    private FirebaseAuth mAuth;

    private FirebaseUser currentUser;
    private FirebaseDatabase database;


    private List<User> userlist;
    private ArrayList<String> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://memorypower-3ada5.firebaseio.com/");


        int month = Calendar.getInstance().get(Calendar.MONTH);


        rankingSpinnerType.setOnItemSelectedListener(new typeItemClicked());


        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = mAuth.getCurrentUser();

        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.e("sign ", "sign in success");
                    showList(((String) rankingSpinnerType.getItemAtPosition(0)).toLowerCase()); //gets numbers type
                } else {
                    Log.e("sign ", "failed");
                }
            }
        });


    }

    private void showList(String type) {


        DatabaseReference reference = database.getReference(type);

        Query query = reference.orderByChild("score").limitToLast(5);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.e("tad", String.valueOf(User.score)+" "+ User.name);

                userlist = new ArrayList<>();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    User item = dsp.getValue(User.class);
                    userlist.add(item);
                    Log.e("tad", String.valueOf(item.score) + " " + String.valueOf(item.name));
                }


                Collections.reverse(userlist);

//                for (User item : userlist)
//                {
//                    adapterList.add(item.)
//                }


                setSimpleAdapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    void setSimpleAdapter() {

        customRankingAdapter customRankingAdapter = new customRankingAdapter(this, userlist);
        listView.setAdapter(customRankingAdapter);
    }

    @OnClick(R.id.buttonTEMP1)
    void addScoreToNumbers() {
        Integer score = Integer.parseInt(scoreTempUp.getText().toString());
        String name = String.valueOf(scoreTempDown.getText());
        if (score == 0)
            score = 3;

        if (name.equals(""))
            name = "test";


        Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        String key = myRef.child("numbers").push().getKey();

        User testObj = new User(name, score, 35);
//        myRef.child("numbers").child(currentUser.getUid()).setValue(User);  //todo this works with anonymous auth , its overriding data
        myRef.child("numbers").child(key).setValue(testObj);

    }

    @OnClick(R.id.buttonTEMP2)
    void addScoreToWords() {

        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        String key = myRef.child("words").push().getKey();
        User testObj = new User("Rob", 5, 35);
        myRef.child("words").child(currentUser.getUid()).setValue(testObj);
    }

    @OnClick(R.id.buttonTEMP3)
    void UpdateScoreNumbers() {
        Toast.makeText(this, "updatin numbers", Toast.LENGTH_SHORT).show();
        String key = myRef.child("numbers").getKey();
        String userKey = myRef.child("numbers").push().getKey();
        User testObj = new User("Tom", 6, 56);
        Map<String, Object> postValues = testObj.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put(key+ "/" + userKey, postValues); //creates new in numbers with key > data
//        childUpdates.put(key, postValues); // this delates old from numbers but doesnt create user folder,  numbers> data
        childUpdates.put(key + "/" + currentUser.getUid() + "/", postValues);


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

    class typeItemClicked implements  AdapterView.OnItemSelectedListener {



        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i) {
                case 0:
                    showList("numbers");
                    break;
                case 1:
                    showList("words");
                    break;
                case 2:
                    showList("cards");
                    break;
                case 3:
                    showList("names");
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}

