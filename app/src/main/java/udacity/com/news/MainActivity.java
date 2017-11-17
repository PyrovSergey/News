package udacity.com.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button searchButton;
    EditText requestEditText;

    static String requestString;

    public static final String LOG_TAG = "MyTAGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = (Button) findViewById(R.id.buttonSearch);
        requestEditText = (EditText) findViewById(R.id.editTextSearch);

        searchButton.setOnClickListener(this);
        requestEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() != 0) {
                    searchButton.setText("Find news");
                }
                if (charSequence.length() == 0) {
                    searchButton.setText("Read news");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        requestEditText.setText("");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonSearch) {
            requestString = requestEditText.getText().toString();
            if (TextUtils.isEmpty(requestString)) {
                searchButton.setText("Read wews");
            }
            Intent intent = new Intent(this, ResultSearchActivity.class);
            startActivity(intent);
        }
    }

}
