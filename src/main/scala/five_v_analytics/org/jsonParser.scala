package five_v_analytics.org


import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.DefaultFormats._

/**
  * Created by davbzh on 2017-05-11.
  */

// a case class to match the JSON data
case class EmailAccount(
                         accountName: String,
                         url: String,
                         username: String,
                         password: String,
                         minutesBetweenChecks: Int,
                         usersOfInterest: List[String]
                       )

object JsonParser {

  implicit val formats = DefaultFormats

  // a JSON string that represents a list of EmailAccount instances
  val jsonString2 : String =
    """
      |{
      |  "success": true,
      |  "statusCode": 0,
      |  "data": {
      |    "limit": 0,
      |    "contacts": [
      |      {
      |        "id": "string",
      |        "email": "string",
      |        "first_name": "string",
      |        "last_name": "string",
      |        "sent": 0,
      |        "opened": 0,
      |        "clicked": 0
      |      }
      |    ],
      |    "moreAvailable": true,
      |    "page": 0
      |  }
      |}
    """.stripMargin

  val jsonString =
    """
      |{
      |"accounts": [
      |{ "emailAccount": {
      |"accountName": "YMail",
      |"username": "USERNAME",
      |"password": "PASSWORD",
      |"url": "imap.yahoo.com",
      |"minutesBetweenChecks": 1,
      |"usersOfInterest": ["barney", "betty", "wilma"]
      |}},
      |{ "emailAccount": {
      |"accountName": "Gmail",
      |"username": "USER",
      |"password": "PASS",
      |"url": "imap.gmail.com",
      |"minutesBetweenChecks": 1,
      |"usersOfInterest": ["pebbles", "bam-bam"]
      |}}
      |]
      |}
    """.stripMargin

  def main(args: Array[String]): Unit = {

    // json is a JValue instance
    val json = parse(jsonString)
    val elements = (json \\ "emailAccount").children
    for (acct <- elements) {
      val m = acct.extract[EmailAccount]
      println(s"Account: ${m.url}, ${m.username}, ${m.password}")
      println(" Users: " + m.usersOfInterest.mkString(","))
    }


  }
}
