package com.project.minor.e_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.minor.e_attendance.object.Parent;
import com.project.minor.e_attendance.object.Student;

import java.util.Arrays;
import java.util.List;


public class AddParent extends AppCompatActivity {

    EditText parentName;
    EditText mobile;
    EditText sid;
    EditText sname;
    EditText parentpassword;
    Spinner classspinner;
    Button btnadd;
    Button btnrm;
    Toolbar toolbar;

    DatabaseReference databaseParent;
    DatabaseReference databaseStudent;

    String ssname;
    String sclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);

        toolbar = findViewById(R.id.ptoolbar);
        parentName = findViewById(R.id.pname);
        mobile = findViewById(R.id.pmno);
        sid = findViewById(R.id.psid);
        sname = findViewById(R.id.psname);
        parentpassword = findViewById(R.id.ppasswd);
        classspinner = findViewById(R.id.pspclass);
        btnadd = findViewById(R.id.btn_addp);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("Add/Remove");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseParent = FirebaseDatabase.getInstance().getReference(String.valueOf(R.string.parents));
        databaseStudent = FirebaseDatabase.getInstance().getReference("Student");

        AlertDialog.Builder builder = new AlertDialog.Builder(AddParent.this)
                .setCancelable(false)
                .setView(R.layout.layout_loading_dialog);
        final AlertDialog dialog = builder.create();

       this.sid.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

              String s1 = s.toString();
              if (s1.length() == 4) {
                  dialog.show();
              databaseStudent.child(s1).addListenerForSingleValueEvent(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      Student student = dataSnapshot.getValue(Student.class);
                      assert student != null;
                      ssname = student.getSname();
                      sname.setText(ssname);
                      sclass = student.getBranch();
                      List<String> classes = Arrays.asList(getResources().getStringArray(R.array.classes));
                      if (sclass.equals(classes.get(1))) {
                          classspinner.setSelection(1, true);
                      } else if (sclass.equals(classes.get(2))) {
                          classspinner.setSelection(2, true);
                      } else if (sclass.equals(classes.get(3))) {
                          classspinner.setSelection(3, true);
                      } else if (sclass.equals(classes.get(4))) {
                          classspinner.setSelection(4, true);
                      }
                      if (dataSnapshot.exists()) {
                          dialog.dismiss();
                      }
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError databaseError) {

                  }
              });
           } else {
                  Toast.makeText(AddParent.this, "Sid cannot be greater than or less than four numbers.", Toast.LENGTH_SHORT).show();
              }
           }
       });

       btnadd.setOnClickListener(this::addParent);
       btnrm.setOnClickListener(this::removeStudent);
    }

    public void addParent(View v){

        AlertDialog.Builder builder = new AlertDialog.Builder(AddParent.this)
                .setCancelable(false)
                .setView(R.layout.layout_loading_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();

        if ((!(TextUtils.isEmpty(parentName.getText().toString()))) || (!(TextUtils.isEmpty(mobile.getText().toString()))) || (!(TextUtils.isEmpty(sid.getText().toString()))) || (!(TextUtils.isEmpty(sname.getText().toString()))) || (!(TextUtils.isEmpty(parentpassword.getText().toString())))) {
            //String id = databaseStudent.push().getKey();
            String pname;
            String ssid;
            String smobile;
            String parentPasswrd;
            pname = parentName.getText().toString();
            smobile = mobile.getText().toString();
            ssid = sid.getText().toString();
            parentPasswrd = parentpassword.getText().toString();

            sclass = classspinner.getSelectedItem().toString();

            Parent parent = new Parent(pname, smobile, ssid, ssname, parentPasswrd, sclass );
            databaseParent.child(ssid).setValue(parent);
            Toast.makeText(getApplicationContext(),"Parent added successfully", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }else {
            Toast.makeText(getApplicationContext(),"Fields cannot be empty", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

    }
    public void removeStudent(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddParent.this)
                .setCancelable(false)
                .setView(R.layout.layout_loading_dialog);
        final AlertDialog dialog = builder.create();
        dialog.show();
        if (!TextUtils.isEmpty(sid.getText().toString())) {
            String ssid = sid.getText().toString();
            databaseParent.child(ssid).setValue(null);
            databaseStudent.child(ssid).setValue(null);
            Toast.makeText(getApplicationContext(),"Parent and Student removed successfully", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }else {
            Toast.makeText(getApplicationContext(),"Id cannot be empty", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
