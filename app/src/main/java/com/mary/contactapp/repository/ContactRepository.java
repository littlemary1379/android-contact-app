package com.mary.contactapp.repository;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mary.contactapp.db.model.Contact;

import java.util.List;

@Dao
public interface ContactRepository {
    // 삽입된 행의 Primary Key값을 long 형으로 return 해준다.
    // 삽입된 행이 여러건일 경우 List<Long> 형으로 return 해준다.
    @Insert
    long save(Contact contact);

    // 수정된 행의 개수를 int 형으로 return 해준다.
    @Update
    int update(Contact contact);

    // 삭제된 행의 개수를 int 형으로 return 해준다.
    @Query("DELETE FROM contacts WHERE contact_id = :contactId")
    int delete(long contactId);

    @Query("DELETE FROM contacts")
    int deleteAll();

    @Query("SELECT * FROM contacts")
    List<Contact> findAll();

    @Query("SELECT * FROM contacts WHERE contact_id = :contactId ")
    Contact findById(long contactId);
}