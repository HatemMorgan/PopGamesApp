package com.example.hatem.popgames.ORM;

/**
 * Created by hatem on 11/2/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Games  {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("name")
    @Expose
    private String name;

//    private Games(Parcel in) {
//        name = in.readString();
//        id = in.readInt();
//        image.setThumbUrl(in.readString());
//    }

    /**
     *
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     *
     * @param image
     *     The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     *
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        if(image == null){
            return "Games{" +
                    "id=" + id +
                    ", image=" + image +
                    ", name='" + name + '\'' +
                    '}';
        }else{
            return "Games{" +
                    "id=" + id +
                    ", image=" + image.getSmallUrl() +
                    ", name='" + name + '\'' +
                    '}';
        }

    }

    //    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//            dest.writeString(name);
//            dest.writeInt(id);
//            dest.writeString(getImage().getThumbUrl());
//
//    }
//
//    public static final Parcelable.Creator<Games> CREATOR = new Parcelable.Creator<Games>() {
//        public Games createFromParcel(Parcel in) {
//            return new Games(in);
//        }
//
//        public Games[] newArray(int size) {
//            return new Games[size];
//        }
//    };


}