import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_webInterface_mainselectFile_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/main/selectFile.gsp" }
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
invokeTag('captureHead','sitemesh',11,[:],1)
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(4)
createTagBody(2, {->
printHtmlPart(5)
invokeTag('hiddenField','g',20,['name':("defaultFile"),'value':("true")],-1)
printHtmlPart(6)
})
invokeTag('form','g',26,['role':("form"),'action':("selectUser"),'method':("post")],2)
printHtmlPart(7)
createClosureForHtmlPart(8, 2)
invokeTag('form','g',39,['role':("form"),'action':("selectUser"),'method':("post"),'enctype':("multipart/form-data")],2)
printHtmlPart(9)
})
invokeTag('captureBody','sitemesh',40,[:],1)
printHtmlPart(10)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405504520881L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}

@org.codehaus.groovy.grails.web.transform.LineNumber(
	lines = [40, 40, 40, 40, 40, 40, 1, 1, 1, 1, 1, 1, 1, 2, 4, 10, 10, 10, 11, 11, 11, 11, 17, 17, 20, 20, 20, 20, 26, 26, 26, 39, 39, 39, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40],
	sourceName = "selectFile.gsp"
)
class ___LineNumberPlaceholder { }
