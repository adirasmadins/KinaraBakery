package usmanali.kinarabakery;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by SAJIDCOMPUTERS on 5/1/2017.
 */

public class carouseladapter extends PagerAdapter {
    Context con;
    ArrayList<Integer> images;
    public carouseladapter(Context context,ArrayList<Integer> imageslist){
        this.con=context;
        this.images=imageslist;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.carouellayout,null);
        ImageView image=(ImageView) v.findViewById(R.id.carouselimages);
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tabDots);
        image.setImageResource(images.get(position));
        ViewPager vp=(ViewPager)container;
        tabLayout.setupWithViewPager(vp, true);
        vp.addView(v);
        return v;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager=(ViewPager) container;
        View v=(View) object;
        viewPager.removeView(v);
    }
}
