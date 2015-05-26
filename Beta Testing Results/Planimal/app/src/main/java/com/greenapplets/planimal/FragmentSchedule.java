package com.greenapplets.planimal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

/**
 * Created by ASUS on 3/13/2015.
 */
public class FragmentSchedule  extends Fragment{
    private DAO dao;
    private ImageButton btnAdd;
    private CustomListAdapter adapter;
    private ListView listView;

    private boolean isPromptUp = false;

    private ArrayList<Task> taskArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        // SAMPLE INPUT, THIS IS ALSO WHERE YOU INITIALIZE DAO AND STUFF I GUESS
        dao = MainActivity.dao;
        dao.taskList = new ArrayList();
        try {
            dao.readFromFile();
        }
        catch (Exception e) {}
        // END SAMPLE
        taskArrayList = new ArrayList<Task>();
        if (dao.taskList.size() != 0) {
            taskArrayList.addAll(dao.taskList);
        }
        /*
            Intent intent=this.getIntent();
            Bundle b=intent.getExtras();
           Integer m= b.getInt("money");
           */

        adapter = new CustomListAdapter(taskArrayList, getActivity());

        listView = (ListView) view.findViewById(R.id.listTask);
        listView.setAdapter(adapter);

        btnAdd = (ImageButton) view.findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(!isPromptUp){
                    isPromptUp = true;

                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View promptView = li.inflate(R.layout.prompt_add, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                    alertDialogBuilder.setView(promptView);

                    final EditText editPIC = (EditText) promptView.findViewById(R.id.editPIC);
                    final EditText editName = (EditText) promptView.findViewById(R.id.editName);
                    final EditText editPassword = (EditText) promptView.findViewById(R.id.editPassword);
                    final EditText editVenue = (EditText) promptView.findViewById(R.id.editVenue);
                    final Button btnDate = (Button) promptView.findViewById(R.id.btnDate);
                    final Calendar date = Calendar.getInstance();

                    btnDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                    new DatePickerDialog.OnDateSetListener() {

                                        @Override
                                        public void onDateSet(DatePicker view, int year,
                                                              int monthOfYear, int dayOfMonth) {
                                            date.set(Calendar.YEAR, year);
                                            date.set(Calendar.MONTH, monthOfYear);
                                            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                            date.set(Calendar.HOUR_OF_DAY, 23);
                                            date.set(Calendar.MINUTE, 59);
                                            date.set(Calendar.SECOND, 59);                                        }
                                    }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                            datePickerDialog.show();
                        }
                    });

                    alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if(Calendar.getInstance().after(date)){ // ADD CONDITIONS HERE
                                Toast.makeText(getActivity(), "Invalid Date!", Toast.LENGTH_SHORT).show();
                            }else if(editPIC.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), "Empty field for person in charge!", Toast.LENGTH_SHORT).show();
                            }else if(editName.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), "Empty field for task name!", Toast.LENGTH_SHORT).show();
                            }else if(editPassword.getText().toString().isEmpty()){
                                Toast.makeText(getActivity(), "Empty field in password!", Toast.LENGTH_SHORT).show();
                            }else{
                                // ADD TASK TO LIST OR WHATEVER
                                Task input = new Task();

                                input.setVenue(editVenue.getText().toString());
                                input.setName(editName.getText().toString());
                                input.setDeadline(date);
                                input.setPassword(editPassword.getText().toString());
                                input.setPIC(editPIC.getText().toString());
                                 dao.taskList.add(input);
                                 Collections.sort(dao.taskList);
                                adapter.addTask(input);


                                Toast.makeText(getActivity(), "Task added!", Toast.LENGTH_SHORT).show();
                            }
                            isPromptUp = false;
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

        return view;
    }
}
