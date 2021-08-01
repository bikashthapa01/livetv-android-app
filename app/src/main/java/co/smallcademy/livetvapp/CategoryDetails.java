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

import co.smallcademy.livetvapp.adapters.ChannelAdapter;
import co.smallcademy.livetvapp.models.Category;
import co.smallcademy.livetvapp.models.Channel;
import co.smallcademy.livetvapp.services.ChannelDataService;

public class CategoryDetails extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView categoryDetailsList;
    ChannelAdapter adapter;
    List<Channel> channels;
    ChannelDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);

        channels = new ArrayList<>();
        dataService = new ChannelDataService(this);

        Category category = (Category) getIntent().getSerializableExtra("category");
        getSupportActionBar().setTitle(category.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryDetailsList = findViewById(R.id.category_details_list);
        adapter = new ChannelAdapter(channels,"details");
        categoryDetailsList.setLayoutManager(new GridLayoutManager(this,2));
        categoryDetailsList.setAdapter(adapter);

        String url = "http://192.168.1.65/mytv/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat="+category.getName();

        dataService.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        channels.add(c);
                        adapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

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