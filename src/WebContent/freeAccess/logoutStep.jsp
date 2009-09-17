<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Date"
    import="java.util.Locale"
    import="domderrien.i18n.LabelExtractor"
    import="domderrien.i18n.LocaleController"
%><%
    // Locale detection
    Locale locale = LocaleController.getLocale(request);

    // Visual theme selection
    String visualTheme = request.getParameter("visualTheme");
    if (visualTheme == null || visualTheme.length() == 0 || (!"soria".equals(visualTheme) && !"nihilo".equals(visualTheme))) {
        visualTheme = "tundra";
    }

    // Session termination
    session.invalidate();
    response.sendRedirect("/domderrien/@dd2tu.stageId@/protected/");
%><html>
<head>
    <title><%= LabelExtractor.get("dd2tu_applicationName", LocaleController.DEFAULT_LOCALE) %></title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF8" />
    <link rel="icon" href="/domderrien/@dd2tu.stageId@/images/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="/domderrien/@dd2tu.stageId@/images/favicon.ico" />

    <style type="text/css">
        @import "/domderrien/@dd2tu.stageId@/js/dojo/dojo/resources/dojo.css";
        @import "/domderrien/@dd2tu.stageId@/js/dojo/dijit/themes/dijit.css";
        @import "/domderrien/@dd2tu.stageId@/js/dojo/dijit/themes/<%= visualTheme %>/<%= visualTheme %>.css";
        @import "/domderrien/@dd2tu.stageId@/themes/common.css";
    </style>
</head>
<body class="<%= visualTheme %>">

    <p><%= LabelExtractor.get("dd2tu_redirectionMessage", locale) %></p>

</body>
</html>
