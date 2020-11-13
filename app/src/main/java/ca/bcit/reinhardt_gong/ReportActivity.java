package ca.bcit.reinhardt_gong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Reading> arrayList;
    private ArrayList<Reading> arrayList3;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        recyclerView = findViewById(R.id.rvReport);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        arrayList3 = new ArrayList<>();


        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("readings");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Reading report = snapshot.getValue(Reading.class);
                    arrayList.add(report);
                }

                int[] badIndexes = new int[arrayList.size()];
                for (int i = 0; i < arrayList.size(); i++) {
                    int n = 1;
                    float avg_systolic = arrayList.get(i).getSystolicReading();
                    float avg_diastolic = arrayList.get(i).getDiastolicReading();
                    for (int j = i + 1; j < arrayList.size(); j++) {
                        if (arrayList.get(i).getFamily_member().equals(arrayList.get(j).getFamily_member())) {
                            if (arrayList.get(i).getReadingDate().substring(0,3).equals(arrayList.get(j).getReadingDate().substring(0,3)) &&
                                    arrayList.get(i).getReadingDate().substring(8,12).equals(arrayList.get(j).getReadingDate().substring(8,12))) {
                                avg_systolic += (arrayList.get(j).getSystolicReading());
                                avg_diastolic += (arrayList.get(j).getDiastolicReading());
                                n++;
                                badIndexes[j] = 2;
                            }
                        }
                    }
                    if (avg_diastolic > arrayList.get(i).diastolicReading && badIndexes[i] != 2) {
                        arrayList3.add(new Reading(arrayList.get(i).getReadingId(), arrayList.get(i).getFamily_member(), arrayList.get(i).getReadingDate(), arrayList.get(i).getReadingTime(),
                                avg_systolic/n, avg_diastolic/n));
                    } else if (badIndexes[i] != 2) {
                        arrayList3.add(arrayList.get(i));
                    }
                }
                arrayList = arrayList3;

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        adapter = new ReportAdapter(arrayList3, this);
        recyclerView.setAdapter(adapter);
    }
}