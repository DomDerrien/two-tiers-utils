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
    dojo.require("dijit.layout.BorderContainer");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("dijit.Dialog");
    dojo.require("dijit.layout.AccordionContainer");
    dojo.require("dijit.form.Form");
    dojo.require("dijit.form.ValidationTextBox");
    dojo.require("dijit.form.Button");
    dojo.require("dojo.parser");
    dojo.addOnLoad(function(){
        dojo.parser.parse();
    });
    </script>

</head>
<body class="<%= visualTheme %>">

    <div
        dojoType="dijit.layout.BorderContainer"
        id="topContainer"
        style="width: 100%; height: 100%;"
    >
        <div
            dojoType="dijit.layout.ContentPane"
            id="consoleTitle"
            region="top"
        >
            <img src="/domderrien/@dd2tu.stageId@/images/favicon.ico" title="<%= LabelExtractor.get("dd2tu_projectName", locale) %>" />
            <%= LabelExtractor.get("dd2tu_loginPageTitle", locale) %>
        </div>
        <div
            dojoType="dijit.layout.ContentPane"
            region="center"
        >
            <table style="height: 100%; width: 100%;">
                <tr>
                    <td align="center">
                        <table>
                            <tr>
                                <td>
                                    <p><%= LabelExtractor.get("dd2tu_loginFailureInformation", locale) %></p>
                                    <button
                                        dojoType="dijit.form.Button"
                                        iconClass="silkAcceptIcon"
                                        onClick="history.back();"
                                    ><%= LabelExtractor.get("dd2tu_loginFailureRetryLabel", locale) %></button>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>
