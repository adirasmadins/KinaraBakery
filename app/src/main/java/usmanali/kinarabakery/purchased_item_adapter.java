package usmanali.kinarabakery;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 8/9/2017.
 */

public class purchased_item_adapter extends RecyclerView.Adapter<purchaseditemsviewholder> {
    ArrayList<products> checkout_items_list;
    public purchased_item_adapter(ArrayList<products> checkout_items_list) {
        this.checkout_items_list = checkout_items_list;
    }
    @Override
    public purchaseditemsviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.purchased_item_layout,parent,false);
        return new purchaseditemsviewholder(v);
    }

    @Override
    public void onBindViewHolder(purchaseditemsviewholder holder, int position) {
     products checkout_items=checkout_items_list.get(position);
        holder.checkout_item_name.setText(checkout_items.getProductname());
        holder.checkout_item_quantity.setText(String.valueOf(checkout_items.getQuantity())+"x");
        holder.check_out_item_price.setText("Rs"+String.valueOf(checkout_items.getPrice()));
    }

    @Override
    public int getItemCount() {
        return checkout_items_list.size();
    }
}
class purchaseditemsviewholder extends RecyclerView.ViewHolder{
TextView checkout_item_name,check_out_item_price,checkout_item_quantity;
    public purchaseditemsviewholder(View itemView) {
        super(itemView);
        checkout_item_name=(TextView) itemView.findViewById(R.id.checkout_item_name);
        checkout_item_quantity=(TextView) itemView.findViewById(R.id.checkout_item_quantity);
        check_out_item_price=(TextView) itemView.findViewById(R.id.checkout_item_price);
    }
}
