package com.daoyu.niuqun.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.daoyu.niuqun.bean.AreaBean;

/**
 * 数据库操作
 */

public class DataBaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DataBaseHelper";

    private static final String DB_PATH = "/data/data/com.daoyu.niuqun/databases/";

    private static final String DB_NAME = "shop_area.sqlite";

    private static final String TABLE_NAME = "shop_area";

    private SQLiteDatabase myDataBase;

    private Context myContext;

    public DataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException
    {
        boolean dbExist = checkDataBase();
        if (dbExist)
        {
            Logger.d(TAG, "createDataBase, is have!");
        }
        else
        {
            this.getReadableDatabase();
            try
            {
                copyDataBase();
            }
            catch (IOException e)
            {
                throw new Error(TAG + ", Error copying database");
            }
        }
    }

    private boolean checkDataBase()
    {
        SQLiteDatabase checkDB = null;
        try
        {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e)
        {
            Logger.d(TAG, "checkDataBase, SQLiteException: " + e);
        }

        if (checkDB != null)
        {
            checkDB.close();
        }
        return checkDB != null;
    }

    private void copyDataBase() throws IOException
    {
        Logger.d(TAG, "copyDataBase, start!");
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Logger.d(TAG, "copyDataBase, over!");
    }

    public void openDataBase() throws SQLException
    {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        if (null != myDataBase)
        {
            Logger.d(TAG, "openDataBase is success!");
        }
    }

    public List<AreaBean> getProvinces()
    {
        List<AreaBean> provinces = new ArrayList<>();
        if (null == myDataBase)
        {
            Logger.d(TAG, "getProvinces, db is null!");
            return provinces;
        }
        String selectStr = "SELECT province_number , province_name FROM " + TABLE_NAME + " GROUP BY province_name";
        Cursor cursor = myDataBase.rawQuery(selectStr, null);
        Logger.d(TAG, "cursor size: " + cursor.getCount());
        int size = cursor.getCount();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                cursor.moveToPosition(i);
                String name = cursor.getString(cursor.getColumnIndex("province_name"));
                String id = cursor.getString(cursor.getColumnIndex("province_number"));
                AreaBean areaBean = new AreaBean();
                areaBean.setId(id);
                areaBean.setName(name);
                provinces.add(areaBean);
            }
            Logger.d(TAG, "provinces number: " + provinces.size());
            return provinces;
        }
        return provinces;
    }

    public List<AreaBean> getCitiesByProvince(String provinceId)
    {
        List<AreaBean> cities = new ArrayList<>();
        if (null == myDataBase)
        {
            Logger.d(TAG, "getCitiesByProvince, db is null!");
            return cities;
        }
        //此处的db是表名，name是列名
        String selectStr = "SELECT city_number , city_name FROM " + TABLE_NAME + " WHERE province_number = ? GROUP BY city_name";
        Cursor cursor = myDataBase.rawQuery(selectStr, new String[] {provinceId });
        Logger.d(TAG, "cursor size: " + cursor.getCount());
        int size = cursor.getCount();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                cursor.moveToPosition(i);
                String name = cursor.getString(cursor.getColumnIndex("city_name"));
                String id = cursor.getString(cursor.getColumnIndex("city_number"));
                AreaBean areaBean = new AreaBean();
                areaBean.setId(id);
                areaBean.setName(name);
                cities.add(areaBean);
            }
            Logger.d(TAG, "provinces number: " + cities.size());
            return cities;
        }
        return cities;
    }

    public List<AreaBean> getAreasByCity(String areaId)
    {
        List<AreaBean> areas = new ArrayList<>();
        if (null == myDataBase)
        {
            Logger.d(TAG, "getAreasByCity, db is null!");
            return areas;
        }
        //此处的db是表名，name是列名
        String selectStr = "SELECT area_number , area_name FROM " + TABLE_NAME + " WHERE city_number = ? GROUP BY area_name";
        Cursor cursor = myDataBase.rawQuery(selectStr, new String[] {areaId });
        Logger.d(TAG, "cursor size: " + cursor.getCount());
        int size = cursor.getCount();
        if (size > 0)
        {
            for (int i = 0; i < size; i++)
            {
                cursor.moveToPosition(i);
                String name = cursor.getString(cursor.getColumnIndex("area_name"));
                String id = cursor.getString(cursor.getColumnIndex("area_number"));
                AreaBean areaBean = new AreaBean();
                areaBean.setId(id);
                areaBean.setName(name);
                areas.add(areaBean);
            }
            Logger.d(TAG, "provinces number: " + areas.size());
            return areas;
        }
        return areas;
    }

    @Override
    public synchronized void close()
    {
        if (myDataBase != null)
        {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
