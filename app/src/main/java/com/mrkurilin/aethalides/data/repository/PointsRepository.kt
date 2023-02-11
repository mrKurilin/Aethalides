package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.Flow

interface PointsRepository {

    fun getAllPoints(): List<Point>

    fun addPoint(point: Point): Unit

    fun updatePoint(point: Point): Unit

    fun deletePoint(point: Point): Unit

    fun deletePointsByTag(tag: String): Unit

    suspend fun getEpochDaysToPointsMapFlow(): Flow<Map<Long, List<Point>>>

    suspend fun getEpochDaysToPointsColorsMapFlow(): Flow<Map<Long, List<Int>>>

    fun getPointsListFlowByEpochDay(epochDay: Long): Flow<List<Point>>
}