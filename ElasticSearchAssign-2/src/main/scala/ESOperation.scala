import java.io.{File, PrintWriter}
import java.net.InetAddress

import org.elasticsearch.action.bulk.BulkResponse
import org.elasticsearch.action.get.MultiGetItemResponse
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress

import scala.io.Source

/**
 * @author knodus
 *
 */
trait ElasticSearchOperation {

  /**
   * method returning java API client
   *
   * @return
   */
  def getClient(): Client = {
    val settings:Settings = Settings.settingsBuilder()
      .put("cluster.name", "sangeeta").build()
    val client:Client = TransportClient.builder().settings(settings).build()
      .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300))

    client
  }

  def writeToIndex(client: Client): BulkResponse = {

    val inputJson: List[String] = Source.fromFile("src/main/resources/sampleInput.json").getLines().toList
    println("-------------------- INPUT JSON -------------------" + inputJson(0))
    val batchRequest = client.prepareBulk()
    inputJson.foreach{ line =>
      val doc =client.prepareIndex("crud_datastore1","store1").setSource(line)
      batchRequest.add(doc)
    }
    batchRequest.execute().actionGet()
  }

  def writeToOutputJson(client:Client,count:Int) {


    val multiGetItemResponses = client.prepareMultiGet().add("crud_datastore1","store1","1").get()
    val response = multiGetItemResponses.iterator()
    while(response.hasNext){
      val pw = new PrintWriter(new File("/home/knodus/sangeeta/Sang/Projects/crudOnESearch/src/main/resources/output.json" ))
      pw.write("response.next.getResponse.getSourceAsString")
      pw.close


    }
  }


  }