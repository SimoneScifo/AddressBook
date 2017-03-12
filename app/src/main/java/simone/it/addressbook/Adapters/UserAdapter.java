package simone.it.addressbook.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import simone.it.addressbook.Activities.MainActivity;
import simone.it.addressbook.Activities.ViewActivity;
import simone.it.addressbook.Models.User;
import simone.it.addressbook.R;

import static simone.it.addressbook.Activities.MainActivity.REQUEST_DELETE;
import static simone.it.addressbook.Activities.MainActivity.REQUEST_EDIT;

/**
 * Created by Simone on 11/03/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserVH> {
    //ActionMode actionMode;
    private ArrayList<User> dataSet = new ArrayList<>();
    private int position;


    public void addUser(User item) {
        dataSet.add(0, item);
        notifyItemInserted(0);

    }

    public void updateUser(User item, int position) {
        dataSet.set(position, item);
        notifyItemChanged(position);
    }

    public ArrayList<User> getUsers() {
        return dataSet;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public User getUser(int position) {
        return dataSet.get(position);
    }


    public void setDataSet(ArrayList<User> dataSet) {
        this.dataSet = dataSet;
        notifyDataSetChanged();
    }

    public void deleteUser(int position) {
        dataSet.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public UserVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new UserVH(view);
    }

    @Override
    public void onBindViewHolder(UserVH holder, int position) {
        User user = dataSet.get(position);
        holder.nameTV.setText(user.getName());
        holder.addressTV.setText(user.getAddress());
        holder.phoneTV.setText(user.getPhone());
        holder.emailTV.setText(user.getEmail());
    }

    public class UserVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageContact;
        TextView nameTV, phoneTV, addressTV, emailTV;

        public UserVH(View itemView) {
            super(itemView);
            imageContact = (ImageView) itemView.findViewById(R.id.imgUser);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
            phoneTV= (TextView) itemView.findViewById(R.id.phoneTV);
            addressTV = (TextView) itemView.findViewById(R.id.addressTV);
            emailTV = (TextView) itemView.findViewById(R.id.emailTV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ViewActivity.class);
            ((MainActivity)v.getContext()).startActivityForResult(intent, REQUEST_DELETE);
            Intent i = new Intent(v.getContext(), ViewActivity.class);
            ((MainActivity)v.getContext()).startActivityForResult(i, REQUEST_EDIT);
        }
    }
}