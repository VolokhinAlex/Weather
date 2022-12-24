package com.example.java.android1.weather.room

import androidx.room.*

@Dao
interface CityDao {

    @Query("SELECT * FROM city_table WHERE city LIKE :city")
    fun getCityByName(city: String): CityEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CityEntity)

}