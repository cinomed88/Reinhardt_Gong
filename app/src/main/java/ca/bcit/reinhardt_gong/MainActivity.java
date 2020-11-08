package ca.bcit.reinhardt_gong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
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
    final int SYSTOLIC_HC_LIMIT = 180;
    final int DIASTOLIC_HC_LIMIT = 120;

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
                addTask(v);
            }
        });

        lvReadings = findViewById(R.id.lvReadings);
        readingList = new ArrayList<Reading>();


        lvReadings.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Reading reading = readingList.get(position);

                String systolic = Integer.toString(reading.getSystolicReading());
                String diastolic = Integer.toString(reading.getDiastolicReading());

                showUpdateDialog(reading.getReadingId(),
                        reading.getSerial_number(),systolic,
                        diastolic);

                return false;
            }
        });

        Button btnShowReport = (Button)findViewById(R.id.buttonShowReports);

        btnShowReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ReportActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addTask(View v) {
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
        }
        if (TextUtils.isEmpty(diastolicString)) {
            Toast.makeText(this, "You must enter a Diastolic reading.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = databaseReadings.push().getKey();
        Reading task = new Reading(id, serialNumber, currentDate, currentTime, systolic, diastolic);

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

        // Show Warning Message for Hypertensive Crisis range
        if (systolic > SYSTOLIC_HC_LIMIT || diastolic > DIASTOLIC_HC_LIMIT) {
            onClickShowAlert(v);
        }
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

    private void updateReading(String id, String serialNum, String Systolic, String Diastolic) {
        Calendar calendar;
        calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        String currentDate = dateFormatter.format(calendar.getTime());
        String currentTime = timeFormatter.format(calendar.getTime());


        DatabaseReference dbRef = databaseReadings.child(id);

        int systolic = Integer.parseInt(Systolic);
        int diastolic = Integer.parseInt(Diastolic);

        Reading reading = new Reading(id, serialNum, currentDate, currentTime, systolic, diastolic);

        Task setValueTask = dbRef.setValue(reading);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Student Updated.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showUpdateDialog(final String readingId, String serialNumber, String systolic, String diastolic) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextSerialNumber = dialogView.findViewById(R.id.editTextSerialNumber2);
        editTextSerialNumber.setText(serialNumber);

        final EditText editTextSystolic = dialogView.findViewById(R.id.editTextSystolic2);
        editTextSystolic.setText(systolic);

        final EditText editTextDiastolic = dialogView.findViewById(R.id.editTextDiastolic2);
        editTextDiastolic.setText(diastolic);


        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update Reading " + serialNumber);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serialNumber = editTextSerialNumber.getText().toString().trim();
                String systolic = editTextSystolic.getText().toString().trim();
                String diastolic = editTextDiastolic.getText().toString().trim();

                if (TextUtils.isEmpty(serialNumber)) {
                    editTextSerialNumber.setError("Serial Number is required");
                    return;
                } else if (TextUtils.isEmpty(systolic)) {
                    editTextSystolic.setError("Systolic value is required");
                    return;
                } else if (TextUtils.isEmpty(diastolic)) {
                    editTextDiastolic.setError("Diastolic is required");
                    return;
                }

                updateReading(readingId, serialNumber, systolic, diastolic);

                alertDialog.dismiss();

                // Show Warning Message for Hypertensive Crisis range
                if (Integer.parseInt(systolic) > SYSTOLIC_HC_LIMIT ||
                        Integer.parseInt(diastolic) > DIASTOLIC_HC_LIMIT) {
                    onClickShowAlert(v);
                }
            }
        });

        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReading(readingId);

                alertDialog.dismiss();
            }
        });

    }

    private void deleteReading(String id) {
        DatabaseReference dbRef = databaseReadings.child(id);

        Task setRemoveTask = dbRef.removeValue();
        setRemoveTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Reading Deleted.",Toast.LENGTH_LONG).show();
            }
        });

        setRemoveTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Show Warning Message for Hypertensive Crisis range
    public void onClickShowAlert(View view) {
        AlertDialog.Builder myAlertBuilder =
                new AlertDialog.Builder(MainActivity.this);
        myAlertBuilder.setTitle("Warning");
        myAlertBuilder.setMessage("You are in the Hypertensive Crisis range!\nBe careful!");

        myAlertBuilder.setPositiveButton("Ok",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Toast.makeText(getApplicationContext(),"Pressed OK",
                        Toast.LENGTH_SHORT).show();
            }
        });

        myAlertBuilder.show();
    }

}