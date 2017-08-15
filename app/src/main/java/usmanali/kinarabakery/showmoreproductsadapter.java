package usmanali.kinarabakery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 4/13/2017.
 */

public class showmoreproductsadapter extends RecyclerView.Adapter<showmoreproductsviewholder>implements Filterable {
  ArrayList<products> allproductslist;
    Context context;
    filterproducts filter;
    ArrayList<products>tobefilteredlist;

    public showmoreproductsadapter(ArrayList<products> allproductslist,Context context){
        this.allproductslist=allproductslist;
        this.context=context;
        this.tobefilteredlist=allproductslist;
    }
    public showmoreproductsadapter(){}
    @Override
    public showmoreproductsviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.moreproductslayout,parent,false);
        return new showmoreproductsviewholder(v);
    }

    @Override
    public void onBindViewHolder(showmoreproductsviewholder holder, int position) {
        final products product=allproductslist.get(position);
        Picasso.with(context).load(product.getImage()).into(holder.productimage);
        holder.name.setText(product.getProductname());
        holder.price.setText("Rs "+product.getPrice());
        holder.productcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,productdetailActivity.class);
                intent.putExtra("productimage",product.getImage());
                ActivityOptionsCompat optionsCompat= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,null);
                intent.putExtra("price",product.getPrice());
                intent.putExtra("name",product.getProductname());
                intent.putExtra("catorgery",product.getCatorgery());
                intent.putExtra("Productid",product.getId());
                intent.putExtra("Quantity",product.getQuantity());
                intent.putExtra("Weight",product.getWeight());
                context.startActivity(intent,optionsCompat.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return allproductslist.size();
    }
    public void setFilter(ArrayList<products> productsArrayList){
        allproductslist.addAll(productsArrayList);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter=new filterproducts(tobefilteredlist,this);
        }
        return filter;
    }
}
class showmoreproductsviewholder extends RecyclerView.ViewHolder{
    TextView name,price;
    ImageView productimage;
    CardView productcard;
    public showmoreproductsviewholder(View itemView) {
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.productname);
        price=(TextView)itemView.findViewById(R.id.price);
        productimage=(ImageView)itemView.findViewById(R.id.productimage);
        productcard=(CardView)itemView.findViewById(R.id.productcard);
    }

}
