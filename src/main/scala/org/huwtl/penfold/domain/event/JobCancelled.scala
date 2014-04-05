package org.huwtl.penfold.domain.event

import org.huwtl.penfold.domain.model.{QueueId, AggregateVersion, AggregateId}
import org.joda.time.DateTime

case class JobCancelled(aggregateId: AggregateId,
                        aggregateVersion: AggregateVersion,
                        created: DateTime,
                        queues: List[QueueId]) extends JobEvent