package org.huwtl.penfold.readstore

import org.huwtl.penfold.domain.model.Status
import org.huwtl.penfold.domain.model.QueueId
import org.huwtl.penfold.domain.model.AggregateId
import scala.concurrent.duration.FiniteDuration

trait ReadStore {
  def checkConnectivity: Either[Boolean, Exception]

  def retrieveBy(id: AggregateId): Option[TaskProjection]

  def retrieveBy(filters: Filters, pageRequest: PageRequest): PageResult

  def retrieveByQueue(queueId: QueueId, status: Status, pageRequest: PageRequest, sortOrder: SortOrder, filters: Filters = Filters.empty): PageResult

  def forEachTriggeredTask(f: TaskProjectionReference => Unit): Unit

  def forEachTimedOutTask(status: Status, timeout: FiniteDuration, f: TaskProjectionReference => Unit): Unit
}
