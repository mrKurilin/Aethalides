package com.mrkurilin.aethalides.data.repository.roomRepositories

import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.room.PointsDao
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity

class PointsRoomRepository(
    private val pointsDao: PointsDao
) : PointsRepository {

    override fun addPoint(point: Point) {
        pointsDao.addPoint(PointRoomEntity.fromPoint(point))
    }

    override fun getPointsListByDate(epochDay: Long): List<Point> {
        return pointsDao.getPointsListByDate(epochDay).map { pointRoomEntity ->
            pointRoomEntity.toPoint()
        }
    }

    override fun updatePoint(point: Point) {
        pointsDao.updatePoint(PointRoomEntity.fromPoint(point))
    }

    override fun deletePoint(point: Point) {
        pointsDao.deletePoint(PointRoomEntity.fromPoint(point))
    }
}