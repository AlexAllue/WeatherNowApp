package com.example.allu.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class LoginActivity extends Activity {
    private Button btnRegister,btnLogin;
    private EditText editUser,editPassword;
    private SQLiteDatabase db;
    private Cursor c;
    private String[] args;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUser = (EditText)findViewById(R.id.user);
        editPassword = (EditText)findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_user));
                }else if (editPassword.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_pass));
                }else {
                    BaseSQLiteHelper padbh =
                            new BaseSQLiteHelper(getApplication(), "DBBase", null, 1);
                    db = padbh.getReadableDatabase();
                    args = new String[]{editUser.getText().toString()};
                    c = db.rawQuery("SELECT user,password,email FROM Users WHERE user=?", args);
                    if(c.moveToFirst()){
                        if(editPassword.getText().toString().matches(c.getString(c.getColumnIndex("password")))){
                            Intent i = new Intent(getApplication(),WelcomeActivity.class);
                            i.putExtra("user", editUser.getText().toString());
                            i.putExtra("email",c.getString(c.getColumnIndex("email")));
                            startActivity(i);
                            finish();
                        }else{
                            showToast(getString(R.string.no_pass));
                        }
                    }else{
                        showToast(getString(R.string.no_user));
                    }
                }
            }
        });

        btnRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("user", editUser.getText().toString());
        outState.putString("pass", editPassword.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        editUser.setText(savedInstanceState.getString("user"));
        editPassword.setText(savedInstanceState.getString("pass"));
    }

    protected void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(db!=null){
            db.close();
        }
    }

}
