package org.huwtl.penfold.query

import org.huwtl.penfold.domain.model.Status
import org.huwtl.penfold.domain.model.QueueId
import org.huwtl.penfold.domain.model.AggregateId

trait QueryRepository {
  def retrieveBy(id: AggregateId): Option[JobRecord]

  def retrieveBy(filters: Filters, pageRequest: PageRequest): PageResult
  
  def retrieveByQueue(queueId: QueueId, status: Status, pageRequest: PageRequest, filters: Filters = Filters.empty): PageResult

  def retrieveByStatus(status: Status, pageRequest: PageRequest, filters: Filters = Filters.empty): PageResult

  def retrieveJobsToQueue: Stream[JobRecordReference] = {
    val pageSize = 50

    def allPagesOfJobsToQueue(pageRequest: PageRequest): Stream[List[JobRecordReference]] = {
      val page = retrieveNextPageOfJobsToTrigger(pageRequest)
      if (page.isEmpty) Stream.empty else page #:: allPagesOfJobsToQueue(pageRequest.nextPage)
    }

    val allJobsToQueue = for {
      pageOfJobsToQueue <- allPagesOfJobsToQueue(new PageRequest(0, pageSize))
      jobToQueue <- pageOfJobsToQueue
    } yield jobToQueue

    allJobsToQueue
  }

  protected def retrieveNextPageOfJobsToTrigger(pageRequest: PageRequest): List[JobRecordReference]
}