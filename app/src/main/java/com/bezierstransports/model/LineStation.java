package com.bezierstransports.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class LineStation implements Parcelable {

    private Line line;
    private Station station;
    private String direction;
    private int ordre;

    public LineStation() { }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    @Override
    public String toString() {
        return "LineStation{" +
                "line=" + line +
                ", station=" + station +
                ", direction='" + direction + '\'' +
                ", ordre=" + ordre +
                '}';
    }

    private LineStation(Parcel in) {
        this.line = in.readParcelable(getClass().getClassLoader());
        this.station = in.readParcelable(getClass().getClassLoader());
        this.direction = in.readString();
        this.ordre = in.readInt();
    }

    public int describeContents() {
        return this.hashCode();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(line, flags);
        out.writeParcelable(station, flags);
        out.writeString(direction);
        out.writeInt(ordre);
    }

    public static final Parcelable.Creator<LineStation> CREATOR = new Parcelable.Creator<LineStation>() {
        public LineStation createFromParcel(Parcel in) {
            return new LineStation(in);
        }

        public LineStation[] newArray(int size) {
            return new LineStation[size];
        }
    };
}
