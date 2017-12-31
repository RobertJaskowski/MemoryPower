package rj.pl.memorypower;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 31.12.2017.
 */

public class Number_item implements Parcelable {
    int id;
    String number;

    public Number_item(int id,String number) {
        this.id = id;
        this.number = number;

    }

    protected Number_item(Parcel in) {
        id = in.readInt();
        number = in.readString();
    }

    public static final Creator<Number_item> CREATOR = new Creator<Number_item>() {
        @Override
        public Number_item createFromParcel(Parcel in) {
            return new Number_item(in);
        }

        @Override
        public Number_item[] newArray(int size) {
            return new Number_item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(number);
    }
}