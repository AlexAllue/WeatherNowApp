package com.example.allu.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Allu on 14/03/2016.
 */
public class RegisterActivity extends Activity {
    private Button btnLoginScreen,btnRegister;
    private EditText editUser,editPassword, editEmail, editConfirmPassword;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editUser = (EditText)findViewById(R.id.user);
        editPassword = (EditText)findViewById(R.id.password);
        editEmail = (EditText)findViewById(R.id.email);
        editConfirmPassword = (EditText)findViewById(R.id.password_confirm);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editUser.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_user));
                }else if (editEmail.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_email));
                }else if (editPassword.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_pass));
                }else if (editConfirmPassword.getText().toString().matches("")){
                    showToast(getApplication().getString(R.string.null_pass));
                }else if (!editPassword.getText().toString().matches(editConfirmPassword.getText().toString())){
                    showToast(getApplication().getString(R.string.null_confirm));
                }else {
                    BaseSQLiteHelper padbh =
                            new BaseSQLiteHelper(getApplication(), "DBBase", null, 1);
                    db = padbh.getWritableDatabase();
                    if (db != null) {
                        ContentValues nuevoRegistro = new ContentValues();
                        nuevoRegistro.put("user", editUser.getText().toString());
                        nuevoRegistro.put("password", editPassword.getText().toString());
                        nuevoRegistro.put("email", editEmail.getText().toString());
                        db.insert("Users", null, nuevoRegistro);
                    }
                    Intent i = new Intent(getApplication(), WelcomeActivity.class);
                    i.putExtra("user", editUser.getText().toString());
                    i.putExtra("email", editEmail.getText().toString());
                    startActivity(i);
                    finish();
                }
            }
        });

        btnLoginScreen = (Button) findViewById(R.id.btnLinkToLoginScreen);
        btnLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),LoginActivity.class);
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
        outState.putString("email", editEmail.getText().toString());
        outState.putString("passc", editConfirmPassword.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        editUser.setText(savedInstanceState.getString("user"));
        editPassword.setText(savedInstanceState.getString("pass"));
        editConfirmPassword.setText(savedInstanceState.getString("passc"));
        editEmail.setText(savedInstanceState.getString("email"));
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
