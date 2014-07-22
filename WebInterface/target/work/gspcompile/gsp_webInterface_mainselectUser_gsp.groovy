import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_webInterface_mainselectUser_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/main/selectUser.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
printHtmlPart(1)
createTagBody(1, {->
printHtmlPart(2)
invokeTag('captureMeta','sitemesh',10,['gsp_sm_xmlClosingForEmptyTag':("/"),'name':("layout"),'content':("main")],-1)
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',15,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(4)
loop:{
int i = 0
for( user in (userMap) ) {
printHtmlPart(5)
expressionOut.print(i % 2 == 0 ? 'even' : 'odd')
printHtmlPart(6)
createTagBody(3, {->
expressionOut.print(user.key)
})
invokeTag('link','g',35,['action':("displayGraph"),'id':(user.key)],3)
printHtmlPart(7)
expressionOut.print(user.value)
printHtmlPart(8)
i++
}
}
printHtmlPart(9)
})
invokeTag('captureBody','sitemesh',38,[:],1)
printHtmlPart(10)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405506033449L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}

@org.codehaus.groovy.grails.web.transform.LineNumber(
	lines = [39, 39, 39, 39, 39, 39, 1, 1, 1, 1, 1, 1, 1, 2, 4, 10, 10, 10, 10, 15, 15, 16, 31, 31, 31, 31, 31, 31, 34, 34, 35, 35, 35, 35, 35, 36, 36, 36, 36, 36, 36, 38, 38, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39, 39],
	sourceName = "selectUser.gsp"
)
class ___LineNumberPlaceholder { }
