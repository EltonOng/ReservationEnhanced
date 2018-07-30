package sg.edu.rp.c346.reservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    EditText edName;
    EditText edMobileNum;
    EditText edSize;
    EditText etDay;
    EditText etTime;
    CheckBox cbTable;
    Button btnReset;
    Button btnConfirm;
    Calendar c = Calendar.getInstance();
    int gy = c.get(Calendar.YEAR);
    int gm = c.get(Calendar.MONTH);
    int gd = c.get(Calendar.DAY_OF_MONTH);
    int gH = c.get(Calendar.HOUR);
    int gM = c.get(Calendar.MINUTE);

    @Override
    protected void onPause() {
        super.onPause();

        boolean table = false;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putString("name",edName.getText().toString());
        prefEdit.putString("mnum",edMobileNum.getText().toString());
        prefEdit.putString("size",edSize.getText().toString());
        prefEdit.putString("day",etDay.getText().toString());
        prefEdit.putString("time",etTime.getText().toString());

        if(cbTable.isChecked()){
            table = true;
        }
        else{
            table = false;
        }
        prefEdit.putBoolean("table",table);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String name = prefs.getString("name","");
        String mNum = prefs.getString("mnum","");
        String size = prefs.getString("size","");
        String day = prefs.getString("day","");
        String time = prefs.getString("time","");
        Boolean table = prefs.getBoolean("table",false);

        edName.setText(name);
        edMobileNum.setText(mNum);
        edSize.setText(size);
        etDay.setText(day);
        etTime.setText(time);
        cbTable.setChecked(table);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.editTextName);
        edMobileNum = findViewById(R.id.editTextMobileNum);
        edSize = findViewById(R.id.editTextSize);
        cbTable = findViewById(R.id.checkBoxTableArea);
        btnConfirm = findViewById(R.id.buttonConfirm);
        btnReset = findViewById(R.id.buttonReset);
        etDay = findViewById(R.id.editTextDay);
        etTime = findViewById(R.id.editTextTime);


        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        etDay.setText("Date: " + dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                        gy = year;
                        gm = (monthOfYear);
                        gd = dayOfMonth;
                    }
                };


                DatePickerDialog myDateDialog = new DatePickerDialog
                        (MainActivity.this,myDateListener, gy,gm,gd);
                //myDateDialog.updateDate(gy,gm,gd);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        etTime.setText("Time: " + hourOfDay + ":" + minute);
                        gH = hourOfDay;
                        gM = minute;
                    }
                };

                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this, myTimeListener, c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE),true);
                myTimeDialog.updateTime(gH,gM);
                myTimeDialog.show();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String table = "";
                String text = "";
                if(cbTable.isChecked()){
                    table= "Yes";
                }
                else{
                    table = "No";
                }

                if(edName.getText().length() == 0 || edSize.getText().length() == 0 || edMobileNum.getText().length() == 0){
                    text = "Reservation error there is/are empty field";
                }
                else{
                    text = "New Reservation \nName: " + edName.getText().toString() + "\nSmoking: " + table + "\nSize: " + edSize.getText().toString() + "\n" + etDay.getText().toString() + "\n" + etTime.getText().toString();
                }
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(text);
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Confirm",null);
                myBuilder.setNegativeButton("Cancel",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edMobileNum.setText("");
                edName.setText("");
                edSize.setText("");
                cbTable.setChecked(false);
                etDay.setText("");
                etTime.setText("");
                gy = c.get(Calendar.YEAR);
                gm = c.get(Calendar.MONTH);
                gd = c.get(Calendar.DAY_OF_MONTH);
                gH = c.get(Calendar.HOUR);
                gM = c.get(Calendar.MINUTE);
            }
        });


    }


}
