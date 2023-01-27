package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.Flow

interface PointsRepository {

    fun addPoint(point: Point): Unit

    fun getPointsListByDate(epochDay: Long): List<Point>

    fun updatePoint(point: Point): Unit

    fun deletePoint(point: Point): Unit

    fun getAllPlanEpochDaysFromDb(): List<Long>

    fun getAllPointsColorsByEpochDay(epochDay: Long): List<Int>

    fun deletePointByTag(tag: String): Unit

    fun getAllPointsFlow(): Flow<List<Point>>

    fun getAllPointsByEpochDay(epochDay: Long): List<Point>

    suspend fun getEpochDaysToPointsMapFlow(): Flow<Map<Long, List<Point>>>

    suspend fun getEpochDaysToPointsColorsMapFlow(): Flow<Map<Long, List<Int>>>
}