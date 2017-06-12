package usmanali.kinarabakery;

import android.widget.Filter;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 4/15/2017.
 */

public class filterproducts extends Filter {
    ArrayList<products> filterlist;
    showmoreproductsadapter showmoreadapter;
    public filterproducts(ArrayList<products> productsArrayList,showmoreproductsadapter adapter){
        this.filterlist=productsArrayList;
        this.showmoreadapter=adapter;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        if(constraint!=null&&constraint.length()>0){
            constraint=constraint.toString().toUpperCase();
            ArrayList<products> filteredproductslist=new ArrayList<products>();
            for(int i=0;i<filterlist.size();i++){
                if(filterlist.get(i).getProductname().toUpperCase().contains(constraint)){
                    filteredproductslist.add(filterlist.get(i));
                }
            }
            results.count=filteredproductslist.size();
            results.values=filteredproductslist;
        }else{
            results.values=filterlist;
            results.count=filterlist.size();
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        showmoreadapter.allproductslist=(ArrayList<products>)filterResults.values;
        showmoreadapter.notifyDataSetChanged();
    }
}
