package com.alaory.wallmewallpaper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class database(val context: Context) : SQLiteOpenHelper(context, Image_info_database,null, database_version) {

    companion object{
        var Image_info_database = "Image_info.dp";
        val database_version: Int = 1 ;
        var table_name = "image_info_list"

        val name = "name";
        val auther = "auther";
        val url = "url";
        val thumbnail = "thumbnail";
        val title = "title";
        val post_source = "source"
        val width = "width";
        val height = "height";
    }

    override fun onCreate(dp: SQLiteDatabase?) {
        //create sql table and add image_info coloums
        val sql_query_createtable = "CREATE TABLE $table_name ($name TEXT,$auther TEXT,$url TEXT,$thumbnail TEXT,$title TEXT,$post_source TEXT,$width INTEGER,$height INTEGER);"
        dp!!.execSQL(sql_query_createtable);
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        //i am a potato ;0
    }

    fun add_image_info_to_database(image_info: Image_Info){
        val dp = this.writableDatabase;
        val CV = ContentValues();

        //set column data
        CV.put(name,image_info.Image_name);
        CV.put(auther,image_info.Image_auther);
        CV.put(url,image_info.Image_url);
        CV.put(thumbnail,image_info.Image_thumbnail);
        CV.put(title,image_info.Image_title);
        CV.put(post_source,image_info.post_url);
        CV.put(width,image_info.imageRatio!!.Width);
        CV.put(height,image_info.imageRatio!!.Height);

        val result = dp.insert(table_name,null,CV);

        if(result.toInt() == -1)
            Toast.makeText(context,"Image database save failed ;(",Toast.LENGTH_LONG).show();
    }

    fun read_image_info_list_from_database(): Array<Image_Info>{
        var imageinfo_lits : Array<Image_Info> = emptyArray();

        val request_imginfo = "SELECT * FROM $table_name";
        val dp = this.readableDatabase;
        val curser = dp.rawQuery(request_imginfo,null);

        while (curser.moveToNext()){
            val imageInfo = Image_Info(
                curser.getString(2),
                curser.getString(3),
                curser.getString(0),
                curser.getString(1),
                curser.getString(4),
                curser.getString(5),
                Image_Ratio(curser.getInt(6),curser.getInt(7))
                );
            imageinfo_lits += imageInfo;
        }
        return  imageinfo_lits;
    }
}