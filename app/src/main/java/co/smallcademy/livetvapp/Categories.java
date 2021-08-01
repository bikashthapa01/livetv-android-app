package co.smallcademy.livetvapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.smallcademy.livetvapp.adapters.CategoryAdapter;
import co.smallcademy.livetvapp.models.Category;
import co.smallcademy.livetvapp.models.Channel;
import co.smallcademy.livetvapp.services.ChannelDataService;

public class Categories extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView categoryLists;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    ChannelDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataService = new ChannelDataService(this);
        categoryList = new ArrayList<>();
        categoryLists = findViewById(R.id.category_lists);
        categoryLists.setLayoutManager(new GridLayoutManager(this,2));
        categoryAdapter = new CategoryAdapter(categoryList);
        categoryLists.setAdapter(categoryAdapter);

        dataService.getChannelData("http://192.168.1.65/tvapp/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&categories=all", new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject categoryData = response.getJSONObject(String.valueOf(i));

                        Category category = new Category(categoryData.getInt("id"),categoryData.getString("name"),categoryData.getString("image_url"));
                        categoryList.add(category);
                        categoryAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}