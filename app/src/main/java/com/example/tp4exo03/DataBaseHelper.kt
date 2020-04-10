package com.example.tp4exo03

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf
import java.security.AccessControlContext

val DATABASE_NAME="MyDB"
val TABLE_NAME="Tasks"
val COL_NAME = "NAME"
val COL_DATE = "DATE"
val COL_ID="ID"

class DataBaseHelper(var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,1) {


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable="CREATE TABLE "+ TABLE_NAME +" (" +
                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_NAME+" VARCHAR(256)," +
                COL_DATE+" VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    // fonction pour inserer les données
    fun insertData(task: Task)
    {
        val db = this.writableDatabase
        var cv=ContentValues()
        cv.put(COL_NAME,task.name)
        cv.put(COL_DATE,task.date)
        var result = db.insert(TABLE_NAME,null,cv)
        if (result== -1.toLong())
            Toast.makeText(context,"Failed to add task",Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context,"successfully added",Toast.LENGTH_SHORT).show()
    }
    // fonction pour lire les données
    fun readData():MutableList<Task>
    {
        var list :MutableList<Task> = ArrayList()
        val db =this.readableDatabase
        val query = "SELECT * FROM "+ TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst())
        {
            do{
                var task = Task()
                task.id=result.getString(result.getColumnIndex(COL_ID)).toInt()
                task.name=result.getString(result.getColumnIndex(COL_NAME))
                task.date=result.getString(result.getColumnIndex(COL_DATE))
                list.add(task)
            }while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }
    // fonction pour supprimer les données
    fun deleteData(task: Task)
    {

        val db =this.writableDatabase
        db.delete(TABLE_NAME, COL_ID +"="+task.id,null)
        db.close()

    }
    // fonction pour modifier les données
    fun updateData()
    {
        val db =this.writableDatabase
        val query = "SELECT * FROM "+ TABLE_NAME
        val result = db.rawQuery(query,null)
        if(result.moveToFirst())
        {
            do{
                var name = ContentValues()
                var date = ContentValues()

                name.put(COL_NAME,result.getString(result.getColumnIndex(COL_NAME)))
                date.put(COL_DATE,result.getString(result.getColumnIndex(COL_DATE)))

                db.update(TABLE_NAME,name, COL_ID+ "=? AND "+ COL_NAME+ "=?",
                    arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                            result.getString(result.getColumnIndex(COL_NAME))))
                db.update(TABLE_NAME,date, COL_ID+ "=? AND "+ COL_NAME+ "=?",
                    arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                        result.getString(result.getColumnIndex(COL_DATE))))

            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }

}
