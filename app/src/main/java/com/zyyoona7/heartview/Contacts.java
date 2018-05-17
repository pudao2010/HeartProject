package com.zyyoona7.heartview;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/19 0019.
 */

public class Contacts implements Serializable {
    private static final long serialVersionUID = 646863438754L;


    private int id;

    private String phoneNumber;

    private long contactId;

    private String category;

    private String userName;

    private boolean isUploaded = false;

    public Contacts(){

    }

    public Contacts(String userName, String phoneNumber, long contactId, String category) {
        this.userName = userName;
        this.contactId = contactId;
        this.phoneNumber = phoneNumber;
        this.category = category;
    }

    /*public static List<Contacts> parseFromMailList(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Type type = new TypeToken<List<Contacts>>() {
        }.getType();
        return GsonHelper.fromJson(json, type);
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isUploaded() {
        return isUploaded;
    }

    public void setUploaded(boolean uploaded) {
        isUploaded = uploaded;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return "Contacts{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", contactId=" + contactId +
                ", category='" + category + '\'' +
                ", userName='" + userName + '\'' +
                ", isUploaded=" + isUploaded +
                '}';
    }
}
