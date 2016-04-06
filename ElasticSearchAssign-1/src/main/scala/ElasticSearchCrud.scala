import java.net.InetAddress
import org.elasticsearch.action.update.{UpdateResponse, UpdateRequest}
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.action.delete.DeleteResponse
import org.elasticsearch.action.get.GetResponse
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.common.transport.InetSocketTransportAddress

import org.elasticsearch.common.xcontent.XContentFactory._


/**
  * Created by knoldus on 3/4/16.
  */
class ElasticSearchCrud {

  def getClient(): Client = {

    val settings:Settings = Settings.settingsBuilder()
      .put("cluster.name", "sangeeta").build()
    val client:Client = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))

    client
  }

  def insert(client: Client, id: Int, user: String, userage: Int): Unit ={
    val response: IndexResponse = client.prepareIndex("company",
      "employee", id.toString)
      .setSource(jsonBuilder()
        .startObject()
        .field("user", user)
        .field("age", userage)
        .endObject()
      ).get()
  }

  def update(client: Client, id: Int, msg: String): UpdateResponse = {
    val updateRequest: UpdateRequest = new UpdateRequest();
    updateRequest.index("company")
    updateRequest.`type`("employee")
    updateRequest.id(id.toString)
    updateRequest.doc(jsonBuilder()
      .startObject()
      .field("message", msg)
      .endObject())
    client.update(updateRequest).get()
  }

  def read(client: Client, id: Int): GetResponse = {
    val response: GetResponse = client.prepareGet("company",
      "employee", id.toString).get()
    response

  }

  def delete(client: Client, id: Int): DeleteResponse = {
    val response: DeleteResponse = client.prepareDelete("company",
      "employee", id.toString).get()
    response
  }
}


object crudApp extends App {

  val crudObj = new ElasticSearchCrud
  val client = crudObj.getClient()

println ( "Inserting Records ")
  val create1 = crudObj.insert(client, 1, "abc", 28)
  val create2 = crudObj.insert(client, 2, "xyz", 22)
  val create3 = crudObj.insert(client, 3, "asd", 20)

println( " Reading Record ")
  val read=crudObj.read(client,2)
  println(read.isExists)


  println(" Updating Record ")
  val update=crudObj.update(client,2,"Here is update")
  val result=crudObj.read(client,2)
  println("Here :" +result.toString)
  println(result.getSource)

  println(" Deleting Record ")
  val del=crudObj.delete(client,2)
  println(del.isFound)

}