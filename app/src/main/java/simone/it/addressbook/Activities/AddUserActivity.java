package simone.it.addressbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import simone.it.addressbook.R;

/**
 * Created by Simone on 11/03/2017.
 */

public class AddUserActivity extends AppCompatActivity implements View.OnClickListener {
    public final static String USER_NAME_KEY = "USER_NAME_KEY";
    public final static String USER_ADDRESS_KEY = "USER_ADDRESS_KEY";
    public final static String USER_PHONE_KEY = "USER_PHONE_KEY";
    public final static String USER_EMAIL_KEY = "USER_EMAIL_KEY";

    TextInputEditText addNameET, addAddressET, addPhoneET, addEmailET;
    Button btnSave, btnCancel;
    ImageButton btnAddPhoto;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);
        addNameET = (TextInputEditText) findViewById(R.id.addNameET);
        addAddressET = (TextInputEditText) findViewById(R.id.addAddressET);
        addPhoneET = (TextInputEditText) findViewById(R.id.addPhoneET);
        addEmailET = (TextInputEditText) findViewById(R.id.addEmailET);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnAddPhoto = (ImageButton) findViewById(R.id.btnAddPhoto);
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        intent = getIntent();
        if (getIntent() != null) {
            if (getIntent().getStringExtra(USER_NAME_KEY) != null) {
                addNameET.setText(getIntent().getStringExtra(USER_NAME_KEY));
                addAddressET.setText(getIntent().getStringExtra(USER_ADDRESS_KEY));
                addPhoneET.setText(getIntent().getStringExtra(USER_PHONE_KEY));
                addEmailET.setText(getIntent().getStringExtra(USER_EMAIL_KEY));
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave){
            Intent intent = new Intent();
            intent.putExtra(USER_NAME_KEY, addNameET.getText().toString());
            intent.putExtra(USER_ADDRESS_KEY, addAddressET.getText().toString());
            intent.putExtra(USER_PHONE_KEY, addPhoneET.getText().toString());
            intent.putExtra(USER_EMAIL_KEY, addEmailET.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (v.getId() == R.id.btnCancel) {
            finish();
        }
        else if (v.getId() == R.id.btnAddPhoto){

        }
    }
}
