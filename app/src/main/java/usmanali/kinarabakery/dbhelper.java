package usmanali.kinarabakery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAJIDCOMPUTERS on 4/8/2017.
 */

public class dbhelper extends SQLiteOpenHelper {
    Context c;
    String Table_Name="shoppingcart";
    String[] pricecolumn={"price"};
    String[] columns={"id","productname","price","image","quantity","Username"};
    String creat_table="create table shoppingcart (id integer primary key autoincrement,productname text,price integer,image text,quantity integer,Username text);";
    public dbhelper(Context context) {
        super(context,"shoppingcartdb", null, 1);
        this.c=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
sqLiteDatabase.execSQL(creat_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS shoppingcart");
        onCreate(sqLiteDatabase);
    }
    public Boolean insert_product_toshoppingcart(String productname,int price,String imageurl,int quantity,String Username){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("productname ",productname);
        cv.put("price ",price);
        cv.put("image",imageurl);
        cv.put("quantity",quantity);
        cv.put("Username",Username);
       long i= db.insert("shoppingcart",null,cv);
        if(i==-1){
            return false;
        }else{
            return true;
        }

    }
    public  Cursor get_products_in_cart(String Username)throws SQLException {
        SQLiteDatabase db= getReadableDatabase();
        Cursor products=db.query("shoppingcart",columns,"Username = ?",new String[]{Username},null,null,null,null);
        return products;
    }
    public int get_num_of_rows(){
        SQLiteDatabase db=getReadableDatabase();
        int numberofrows=(int) DatabaseUtils.queryNumEntries(db,"shoppingcart");
        return numberofrows;
    }

    public int getTotalOfAmount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT SUM(price) FROM " + Table_Name, null);
        c.moveToFirst();
        int i = c.getInt(0);
        c.close();
        return i;
    }
    public Integer delete(String id){
        SQLiteDatabase sdb = this.getReadableDatabase();
        return sdb.delete("shoppingcart", "id = ?", new String[] {id});
    }
    public void delete_all(String Username){
        SQLiteDatabase sdb = this.getReadableDatabase();
         sdb.delete("shoppingcart", "Username = ?", new String[] {Username});
    }

}
