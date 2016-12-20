package com.myapps.materialapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ozhar on 23-04-2016.
 */
public class UpdatesAdapter extends RecyclerView.Adapter<UpdatesAdapter.ViewHolder> {

    private List<Update> mUpdate;
    private Firebase firebase;

    public UpdatesAdapter(){

    }

    public UpdatesAdapter(Context context){
        mUpdate = new ArrayList<>();
        Firebase.setAndroidContext(context);
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        AuthData authData = mRef.getAuth();
        String mail = authData.getProviderData().get("email").toString();
        StringTokenizer token = new StringTokenizer(mail, "@");
        String uemail = token.nextToken();
        firebase = new Firebase("https://medremind.firebaseio.com/updates/" + uemail);
        firebase.addChildEventListener(new DoctorChildEventListener());
    }

    private class DoctorChildEventListener implements ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Update update = dataSnapshot.getValue(Update.class);
            update.setKey(dataSnapshot.getKey());
            mUpdate.add(0, update);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_card_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Update update = mUpdate.get(position);
        holder.name.setText(update.getMedname());
        holder.message.setText(update.getMessage());
        holder.time.setText(update.getMedtime());
    }

    @Override
    public int getItemCount() {
        return mUpdate.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView message;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvname);
            message = (TextView) itemView.findViewById(R.id.tvmessage);
            time = (TextView) itemView.findViewById(R.id.tvtime);
        }
    }

}
