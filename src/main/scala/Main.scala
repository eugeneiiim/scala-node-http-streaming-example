import java.net.{URL, HttpURLConnection}
import java.io.{OutputStream, BufferedReader, InputStreamReader}
import collection.mutable.ListBuffer

object Main {
  def main(args: Array[String]) {
    val connection =
      new URL("http", "localhost", 5432, "/stream").openConnection().asInstanceOf[HttpURLConnection]

    connection.setRequestMethod("POST")
    connection.setDoInput(true)
    connection.setDoOutput(true)
    connection.setRequestProperty("content-type", "text/plain")
    connection.setChunkedStreamingMode(0);

    val output = connection.getOutputStream()

    val t = new Thread {
      override def run = {
        try {
          while (true) {
            output.write("hello\n".getBytes("UTF-8"))
            output.flush
            Thread.sleep(500)
          }
        } catch {
          case e => e.printStackTrace
        }
      }
    }
    t.start


    // TODO can't read simultaneously
//     val response = connection.getInputStream()
//     val br = new BufferedReader(new InputStreamReader(response))
//     var l: String = ""
//     while (l != null) {
//       l = br.readLine()
//       println(l)
//     }


    t.join
  }
}
