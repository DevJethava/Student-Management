package com.android.dev.studentmanagement.Adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.dev.studentmanagement.Activity.RegisterActivity;
import com.android.dev.studentmanagement.DataBase.Model.StudentModel;
import com.android.dev.studentmanagement.DataBase.Room.RoomDB;
import com.android.dev.studentmanagement.R;

import java.util.List;

/**
 * Created by Dev Jethava on 10/10/2020.
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder> {

    Activity activity;
    List<StudentModel> modelList;
    RoomDB roomDB;

    public StudentAdapter(Activity activity, List<StudentModel> modelList) {
        this.activity = activity;
        this.modelList = modelList;
        roomDB = RoomDB.getInstance(this.activity);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvName.setText(modelList.get(position).getName());
        holder.tvEmail.setText(modelList.get(position).getEmail());
        holder.tvCity.setText(modelList.get(position).getCity());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StudentModel model = modelList.get(position);

                Intent intent = new Intent(activity, RegisterActivity.class);
                intent.putExtra("data", model);
                activity.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Do you went to delete ??");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        roomDB.dataDAO().delete(modelList.get(position));
                        Toast.makeText(activity, "Data Deleted..", Toast.LENGTH_SHORT).show();

                        modelList.clear();
                        modelList.addAll(roomDB.dataDAO().getAll());
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();
                    }
                });

                builder.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        if (modelList == null)
            return 0;
        else
            return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvEmail;
        TextView tvCity;

        Button btnEdit;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCity = itemView.findViewById(R.id.tvCity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void filterList(List<StudentModel> modelList){
        this.modelList = modelList;
        notifyDataSetChanged();
    }
}
