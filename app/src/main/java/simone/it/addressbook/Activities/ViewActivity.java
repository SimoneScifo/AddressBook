package simone.it.addressbook.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import simone.it.addressbook.R;

import static simone.it.addressbook.Activities.AddUserActivity.USER_ADDRESS_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_EMAIL_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_NAME_KEY;
import static simone.it.addressbook.Activities.AddUserActivity.USER_PHONE_KEY;
import static simone.it.addressbook.Activities.MainActivity.REQUEST_DELETE;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String USER_POSITION_KEY = "USER_POSITION_KEY" ;
    TextView viewNameTV, viewAddressTV, viewPhoneTV, viewEmailTV;
    ImageButton btncall, btnmap, btnmail;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewNameTV = (TextView) findViewById(R.id.viewNameTV);
        viewAddressTV = (TextView) findViewById(R.id.viewAddressTV);
        viewPhoneTV = (TextView) findViewById(R.id.viewPhoneTV);
        viewEmailTV = (TextView) findViewById(R.id.viewEmailTV);

        btncall = (ImageButton) findViewById(R.id.btnCall);
        btnmail = (ImageButton) findViewById(R.id.btnMail);
        btnmap = (ImageButton) findViewById(R.id.btnMap);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        btnmap.setOnClickListener(this);
        btncall.setOnClickListener(this);
        btnmail.setOnClickListener(this);



        intent = getIntent();
        if (getIntent() != null) {
            viewNameTV.setText(getIntent().getStringExtra(USER_NAME_KEY));
            viewAddressTV.setText(getIntent().getStringExtra(USER_ADDRESS_KEY));
            viewPhoneTV.setText(getIntent().getStringExtra(USER_PHONE_KEY));
            viewEmailTV.setText(getIntent().getStringExtra(USER_EMAIL_KEY));
            setTitle(viewNameTV.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
       int id =  v.getId();
        switch (id){
            case R.id.fab:
                String text = "Il nome è: " + viewNameTV.getText().toString() + "\n"+ "L'indirizzo è: " + viewAddressTV.getText().toString() + "\n" + "Il numero di telefono è: " + viewPhoneTV.getText().toString() + "\n"+ "L'email è: " + viewEmailTV.getText().toString();
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            case R.id.btnCall:
                String phoneNumber = viewPhoneTV.getText().toString();
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(i);
                break;

            case R.id.btnMail:
                String textMessage = "Il nome è: " + viewNameTV.getText().toString() + "\n"+ "L'indirizzo è: " + viewAddressTV.getText().toString() + "\n" + "Il numero di telefono è: " + viewPhoneTV.getText().toString() + "\n"+ "L'email è: " + viewEmailTV.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:" + viewEmailTV.getText().toString());
                intent.setData(uri);
                intent.putExtra("subject", "My contact");
                intent.putExtra("body", textMessage);
                startActivity(intent);
                break;

            case R.id.btnMap:
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + viewAddressTV.getText().toString());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(mapIntent);
                break;
        }
    }
}
