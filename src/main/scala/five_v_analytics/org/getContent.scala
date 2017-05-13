package five_v_analytics.org

/**
  * Created by davbzh on 2017-05-13.
  */

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils

object getContent {

  def getNewRelicContentFromUrl(apikey: String, apiurl: String): Unit = {

    //import sys.process._
    //val data = List("curl", "-H", s"X-API-KEY: ${apikey}", s"${apiurl}").!!
    //println("Shipping data is " + data)

    val httpGet = new HttpGet(apiurl)
    // set the desired header values
    httpGet.setHeader("X-API-KEY", apikey)

    // execute the request
    val client = new DefaultHttpClient
    val entity = client.execute(httpGet).getEntity
    val json = EntityUtils.toString(entity)
    println(json)
    client.getConnectionManager.shutdown
  }

  def main(args: Array[String]) {

    getNewRelicContentFromUrl(args(0), args(1))

  }
}
