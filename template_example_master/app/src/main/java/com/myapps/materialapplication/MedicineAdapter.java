package com.myapps.materialapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
 * Created by ozhar on 21-04-2016.
 */
public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private List<Medicine> mMedicines;
    private Firebase firebase;

    public MedicineAdapter(){

    }

    public MedicineAdapter(Context context){
        mMedicines = new ArrayList<>();
        Firebase.setAndroidContext(context);
        Firebase mRef = new Firebase(Constants.FIREBASE_HOME_URL);
        AuthData authData = mRef.getAuth();
        String mail = authData.getProviderData().get("email").toString();
        StringTokenizer token = new StringTokenizer(mail, "@");
        String uemail = token.nextToken();
        firebase = new Firebase("https://medremind.firebaseio.com/medicines/" + uemail);
        firebase.addChildEventListener(new MedicineChildEventListener());
    }

    private class MedicineChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Medicine medicine = dataSnapshot.getValue(Medicine.class);
            medicine.setKey(dataSnapshot.getKey());
            mMedicines.add(0, medicine);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.med_card_view_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Medicine medicine = mMedicines.get(position);
        holder.medName.setText(medicine.getMedname());
        holder.medTime.setText("Time to take :  " + medicine.getMedtime());
        if (medicine.isType()){
            holder.medIntake.setText(medicine.getMedintake() + "tablet/s");
        }
        else {
            holder.medIntake.setText(medicine.getMedintake() + "ml");
        }

        if (medicine.isType()){
            holder.medImage.setImageResource(R.drawable.tablet);
        }
        else {
            holder.medImage.setImageResource(R.drawable.syrup);
        }
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView medName;
        private TextView medTime;
        private TextView medIntake;
        private ImageView medImage;

        public ViewHolder(View itemView) {
            super(itemView);
            medName = (TextView) itemView.findViewById(R.id.mname);
            medTime = (TextView) itemView.findViewById(R.id.mtime);
            medIntake = (TextView) itemView.findViewById(R.id.mintake);
            medImage = (ImageView) itemView.findViewById(R.id.mimage);
        }
    }
}
