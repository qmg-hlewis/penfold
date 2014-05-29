package org.huwtl.penfold.app.schedule

import org.huwtl.penfold.command.{ArchiveTask, CommandDispatcher}
import org.huwtl.penfold.readstore.{TaskRecordReference, ReadStore}
import org.huwtl.penfold.domain.exceptions.AggregateConflictException
import grizzled.slf4j.Logger
import scala.util.Try
import org.huwtl.penfold.app.TaskArchiverConfiguration

class TaskArchiveScheduler(readStore: ReadStore, commandDispatcher: CommandDispatcher, archiverConfig: TaskArchiverConfiguration) extends Scheduler {
  private lazy val logger = Logger(getClass)

  override val name = "task archiver"

  override val frequency = archiverConfig.checkFrequency

  override def process() {
    readStore.retrieveTasksToArchive(archiverConfig.timeoutAttributePath).foreach(archiveTask)
  }

  private def archiveTask(task: TaskRecordReference) {
    Try(commandDispatcher.dispatch(ArchiveTask(task.id))) recover {
      case e: AggregateConflictException => logger.info("conflict archiving task", e)
      case e: Exception => logger.error("error archiving task", e)
    }
  }
}
