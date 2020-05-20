package com.project.minor.e_attendance;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.minor.e_attendance.object.Parent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener {
    private static long back_pressed;
    EditText username, password;
    String item,verText;
    String userid, pass;
    TextView verTexttv;
    DatabaseReference ref;
    Bundle basket;
    ProgressDialog mDialog;
    PackageManager manager;
    PackageInfo info;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.editText2);
        verTexttv = findViewById(R.id.verText);
        context = this.getApplicationContext();
        try {
            verText = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        verTexttv.setText(verText);
        // Spinner element
        Spinner spinner = findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Admin");
        categories.add("Teacher");
        categories.add("Student");
        categories.add("Parents");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        spinner.setAdapter(dataAdapter);

//        ref.child("Admin").child("username").setValue("")
    }

        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
            item = parent.getItemAtPosition(position).toString();
        }
        public void onNothingSelected (AdapterView < ? > arg0){
            // TODO Auto-generated method stub
        }


    public void onButtonClick(View v) {
        userid = username.getText().toString();
        pass = password.getText().toString();
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Please Wait..." + userid);
        mDialog.setTitle("Loading");
        mDialog.show();
        basket = new Bundle();
        basket.putString("message", userid);

        ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference dbuser = ref.child(item);

        switch (item) {
            case "Admin":
                dbuser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null) {
                                HashMap login = (HashMap) dataSnapshot.getValue();
                                String username = (String) login.get("username");
                                String password = (String) login.get("password");
                                verify(username, password);
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
//
////
                break;
            case "Teacher": {
                DatabaseReference reference = dbuser.child(userid);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null) {
                                HashMap login = (HashMap) dataSnapshot.getValue();
                                String tid = (String) login.get("tid");
                                String password = (String) login.get("tpass");
                                try {
                                    String p = EncDecUtil.decrypt(password, tid);
                                    verify(tid, p);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Database Error.Please check your entries.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        mDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            }
            case "Student": {
                DatabaseReference reference = dbuser.child(userid);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null) {
                                HashMap login = (HashMap) dataSnapshot.getValue();
                                String sid = (String) login.get("sid");
                                String password = (String) login.get("spass");
                                try {
                                    String p = EncDecUtil.decrypt(password, sid);
                                    verify(sid, p);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Database Error.Please check your entries.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
                break;
            }
            case "Parents" : {
                DatabaseReference reference = dbuser.child(userid);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.getValue() != null){
                                Parent parent = dataSnapshot.getValue(Parent.class);
                                String sid = parent.getSid();
                                String pssw = parent.getParentpassword();
                                verify(sid, pssw);
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Database Error.Please check your entries.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    public void verify(String dbusername, String dbpassword){
        if(userid.isEmpty()) {
            mDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Username cannot be empty", Toast.LENGTH_LONG).show();
        }
        else if (item.equals("Teacher") && pass.equals(dbpassword) && userid.equals(dbusername)) {

            mDialog.dismiss();
            Intent intent = new Intent(this, TeacherLogin.class);
            intent.putExtras(basket);
            startActivity(intent);

        }
        else if (item.equals("Admin") && pass.equals(dbpassword) && userid.equals(dbusername) ) {
            //  if (userid.equalsIgnoreCase("admin") && pass.equals("admin")) {
            mDialog.dismiss();
            Intent intent = new Intent(this, AdminLogin.class);
            intent.putExtras(basket);
            startActivity(intent);
            //  }
        }
        else if (item.equals("Student") && pass.equals(dbpassword ) && userid.equals(dbusername)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, StudentLogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if (item.equals("Parent") && pass.equals(dbpassword) && userid.equals(dbusername)) {
            mDialog.dismiss();
            Intent intent = new Intent(this, ParentLogin.class);
            intent.putExtras(basket);
            startActivity(intent);
        }
        else if(!pass.equals(dbpassword) || !userid.equals(dbusername)){
            mDialog.dismiss();
            Toast.makeText(getApplicationContext(),"UserId or Password is Incorrect", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        }
        else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }
}
