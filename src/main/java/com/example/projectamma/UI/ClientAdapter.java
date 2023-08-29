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
import com.example.projectamma.entities.Client;
import com.example.projectamma.entities.CorporateClient;
import com.example.projectamma.entities.IndividualClient;
import java.util.List;


/** RecyclerView adapter for displaying the client list. */
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ClientViewHolder> {

    /** ViewHolder that sets each item in the client list. */
    class ClientViewHolder extends RecyclerView.ViewHolder {
        private final TextView clientItemView;

        /** Constructor for the ViewHolder.
         * @param itemView View for the itemView in the recycler. */
        private ClientViewHolder(View itemView){
            super(itemView);
            clientItemView=itemView.findViewById(R.id.textViewClientItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    final Client current = mClients.get(position);
                    Intent intent = new Intent(context, ClientDetails.class);

                    if (current instanceof IndividualClient) {
                        intent.putExtra("clientOccupation", ((IndividualClient) current).getClientOccupation());
                        intent.putExtra("clientID", current.getClientID());
                        intent.putExtra("clientName", current.getClientName());
                        intent.putExtra("clientEmail", current.getClientEmail());
                        intent.putExtra("clientNumber", current.getClientNumber());
                        intent.putExtra("clientAddress", current.getClientAddress());
                    } else if (current instanceof CorporateClient) {
                        intent.putExtra("clientIndustry", ((CorporateClient) current).getClientIndustry());
                        intent.putExtra("clientID", current.getClientID());
                        intent.putExtra("clientName", current.getClientName());
                        intent.putExtra("clientEmail", current.getClientEmail());
                        intent.putExtra("clientNumber", current.getClientNumber());
                        intent.putExtra("clientAddress", current.getClientAddress());
                    }
                    context.startActivity(intent);
                }
            });
        }
    }


    /** List of client. */
    private List<Client> mClients;
    private final Context context;
    private final LayoutInflater mInflater;

    /** Constructor for the ClientAdapter.
     * @param context The context for the activity. */
    public ClientAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }


    /** Called when RecyclerView needs a new view holder.
     * @param parent The ViewGroup the new view will be added to.
     * @param viewType The view type.
     * @return A new ViewHolder that holds a view of the view type. */
    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.client_list_item,parent,false);
        return new ClientViewHolder(itemView);
    }


    /** Called by RecyclerView to display the data.
     * @param holder The ViewHolder that should be updated.
     * @param position The position of the item. */
    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ClientViewHolder holder, int position) {
        if(mClients!=null){
            Client current=mClients.get(position);
            String name=current.getClientName();
            holder.clientItemView.setText(name);
        }
        else {
            holder.clientItemView.setText("No client title");
        }
    }


    /** Sets the client list.
     * @param clients The list of clients. */
    public void setClients(List<Client> clients){
        mClients=clients;
        notifyDataSetChanged();
    }

    /** Gets the item count of mClients. */
    @Override
    public int getItemCount() {
        return mClients.size();
    }

}


