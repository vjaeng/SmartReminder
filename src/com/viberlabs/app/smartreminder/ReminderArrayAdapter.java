package com.viberlabs.app.smartreminder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ReminderArrayAdapter extends ArrayAdapter<Reminder> {
	private String TAG = ReminderArrayAdapter.class.getName();	
	
	private List<Reminder> reminderList;
	private LayoutInflater mInflater;

	public ReminderArrayAdapter(Context context, int resource, List<Reminder> reminderList) {
		//,	int textViewResourceId, 
		super(context, resource, reminderList);
		//resource, textViewResourceId, 
		// TODO Auto-generated constructor stub
		this.reminderList = reminderList;
		mInflater = LayoutInflater.from(context);
		Log.d(TAG, "ReminderArrayAdapter.Constructor");
	}
	/**
	 * 
	 * @param reminderList
	 */
    public void changeData(List<Reminder> reminderList) {
    	//this.reminderList = reminderList;

        //notifyDataSetChanged();
    	this.reminderList.clear();
    	this.reminderList.addAll(reminderList);
    	Log.d(TAG, "ReminderArrayAdapter.changeData " + this.reminderList.size());    	
        notifyDataSetChanged();    	
    }	

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "Entrando a getView");
    	if (reminderList == null || reminderList.size() == 0 ){
    		return null;
    	} else{	
	    ViewHolder holder;

//        if (convertView == null) {
//            convertView = getLayoutInflater().inflate(R.layout.row, parent, false);


//        ((TextView) convertView).setText(getItem(position));
//        ((TextView) convertView).s.setText(getItem(position));
        
        if (convertView == null) {		

		      
        	convertView = mInflater.inflate(R.layout.row, null);	

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();			
//			if (event != null){
	            holder.name = (TextView) convertView.findViewById(R.id.name);
	            holder.repeat = (TextView) convertView
				.findViewById(R.id.repeat);			
	            holder.date = (TextView) convertView.findViewById(R.id.date);
	            holder.type = (TextView) convertView.findViewById(R.id.type);
	            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
	            
	            
//			}    
		            convertView.setTag(holder);							
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }    
        //
        Log.d(TAG, "getView: Obteniendo Reminder en posicion :" + position);
        Reminder reminder = (Reminder) reminderList.get(position);
        
		if (holder.name != null) {
			holder.name.setText(reminder.getName());
			
		}	    
		if (holder.type != null) {
			holder.type.setText("Type:" + String.valueOf(reminder.getType()));
			
		}
//		if (holder.repeat != null) {
//			holder.repeat.setText("Repeat:" + reminder.getRepeat().toString());
//
//		}
		
//		if (holder.date != null) {
//			java.text.DateFormat mTitleDateFormat = java.text.DateFormat
//			.getDateInstance(java.text.DateFormat.FULL);
//
//			holder.date.setText(mTitleDateFormat.format(reminder.getDueDate()));
//
//		}

		
		if (holder.icon != null){
			 
//			if (getItem(position).toString().startsWith("Sha")){
//				holder.icon.setImageResource(R.drawable.birthday);
//			} 	
//			else if (getItem(position).toString().startsWith("Take")){
//					holder.icon.setImageResource(R.drawable.birthday);					
//			}
//			switch (reminder.getType()) {
//			case 3:
//				holder.icon.setImageResource(R.drawable.birthday);
//				break;
//			case 2:
//				holder.icon.setImageResource(R.drawable.mail);
//				break;
//			case 1:
//				holder.icon.setImageResource(R.drawable.phone_call);
//				break;
//			case 0:
//				holder.icon.setImageResource(R.drawable.ic_launcher);
//				break;
//			case 4:
//				holder.icon.setImageResource(R.drawable.medicine);
//				break;
//			}
			//GENERIC, PHONE_CALL, EMAIL, BIRTHDAY, PRESCRIPTION
//			holder.icon.setImageResource(R.drawable.phone_call);
		}
        
//		if (position % 2 == 0){
//			convertView.setBackgroundColor(
//			getResources().getColor(R.color.row_color1));
//		} else {
//			convertView.setBackgroundColor(
//					getResources().getColor(R.color.row_color2));				
//		}	        
        
        return convertView;
    }
    }
	class ViewHolder {       
		TextView name;
		TextView date;
		TextView repeat;
		TextView type;
		ImageView icon;	
	}           

}
