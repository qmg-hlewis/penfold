package org.huwtl.penfold.command

import org.specs2.mutable.Specification
import org.specs2.mock.Mockito
import org.huwtl.penfold.domain.model.{AggregateVersion, Task, AggregateId}
import org.huwtl.penfold.domain.store.DomainRepository

class CloseTaskHandlerTest extends Specification with Mockito {

  val expectedAggregateId = AggregateId("a1")

  val version = AggregateVersion.init

  val domainRepository = mock[DomainRepository]

  val startedTask = mock[Task]
  val closedTask = mock[Task]

  val commandDispatcher = new CommandDispatcherFactory(domainRepository, null).create

  "close task" in {
    domainRepository.getById[Task](expectedAggregateId) returns startedTask
    startedTask.close(version, None, None, None) returns closedTask

    commandDispatcher.dispatch(CloseTask(expectedAggregateId, version, None, None, None))

    there was one(domainRepository).add(closedTask)
  }
}
