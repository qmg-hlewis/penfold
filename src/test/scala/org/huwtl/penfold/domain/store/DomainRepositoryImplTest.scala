package org.huwtl.penfold.domain.store

import org.huwtl.penfold.domain.event.{TaskCreated, TaskTriggered}
import org.huwtl.penfold.domain.model.{AggregateId, Payload, _}
import org.huwtl.penfold.readstore.EventNotifier
import org.joda.time.DateTime
import org.specs2.mock.Mockito
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.specification.Scope

class DomainRepositoryImplTest extends SpecificationWithJUnit with Mockito {
  class context extends Scope {
    val aggregateId = AggregateId("a1")

    val queueId = QueueId("q1")

    val timestamp = DateTime.now

    val createdTask = Task.create(aggregateId, queueId, Payload.empty, None)
    
    val events = createdTask.uncommittedEvents 

    val eventStore = mock[EventStore]

    val notifier = mock[EventNotifier]

    val repo = new DomainRepositoryImpl(eventStore, notifier)
  }

  "append new aggregate root events to event store" in new context {
    val task = repo.add(createdTask)

    task.uncommittedEvents must beEmpty
    there was one(notifier).notify(events)
  }

  "load aggregate by id" in new context {
    eventStore.retrieveBy(aggregateId) returns List(
      TaskCreated(aggregateId, AggregateVersion.init, timestamp, queueId, timestamp, Payload.empty, timestamp.getMillis),
      TaskTriggered(aggregateId, AggregateVersion.init.next, timestamp)
    )

    val task = repo.getById[Task](aggregateId)

    task.status must beEqualTo(Status.Ready)
  }

  "throw exception when no aggregate found with id" in new context {
    val unknownAggregateId = AggregateId("unknown")
    eventStore.retrieveBy(unknownAggregateId) returns Nil

    repo.getById[Task](AggregateId("unknown")) must throwA[IllegalArgumentException]
  }
}
