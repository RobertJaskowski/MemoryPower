package rj.pl.memorypower;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Robert on 09.01.2018 - 18:30.
 */

public class Cards_item implements Parcelable {
    int id;
    int card;

    int number;

    public Cards_item(int id, int number) {
        this.id = id;
        this.number = number;

        Cards cards = new Cards();

        this.card = cards.getCard(number);
    }


    protected Cards_item(Parcel in) {
        id = in.readInt();
        card = in.readInt();
        number = in.readInt();
    }

    public static final Creator<Cards_item> CREATOR = new Creator<Cards_item>() {
        @Override
        public Cards_item createFromParcel(Parcel in) {
            return new Cards_item(in);
        }

        @Override
        public Cards_item[] newArray(int size) {
            return new Cards_item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(card);
        parcel.writeInt(number);
    }
}