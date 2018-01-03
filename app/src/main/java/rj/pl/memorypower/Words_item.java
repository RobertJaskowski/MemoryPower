package rj.pl.memorypower;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 03.01.2018.
 */

public class Words_item implements Parcelable {
    int id;
    String word;

    int number;


    public Words_item(int id, int number) {
        this.id = id;
        this.number = number;

        Words words = new Words();

        this.word = words.getWords(number);

    }

    protected Words_item(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        word = in.readString();
    }

    public static final Parcelable.Creator<Words_item> CREATOR = new Parcelable.Creator<Words_item>() {
        @Override
        public Words_item createFromParcel(Parcel in) {
            return new Words_item(in);
        }

        @Override
        public Words_item[] newArray(int size) {
            return new Words_item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(word);
        parcel.writeInt(number);
    }
}
