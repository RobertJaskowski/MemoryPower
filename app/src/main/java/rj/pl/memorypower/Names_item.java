package rj.pl.memorypower;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created  by Robert on 07.01.2018 - 17:51.
 */

public class Names_item implements Parcelable{
    int id;
    String word;

    int number;


    public Names_item(int id, int number) {
        this.id = id;
        this.number = number;

        Names names = new Names();

        this.word = names.getNames(number);

    }

    public Names_item(int id, String word) {
        this.id = id;
        this.word = word;
    }

    protected Names_item(Parcel in) {
        id = in.readInt();
        number = in.readInt();
        word = in.readString();
    }

    public static final Parcelable.Creator<Names_item> CREATOR = new Parcelable.Creator<Names_item>() {
        @Override
        public Names_item createFromParcel(Parcel in) {
            return new Names_item(in);
        }

        @Override
        public Names_item[] newArray(int size) {
            return new Names_item[size];
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
