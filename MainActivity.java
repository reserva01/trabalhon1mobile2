package com.example.trabalhon1mobile;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.trabalhon1mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RequestQueue requestQueue;
    private static final String JSON_URL = "https://jsonplaceholder.typicode.com/comments";
    private Button fetchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        fetchButton = findViewById(R.id.button_fetch_comments);
        requestQueue = Volley.newRequestQueue(this);

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject comment = response.getJSONObject(i);
                                int id = comment.getInt("id");
                                int postId = comment.getInt("postId");
                                String name = comment.getString("name");
                                String email = comment.getString("email");
                                String body = comment.getString("body");

                                stringBuilder.append("ID: ").append(id).append("\n");
                                stringBuilder.append("Post ID: ").append(postId).append("\n");
                                stringBuilder.append("Name: ").append(name).append("\n");
                                stringBuilder.append("Email: ").append(email).append("\n");
                                stringBuilder.append("Body: ").append(body).append("\n\n");
                            }
                            textViewResult.setText(stringBuilder.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(request);
    }
}
