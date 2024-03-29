package za.ac.cput_shuttleapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class StudentBooking extends AppCompatActivity {
    //Calls the database classes
    SQLiteDatabase myDb;
    SQLiteOpenHelper oh;

    //Variables
    Spinner editFrom;
    Spinner editTo;
    Spinner editTime;
    Spinner editDate;
    Button btnAddData;
    Button btnDelete;
    Button btnNext;

    //Counter for counting bus seats
    int counter = 30;

    private SimpleDateFormat sdf;
    private String date;

    //Calendar that shows current system date (updates everyday)
    Calendar c= Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance().format(c.getTime());

    //Arraylists that stores selected items for the student to choose from
    List<String> Bfrom = Arrays.asList("--nothing selected--","District Six","Orchard Residence");
    List<String> Bto = Arrays.asList("--nothing selected--","Orchard Residence","District Six");
    List<String> Btime = Arrays.asList("--nothing selected--","7:00","8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00");
    List<String> Bdate = Arrays.asList(currentDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_booking);
        oh = new BookingDatabase(this);

        //Array adapters/spinners/combo-boxes
        //class for adapting an array of objects as a datasource
        editFrom = findViewById(R.id.spinnerFrom);
        ArrayAdapter fromWhere = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Bfrom);
        fromWhere.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editFrom.setAdapter(fromWhere);

        editTo = findViewById(R.id.spinnerTo);
        ArrayAdapter toWhere = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Bto);
        toWhere.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTo.setAdapter(toWhere);

        editTime = findViewById(R.id.spinnerTime);
        ArrayAdapter bookingTime = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Btime);
        bookingTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTime.setAdapter(bookingTime);

        editDate = findViewById(R.id.spinnerDate);
        ArrayAdapter bookingDate = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,Bdate);
        bookingDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editDate.setAdapter(bookingDate);

        btnAddData = findViewById(R.id.button_save);
        btnDelete = findViewById(R.id.button_delete);
        btnNext = findViewById(R.id.button_exit);

        //Onclick listener for the add button
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get input from the combo-boxes
                String dep = editFrom.getSelectedItem().toString();
                String des = editTo.getSelectedItem().toString();
                String time = editTime.getSelectedItem().toString();
                String date = editDate.getSelectedItem().toString();
                myDb = oh.getWritableDatabase();

                //If the the first item (--nothing selected--) is selected means the user hasn't choose from the combo-box yet
            if(editFrom.getSelectedItem().toString().equals(Bfrom.get(0)) || (editTo.getSelectedItem().toString().equals(Bto.get(0))||
                        editTime.getSelectedItem().toString().equals(Btime.get(0)))) {
                    Toast.makeText(StudentBooking.this, "No booking has been made.Please complete all fields", Toast.LENGTH_LONG).show();

                //User cannot make the destination the same as his/her starting point
            }else if(editFrom.getSelectedItem().toString().equals(Bfrom.get(1)) && (editTo.getSelectedItem().toString().equals(Bto.get(2))) ||
                    (editFrom.getSelectedItem().toString().equals(Bfrom.get(2)) && (editTo.getSelectedItem().toString().equals(Bto.get(1))))){
                Toast.makeText(StudentBooking.this,"Starting point cannot be the same as destination",Toast.LENGTH_LONG).show();

                //Gets user input from the comb-boxes and checks if all details has been completed
                //User gets a message that the booking has been made
            }else if(editFrom.getSelectedItem().toString().equals(dep)&&((editTo.getSelectedItem().toString().equals(des)&&(editTime.getSelectedItem().toString().equals(time)
                        &&(editDate.getSelectedItem().toString().equals(date) && (counter != 0)))))){
                    insertDetails(dep,des,time,date);
                   int dec = counter--;
                    Toast.makeText(StudentBooking.this,"Booking has been made.Seats available: " + dec,Toast.LENGTH_LONG).show();
                    btnAddData.setEnabled(false);//Enables button after the booking has been made
                    btnDelete.setEnabled(false);//Enables button after the booking has been made
            }

            }
        });

        //Take user back to previous page
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        //Takes user to the next page
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

    }

    //This method calls the database class and allows the input in this perimeter
    public void insertDetails(String departure,String destination,String time,String date){
        ContentValues contentValues = new ContentValues();
        contentValues.put(BookingDatabase.COL_2, departure);
        contentValues.put(BookingDatabase.COL_3, destination);
        contentValues.put(BookingDatabase.COL_4, time);
        contentValues.put(BookingDatabase.COL_5, date);
        long id = myDb.insert(BookingDatabase.TABLE_NAME, null, contentValues);

    }

    //Take user back to previous page
    public void back(){
        Intent back = new Intent(this,Timetable.class);
        startActivity(back);
    }

    //Takes user to the next page
    public void next(){
        Intent next = new Intent(this,disabledStudentBooking.class);
        startActivity(next);
        finish();
    }

    }


