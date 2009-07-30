<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page
	language="java"
	contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.Enumeration"
	import="java.util.Locale"
	import="java.util.ResourceBundle"
	import="domderrien.i18n.LabelExtractor"
	import="domderrien.i18n.LocaleController"
%><%
    // Locale detection
    Locale locale = LocaleController.detectLocale(request);
	String localeId = LocaleController.getLocaleId(request);
	
	// Visual theme selection
	String visualTheme = request.getParameter("visualTheme");
	if (visualTheme == null || visualTheme.length() == 0 || (!"soria".equals(visualTheme) && !"nihilo".equals(visualTheme))) {
		visualTheme = "tundra";
	}
%><html>
<head>
    <title><%= LabelExtractor.get("dd2tu_applicationName", locale) %></title>    
    <meta http-equiv="Content-Type" content="text/html;charset=UTF8" /> 
	<link rel="icon" href="/domderrien/@dd2tu.stageId@/images/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="/domderrien/@dd2tu.stageId@/images/favicon.ico" />

	<style type="text/css">
		@import "/domderrien/@dd2tu.stageId@/js/dojo/dojo/resources/dojo.css";
		@import "/domderrien/@dd2tu.stageId@/js/dojo/dijit/themes/dijit.css";
		@import "/domderrien/@dd2tu.stageId@/js/dojo/dijit/themes/<%= visualTheme %>/<%= visualTheme %>.css";
		@import "/domderrien/@dd2tu.stageId@/themes/common.css";
	</style>

	<!-- load the dojo toolkit base -->
	<% if (Boolean.parseBoolean("@dojo.optimization@")) { %><script
		djConfig="parseOnLoad: false, isDebug: false, modulePaths: { domderrien: '/domderrien/@dd2tu.stageId@/js-release/dojo/domderrien' }"
		src="/domderrien/@dd2tu.stageId@/js-release/dojo/dojo/dojo.js"
		type="text/javascript"
	></script><% } else {  %><script
		djConfig="parseOnLoad: false, isDebug: false, modulePaths: { domderrien: '/domderrien/@dd2tu.stageId@/js/domderrien' }"
		src="/domderrien/@dd2tu.stageId@/js/dojo/dojo/dojo.js"
		type="text/javascript"
	></script><% } %>

    <script type="text/javascript">
    dojo.require("dijit.layout.AccordionContainer");
    dojo.require("dojo.parser");
    </script>

    </head>
    <body class="<%= visualTheme %>">

        <!--
        Copy of this <body/> is located in help-standalone.html.
        Inserting the <body/> content in a remote ContentPane with the
        corresponding href breaks the rendering in IE.
        IMPORTANT: Both files' content should be maintained synchronized.
        -->
        <div
            class="helpContainer"
            dojoType="dijit.layout.AccordionContainer"
            region="center"
        >
            <div
                dojoType="dijit.layout.AccordionPane"
                href="/@dd2tu.contextRoot@/freeAccess/help/introduction.html"
                title="<%= LabelExtractor.get("dd2tu_helpIntroductionSectionTitle", locale) %>"
            ></div>
            <div
                dojoType="dijit.layout.AccordionPane"
                href="/@dd2tu.contextRoot@/freeAccess/help/credits.html"
                title="<%= LabelExtractor.get("dd2tu_helpCreditsSectionTitle", locale) %>"
            ></div>
            <div
                dojoType="dijit.layout.AccordionPane"
                href="/@dd2tu.contextRoot@/freeAccess/help/contactUs.html"
                title="<%= LabelExtractor.get("dd2tu_helpContactSectionTitle", locale) %>"
            ></div>

        </div>

    </body>
</html>