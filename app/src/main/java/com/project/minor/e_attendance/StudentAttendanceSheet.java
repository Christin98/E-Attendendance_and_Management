package com.project.minor.e_attendance;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StudentAttendanceSheet extends AppCompatActivity {

    public static int TOC=1,NOP=1,NOA=1;
    float average= (float) 0.0;
    TextView t;
    String avg,p1,p2,p3,p4,p5,p6,p7,p8;
    String student_id;
    ArrayList dates = new ArrayList<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_sheet);
        t= findViewById(R.id.textView3);

        listView = findViewById(R.id.list);
        Bundle bundle = getIntent().getExtras();
        student_id = bundle.getString("sid");
        t.setText(student_id);

        ref.child("Student").child(student_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dates.clear();

                dbAttendance = ref.child("Attendance").child(student_id).child("Semester-8");
                dbAttendance.addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            String att = dsp.getKey();
                            assert att != null;
                            dbAttendance.child(att).child("atd").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                    for (DataSnapshot dsp1 : dataSnapshot1.getChildren()) {
                                        dates.add(dsp1.getKey() + "    " + p1 + "     " + p2 + "     " + p3 + "     " + p4 + "      " + p5 + "       " + p6 + "      " + p7 + "      " + p8); //add result into array list


                                        //  Toast.makeText(getApplicationContext(),dsp.child(student_id).child("p1").getValue().toString(),Toast.LENGTH_LONG).show();
                                        if (p1.equals("P")||p2.equals("P")||p3.equals("P")||p4.equals("P")||p5.equals("P")||p6.equals("P")||p7.equals("P")||p8.equals("P")) {

                                            NOP++;
                                            TOC++;
                                        }
                                        if(p1.equals("A")||p2.equals("A")||p3.equals("A")||p4.equals("A")||p5.equals("A")||p6.equals("A")||p7.equals("A")||p8.equals("A")) {
                                            NOA++;
                                            TOC++;
                                        }
                                    }
                                    list(dates,NOP,TOC,NOA);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        //  Toast.makeText(getApplicationContext(), dates.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void list(ArrayList studentlist,int NOP,int TOC,int NOA){
        // Toast.makeText(this,NOP+TOC,Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        // Assign adapter to ListView
        listView.setAdapter(adapter);
        try {

            average =(float)((NOP*100)/TOC);
            String avg=Float.toString(average);
            t.setText(String.format("Your Attendance is :%s%%", avg));
            if(average>=75)
                t.setTextColor(Color.GREEN);
            if(average<75)
                t.setTextColor(Color.RED);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
