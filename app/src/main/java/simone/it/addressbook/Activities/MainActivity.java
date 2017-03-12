package simone.it.addressbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import simone.it.addressbook.Adapters.UserAdapter;
import simone.it.addressbook.DatabaseHandler.DatabaseHandler;
import simone.it.addressbook.Models.User;
import simone.it.addressbook.R;

import static simone.it.addressbook.Activities.AddUserActivity.USER_ADDRESS_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_EMAIL_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_NAME_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_PHONE_KEY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    public static final int REQUEST_ADD = 1001;
    public static final int REQUEST_DELETE = 1002;
    public static final int REQUEST_EDIT = 1003;
    EditText searchUserET;
    FloatingActionButton btnAdd;

    User user = new User();


    RecyclerView.LayoutManager layoutManager;
    UserAdapter adapter;
    RecyclerView userRV;
    DatabaseHandler database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userRV = (RecyclerView) findViewById(R.id.search_rv);
        searchUserET = (EditText) findViewById(R.id.searchUserET);
        layoutManager = new LinearLayoutManager(this);
        adapter = new UserAdapter();
        userRV.setAdapter(adapter);
        userRV.setLayoutManager(layoutManager);
        database = new DatabaseHandler(this);
        adapter.setDataSet(database.getAllUsers());

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        searchUserET.addTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        ((Activity) v.getContext()).startActivityForResult((new Intent(v.getContext(), AddUserActivity.class)), REQUEST_ADD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK) {
            user.setName(data.getStringExtra(USER_NAME_KEY));
            user.setAddress(data.getStringExtra(USER_ADDRESS_KEY));
            user.setPhone(data.getStringExtra(USER_PHONE_KEY));
            user.setEmail(data.getStringExtra(USER_EMAIL_KEY));
            userRV.scrollToPosition(0);
            database.addUser(user);
            adapter.addUser(user);
            Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        adapter.setDataSet(database.getSearchUsers(s));
    }

    @Override
    public void afterTextChanged(Editable s) {
        if(searchUserET.getText().toString().equals("") || searchUserET.getText().toString().equals(" ")){
            searchUserET.setHint("Cerca");
            adapter.setDataSet(database.getAllUsers());
        }
    }
}
