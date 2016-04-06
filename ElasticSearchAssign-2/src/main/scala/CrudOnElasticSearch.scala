
object CrudOnElasticSearch extends ElasticSearchOperation with App {

  val newClient = getClient()

  val bulkInsertResponse = writeToIndex(newClient)
  println("----------number of documents inserted from input file " + bulkInsertResponse.getItems.length)
  Thread.sleep(800)

  val no_of_json=bulkInsertResponse.getItems.length
  val sortedResult = writeToOutputJson(newClient,no_of_json)


}