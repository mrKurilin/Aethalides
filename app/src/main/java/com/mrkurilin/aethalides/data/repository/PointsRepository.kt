package com.mrkurilin.aethalides.data.repository

import com.mrkurilin.aethalides.data.model.Point

interface PointsRepository {

    fun addPoint(point: Point): Unit

    fun getPointsListByDate(epochDay: Long): List<Point>

    fun updatePoint(point: Point): Unit

    fun deletePoint(point: Point): Unit
}