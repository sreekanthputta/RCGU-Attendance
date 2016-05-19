package com.slabs.sreekanth.rcguattendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class  MainActivity  extends Activity  {

	public static String  URL , id, Eventname="";
    public static String name, pin, year, branch, section, email, blood, phone, hostel, local, college, dob, gender, term, hometown, bloodwilling, registered;
    public static int imagetotsize;
    String pincheck="";
	ImageView Iface;
	EditText Eid;
    public static TextView Tevent, Ename, Epin, Eyear, Ebranch, Esection, Eemail, Eblood, Ephone, Tpassword;
	Button Bgetdetails, Bsubmit;
    public static EditText Eseteventname, Epassword;
    public static Button  Bpassword;
	StringBuilder text = new StringBuilder();
	String text1;

    public static int count;
			@Override
			public  void  onCreate(Bundle  savedInstanceState)  {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);


                Iface	= (ImageView) findViewById(R.id.iface);
                Tevent  = (TextView) findViewById(R.id.tevent);
                Eid     = (EditText) findViewById(R.id.eid);
                Ename   = (TextView) findViewById(R.id.ename);
                Epin    = (TextView) findViewById(R.id.epin);
                Eyear   = (TextView) findViewById(R.id.eyear);
                Ebranch = (TextView) findViewById(R.id.ebranch);
                Esection= (TextView) findViewById(R.id.esection);
                Eemail  = (TextView) findViewById(R.id.eemail);
                Eblood  = (TextView) findViewById(R.id.eblood);
                Ephone  = (TextView) findViewById(R.id.ephone);
                Bgetdetails = (Button) findViewById(R.id.bgetdetails);
				Bsubmit	= (Button) findViewById(R.id.bsubmit);
				Tpassword = (TextView) findViewById(R.id.tpassword);


                File RCGUfolder = new File("/sdcard/RCGU Attendance");
                if (!RCGUfolder.exists()) {
                    boolean result = false;

                    try{
                        RCGUfolder.mkdir();
                        result = true;
                    }
                    catch(SecurityException se){
                        Toast.makeText(getApplicationContext(),"Cant create output Directory",Toast.LENGTH_SHORT).show();
                    }
                    if(result) {
                        Toast.makeText(getApplicationContext(),"RCGU Attendance DIR created in Internal Storage",Toast.LENGTH_SHORT).show();
                    }
                }
                File Images = new File("/sdcard/RCGU Attendance/Images");
                if (!Images.exists()) {
                    boolean result = false;

                    try{
                        Images.mkdir();
                        result = true;
                    }
                    catch(SecurityException se){
                        Toast.makeText(getApplicationContext(),"Cant create images output directory",Toast.LENGTH_SHORT).show();
                    }
                    if(result) {
                        Toast.makeText(getApplicationContext(),"Images DIR created in RCGU Attendance",Toast.LENGTH_SHORT).show();
                    }
                }

                if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
					Toast.makeText(getApplicationContext(),"External storage unavailable",Toast.LENGTH_SHORT).show();
                }



				/*File dir = Environment.getExternalStorageDirectory();
				//File yourFile = new File(dir, "path/to/the/file/inside/the/sdcard.ext");
				//Get the text file
				File inputfile = new File(dir,"text.txt");
				// i have kept text.txt in the sd-card




                        if(inputfile.exists())   // check if file exist
                        {
                            //Read text from file


                            try {
                                BufferedReader br = new BufferedReader(new FileReader(inputfile));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    text.append(line);
                                    text.append('\n');
                                }
					}
					catch (IOException e) {
						//You'll need to add proper error handling here
					}
					//Set the text
					//Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();

				}
				else
				{
					Toast.makeText(getApplicationContext(), "Sorry database doesn't exist!!\nMake sure that the file is placed in sd card", Toast.LENGTH_SHORT).show();

					//tv.setText("Sorry file doesn't exist!!");
				}*/

				text.append("{\"rcgu\":[ { \"Name\": \"E.Ravichandra\", \"Pin\": 1210514113, \"Year\": \"2year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7036374416, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M.Neeharika\", \"Pin\": 1210313264, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7731911912, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"R.V.N.Mouli\", \"Pin\": 1210513443, \"Year\": \"3 year\", \"Branch\": \"EEE\", \"Section\": \"D4\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8121241405, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Pawan Yadav\", \"Pin\": 1210313340, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8333825450, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"E.Jeevan\", \"Pin\": 1210813115, \"Year\": \"3 year\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8498022240, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B.M.Avinash\", \"Pin\": 1210813107, \"Year\": \"3 year\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9581234744, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V.Prashanth\", \"Pin\": 1210813163, \"Year\": \"3 year\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9640709367, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.A.Javed\", \"Pin\": 1210613136, \"Year\": \"3 year\", \"Branch\": \"EIE\", \"Section\": \"E1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9666968415, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"R Yashwitha\", \"Pin\": 1210613231, \"Year\": \"3 year\", \"Branch\": \"EIE\", \"Section\": \"E2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9700661365, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K Deepthi\", \"Pin\": 1210613217, \"Year\": \"3 year\", \"Branch\": \"EIE\", \"Section\": \"E2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9848899535, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"A.Sai Soma Raju\", \"Pin\": 1210813103, \"Year\": \"3 year\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9963640333, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G Santhi Sree\", \"Pin\": 1210613211, \"Year\": \"3 year\", \"Branch\": \"EIE\", \"Section\": \"E2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9666656190, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ch.Sree Sanjana\", \"Pin\": 1210313212, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9963509397, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ankit Verma\", \"Pin\": 1210613102, \"Year\": \"3 year\", \"Branch\": \"EIE\", \"Section\": \"E1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9989887117, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"nitin kaloya\", \"Pin\": 0, \"Year\": \"3\\\\4\", \"Branch\": \"m.sc intg biotech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7354100610, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"v.srisailakshmi\", \"Pin\": 0, \"Year\": \"3\\\\4\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7799109579, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"vinay\", \"Pin\": 0, \"Year\": \"3\\\\4\", \"Branch\": \"ECE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8985841619, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"nitin kaloya\", \"Pin\": 0, \"Year\": \"3\\\\5\", \"Branch\": \"M.SC INTG BIOTECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7842394623, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.Manoj\", \"Pin\": 1210313553, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8096026122, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ch.Murali Krishna\", \"Pin\": 1210313507, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8790645757, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M.N.S.Prudhvi Kumar\", \"Pin\": 1210313535, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8885475405, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G.Aakash Varma\", \"Pin\": 1216513101, \"Year\": \"3rd\", \"Branch\": \"ARCHITECTURE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8885678348, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.SAI PRASANNA\", \"Pin\": 1210313451, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B4\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9533539351, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.Smita\", \"Pin\": 1210313247, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9642344107, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.Avyash\", \"Pin\": 1210813151, \"Year\": \"3rd\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7207886646, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V Chandu\", \"Pin\": 0, \"Year\": \"3rd\", \"Branch\": \"EEE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8712249412, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ankit Verma\", \"Pin\": 1210613102, \"Year\": \"3rd\", \"Branch\": \"EIE\", \"Section\": \"E1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9987887117, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B.Sravan\", \"Pin\": 1210812305, \"Year\": \"4 year\", \"Branch\": \"MECH\", \"Section\": \"H3\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7794841440, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Vishnu Varma\", \"Pin\": 1210412603, \"Year\": \"4 year\", \"Branch\": \"EEE\", \"Section\": \"D6\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8522816459, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G.L.S.Sai Kiran\", \"Pin\": 1210812111, \"Year\": \"4 year\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9493832072, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Chaturya\", \"Pin\": 0, \"Year\": \"4 year\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7382045999, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.V.Dedeepya\", \"Pin\": 1210412662, \"Year\": \"4 year\", \"Branch\": \"EEE\", \"Section\": \"D6\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8099362739, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Vamsi\", \"Pin\": 0, \"Year\": \"4 year\", \"Branch\": 0, \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 0, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Pravallika\", \"Pin\": 0, \"Year\": \"4 year\", \"Branch\": 0, \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 0, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"PUDOTA VASISTA\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"IT\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7702722899, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"RAHUL KANAMARLAPUDI\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9885492362, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Naresh kumar\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9985830073, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"harshini jaddu\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7799683000, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"KRISHNA GANESH\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"MECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8125202243, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"vyshnavi chalavadi\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"ECE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8179377151, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ch.namrata\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"b.pharm\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8374021602, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T.SAI CHETAN\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"MECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8500620320, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"L V JAYANDRA KUMAR\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8686031314, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ganesh chowdary\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8985895999, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"chennuru bhaskar reddy\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9440632417, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"g.pranay\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"ECE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9492185977, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"harish chowdary\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9492890472, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"keerthi.m\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9652729163, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ANUROOP\", \"Pin\": 0, \"Year\": \"4\\\\4\", \"Branch\": \"MECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9705841838, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"MEDA RADHA KRISHNA\", \"Pin\": 0, \"Year\": \"4\\\\5\", \"Branch\": \"MECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7396205212, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"samson wassan\", \"Pin\": 0, \"Year\": \"4\\\\5\", \"Branch\": \"m.sc intg biotech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 9885172768, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"GOVARDHAN SURAJ.T\", \"Pin\": 0, \"Year\": \"4\\\\5\", \"Branch\": \"MECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7396717055, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"samvarthika\", \"Pin\": 0, \"Year\": \"4\\\\5\", \"Branch\": \"M.SC INTG BIOTECH\", \"Section\": 0, \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7416498953, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ch.V.Sampath\", \"Pin\": 1210312309, \"Year\": \"4th\", \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 7207893734, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B.Surya\", \"Pin\": 1210312209, \"Year\": \"4th\", \"Branch\": \"CSE\", \"Section\": \"B2\", \"Email\": 0, \"Blood Group\": 0, \"Phone\": 8500006894, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"p.santhosh kumar\", \"Pin\": 1210814447, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 7075184787, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T.bhanu prasad\", \"Pin\": 1210514449, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 7093032356, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"usha amulya\", \"Pin\": 1232614105, \"Year\": 2, \"Branch\": \"law\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 7382105069, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"r.satya sandeep\", \"Pin\": 1210314242, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 8686414149, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"VK.rishith\", \"Pin\": 1210814358, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 8688010105, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"pavan raghav\", \"Pin\": 1210814403, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 9490465456, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"meghana\", \"Pin\": 1216512130, \"Year\": 4, \"Branch\": \"barch\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+\", \"Phone\": 9704908962, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"L.S.Vignesh\", \"Pin\": 1210514151, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 8185811883, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Akhil Hussain\", \"Pin\": 1210814101, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 7207310929, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Akhil Hussain\", \"Pin\": 1210814101, \"Year\": \"2 year\", \"Branch\": \"Mechanical\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 7207310929, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.Anuhya\", \"Pin\": 1210314924, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 8978014257, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"I Sai Thanay\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 8985458264, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"D R H D Srikanth\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"A+\", \"Phone\": 9959041074, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"v vijay reddy\", \"Pin\": 1210414858, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 7893874016, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"y rakesh\", \"Pin\": 1210714255, \"Year\": 2, \"Branch\": \"it\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 8179378580, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ashok reddy\", \"Pin\": 1210414322, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9394378999, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"a krishna sagar\", \"Pin\": 1210314302, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9492332609, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sridevi\", \"Pin\": 1210414204, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9494699783, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"p harsha vardhan\", \"Pin\": 1210814443, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9581750117, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"v prudhvi raj\", \"Pin\": 1210514348, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9701993138, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"p naga sai\", \"Pin\": 1210414544, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"a+ve\", \"Phone\": 9705252276, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V.Siva Kumar\", \"Pin\": 1210514353, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D3\", \"Email\": 0, \"Blood Group\": \"A+ve\", \"Phone\": 9505293636, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.Kartik\", \"Pin\": 1210514117, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"A+ve\", \"Phone\": 9642693094, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Y.Pavan Sri Hari\", \"Pin\": 1210814463, \"Year\": \"2nd\", \"Branch\": \"MECH\", \"Section\": \"H4\", \"Email\": 0, \"Blood Group\": \"A+ve\", \"Phone\": 9492747283, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P.Saikiran\", \"Pin\": 1210313551, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"A+ve\", \"Phone\": 9989360986, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"keerthi priya\", \"Pin\": 1216512115, \"Year\": 4, \"Branch\": \"barch\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"ab+\", \"Phone\": 9000122121, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"pranaya rayudu\", \"Pin\": 1210514437, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"ab+ve\", \"Phone\": 8978198698, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Y Krishna Sindhusha\", \"Pin\": 1210314963, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"AB+ve\", \"Phone\": 8985847616, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Abhishek\", \"Pin\": 1210314365, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": \"AB+ve\", \"Phone\": 7075009313, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"maniroop\", \"Pin\": 1210313453, \"Year\": \"3 year\", \"Branch\": \"cse\", \"Section\": \"B4\", \"Email\": 0, \"Blood Group\": \"AB+ve\", \"Phone\": 9000351865, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"NV sai teja\", \"Pin\": 1210814440, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b-\", \"Phone\": 8008875746, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P.hari krishna\", \"Pin\": 1210514435, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b-\", \"Phone\": 9603030725, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ch.rahul\", \"Pin\": 1210414309, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+\", \"Phone\": 7207444888, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sk shahid\", \"Pin\": 1210414255, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+\", \"Phone\": 8885376169, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"R.surya teja\", \"Pin\": 1210514242, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+\", \"Phone\": 9701122931, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"teja\", \"Pin\": 1210314956, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+\", \"Phone\": 9908882288, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Pothuru Mahidhar\", \"Pin\": 1210314340, \"Year\": 2, \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": \"B+\", \"Phone\": 7674910277, \"Hostel\": \"Yes\", \"Local\": \"No\", \"College\": \"GIT\", \"Date of Birth\": 19, \"Gender\": \"Male\", \"Term\": \"2014-18\", \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"kavya\", \"Pin\": 1216512114, \"Year\": 4, \"Branch\": \"B Arch\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+\", \"Phone\": 9640497371, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.A.Akshay\", \"Pin\": 1210314923, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"B+\", \"Phone\": 7659817798, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V Ganesh\", \"Pin\": 1210815149, \"Year\": 1, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9502968867, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V Naveena Lasya\", \"Pin\": 1210514133, \"Year\": 2, \"Branch\": \"EEE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9491212872, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B Surya\", \"Pin\": 1230814110, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 7675075150, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"AP santosh vamsi\", \"Pin\": 1210414842, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 8341221389, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B Roopvishal\", \"Pin\": 1210814208, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 8374525204, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B Prakash\", \"Pin\": 1210314009, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 8885777920, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"surya kumar\", \"Pin\": 1210714218, \"Year\": 2, \"Branch\": \"it\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 9059923441, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"p aditya varma\", \"Pin\": 1210814445, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 9443584399, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"m nithin\", \"Pin\": 1210814435, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 9493523961, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"t anisha\", \"Pin\": 1210314954, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 9666380858, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"prashant\", \"Pin\": 1210613129, \"Year\": 3, \"Branch\": \"eie\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 8297787570, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P Monica\", \"Pin\": 1210313538, \"Year\": 3, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b+ve\", \"Phone\": 9676266690, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G Sai Siddarth Reddy\", \"Pin\": 1210814416, \"Year\": \"2 year\", \"Branch\": \"MECHANICAL\", \"Section\": \"H4\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 8019813890, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Srihas Udutha\", \"Pin\": 1210314349, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 8897429422, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ganpath Swain\", \"Pin\": 1210814119, \"Year\": \"2 year\", \"Branch\": \"MECHANICAL\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9618464054, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Karri Vamsi\", \"Pin\": 1210514120, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9652280814, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Sharan Kumar Korvi\", \"Pin\": 1210214347, \"Year\": \"2 year\", \"Branch\": \"CIVIL\", \"Section\": \"G3\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9848883827, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Satvik Vuppala\", \"Pin\": 1210814450, \"Year\": \"2 year\", \"Branch\": \"MECHANICAL\", \"Section\": \"H4\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9949982867, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Suraj Kumar Shasini\", \"Pin\": 1210514142, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 8179559868, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"N.M.Maneesha\", \"Pin\": 1210314933, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9000766881, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M Yogeshwar Reddy\", \"Pin\": 1210514130, \"Year\": \"2year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9652965385, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Kondeti Naveen\", \"Pin\": 1210313527, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 7382356198, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T.Akhil\", \"Pin\": 1210713242, \"Year\": \"3 year\", \"Branch\": \"IT\", \"Section\": \"C2\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 8125823343, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G.Vamsi\", \"Pin\": 1210313513, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9160690505, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Manogna\", \"Pin\": 1210312553, \"Year\": \"4 year\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"B+ve\", \"Phone\": 9177653428, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Henry\", \"Pin\": 1230814120, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"b-ve\", \"Phone\": 7675865022, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Sai Ram\", \"Pin\": 1210414534, \"Year\": 2, \"Branch\": \"ECE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O-\", \"Phone\": 8500677797, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"b kiran kumar\", \"Pin\": 1210814430, \"Year\": 2, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o-\", \"Phone\": 9640506013, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ch.sravya\", \"Pin\": 1210314913, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 7382358298, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Meghdeep\", \"Pin\": 1210314912, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 7659971701, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G.jignash\", \"Pin\": 1210214223, \"Year\": 2, \"Branch\": \"civil\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 7730944317, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M.vipin\", \"Pin\": 1210314434, \"Year\": 2, \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8008845454, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.gowri sankar\", \"Pin\": 1210514227, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 8106909359, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sanjana\", \"Pin\": 1210314245, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 8374854914, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"m.sai teja\", \"Pin\": 1210414235, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 8500322847, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"shaikh adil aman\", \"Pin\": 1210514404, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8978436051, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"n.revanth pavan\", \"Pin\": 1210314237, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9133669333, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V.S.N.Sailesh\", \"Pin\": 1230814156, \"Year\": 2, \"Branch\": \"mech-5\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9533166262, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sri sai lakshmi\", \"Pin\": 1210414228, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9550358173, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P.vinay\", \"Pin\": 1210314941, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9989508796, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"D.prudhvi\", \"Pin\": 1210313510, \"Year\": 3, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9494739070, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"I.pramodh\", \"Pin\": 1210313516, \"Year\": 3, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9963049149, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"uc vijaya\", \"Pin\": 1216512109, \"Year\": 4, \"Branch\": \"barch\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 9966135659, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"B.Amarnath reddy\", \"Pin\": 1230814108, \"Year\": \"01-Jan\", \"Branch\": \"mech-5\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+\", \"Phone\": 7675062308, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.S.S.Kaushik\", \"Pin\": 1210514123, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 9652948288, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"A.Sravya\", \"Pin\": 1210314903, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7729934220, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M.Anusha\", \"Pin\": 1210314905, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7036298773, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"U.M.V.Raghuram\", \"Pin\": 1210314957, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7799334106, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"A.Manikanth\", \"Pin\": 1210314902, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8886202566, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P.Suvankar\", \"Pin\": 1210814153, \"Year\": \"2nd\", \"Branch\": \"MECH\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 9502199259, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P.Sudhamayi\", \"Pin\": 1232614103, \"Year\": \"2nd\", \"Branch\": \"LAW\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7032136986, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Likitha\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7036187316, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"A.K.S.Anurag\", \"Pin\": 1210514124, \"Year\": \"2nd\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7075939699, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Vakul kumar Pathi\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": 0, \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 7207830720, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"N K Chaithanya\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8019522272, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P Veerendra\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8297611935, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"G Supranath\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8333820381, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Surya Mehar\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"CSE\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8790649776, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"M Sai Sanjay Varma\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"Mechnical\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8897658682, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"PN Durga Prasad\", \"Pin\": 0, \"Year\": \"2nd\", \"Branch\": \"IT\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 9848731831, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Ravi Ranjan\", \"Pin\": 1210413744, \"Year\": \"3 year\", \"Branch\": \"ECE\", \"Section\": \"A7\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 9912867316, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K.Sai Sree\", \"Pin\": 1210313521, \"Year\": \"3rd\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"O+\", \"Phone\": 8332903284, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T Maheedhar\", \"Pin\": 1210815148, \"Year\": 1, \"Branch\": \"mech\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 8466066106, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"s akshara\", \"Pin\": 1210314966, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7032524075, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ashish kumar\", \"Pin\": 1210514456, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7036942725, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"rs kranti\", \"Pin\": 1210314241, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7416121339, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"J V Karthik\", \"Pin\": 1210414859, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7416834837, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"d dheeraj\", \"Pin\": 1210414514, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7675873448, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"I Puneeth\", \"Pin\": 1210314027, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 7702824746, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"tharunendra\", \"Pin\": 1210314243, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7799434888, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sindhu\", \"Pin\": 1210314203, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 7893565679, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"sagarika nandi\", \"Pin\": 1210514442, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8093896668, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"anudeep\", \"Pin\": 1210614101, \"Year\": 2, \"Branch\": \"eie\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8121710458, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"e sandeep\", \"Pin\": 1210414517, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8374076566, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"vivekananda reddy\", \"Pin\": 1210414823, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8466858006, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"k vinay\", \"Pin\": 1210314929, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8498826280, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"g sravanti\", \"Pin\": 1210314941, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8501876661, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"susmitha\", \"Pin\": 1210314229, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8897888666, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"kondareddy\", \"Pin\": 1230814131, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 8985499460, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"NVS Naveen\", \"Pin\": 1210314336, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9000012139, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"V dorababu\", \"Pin\": 1230814154, \"Year\": 2, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9154882999, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"c komal aditya\", \"Pin\": 1210514413, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9491944405, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"b srikar aditya\", \"Pin\": 1210514410, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9494666797, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"b srujan varma\", \"Pin\": 1210414308, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9505290329, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"kovida\", \"Pin\": 1210314047, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9542633393, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"CH Sai Bhargav\", \"Pin\": 1210314019, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9642692065, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K snigdha\", \"Pin\": 1210314221, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9908600880, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T jyothi\", \"Pin\": 1210314265, \"Year\": 2, \"Branch\": \"cse\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9963499852, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"I V Pavan ambareesh\", \"Pin\": 1230813126, \"Year\": 3, \"Branch\": \"mech 5/5\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9491267957, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"dhoni\", \"Pin\": 1230813117, \"Year\": 3, \"Branch\": \"mec\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o+ve\", \"Phone\": 9959561063, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Arvind Ratnala\", \"Pin\": 1210314906, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"B9\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9160838491, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Abhishek Jaiswal\", \"Pin\": 1210514402, \"Year\": \"2 year\", \"Branch\": \"EEE\", \"Section\": \"D4\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 7396436721, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"P Sreekanth\", \"Pin\": 1210314341, \"Year\": \"2 year\", \"Branch\": \"CSE\", \"Section\": \"B3\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 7569426264, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Undavalli Devi Raja Sekhar\", \"Pin\": 1210814158, \"Year\": \"2 year\", \"Branch\": \"MECHANICAL\", \"Section\": \"H1\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 8500796772, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"T.Kali Mohan\", \"Pin\": 1210514144, \"Year\": \"2year\", \"Branch\": \"EEE\", \"Section\": \"D1\", \"Email\": 0, \"Blood Group\": \"O+VE\", \"Phone\": 9505483252, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"Yelagandula Anurag\", \"Pin\": 1210313564, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B5\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9949976973, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.Sarath Kumar\", \"Pin\": 1210513355, \"Year\": \"3 year\", \"Branch\": \"EEE\", \"Section\": \"D3\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 9963358116, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"ch.chanukya\", \"Pin\": 1210313414, \"Year\": \"3year\", \"Branch\": \"cse\", \"Section\": \"B4\", \"Email\": 0, \"Blood Group\": \"O+ve\", \"Phone\": 7661062006, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"K vamsidhar\", \"Pin\": 1210414525, \"Year\": 2, \"Branch\": \"ece\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"o-ve\", \"Phone\": 8523864158, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"S.Saiprasanna\", \"Pin\": 1210313451, \"Year\": \"3 year\", \"Branch\": \"CSE\", \"Section\": \"B4\", \"Email\": 0, \"Blood Group\": \"O-ve\", \"Phone\": 9160013855, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"DEVASURYA\", \"Pin\": 1210313411, \"Year\": \"3YEAR\", \"Branch\": \"CSE\", \"Section\": \"B4\", \"Email\": 0, \"Blood Group\": \"O-ve\", \"Phone\": 9160033855, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 }, { \"Name\": \"pradeep reddy\", \"Pin\": 1210514155, \"Year\": 2, \"Branch\": \"eee\", \"Section\": 0, \"Email\": 0, \"Blood Group\": \"royal stag\", \"Phone\": 9133771183, \"Hostel\": 0, \"Local\": 0, \"College\": 0, \"Date of Birth\": 0, \"Gender\": 0, \"Term\": 0, \"Home Town\": 0, \"Blood Donation Willingness\": 0, \"Registered on\": 0 } ]}");

				text1=text.toString();




                Bgetdetails.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View v) {


                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);

                        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);



                        id	=	Eid.getText().toString();
                        if(Tevent.getText().toString().length()==0){
                            Toast.makeText(getApplicationContext(),"Event name cant be empty\nAdd Event name by clicking on it",Toast.LENGTH_SHORT).show();
                        }
                        else if(id.length()==0){
                            Eid.setError("Enter ID or Phone Number");
                        }
                        else if(id.length()!=10){
                            setblankface();
                            Eid.setError("Please check your ID or Phone number");
                        }
                        else {
                            try{
                                FileInputStream input_document = new FileInputStream(new File(Environment.getExternalStorageDirectory().getPath() +"/RCGU Attendance/New Registrations"+".xls"));
                                HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document);
                                HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);
                                Cell c = null;
                                boolean flag=false;
                                int pos=0;
                                Row row1 = null;
                                try{
                                    for (int j=0; j< my_worksheet.getLastRowNum() + 1; j++) {
                                        row1 = my_worksheet.getRow(j);
                                        Cell cell1=null,cell7=null;
                                        if(row1.getCell(0).toString().length()!=0){cell1 = row1.getCell(1);}
                                        if(row1.getCell(0).toString().length()!=0){cell7 = row1.getCell(7);}
                                        if(cell1.toString().equals(id)||cell7.toString().equals(id)){
                                            flag=true;
                                            pos=j;
                                        }
                                    }
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if(flag==true){
                                    row1 = my_worksheet.getRow(pos);
                                    name = row1.getCell(0).toString();
                                    pin = row1.getCell(1).toString();
                                    year= row1.getCell(2).toString();
                                    branch = row1.getCell(3).toString();
                                    section = row1.getCell(4).toString();
                                    email = row1.getCell(5).toString();
                                    blood = row1.getCell(6).toString();
                                    phone = row1.getCell(7).toString();
                                    hostel = row1.getCell(8).toString();
                                    local = row1.getCell(9).toString();
                                    college = row1.getCell(10).toString();
                                    dob = row1.getCell(11).toString();
                                    gender = row1.getCell(12).toString();
                                    term = row1.getCell(13).toString();
                                    hometown = row1.getCell(14).toString();
                                    bloodwilling = row1.getCell(15).toString();
                                    registered = row1.getCell(16).toString();
                                    postattendance(flag);
                                }
                                else{
                                    new AsyncTaskParseJson().execute();
                                }

                            }
                            catch (IOException e){
                                new AsyncTaskParseJson().execute();
                            }
                        }



					}


				});

                Bsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{
                            if(name.length()!=0){
                                if(new File(Environment.getExternalStorageDirectory().getPath()+"/RCGU Attendance/"+MainActivity.Tevent.getText().toString()+".xls").exists()){
                                    Log.e("Excel","is available");
                                    exportExcel.addElement(getApplicationContext(),Tevent.getText().toString(),name,pin,year,branch,section,email,blood,phone,hostel,local,college,dob,gender,term,hometown,bloodwilling,registered);
                                }
                                else{
                                    Log.e("Excel","is not available");
                                    exportExcel.saveExcelFile(MainActivity.this);
                                    exportExcel.addElement(getApplicationContext(),Tevent.getText().toString(),name,pin,year,branch,section,email,blood,phone,hostel,local,college,dob,gender,term,hometown,bloodwilling,registered);
                                }
                            }
                            else {
                                Eid.setError("Enter ID");
                            }
                        }
                        catch (NullPointerException e){
                            if(Eventname.length()==0){
                                Toast.makeText(getApplicationContext(),"Event name cant be empty\nAdd Event name by clicking on it",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                e.printStackTrace();
                                Eid.setError("Enter ID or Phone Number");
                            }
                        }

                    }
                });
                Tevent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.custom_dialog);
                        dialog.setTitle("Set Event Name");


                        dialog.show();
                        Bpassword       = (Button)dialog.findViewById(R.id.bpassword);
                        Epassword       = (EditText) dialog.findViewById(R.id.epassword);
                        Eseteventname   = (EditText) dialog.findViewById(R.id.eseteventname);
                        Bpassword.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                            if (Epassword.getText().toString().equals("1234")) {
                                                Tevent.setText(Eseteventname.getText().toString());
                                                dialog.dismiss();
                                            } else {
                                                Epassword.setError("Invalid Password");
                                            }

                                    }
                                });
                    }
                });


            }
    public void setEvent(View v){

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                startActivity(new Intent(this, register.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setblankface() {
            Iface.setImageResource(R.mipmap.blankface);
    }
    private  class  GetXMLTask  extends  AsyncTask<String,  Void,  Bitmap>  {
        @Override
        protected void onPreExecute() {
            setblankface();
        }

        @Override
		protected  Bitmap  doInBackground(String...  urls)  {
			Bitmap  map  =  null;
			for  (String  url  :  urls)  {
				map  =  getBitmapFromURL(url);
			}
			return  map;
		}
		@Override
		protected  void  onPostExecute(Bitmap  result)  {
            Iface.setImageDrawable(Drawable.createFromPath("sdcard/RCGU Attendance/Images/"+pin+".jpg"));
		}

        public Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                imagetotsize=connection.getContentLength();
                InputStream input = connection.getInputStream();
                OutputStream output = new FileOutputStream("/sdcard/RCGU Attendance/Images/"+pin+".jpg");
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

                return null;
            } catch (IOException e) {
                return null;
            }
        }
	}
	public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        boolean flag;

        String text1= text.toString();
		final String TAG = "AsyncTaskParseJson.java";


		JSONArray dataJsonArr = null;

		@Override
		protected void onPreExecute() {}

		@Override
		protected String doInBackground(String... arg0) {

			try {

				JSONObject json = new JSONObject(text1);

				dataJsonArr = json.getJSONArray("rcgu");

				for (int i = 0; i < dataJsonArr.length(); i++) {

					JSONObject c = dataJsonArr.getJSONObject(i);

                    flag=false;
					name = c.getString("Name");
					pin = c.getString("Pin");
					year= c.getString("Year");
                    branch = c.getString("Branch");
                    section = c.getString("Section");
                    email = c.getString("Email");
                    blood = c.getString("Blood Group");
                    phone = c.getString("Phone");
                    hostel = c.getString("Hostel");
                    local = c.getString("Local");
                    college = c.getString("College");
                    dob = c.getString("Date of Birth");
                    gender = c.getString("Gender");
                    term = c.getString("Term");
                    hometown = c.getString("Home Town");
                    bloodwilling = c.getString("Blood Donation Willingness");
                    registered = c.getString("Registered on");

                    if (id.equals(phone) || id.equals(pin)) {
                        flag=true;
                        break;
                    }
                    else if (flag == false) {
                        name=pin=year=branch=section=email=blood=phone=hostel=local=college=dob=gender=term=hometown=bloodwilling=registered="";

                    }
				}

			} catch (JSONException e) {
				e.printStackTrace();
            }

			return null;
		}

		@Override
		protected void onPostExecute(String strFromDoInBg) {
            postattendance(flag);
        }
    }

    public void postattendance(boolean flag){

        final String TAG = "AsyncTaskParseJson.java";

        Ename.setText(name);
        Epin.setText(pin);
        Eyear.setText(year);
        Ebranch.setText(branch);
        Esection.setText(section);
        Eemail.setText(email);
        Eblood.setText(blood);
        Ephone.setText(phone);


        File file = new File("sdcard/RCGU Attendance/Images/"+pin+".jpg");
        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        try{
            if(flag==false)
            {
                setblankface();
                FragmentManager fm;
                fm= getFragmentManager();
                showDialog dialog =new showDialog();
                dialog.show(fm,"message");

            }
            else if(pincheck.equals(pin)||pin.equals("")){

            }

            else if(file.exists()){

                Iface.setImageDrawable(Drawable.createFromPath(file.toString()));
            }

            else if(conMgr.getActiveNetworkInfo().isConnectedOrConnecting() && conMgr.getActiveNetworkInfo()!=null && conMgr.getActiveNetworkInfo().isAvailable() ) {
                Log.e("network ", "connected");

                URL = "https://doeresults.gitam.edu/gitamhallticket/img.aspx?id=";
                URL = URL + pin;
                Log.e("file not available","entered");
                GetXMLTask task = new GetXMLTask();
                task.execute(URL);
                pincheck=pin;

            }


        }
        catch (NullPointerException e){
            Log.e("network ", "disconnected");
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Network connection is required to load photo",Toast.LENGTH_SHORT).show();

        }

    }

    public class showDialog extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You are not registered")
                    .setPositiveButton("Register Now", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(MainActivity.this,com.slabs.sreekanth.rcguattendance.register.class));

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            return builder.create();
        }
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }
}
