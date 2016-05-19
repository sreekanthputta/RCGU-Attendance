package com.slabs.sreekanth.rcguattendance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Sreekanth Putta on 14-05-2016.
 */
public class register extends Activity implements AdapterView.OnItemSelectedListener {
    EditText Regname, Regpin, Regemail, Regphone, Regdob, Reghometown, Regsection, Regprogram, Regblood;
    RadioButton Regmale, Regfemale, Reghostelyes, Reghostelno, Reglocalyes, Reglocalno, Regbloodyes, Regbloodno;
    RadioGroup Reggenderselect, Reghostelselect, Reglocalselect, Regbloodselect;
    Spinner Regcollege, Regbranch, Regyear, Regterm;
    Button Regsubmit;
    int colspinner, yearspinner;

    String[] college = new String[] {"GIT", "GIS", "GIM", "GSIB", "GIP", "GSA", "GSL", "CDL"};
    String[] branchgit = new String[] {"Biotechnology", "Civil", "CSE", "EEE", "ECE", "EIE", "IE", "IT", "Mechanical", "Physics", "Chemistry","English", "Maths"};
    String[] branchgis = new String[] {"Applied Mathematics", "Biochemistry", "Biotechnology", "Chemistry", "Computer Science", "Environmental Studies", "Electronics/Physics", "Microbiology", "English"};
    String[] year = new String[] {"1", "2", "3", "4", "5"};
    String[] term1 = new String[] {"2016-20", "2016-21"};
    String[] term2 = new String[] {"2015-19", "2015-20"};
    String[] term3 = new String[] {"2014-18", "2014-19"};
    String[] term4 = new String[] {"2013-17", "2013-18"};
    String[] term5 = new String[] {"2012-16", "2012-17"};
    String[] nothing = new String[] {""};
    ArrayAdapter<String> adapter;

    private DatePickerDialog dobPickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        Regname = (EditText) findViewById(R.id.regname);
        Regpin = (EditText) findViewById(R.id.regpin);
        Regemail = (EditText) findViewById(R.id.regemail);
        Regphone = (EditText) findViewById(R.id.regphone);
        Regdob = (EditText) findViewById(R.id.regdob);
        Reghometown = (EditText) findViewById(R.id.reghometown);
        Regcollege = (Spinner) findViewById(R.id.regcollege);
        Regbranch = (Spinner) findViewById(R.id.regbranch);
        Regprogram = (EditText) findViewById(R.id.regprogram);
        Regyear = (Spinner) findViewById(R.id.regyear);
        Regterm = (Spinner) findViewById(R.id.regterm);
        Regsection = (EditText) findViewById(R.id.regsection);
        Regblood = (EditText) findViewById(R.id.regblood);
        Regsubmit = (Button) findViewById(R.id.regsubmit);
        Reggenderselect = (RadioGroup) findViewById(R.id.reggenderselect);
        Reghostelselect = (RadioGroup) findViewById(R.id.reghostelselect);
        Reglocalselect = (RadioGroup) findViewById(R.id.reglocalselect);
        Regbloodselect = (RadioGroup) findViewById(R.id.regbloodselect);

        Regname.requestFocus();
        Regdob.setInputType(InputType.TYPE_NULL);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, college);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Regcollege.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.college_spinner_row_nothing_selected, this));
        Regcollege.setOnItemSelectedListener(this);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Regbranch.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.branch_spinner_row_nothing_selected, this));

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        Regyear.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.year_spinner_row_nothing_selected, this));
        Regyear.setOnItemSelectedListener(this);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nothing);
        Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));


        Regdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();

                dobPickerDialog = new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        Regdob.setText(dateFormatter.format(newDate.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


                if (v == Regdob) {
                    dobPickerDialog.show();
                }
            }
        });

        Regsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String branch, local, blood, hostel, gender;
                branch=local=blood=hostel=gender="";
                boolean branchflag=false;
                if (Regbranch.getVisibility() == View.VISIBLE) {
                    if(Regbranch.getSelectedItemPosition()>1){
                        branch = Regbranch.getSelectedItem().toString();
                        branchflag=true;
                    }
                } else {
                    if(Regprogram.getText().length()!=0) {
                        branch = Regprogram.getText().toString();
                        branchflag=true;
                    }
                }

                Time time = new Time();
                time.setToNow();

                if(
                        Regname.getText().length()==0
                        ||Regpin.getText().length()==0
                        ||Regyear.getSelectedItemPosition()<1
                        ||!branchflag
                        ||Regsection.getText().length()==0
                        ||Regemail.getText().length()==0
                        ||Regblood.getText().length()==0
                        ||Regphone.getText().length()==0
                        ||Reghostelselect.getCheckedRadioButtonId()<1
                        ||Reglocalselect.getCheckedRadioButtonId()<1
                        ||Regcollege.getSelectedItemPosition()<1
                        ||Regdob.getText().length()==0
                        ||Regterm.getSelectedItemPosition()<1
                        ||Reghometown.getText().length()==0
                        ||Regbloodselect.getCheckedRadioButtonId()<1
                        ){
                    Toast.makeText(getApplicationContext(),"Required field is empty",Toast.LENGTH_SHORT).show();
                }
                else if(Regpin.getText().length()<10||Regphone.getText().length()<10){
                    Toast.makeText(getApplicationContext(),"Incorrect PIN or Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    int radioid;
                    RadioButton radioButton;

                    radioid = Reggenderselect.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(radioid);
                    gender = radioButton.getText().toString();

                    radioid = Reghostelselect.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(radioid);
                    hostel = radioButton.getText().toString();

                    radioid = Reglocalselect.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(radioid);
                    local = radioButton.getText().toString();

                    radioid = Regbloodselect.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(radioid);
                    blood = radioButton.getText().toString();
                    if (!(new File(Environment.getExternalStorageDirectory().getPath() + "/RCGU Attendance/New Registrations" + ".xls").exists())) {

                        exportExcel.createReg(getApplicationContext());
                    }
                    exportExcel.addElementReg(getApplicationContext(),
                            MainActivity.Tevent.getText().toString(),
                            Regname.getText().toString(),
                            Regpin.getText().toString(),
                            Regyear.getSelectedItem().toString(),
                            branch,
                            Regsection.getText().toString(),
                            Regemail.getText().toString(),
                            Regblood.getText().toString(),
                            Regphone.getText().toString(),
                            hostel,
                            local,
                            Regcollege.getSelectedItem().toString(),
                            Regdob.getText().toString(),
                            gender,
                            Regterm.getSelectedItem().toString(),
                            Reghometown.getText().toString(),
                            blood,
                            time.toString());

                }
    }
});

    }
    @Override
     public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {

        int colid = Regcollege.getSelectedItemPosition();
        int yearid = Regyear.getSelectedItemPosition();
        if (colid!=colspinner) {
            if (colid == 1) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchgit);
                Regbranch.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.branch_spinner_row_nothing_selected, this));
                Regbranch.setVisibility(View.VISIBLE);
                Regprogram.setVisibility(View.GONE);
            }
            if (colid == 2) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, branchgis);
                Regbranch.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.branch_spinner_row_nothing_selected, this));
                Regbranch.setVisibility(View.VISIBLE);
                Regprogram.setVisibility(View.GONE);
            }
            if (colid == 3 || colid == 4 || colid == 5 || colid == 6 || colid == 7 || colid == 8) {
                Regbranch.setVisibility(View.GONE);
                Regprogram.setVisibility(View.VISIBLE);
            }
            colspinner = colid;
        }
        if (yearid!=yearspinner) {
            if (yearid == 1) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, term1);
                Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));
            }
            if (yearid == 2) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, term2);
                Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));
            }
            if (yearid == 3) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, term3);
                Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));
            }
            if (yearid == 4) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, term4);
                Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));
            }
            if (yearid == 5) {
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, term5);
                Regterm.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.term_spinner_row_nothing_selected, this));
            }
            yearspinner = yearid;
        }


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}

