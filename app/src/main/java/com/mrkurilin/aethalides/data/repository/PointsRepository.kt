package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Point
import kotlinx.coroutines.flow.Flow

interface PointsRepository {

    fun addPoint(point: Point): Unit

    fun getPointsListByDate(epochDay: Long): List<Point>

    fun updatePoint(point: Point): Unit

    fun deletePoint(point: Point): Unit

    fun getAllPlanDatesFromDb(): Flow<List<Long>>

    fun getAllPointsColorsByEpochDay(epochDay: Long): List<Int>

    fun deletePointByTag(tag: String): Unit
}