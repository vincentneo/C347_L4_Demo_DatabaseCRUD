package sg.edu.rp.c347.id19007966.demodatabasecrud;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnEdit, btnRetrieve;
    TextView tvDBContent;
    EditText etContent;
    ArrayList<Note> al;
    ArrayAdapter<Note> aa;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        btnEdit = findViewById(R.id.btnEdit);
        btnRetrieve = findViewById(R.id.btnRetrieve);

        tvDBContent = findViewById(R.id.tvDBContent);

        etContent = findViewById(R.id.etContent);

        listView = findViewById(R.id.listView);

        al = new ArrayList<>();
        aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, al);
        listView.setAdapter(aa);

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
                // Section F: The edit text will act for filtering keyword.
                al.addAll(dbHelper.getAllNotes(etContent.getText().toString()));
                aa.notifyDataSetChanged();
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // prevent crash if user click edit before any clicking of retrieve
                if (al.isEmpty()) {
                    DBHelper dbHelper = new DBHelper(MainActivity.this);
                    al.clear();
                    al.addAll(dbHelper.getAllNotes(""));
                    aa.notifyDataSetChanged();
                }
                Note target = al.get(0);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data", target);
                startActivityForResult(intent, 9);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Note target = al.get(i);
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("data", target);
                startActivityForResult(intent, 9);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            btnRetrieve.performClick();
        }
    }
}