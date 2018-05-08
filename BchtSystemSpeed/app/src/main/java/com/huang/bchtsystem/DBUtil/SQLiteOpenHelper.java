package com.huang.bchtsystem.DBUtil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/12 0012.
 */

public class SQLiteOpenHelper  extends android.database.sqlite.SQLiteOpenHelper {

    // 定义一个SQLiteDatabase对象，对表进行相应的操作
    private SQLiteDatabase database;

    public SQLiteOpenHelper(Context context) {
        super(context, UserUtil.DATABASE_NAME, null,
                UserUtil.DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql语句
        String sql = "create table " + UserUtil.TABLE_NAME + "("
                + UserUtil.ID + " integer primary key,"
                + UserUtil.USER_NAME + " text not null,"
                + UserUtil.USER_INFO + " text not null,"
                + UserUtil.USER_TYPE + " text not null,"
                + UserUtil.USER_PWD + " text not null) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    /**
     * 添加数据
     */
    public boolean insertData(ContentValues cv){
        return database.insert(UserUtil.TABLE_NAME, null, cv)>0;
    }
    /**
     *数据的删除
     */
    public void delete(int position){
        if (position >1){
            database.delete(UserUtil.TABLE_NAME, "Id = ?", new String[]{Integer.toString(position)});
        }
    }

    /**
     *数据的修改
     */
    public void update(ContentValues values ,int position){
        database.update(UserUtil.TABLE_NAME,values,"Id = ?", new String[] {Integer.toString(position)});
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<User> queryData(){
        List<User> userlist=new ArrayList<User>();
        //从数据库里查询数据
        Cursor cursor=database.query(UserUtil.TABLE_NAME, null, null, null, null, null, null);
        if(cursor!=null){
            //取出数据
            while (cursor.moveToNext()) {
                User user=new User();
                user.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                user.setInfo(cursor.getString(cursor.getColumnIndex("info")));
                user.setType(cursor.getString(cursor.getColumnIndex("type")));
                user.setPwd(cursor.getString(cursor.getColumnIndex("pwd")));
                userlist.add(user);
            }
        }
        return userlist;
    }
}
