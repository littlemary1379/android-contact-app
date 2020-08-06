package com.mary.contactapp.service;

import com.mary.contactapp.db.model.Contact;
import com.mary.contactapp.repository.ContactRepository;

import java.util.ArrayList;
import java.util.List;

public class ContactService {

    private ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public long 연락처등록(Contact contact){
        return contactRepository.save(contact);
    }

    public int 연락처수정(Contact contact){
        return contactRepository.update(contact);
    }

    public int 연락처삭제(long contactId){
        return contactRepository.delete(contactId);
    }

    public int 연락처전체삭제(){
        return contactRepository.deleteAll();
    }

    public Contact 연락처상세보기(long contactId){
        return contactRepository.findById(contactId);
    }

    public List<Contact> 연락처전체보기(){
        List<Contact> contacts = contactRepository.findAll();
        if(contacts == null){
            return new ArrayList<>();
        }
        return contactRepository.findAll();
    }
}