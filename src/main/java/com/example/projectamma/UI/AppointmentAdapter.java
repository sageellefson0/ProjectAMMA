package com.example.projectamma.UI;

/* Imports */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projectamma.R;
import com.example.projectamma.entities.Appointment;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    /** ViewHolder that sets each item in the appointment list. */
class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView appointmentNameView;
        private final TextView appointmentDateTimeView;

    /** Constructor for the ViewHolder.
     * @param itemView View for the itemView in the recycler. */
    private AppointmentViewHolder(View itemView){
        super(itemView);
        appointmentNameView = itemView.findViewById(R.id.textViewAppointmentName);
        appointmentDateTimeView = itemView.findViewById(R.id.textViewAppointmentDateTime);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getBindingAdapterPosition();
                final Appointment current = mAppointments.get(position);
                Intent intent = new Intent(context, AppointmentDetails.class);
                intent.putExtra("appointmentID", current.getAppointmentID());
                intent.putExtra("appointmentType", current.getAppointmentType());
                intent.putExtra("appointmentDescription", current.getAppointmentDescription());
                intent.putExtra("appointmentLocation", current.getAppointmentLocation());
                intent.putExtra("appointmentDate", current.getAppointmentDate());
                intent.putExtra("appointmentStartTime", current.getAppointmentStartTime());
                intent.putExtra("appointmentEndTime", current.getAppointmentEndTime());
                context.startActivity(intent);
                }
            });
        }
    }

    public List<Appointment> getAppointments() {
        return mAppointments;
    }
    /** List of appointments. */
    private List<Appointment> mAppointments;
    private final Context context;
    private final LayoutInflater mInflater;

    /** Constructor for the AppointmentAdapter.
     * @param context The context for the activity. */
    public AppointmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    /** Called when RecyclerView needs a new view holder.
     * @param parent The ViewGroup the new view will be added to.
     * @param viewType The view type.
     * @return A new ViewHolder that holds a view of the view type. */
    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.appointment_list_item,parent,false);
        return new AppointmentViewHolder(itemView);
    }

    /** Called by RecyclerView to display the data.
     * @param holder The ViewHolder that should be updated.
     * @param position The position of the item. */
    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewHolder holder, int position) {
        if(mAppointments!=null){
            Appointment current=mAppointments.get(position);
            String name=current.getAppointmentType();
            String dateTimeStamp = current.getAppointmentDate() + " " + current.getAppointmentStartTime();

            holder.appointmentNameView.setText(name);
            holder.appointmentDateTimeView.setText(dateTimeStamp);

        }
        else {
            holder.appointmentNameView.setText("No appointment title");
            holder.appointmentDateTimeView.setText("No appointment date");
        }
    }

    /** Sets the appointments list.
     * @param appointments The list of appointments. */
    public void setAppointments(List<Appointment> appointments){
        mAppointments = appointments;
        notifyDataSetChanged();
    }

    /** Gets the item count of mAppointments. */
    @Override
    public int getItemCount() {
        return mAppointments.size();
    }

}


