package ca.georgebrown.comp3074.lab72;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnGet = findViewById(R.id.btnGet);
        EditText name = findViewById(R.id.editName);
        EditText grade = findViewById(R.id.editGrade);

        TextView out = findViewById(R.id.txtOut);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();

                values.put(StudentProvider.NAME, name.getText().toString());
                values.put(StudentProvider.GRADE, grade.getText().toString());

                Uri uri = getContentResolver().insert(StudentProvider.CONTENT_URI, values);
                Toast.makeText(MainActivity.this,
                        uri.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = StudentProvider.CONTENT_URI;

                Cursor c = managedQuery(uri, null, null,
                        null, null);

                String msg = "";
                if(c!=null && c.moveToFirst()){
                    do{
                        msg+="\n"+
                                c.getString(c.getColumnIndex(StudentProvider.ID))+
                                ", "+c.getString(c.getColumnIndex(StudentProvider.NAME))+
                                ", "+c.getString(c.getColumnIndex(StudentProvider.GRADE));
                    }while(c.moveToNext());

                    out.setText(msg);
                }else{
                    out.setText("No data");
                }
                //hide keyboard
                ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });

    }
}