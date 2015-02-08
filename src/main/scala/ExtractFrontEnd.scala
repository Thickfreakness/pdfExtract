import java.io.File

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

/**
 * Created by alex on 08/02/15.
 */
object ExtractFrontEnd {
  System.setProperty("java.awt.headless", "true")

  def main(args: Array[String]) {
    if (args.length != 3) printUsageAndExit

    val startPage = args(0).toInt
    val endPage = args(1).toInt
    val filename = args(2)

    // sanity check
    if (startPage > endPage) printUsageAndExit

    println(getTextFromPdf(startPage, endPage, filename))
  }

  def printUsageAndExit {
    println("")
    println("Usage: pdftotext startPage endPage filename")
    println("       (endPage must be >= startPage)")
    System.exit(1)
  }

  def getTextFromPdf(startPage: Int, endPage: Int, filename: String): Option[String] = {
    try {
      val pdf = PDDocument.load(new File(filename))
      val stripper = new PDFTextStripper
      stripper.setStartPage(startPage)
      stripper.setEndPage(endPage)
      Some(stripper.getText(pdf))
    } catch {
      case t: Throwable =>
        t.printStackTrace
        None
    }
  }

}
