package com.mfr414.todolist.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.mfr414.todolist.DBHelper.TaskDBHelper;
import com.mfr414.todolist.Model.Task;
import com.mfr414.todolist.R;
import com.mfr414.todolist.TaskFragment;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;
    private Context context;
    SharedPreferences pref;
    String size;
    TaskFragment taskfrag;


    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_task, viewGroup, false);

        //get Shared Preference value to set the text size
        size = pref.getString("text_size","20");
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder taskViewHolder, int position) {
        //assigning tasklist to taskclass
        final Task taskclass = taskList.get(position);
        //set task to textview
        taskViewHolder.taskNameDisplay.setText(taskclass.getTask());

        //delete button onclick listener
        taskViewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskfrag.deleteTask(taskclass.getId());
            }
        });
    }

    //to get item count from tasklist size
    @Override
    public int getItemCount()  {
        return (taskList != null) ? taskList.size() : 0;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        //Create local variable for textview and button;
        TextView taskNameDisplay;
        Button deletebtn;
        CheckBox cbSelected;

        public TaskViewHolder(View itemView) {
            super(itemView);
            //assigning textview and button from view to local variable
            taskNameDisplay = itemView.findViewById(R.id.taskName);
            deletebtn = itemView.findViewById(R.id.deleteBtn);

            //load textsize from SharedPreference
            taskNameDisplay.setTextSize(Float.parseFloat(size));
        }
    }

    public TaskAdapter(Context context, List<Task> task, TaskFragment taskfrag) {
        this.context = context;
        this.taskList = task;
        this.taskfrag = taskfrag;
    }

    //set Shared From taskfragment
    public void setShared(SharedPreferences pref){
        this.pref=pref;
    }
}
