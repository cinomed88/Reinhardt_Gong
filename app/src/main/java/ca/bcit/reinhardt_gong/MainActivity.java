package ca.bcit.reinhardt_gong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextSerialNumber;
    EditText editTextSystolic;
    EditText editTextDiastolic;
    TextView tvw;
    DatabaseReference databaseReadings;
    Button buttonAddReading;

    ListView lvReadings;
    List<Reading> readingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReadings = FirebaseDatabase.getInstance().getReference("readings");

        editTextSerialNumber = findViewById(R.id.editTextSerialNumber);
        editTextSystolic = findViewById(R.id.editTextSystolic);
        editTextDiastolic = findViewById(R.id.editTextDiastolic);
        buttonAddReading = findViewById(R.id.buttonAddReading);

        buttonAddReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        lvReadings = findViewById(R.id.lvReadings);
        readingList = new ArrayList<Reading>();

    }




    private void addTask() {
        Calendar calendar;

        calendar = Calendar.getInstance();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String currentDate = dateFormatter.format(calendar.getTime());
        String currentTime = timeFormatter.format(calendar.getTime());


        String serialNumber = editTextSerialNumber.getText().toString().trim();
        String diastolicString = editTextDiastolic.getText().toString().trim();
        String systolicString = editTextSystolic.getText().toString().trim();
        int systolic = Integer.parseInt(systolicString);
        int diastolic = Integer.parseInt(diastolicString);

        if (TextUtils.isEmpty(serialNumber)) {
            Toast.makeText(this, "You must enter a Task.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(systolicString)) {
            Toast.makeText(this, "You must enter a Systolic reading.", Toast.LENGTH_LONG).show();
            return;
        }  if (TextUtils.isEmpty(diastolicString)) {
            Toast.makeText(this, "You must enter a Diastolic reading.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = databaseReadings.push().getKey();
        Reading task = new Reading(serialNumber, currentDate, currentTime, systolic, diastolic);

        com.google.android.gms.tasks.Task setValueTask = databaseReadings.child(id).setValue(task);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,"Student added.",Toast.LENGTH_LONG).show();

                editTextSerialNumber.setText("");
                editTextSystolic.setText("");
                editTextDiastolic.setText("");
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReadings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                readingList.clear();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    Reading reading = taskSnapshot.getValue(Reading.class);
                    readingList.add(reading);
                }

                ReadingListAdapter adapter = new ReadingListAdapter(MainActivity.this, readingList);
                lvReadings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

}