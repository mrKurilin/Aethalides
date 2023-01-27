package com.mrkurilin.aethalides.data.repository.roomRepositories

import com.mrkurilin.aethalides.data.model.Point
import com.mrkurilin.aethalides.data.repository.PointsRepository
import com.mrkurilin.aethalides.data.room.PointsDao
import com.mrkurilin.aethalides.data.room.entities.PointRoomEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

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

    override fun getAllPlanEpochDaysFromDb(): List<Long> {
        return pointsDao.getAllPlanDatesFromDb()
    }

    override fun getAllPointsColorsByEpochDay(epochDay: Long): List<Int> {
        return pointsDao.getAllPointsColorsByEpochDay(epochDay)
    }

    override fun deletePointByTag(tag: String) {
        pointsDao.deletePointsByTag(tag)
    }

    override fun getAllPointsFlow(): Flow<List<Point>> {
        return pointsDao.getAllPointRoomEntitiesFlow().transform { pointRoomEntities ->
            pointRoomEntities.map { pointRoomEntity ->
                pointRoomEntity.toPoint()
            }
        }
    }

    override fun getAllPointsByEpochDay(epochDay: Long): List<Point> {
        return pointsDao.getAllPointsByEpochDay(epochDay).map { pointRoomEntity ->
            pointRoomEntity.toPoint()
        }
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