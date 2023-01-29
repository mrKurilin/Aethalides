package com.mrkurilin.aethalides.data.repository.roomRepositories

import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.room.daos.PointsDao
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PointsRoomRepository(
    private val pointsDao: PointsDao
) : PointsRepository {

    override fun getAllPoints(): List<Point> {
        return pointsDao.getAllPointRoomEntities().map { pointRoomEntity ->
            pointRoomEntity.toPoint()
        }
    }

    override fun addPoint(point: Point) {
        pointsDao.addPoint(PointRoomEntity.fromPoint(point))
    }

    override fun updatePoint(point: Point) {
        pointsDao.updatePoint(PointRoomEntity.fromPoint(point))
    }

    override fun deletePoint(point: Point) {
        pointsDao.deletePoint(PointRoomEntity.fromPoint(point))
    }

    override fun deletePointsByTag(tag: String) {
        pointsDao.deletePointsByTag(tag)
    }

    override suspend fun getEpochDaysToPointsMapFlow(): Flow<Map<Long, List<Point>>> {
        return pointsDao.getEpochDaysToPointsRoomEntitiesMapFlow().map { pointRoomEntityMap ->
            pointRoomEntityMap.mapValues { list ->
                list.value.map { it.toPoint() }
            }
        }
    }

    override suspend fun getEpochDaysToPointsColorsMapFlow(): Flow<Map<Long, List<Int>>> {
        return pointsDao.getEpochDaysToColorsMapFlow()
    }
}