package co.smallcademy.livetvapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.smallcademy.livetvapp.Details;
import co.smallcademy.livetvapp.R;
import co.smallcademy.livetvapp.models.Channel;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewHolder> {
    List<Channel> channels;
    String type;

    public ChannelAdapter(List<Channel> channels, String type) {
        this.channels = channels;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v;
        if(type.equals("slider")){
             v = LayoutInflater.from(parent.getContext()).inflate(R.layout.big_slider_view,parent,false);
        }else if(type.equals("details")){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.details_view,parent,false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_cat_view,parent,false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  ChannelAdapter.ViewHolder holder, int position) {
        holder.channelName.setText(channels.get(position).getName());
        Picasso.get().load(channels.get(position).getThumbnail()).into(holder.channelImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Details.class);
                i.putExtra("channel",channels.get(position));
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView channelImage;
        TextView channelName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            channelImage = itemView.findViewById(R.id.channelThumbnail);
            channelName = itemView.findViewById(R.id.channelName);
        }
    }
}
