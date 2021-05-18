package sg.edu.rp.c347.id19007966.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve = findViewById(R.id.btnRetrieve);

        tvDBContent = findViewById(R.id.tvDBContent);

        etContent = findViewById(R.id.etContent);

        al = new ArrayList<>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = etContent.getText().toString();
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                long inserted_id = dbHelper.insertNote(data);

                dbHelper.close();

                if (inserted_id != -1) {
                    Toast.makeText(MainActivity.this, "Insert successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbHelper.getAllNotes());

                dbHelper.close();

                String text = "";
                for (int i = 0; i < al.size(); i++) {
                    Note tmp = al.get(i);
                    text += "ID: " + tmp.getId();
                    text += ", " + tmp.getNoteContent();
                    text += "\n";
                }
                tvDBContent.setText(text);
            }
        });

    }
}