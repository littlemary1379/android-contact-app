package com.mary.contactapp.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true) // 기본키 + 시퀀스
    @ColumnInfo(name="contact_id")
    private long id;

    @ColumnInfo(name="contact_name")
    private String name;

    @ColumnInfo(name="contact_email")
    private String email;

    @ColumnInfo(name="contact_profileUrl")
    private String profileUrl;

    @Ignore
    public Contact() { }

    //DB에 넣을 때 필요 없는 생성자는 Ignore
    //Room은 생성자로 하기 때문 <<
    public Contact(String name, String email,String profileUrl) {
        this.name = name;
        this.email = email;
        this.profileUrl=profileUrl;
    }

    @Ignore
    public Contact(long id, String name, String email,String profileUrl) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.profileUrl=profileUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}

/**
 *  @Ignore // 엔티티에 지속하고 싶지 않은 필드나 생성자가 있는 경우 @Ignore를 사용한다.
 */