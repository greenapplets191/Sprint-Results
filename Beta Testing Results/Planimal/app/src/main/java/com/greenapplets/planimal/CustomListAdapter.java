package com.greenapplets.planimal;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentTransaction;
        import android.text.InputType;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.EditText;
        import android.widget.ImageButton;
        import android.widget.ListAdapter;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Collections;

/**
 * Created by ASUS on 3/14/2015.
 */
public class CustomListAdapter extends BaseAdapter implements ListAdapter, AdapterView.OnClickListener {
     private ArrayList<Task> taskList;
     private Context context;
     private DAO dao = MainActivity.dao;
     private MyPetFile mpf=MainActivity.mpf;

     public CustomListAdapter(ArrayList<Task> taskList, Context context){
          this.taskList = taskList;
          this.context = context;
     }

     public void addTask(Task task){
          this.taskList.add(task);
          Collections.sort(this.taskList);
          dao.writeToFile();
          notifyDataSetChanged();
     }

     @Override
     public int getCount() {
          return taskList.size();
     }

     @Override
     public Object getItem(int position) {
          return taskList.get(position);
     }

     @Override
     public long getItemId(int position) {
          return 0;
     }

     /*
     * ACTUAL STUFF HAPPENING HERE
     * */
     @Override
     public View getView(final int position, View convertView, ViewGroup parent) {
          View view = convertView;
          if (view == null) {
               LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               view = inflater.inflate(R.layout.list_task, null);



          }
          view.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    Dialog dialog = new Dialog(context);

                    dialog.setContentView(R.layout.view_task);

                    TextView viewTaskName = (TextView) dialog.findViewById(R.id.viewTaskName);
                    TextView viewDeadline = (TextView) dialog.findViewById(R.id.viewDeadline);
                    TextView viewPIC = (TextView) dialog.findViewById(R.id.viewPIC);
                    TextView viewVenue = (TextView) dialog.findViewById(R.id.viewVenue);

                    viewTaskName.setText(taskList.get(position).getName());
                    viewDeadline.setText(taskList.get(position).getStringDeadline());
                    viewPIC.setText(taskList.get(position).getPIC());
                    viewVenue.setText(taskList.get(position).getVenue());

                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setTitle("Details");
                    dialog.show();
               }
          });


          final TextView taskName = (TextView) view.findViewById(R.id.taskName);
          taskName.setText(taskList.get(position).getName());

          TextView taskDate = (TextView) view.findViewById(R.id.taskDate);
          taskDate.setText(taskList.get(position).getStringDeadline());

          ImageButton btnDel = (ImageButton) view.findViewById(R.id.btnDel);
          ImageButton btnEdit = (ImageButton) view.findViewById(R.id.btnEdit);
          ImageButton btnComplete = (ImageButton) view.findViewById(R.id.btnComplete);

          btnComplete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Verify completion");

                    // Set up the input
                    final EditText input = new EditText(context);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    alertDialogBuilder.setView(input);

                    // Set up the buttons
                    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              String password = input.getText().toString();

                        /*
                        * COMPARE PASSWORDS                        *
                        * */

                              if(password.compareTo(taskList.get(position).getPassword())==0){
                                   taskList.remove(position);
                                   dao.taskList.remove(position);
                                   dao.writeToFile();
                                   mpf.p.setMoney(mpf.p.getMoney() + 15);
                                   mpf.writePetFile();
                                   notifyDataSetChanged();
                                   Toast.makeText(context, "Task completed! As a reward, 15 moji is added to your money.", Toast.LENGTH_SHORT).show();
                              }else{
                                   Toast.makeText(context, "Invalid password!", Toast.LENGTH_SHORT).show();
                              }
                         }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                         }
                    });

                    alertDialogBuilder.show();

               }
          });

          btnDel.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                /*
                * CONFIRMATION HERE
                * */

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setCancelable(false).setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                /*
                                * ACTUAL DELETE ACTIONS IN HERE
                                * */

                                      dao.taskList.remove(position);
                                      dao.writeToFile();
                                      taskList.remove(position);
                                      notifyDataSetChanged();
                                 }
                            }
                    ).setNegativeButton("NO", new DialogInterface.OnClickListener() {

                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              // Do nothing
                              dialog.dismiss();
                         }
                    }).setMessage("Delete task?");
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
               }
          });

          btnEdit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                /*
                * EDIT ACTIONS DO NOT GO IN HERE
                * BUT OTHER ACTIONS OK
                * please note that whatever changes that happen in dao should happen in the
                * name and date ArrayLists if ever or else the UI won't update
                * */

                    final LayoutInflater li = LayoutInflater.from(context);
                    View promptView = li.inflate(R.layout.prompt_edit, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    alertDialogBuilder.setView(promptView);

                    final EditText editName = (EditText) promptView.findViewById(R.id.editName);
                    final EditText editVenue = (EditText) promptView.findViewById(R.id.editVenue);
                    editName.setText(taskList.get(position).getName());
                    editVenue.setText(taskList.get(position).getVenue());
                    AlertDialog.Builder builder = alertDialogBuilder.setCancelable(false).setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                /*
                                * ACTUAL EDIT ACTIONS GO IN HERE
                                * */
                                      if (!editName.getText().toString().isEmpty()) {
                                           taskList.get(position).setName(editName.getText().toString());
                                           taskList.get(position).setVenue(editVenue.getText().toString());
                                           dao.taskList.get(position).setName(editName.getText().toString());
                                           dao.taskList.get(position).setVenue(editVenue.getText().toString());
                                           dao.writeToFile();
                                           notifyDataSetChanged();
                                           Toast.makeText(context, "Changed task name and venue!", Toast.LENGTH_SHORT).show();
                                      }
                                      else {
                                           Toast.makeText(context, "Empty field for task name!", Toast.LENGTH_SHORT).show();
                                      }
                                 }
                            }
                    ).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                              // Do nothing
                              dialog.dismiss();
                         }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    notifyDataSetChanged();
               }
          });

          return view;
     }

     @Override
     public void onClick(View v) {

     }
}
