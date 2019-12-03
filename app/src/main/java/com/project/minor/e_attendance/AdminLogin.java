package com.project.minor.e_attendance;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.minor.e_attendance.object.AttendanceSheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdminLogin extends AppCompatActivity {

    private static long back_pressed;
    DatabaseReference ref;
    DatabaseReference dbStudent;
    DatabaseReference dbAttendance;
    DatabaseReference dbadmin;
    Toolbar mToolbar;
    ArrayList Studentlist = new ArrayList<>();

    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mToolbar= findViewById(R.id.ftoolbar);
        mToolbar.setTitle("Admin Dashboard : "+"("+date+")");
        ref = FirebaseDatabase.getInstance().getReference();
        dbStudent = ref.child("student");
        dbAttendance = ref.child("attendance");
    }

    public void AddTeacherButton(View v){
        Intent intent = new Intent(this, AddTeacher.class);
        startActivity(intent);
    }

    public void AddStudentButton(View v){
        Intent intent = new Intent(this, AddStudent.class);
        startActivity(intent);
    }

    public void attendanceRecord(View v){
        Intent intent = new Intent(this, AdminAttendanceSheet.class);
        startActivity(intent);
    }

    public void CreateAttendance(View v){

        //Toast.makeText(getApplicationContext(),date, Toast.LENGTH_LONG).show();
        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sid,P1="-",P2="-",P3="-",P4="-",P5="-",P6="-",P7="-",P8="-";
                AttendanceSheet a = new AttendanceSheet(P1,P2,P3,P4,P5,P6,P7,P8);
                // Result will be holded Here
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    sid=dsp.child("sid").getValue().toString(); //add result into array list
                    dbAttendance.child(date).child(sid).setValue(a);

                }
                Toast.makeText(getApplicationContext(),"successfully created "+date+" db", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }

    public void logout(View view) {

        Intent logout=new Intent(AdminLogin.this,LoginActivity.class);
        logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(logout);

    }

    public void changepassword(View view) {
        dbadmin=ref.child("admin");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Type your new password");
        final LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.change_password, null);
        final EditText password= add_menu_layout.findViewById(R.id.newpassword);
        alertDialog.setView(add_menu_layout);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(password.getText().toString()))
                {
                    dbadmin.child("Admin").setValue(password.getText().toString());
                    Toast.makeText(AdminLogin.this, "Successfully Changed", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(AdminLogin.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
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

/*firebaseDatabase.getReference("parent")
                            .orderByChild("childNode")
                            .startAt("[a-zA-Z0-9]*")
                            .endAt(searchString)


*/
