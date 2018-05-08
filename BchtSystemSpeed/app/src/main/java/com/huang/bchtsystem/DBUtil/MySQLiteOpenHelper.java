package com.huang.bchtsystem.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    // 定义一个SQLiteDatabase对象，对表进行相应的操作
    private SQLiteDatabase mDatabase;

    public MySQLiteOpenHelper(Context context) {
        super(context, ConstantUtil.DATABASE_NAME, null,
                ConstantUtil.DATABASE_VERSION);
        mDatabase = getWritableDatabase();
    }

    /*
     *  创建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        //sql语句
        String sql = "create table " + ConstantUtil.TABLE_NAME + "("
                + ConstantUtil.USER_ID + " integer primary key,"
                + ConstantUtil.USER_CARNUM + " text not null,"
                + ConstantUtil.USER_COLOR + " text not null,"
                + ConstantUtil.USER_ADDRESS + " text not null,"
                + ConstantUtil.USER_TYPE + " text not null) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        onCreate(db);
    }

    /**
     * 添加数据
     * @param cv
     * @return
     */
    public boolean insertData(ContentValues cv){
        return mDatabase.insert(ConstantUtil.TABLE_NAME, null, cv)>0;
    }

    /**
     * 查询部分数据
     * @return
     */
    public List<BlackInfo> queryData(String msg){

        List<BlackInfo> userinfos=new ArrayList<BlackInfo>();
        //从数据库里查询数据
//        Cursor cursor=mDatabase.query(ConstantUtil.TABLE_NAME, null, null, null, null, null, null);
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM "+ ConstantUtil.TABLE_NAME+" WHERE carnum = '"+msg+"';", null);
        if(cursor!=null){
            //取出数据
            while (cursor.moveToNext()) {
                BlackInfo userinfo=new BlackInfo();
                userinfo.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
                userinfo.setCarnum(cursor.getString(cursor.getColumnIndex("carnum")));
                userinfo.setColor(cursor.getString(cursor.getColumnIndex("color")));
                userinfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                userinfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                userinfos.add(userinfo);
            }
        }
        return userinfos;
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<BlackInfo> queryData(){

        List<BlackInfo> userinfos=new ArrayList<BlackInfo>();
        //从数据库里查询数据
        Cursor cursor=mDatabase.query(ConstantUtil.TABLE_NAME, null, null, null, null, null, null);
//        Cursor cursor = mDatabase.rawQuery("SELECT * FROM "+ConstantUtil.TABLE_NAME+" WHERE carnum = '"+msg+"';", null);
        if(cursor!=null){
            //取出数据
            while (cursor.moveToNext()) {
                BlackInfo userinfo=new BlackInfo();
                userinfo.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
                userinfo.setCarnum(cursor.getString(cursor.getColumnIndex("carnum")));
                userinfo.setColor(cursor.getString(cursor.getColumnIndex("color")));
                userinfo.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                userinfo.setType(cursor.getString(cursor.getColumnIndex("type")));
                userinfos.add(userinfo);
            }

        }
        return userinfos;
    }

    /**
     *数据的删除
     */
    public void delete(int position){
        if (position >2){
            mDatabase.delete(ConstantUtil.TABLE_NAME, "userId = ?", new String[]{Integer.toString(position)});
        }
    }

    /**
     *数据的修改
     */
    public void update(ContentValues values ,int position){
        mDatabase.update(ConstantUtil.TABLE_NAME,values,"userId = ?", new String[] {Integer.toString(position)});
    }

}
