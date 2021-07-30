package co.smallcademy.livetvapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import co.smallcademy.livetvapp.models.Channel;

public class Details extends AppCompatActivity {
    PlayerView playerView;
    ImageView fbLink,twtLink,ytLink,webLink;
    TextView description;
    ImageView fullScreen;
    boolean isFullScreen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        getSupportActionBar().setTitle(channel.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = findViewById(R.id.playerView);
        fullScreen = playerView.findViewById(R.id.exo_fullscreen_icon);
        
        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullScreen){

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

//                    Toast.makeText(Details.this, "We are Now going back to normal mode.", Toast.LENGTH_SHORT).show();
                    isFullScreen = false;                    
                }else {

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

//                    Toast.makeText(Details.this, "We are going to FullScreen Mode.", Toast.LENGTH_SHORT).show();
                    isFullScreen = true;
                }
            }
        });


        fbLink = findViewById(R.id.facebookLink);
        twtLink = findViewById(R.id.twitterLink);
        ytLink = findViewById(R.id.youtubeLink);
        webLink = findViewById(R.id.websiteLink);

        description = findViewById(R.id.channelDesc);
        description.setText(channel.getDescription());

        fbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getFacebook());
            }
        });

        twtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getTwitter());
            }
        });

        ytLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getYoutube());
            }
        });

        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(channel.getWebsite());
            }
        });

        playChannel(channel.getLive_url());

    }

    public void openLink(String url){
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void playChannel(String live_url){
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(live_url);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();

    }
}