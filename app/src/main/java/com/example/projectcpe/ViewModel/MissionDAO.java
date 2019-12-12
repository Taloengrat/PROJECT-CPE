package com.example.projectcpe.ViewModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MissionDAO {
    @Query("select * from mission")
    List<Mission> getAllMission();
//
//    @Query("select * from mission")
//    List<Step> getAllStep();

    @Query("select * from mission where id=:id")
    int getDesMission(int id);

    @Query("select * from mission where id=:id")
    List<Mission> getAllinfoOfMission(int id);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(Mission mission);

    @Update
    void update(Mission mission);

    @Delete
    void delete(Mission mission);
}
