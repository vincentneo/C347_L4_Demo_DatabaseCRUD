package sg.edu.rp.c347.id19007966.demodatabasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    TextView tvID;
    EditText etContent;
    Button btnUpdate, btnDelete;
    Note data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvID = findViewById(R.id.textViewEditID);
        etContent = findViewById(R.id.editTextEditContent);
        btnUpdate = findViewById(R.id.buttonUpdate);
        btnDelete = findViewById(R.id.buttonDelete);

        Intent i = getIntent();
        data = (Note) i.getSerializableExtra("data");

        tvID.setText("ID: " + data.getId());
        etContent.setText(data.getNoteContent());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(EditActivity.this);
                data.setNoteContent(etContent.getText().toString());
                dbHelper.updateNote(data);
                dbHelper.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(EditActivity.this);
                dbHelper.deleteNote(data.getId());
                dbHelper.close();
                finish();
            }
        });
    }
}