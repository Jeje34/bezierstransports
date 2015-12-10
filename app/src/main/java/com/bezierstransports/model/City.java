package com.bezierstransports.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class City implements Parcelable {

    private long id;
    private String cityName;

    public City() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private City(Parcel in) {
        this.id = in.readLong();
        this.cityName = in.readString();
    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(cityName);
    }

    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        public City[] newArray(int size) {
            return new City[size];
        }
    };
}
