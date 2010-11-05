<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Date"
    import="java.util.Enumeration"
    import="java.util.Locale"
    import="java.util.ResourceBundle"
    import="domderrien.i18n.LabelExtractor"
    import="domderrien.i18n.LocaleController"
    import="domderrien.i18n.StringUtils"
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
    <meta http-equiv="Content-Type" content="text/html;charset=<%= StringUtils.HTML_UTF8_CHARSET %>" />
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
        dojo.byId("username").focus();
    });
    var switchLanguage = function() {
        var urlParams = window.location.search;
        var selector = document.getElementById("languageSelector");
        var newLanguage = selector.options[selector.selectedIndex].value;
        if (urlParams.indexOf("lang=") == -1) {
            urlParams += (urlParams.length == 0 ? "" : "&") + "lang=" + newLanguage;
        }
        else {
            urlParams = urlParams.replace(/lang=((\w\w_\w\w)|(\w\w))/g, "lang=" + newLanguage);
        }
        window.location.search = urlParams;
    };
    </script>

</head>
<body class="<%= visualTheme %>">

    <div
        dojoType="dijit.Dialog"
        href="/@dd2tu.contextRoot@/freeAccess/help-popup.jsp?<%= LocaleController.REQUEST_LOCALE_KEY %>=<%= localeId %>"
        id="helpSystemDialog"
        title="<%= LabelExtractor.get("dd2tu_helpDialogTitle", locale) %>"
    ></div>

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
            class="loginFormPane"
            dojoType="dijit.layout.ContentPane"
            region="center"
        >
            <form
                action="j_security_check?<%= LocaleController.REQUEST_LOCALE_KEY %>=<%= localeId %>"
                dojoType="dijit.form.Form"
                encType="multipart/form-data"
                id="cacheSettings"
                method="get"
            >
                <script type="dojo/method" event="onSubmit">
                    // console.debug('Attempting to submit form w/values:\n', dojo.toJson(this.getValues(), true));
                    if(this.validate()) {
                        return true;
                    }
                    dojo.byId("errorMessage").appendChild(
                        document.createTextNode('<%= LabelExtractor.get("dd2tu_loginFormInvalidMsg", locale) %>')
                    );
                    return false;
                </script>
                <table style="position: absolute; top: 40%; left: 30%;">
                    <tr>
                        <td align="right"><label for="username"><%= LabelExtractor.get("dd2tu_loginFormUsernameLabel", locale) %></label></td>
                        <td align="left" width="50%">
                            <input
                                dojoType="dijit.form.ValidationTextBox"
                                id="username"
                                isInRange="return this != ''"
                                name="j_username"
                                required="true"
                                type="text"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><label for="password"><%= LabelExtractor.get("dd2tu_loginFormPasswordLabel", locale) %></label></td>
                        <td align="left" width="50%">
                            <input
                                dojoType="dijit.form.ValidationTextBox"
                                id="password"
                                name="j_password"
                                required="true"
                                type="password"
                            />
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <button
                                dojoType="dijit.form.Button"
                                iconClass="silkAcceptIcon"
                                type="submit"
                            ><%= LabelExtractor.get("dd2tu_loginFormLoginLabel", locale) %></button>
                        </td>
                    </tr>
                    <tr>
                        <td
                            align="center"
                            colspan="2"
                            id="errorMessage"
                            style="color: orange; font-style: italic;"
                        ></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

    <div class="topCommandBox">
        <!-- Need to be in reverse order because this <div /> is "float: right;" -->
        <select
            class="topCommand"
            dojotType="dijit.form.FilteringSelect"
            id="languageSelector"
            onchange="switchLanguage();"
        ><%
            ResourceBundle languageList = LocaleController.getLanguageListRB();
            Enumeration<String> keys = languageList.getKeys();
            while(keys.hasMoreElements()) {
                String key = keys.nextElement();%>
                <option<% if (key.equals(localeId)) { %> selected<% } %> value="<%= key %>"><%= languageList.getString(key) %></option><%
            } %>
        </select>
           <a
                class="topCommand"
                href="/@dd2tu.contextRoot@/freeAccess/help-standalone.jsp?<%= LocaleController.REQUEST_LOCALE_KEY %>=<%= localeId %>"
                onClick="dijit.byId('helpSystemDialog').show(); return false;"
                target="_blank"
                title="<%= LabelExtractor.get("dd2tu_topCommandBox_Help", locale) %>"
            ><img class="anchorLogo" src="/domderrien/@dd2tu.stageId@/images/iconHelp.png" title="<%= LabelExtractor.get("dd2tu_topCommandBox_Help", locale) %>" /></a>
    </div>
</body>
</html>
