<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Locale"
    import="domderrien.i18n.LocaleController"
    import="domderrien.i18n.LabelExtractor"
%><%
    // Locale detection
    Locale locale = LocaleController.getLocale(request);
    String localeId = LocaleController.getLocaleId(request);

    // Visual theme selection
    String visualTheme = request.getParameter("visualTheme");
    if (visualTheme == null || visualTheme.length() == 0 || (!"soria".equals(visualTheme) && !"nihilo".equals(visualTheme))) {
        visualTheme = "tundra";
    }
%><html>
<head>
    <title><%= LabelExtractor.get("dd2tu_applicationName", locale) %></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

    <div id="introFlash">
        <div><span>Please, wait while the Console is being built...</span></div>
    </div>

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
    dojo.require("dijit.layout.BorderContainer");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("domderrien.i18n.LabelExtractor");
    dojo.require("dojo.parser");
    dojo.addOnLoad(function(){
        dojo.parser.parse();
        // Get the localized resource bundle
        var _labelExtractor = domderrien.i18n.LabelExtractor.init(
                "domderrien.i18n",
                "@dd2tu.localizedLabelBaseFilename@",
                "<%= localeId %>"
            );
        dojo.fadeOut({
            node: "introFlash",
            delay: 500,
            onEnd: function() {
                dojo.style("introFlash", "display", "none");
            }
        }).play();
    });
    </script>

    <div dojoType="dijit.layout.BorderContainer" id="topContainer">
        <div dojoType="dijit.layout.ContentPane" region="center">
            <p><%= LabelExtractor.get("dd2tu_contruction", LocaleController.DEFAULT_LOCALE) %></p>
        </div>
    </div>

</body>
</html>
