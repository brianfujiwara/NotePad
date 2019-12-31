package com.example.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private EditText header;
    private EditText notes;
    public static final String EXTRA_MESSAGE = "com.example.NotePad.MESSAGE";
    public static final String EXTRA_VALUE = "com.example.NotePad.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        header = findViewById(R.id.header);
        notes = findViewById(R.id.notesbox);



        Intent intent = getIntent();
        if (intent.hasExtra("head")) {
            String text = intent.getStringExtra("head");
            String note = intent.getStringExtra("notes");
            header.setText(text);
            notes.setText(note);
        } else {
           // notes.setText("ActivityB\n\nNo Magic Word Provided\n:-(");
        }



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.second_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {
            case R.id.save:
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                String h = header.getText().toString();
                String n = notes.getText().toString();

                //MainActivity.newNote(h,n);
                //to transfer data to main activity use intent;
                Intent intent = new Intent(this, MainActivity.class);
               // Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("EXTRA_MESSAGE", h);
                intent.putExtra("EXTRA_VALUE", n);
                startActivity(intent);
               // startActivity(intent2);

//                Intent data = new Intent(); // Used to hold results data to be returned to original activity
//                data.putExtra("head", header.getText().toString());
//                data.putExtra("note", header.getText().toString());
//
//                setResult(RESULT_OK, data);
//                finish(); // This closes the current activity, returning us to the original activity


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

        }

        @Override
        public void onBackPressed(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Save Data");
            builder.setMessage("Do you want to save data?");

            //Intent intent = new Intent(this, MainActivity.class);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    getup();

//                    String h = header.getText().toString();
//                    String n = notes.getText().toString();

                    //MainActivity.newNote(h,n);
                    //to transfer data to main activity use intent;
                   // Intent intent = new Intent(this, MainActivity.class);
                    // Intent intent = new Intent(this, MainActivity.class);
//                    intent.putExtra("EXTRA_MESSAGE", h);
//                    intent.putExtra("EXTRA_VALUE", n);
//                    startActivity(intent);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }


        public void getup(){

            String h = header.getText().toString();
            String n = notes.getText().toString();

            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra("EXTRA_MESSAGE", h);
            intent.putExtra("EXTRA_VALUE", n);
            startActivity(intent);
        }
}
