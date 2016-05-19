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



				File dir = Environment.getExternalStorageDirectory();
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
				}

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
