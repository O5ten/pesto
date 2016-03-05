@Grab
import groovy.xml.MarkupBuilder as Builder

static String createPage(Map queryParams) {
  def writer = new StringWriter()
  new Builder(writer).html {
    h2 'A Dynamic Webpage!'
    div(id: 'query-params') {
      if (!queryParams) li 'No query parameters given'
      else ul {
        for (key in queryParams.keySet()) {
          li "Query param: $key -> ${queryParams[key]}"
        }
      }
    }
    p "Current date: ${new Date()}"
  }
  writer.toString()
}
