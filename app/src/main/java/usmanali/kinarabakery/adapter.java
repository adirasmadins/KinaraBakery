package usmanali.kinarabakery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by SAJIDCOMPUTERS on 3/7/2017.
 */

public class adapter extends RecyclerView.Adapter<viewholder> {
    ArrayList<products> productslist;Context c;
    public adapter(ArrayList<products> listofproducts,Context ctx){
        this.productslist=listofproducts;
        this.c=ctx;
    }
    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout,parent,false);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
final products product=productslist.get(position);
        Picasso.with(c).load(product.getImage()).into(holder.productimage);
        holder.name.setText(product.getProductname());
        holder.price.setText("Rs "+product.getPrice());
        holder.productcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) c,null);
                Intent intent=new Intent(c,productdetailActivity.class);
                intent.putExtra("productimage",product.getImage());
                intent.putExtra("price",product.getPrice());
                intent.putExtra("name",product.getProductname());
                intent.putExtra("catorgery",product.getCatorgery());
                intent.putExtra("Productid",product.getId());
                intent.putExtra("Quantity",product.getQuantity());
                c.startActivity(intent,optionsCompat.toBundle());
            }
        });
    }
    @Override
    public int getItemCount() {
        return productslist.size();
    }
}
class viewholder extends RecyclerView.ViewHolder{
TextView name,price;
    ImageView productimage;
    CardView productcard;
    public viewholder(View itemView) {

        super(itemView);
        name=(TextView)itemView.findViewById(R.id.productname);
        price=(TextView)itemView.findViewById(R.id.price);
        productimage=(ImageView)itemView.findViewById(R.id.productimage);
        productcard=(CardView)itemView.findViewById(R.id.productcard);
    }
}