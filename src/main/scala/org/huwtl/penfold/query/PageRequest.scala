package org.huwtl.penfold.query

case class PageRequest(pageNumber: Int, pageSize: Int) {
  val start = pageNumber * pageSize
  val end = start + pageSize
  val isFirstPage = pageNumber == 0
  def nextPage = PageRequest(start + pageSize, pageSize)
}