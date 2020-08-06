package com.mary.contactapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mary.contactapp.adapter.ContactAdapter;
import com.mary.contactapp.db.ContactAppDatabase;
import com.mary.contactapp.db.model.Contact;
import com.mary.contactapp.service.ContactService;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main_Activity";
    private MainActivity mContext = MainActivity.this;

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ContactAdapter adapter;
    private FloatingActionButton fab;
    private CircleImageView ivProfile;

    private ContactAppDatabase contactAppDatabase;
    private ContactService contactService;
    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // DB관련
        contactAppDatabase = Room.databaseBuilder(getApplicationContext(), ContactAppDatabase.class, "ContactDB")
                .allowMainThreadQueries()
                .build();
        contactService = new ContactService(contactAppDatabase.contactRepository());

        initData();
        initObject();
        initDesign();
        initListener();
    }

    private void initData(){
        contacts = contactService.연락처전체보기();
    }

    private void initObject(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recycler_view_contacts);
        fab = findViewById(R.id.fab);
        ivProfile = findViewById(R.id.iv_profile);
        adapter = new ContactAdapter(mContext, contacts);
    }

    private void initDesign(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Contact App");

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initListener(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        contactService.연락처전체삭제();
        notifyListener();
        return true;
    }

    public void addContactDialog(){
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_add_contact, null);
        final EditText etName = dialogView.findViewById(R.id.name);
        final EditText etEmail = dialogView.findViewById(R.id.email);

        // 갤러리 사진 가져오기
        ivProfile = dialogView.findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToAlbum();
            }
        });

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 등록");
        dlg.setView(dialogView);
        dlg.setPositiveButton("등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                createContact(etName.getText().toString(), etEmail.getText().toString());
            }
        });
        dlg.setNegativeButton("닫기", null);
        dlg.show();
    }

    private void createContact(String name, String email) {
        long contactId = contactService.연락처등록(new Contact(name, email));
        // if 해서 contactId가 0이 아닐 때 동작하게 해야 함
        Contact contact = contactService.연락처상세보기((long)contactId);
        adapter.addItem(contact);
        // create 될 때 실행 됨. 자동 notifyDataSetChanged
    }


    public void editContactDialog(final Contact contact){
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_add_contact, null);
        final EditText etName = dialogView.findViewById(R.id.name);
        final EditText etEmail = dialogView.findViewById(R.id.email);

        etName.setText(contact.getName());
        etEmail.setText(contact.getEmail());
        // 갤러리 사진 수정하기
        ivProfile = dialogView.findViewById(R.id.iv_profile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //goToAlbum();
            }
        });

        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
        dlg.setTitle("연락처 수정");
        dlg.setView(dialogView);
        dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                contactService.연락처삭제(contact.getId());
                notifyListener();
            }
        });
        dlg.show();

    }

    public void notifyListener() {
        // DB 내용 변경 -> 어댑터 데이터 변경 -> UI갱신
        adapter.addItems(contactService.연락처전체보기()); // adapter에 내용 변경
        adapter.notifyDataSetChanged(); // UI 갱신
    }
}