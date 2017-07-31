package usmanali.kinarabakery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 7/31/2017.
 */

public class show_orders_adapter extends RecyclerView.Adapter<ordersviewholder> {
    ArrayList<orders> ordersArrayList;
    Context context;

    public show_orders_adapter(ArrayList<orders> ordersArrayList, Context context) {
        this.ordersArrayList = ordersArrayList;
        this.context = context;
    }

    @Override
    public ordersviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_orders_layout,parent,false);
        return new ordersviewholder(v);
    }

    @Override
    public void onBindViewHolder(ordersviewholder holder, int position) {
       orders order=ordersArrayList.get(position);
        holder.orderid.setText("Order id:"+String.valueOf(order.getOrderid()));
        holder.OrderDate.setText(order.getOrderdatetime());
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }
}
class ordersviewholder extends RecyclerView.ViewHolder {
    TextView orderid,OrderDate;
    public ordersviewholder(View itemView) {
        super(itemView);
        orderid=(TextView) itemView.findViewById(R.id.orderid);
        OrderDate=(TextView) itemView.findViewById(R.id.orderdate);
    }
}