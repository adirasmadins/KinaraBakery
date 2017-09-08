package usmanali.kinarabakery;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 4/8/2017.
 */

public class cartitemsadapter extends RecyclerView.Adapter<holder> {
    Context context;
    ArrayList<products> productsArrayList;
   public cartitemsadapter(ArrayList<products> productsArrayList,Context c){
       this.productsArrayList=productsArrayList;
       this.context=c;
   }
    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppingcartlistlayout,parent,false);
        return new holder(v);
    }

    @Override
    public void onBindViewHolder(final holder holder, final int position) {
     final products p=productsArrayList.get(position);
        holder.price.setText("Rs "+p.getPrice());
        Picasso.with(context).load(p.getImage()).into(holder.productimg);
        holder.name.setText(p.getProductname());
        holder.quantity.setText("Quantity x"+p.getQuantity());
        holder.excludefromcartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbhelper mydb = new dbhelper(context);
                new showproducts(context).increment_quantity(String.valueOf(p.getQuantity()),String.valueOf(p.getProduct_id()),context);
                Integer rows = mydb.delete(String.valueOf(p.getId()));
                if (rows > 0) {
                    productsArrayList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(holder.getAdapterPosition(),getItemCount());
                    notifyDataSetChanged();
                }else{
                    Toast.makeText(context,"Item not Removed From Cart",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
}
class holder extends RecyclerView.ViewHolder{
ImageView productimg;
    TextView price,quantity,name;
    Button excludefromcartbtn;
    public holder(View itemView) {
        super(itemView);
        price=(TextView)itemView.findViewById(R.id.productPrice);
        name=(TextView)itemView.findViewById(R.id.productName);
        excludefromcartbtn=(Button)itemView.findViewById(R.id.excludefromcart);
        productimg=(ImageView) itemView.findViewById(R.id.productImage);
        quantity=(TextView) itemView.findViewById(R.id.productquantity);
    }
}
