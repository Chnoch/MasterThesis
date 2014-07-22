import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_webInterface_maindisplayGraph_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/main/displayGraph.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',10,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
invokeTag('javascript','g',11,['src':("Curry-1.0.1.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',12,['src':("seedrandom.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',13,['src':("raphael-min.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',14,['src':("dracula_algorithms.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',15,['src':("dracula_graffle.js")],-1)
printHtmlPart(2)
invokeTag('javascript','g',16,['src':("dracula_graph.js")],-1)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',16,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(4)
expressionOut.print(user)
printHtmlPart(5)
createClosureForHtmlPart(6, 2)
invokeTag('link','g',24,['action':("selectUser"),'class':("btn btn-primary")],2)
printHtmlPart(7)
expressionOut.print(graph.vertices.size())
printHtmlPart(8)
expressionOut.print(graph.edges.size())
printHtmlPart(9)
loop:{
int i = 0
for( node in (graph.vertices) ) {
printHtmlPart(10)
expressionOut.print(node.id)
printHtmlPart(11)
i++
}
}
printHtmlPart(12)
for( edge in (graph.edges) ) {
printHtmlPart(13)
expressionOut.print(edge.getProperty("count"))
printHtmlPart(14)
expressionOut.print(edge.inVertex.id)
printHtmlPart(15)
expressionOut.print(edge.outVertex.id)
printHtmlPart(16)
}
printHtmlPart(17)
})
invokeTag('captureBody','sitemesh',62,[:],1)
printHtmlPart(18)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405953649833L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}

@org.codehaus.groovy.grails.web.transform.LineNumber(
	lines = [62, 62, 62, 62, 62, 62, 1, 1, 1, 1, 1, 1, 1, 2, 4, 10, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 15, 15, 16, 16, 16, 16, 16, 17, 19, 19, 19, 20, 24, 24, 24, 26, 28, 29, 30, 43, 43, 43, 43, 43, 44, 44, 44, 44, 44, 45, 45, 48, 49, 51, 52, 52, 52, 54, 54, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62, 62],
	sourceName = "displayGraph.gsp"
)
class ___LineNumberPlaceholder { }
