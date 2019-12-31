/*Brian Obmalay csc472*/


package com.example.notepad;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import androidx.recyclerview.widget.DividerItemDecoration;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener {

    private ArrayList<Note> noteList = new ArrayList<>();
    private static final int CODE_FOR_B_ACTIVITY = 111;

    private RecyclerView recyclerView;
    private NoteAdaptor noteAdaptor;
    private String header;
    private String notes;
    ActionBar hj;

    private int str;

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Multi Notes");


        recyclerView = findViewById(R.id.recycler);
        noteAdaptor = new NoteAdaptor(noteList, this);

         str =noteAdaptor.getItemCount();
       // hj.setSubtitle(str);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(noteAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager();

        loadFile();
        go();



    }



    private void go() {

        Intent intent = getIntent();
        if (intent.hasExtra("EXTRA_MESSAGE")) {
            header = intent.getStringExtra("EXTRA_MESSAGE");
            notes = intent.getStringExtra("EXTRA_VALUE");
            //textView.setText("ActivityA\n\nMagic Word: " + text);
            Note p = new Note(header, System.currentTimeMillis(), notes);
           // Note p = new Note(header, currentTime, notes);
            noteList.add(p);

            //noteAdaptor.notifyDataSetChanged();


            try {
                saveProducts();
            } catch (IOException|JSONException e){

            }

        } else {
            System.out.print("sorry");
        }





    }

    private void loadFile() {


        try {
            InputStream is = getApplicationContext().
                    openFileInput(getString(R.string.file_name));

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();


            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String name = jsonObject.getString("nameText");
                long time = jsonObject.getLong("timeText");
                //String time = jsonObject.getString("timetext");
                String note = jsonObject.getString("noteText");
                Note p = new Note(name, time, note);
                noteList.add(p);
            }


        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {

        getIntent().removeExtra("EXTRA_MESSAGE");
        getIntent().removeExtra("EXTRA_VALUE");
        try {
            saveProducts();
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (IOException | JSONException e) {
            Toast.makeText(this, getString(R.string.not_saved), Toast.LENGTH_SHORT).show();
        }
        super.onPause();
    }

    private void saveProducts() throws IOException, JSONException {

        //throws IOException, JSONException

        FileOutputStream fos = getApplicationContext().
                openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);

        JSONArray jsonArray = new JSONArray();

        for (Note p : noteList) {
            JSONObject prodJSON = new JSONObject();
            prodJSON.put("nameText", p.getName());
            prodJSON.put("timeText", p.getTime());
            prodJSON.put("noteText", p.getNote());

            jsonArray.put(prodJSON);

        }

        String jsonText = jsonArray.toString();


        fos.write(jsonText.getBytes());
        fos.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

//    public void openNewActivity(View v) {
//        Intent intent = new Intent(this, SecondActivity.class);
//        startActivity(intent);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.info:
                Toast.makeText(this, "You want info", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, InfoActivity.class);
                startActivity(intent2);
                return true;
            case R.id.addNote:
                Intent intent = new Intent(this, SecondActivity.class);
                startActivity(intent);
                Toast.makeText(this, "New Note in Process", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onClick(View v) {
        int pos = recyclerView.getChildLayoutPosition(v);

        Note selection = noteList.get(pos);

        Intent intent = new Intent(this, SecondActivity.class);

        intent.putExtra("head",selection.getName());
        intent.putExtra("notes",selection.getNote());



        startActivityForResult(intent, CODE_FOR_B_ACTIVITY);



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_FOR_B_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra("head");
                String note = data.getStringExtra("note");

                Note p = new Note(text, System.currentTimeMillis(), note);
                // Note p = new Note(header, currentTime, notes);
                noteList.add(p);
                //int f =noteList.indexOf(p);
                //noteList.remove(f-1);
                //noteAdaptor.notifyItemRemoved(f);
                //notesbox.setText(text);

                //  Log.d(TAG, "onActivityResult: User Text: " + text);
            } else {
                // Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {

         pos = recyclerView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Do you want to delete data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                remove();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();

                //finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();




        return true;
    }

    public void remove(){
        //int pos = recyclerView.getChildLayoutPosition(v);

        noteList.remove(pos);
        //recyclerView.removeViewAt(pos);
        noteAdaptor.notifyItemRemoved(pos);


    }


    public String makeheader() {

        if (noteAdaptor.getItemCount() == 0) {
            return "Multi Notes (0)";
        } else {

            return String.format("Multi Notes (%s)", noteAdaptor.getItemCount());
        }
    }

        @Override
        protected void onResume() {
            super.onResume();
            getSupportActionBar().setTitle(makeheader());
            Log.d("TOOLBAR 2", getSupportActionBar().getTitle().toString());
        }

    }


