package com.mfr414.todolist;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mfr414.todolist.Adapter.TaskAdapter;
import com.mfr414.todolist.DBHelper.TaskDBHelper;
import com.mfr414.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment implements View.OnClickListener {

    private TaskDBHelper db;
    private List<Task> tasklist = new ArrayList<>();
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    SharedPreferences pref;
    View v;
    Button btn;

    public TaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task,container, false);
        v = view;

        //set RecyclerView
        recyclerView = view.findViewById(R.id.rv_itemTask);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //set DBHelper
        db = new TaskDBHelper(getContext());
        tasklist.addAll(db.getAllTask());

        //set RecyclerView Adapter
        adapter = new TaskAdapter(getContext(), tasklist,this);
        recyclerView.setAdapter(adapter);
        adapter.setShared(pref);

        //set Button ADD onclicklistener
        btn = view.findViewById(R.id.btnAddTask);
        btn.setOnClickListener(this);
        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        //set SharedPreference
        pref = getActivity().getSharedPreferences("PREFS_TEXT", Context.MODE_PRIVATE);
    }

    //create option menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_task, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //what options item do while the option item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_large_textsize:
                //put new value to shared preference
                pref.edit().putString("text_size","22").apply();
                return true;

            case R.id.action_small_textsize:
                //put new value to shared preference
                pref.edit().putString("text_size","18").apply();
                return true;

            case R.id.action_about:
                //using intent implicit to open my github
                String url = "https://github.com/MFR414";
                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,webpage);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //to assign Add button do while the button is clicked
    @Override
    public void onClick(View v) {
        addTask();
    }

    //to add task to database
    private void addTask() {
        EditText inputAddTask = v.findViewById(R.id.addTask);
        String insertTask = inputAddTask.getText().toString();

        // inserting task in db and getting
        // newly inserted task id
        long id = db.insertTask(insertTask);

        // get the newly inserted task from db
        Task taskAddNewly = db.getTask(id);

        if(taskAddNewly != null){
            // adding new task to array list at 0 position
            tasklist.add(0, taskAddNewly);

            // refreshing the list
            adapter.notifyDataSetChanged();
        }
    }

    //to remove task from database
    public void deleteTask(int id) {
        // deleting the task from db
        db.deleteTask(id);

        tasklist.clear();
        tasklist.addAll(db.getAllTask());
        adapter.notifyDataSetChanged();
    }
}
