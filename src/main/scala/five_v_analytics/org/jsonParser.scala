package five_v_analytics.org

import java.io._
//
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.DefaultFormats._
import org.json4s.jackson.Serialization.{read, write}
import org.json4s.native.Serialization
//
import org.rogach.scallop._
//
import getContent._

/**
  * Created by davbzh on 2017-05-11.
  */

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val njsons = opt[Int]("njsons", required = true)
  val apikey = opt[String]("apikey", required = true)
  val url = opt[String]("url", required = true)
  val output = opt[String]("output", required = true)
  verify()
}

// a case class to match the JSON data
case class Contact(
                         id: String,
                         email: String,
                         firstName: String,
                         lastName: String,
                         sent: Int,
                         opened:Int,
                         clicked:Int
                       )

object jsonParser {

  implicit val formats = DefaultFormats
  //implicit val formats = Serialization.formats(NoTypeHints)

  def main(args: Array[String]) {

    val conf = new Conf(args)
    val apikey = conf.apikey()
    val apiurl = conf.url()
    val njsons = conf.njsons()

    //val pw = new PrintWriter(new File(conf.output()))
    val fw = new FileWriter(conf.output(), true)

    for (njson <- 2 to njsons) {
      println("json: " + njson)
      // json is a JValue instance
      val jsonStringsoundest = getNewRelicContentFromUrl(apiurl + njson.toString, apikey)
      val json = parse(jsonStringsoundest)
      val success = (json \\ "success").extract[Boolean]
      val statusCode = (json \\ "statusCode").extract[Int]
      val elements = (json \\ "contacts").children
      for (acct <- elements) {
        val contacts = acct.extract[Contact]
        fw.write(s"${contacts.id},${contacts.email},${contacts.firstName},${contacts.lastName},${contacts.sent}," +
          s"${contacts.opened},${contacts.clicked}\n")
      }
    }
    //val content = read[Success](getNewRelicContentFromUrl(apiurl, apikey))
    //pw.close
    fw.close()
  }
}