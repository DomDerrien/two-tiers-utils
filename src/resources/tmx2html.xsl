<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" version="4.0" encoding="utf-8" indent="no" />
	<xsl:template match="tmx/header" />
	<xsl:template match="tmx/body">
		<html>
			<head>
				<title>TMX representation</title>
			    <style type="text/css">
			        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.4/dojo/resources/dojo.css";
			        @import "http://ajax.googleapis.com/ajax/libs/dojo/1.4/dijit/themes/tundra/tundra.css";
			        body { margin: 10px; }
			        dl { margin-bottom: 10px; }
			        dl#bundle_language, dl#\_introduction\_ { display: none; }
			        dl dt { margin-bottom: 5px; font-weight: bold; }
			        dl dd { margin-left: 30px; }
					dd.programmingLanguage, dd.note { font-size: smaller; }
					dd.note { color: grey; }
					dd.programmingLanguage { color: brown; }
					span.dojotk { color: green; }
					span.python { color: orange; }
					span.javarb { color: blue; }
			    </style>
            </head>
            <body onload="" class="tundra">
				<xsl:for-each select="tu">
					<xsl:sort select="@tuid" />
					<dl id="{@tuid}">
						<dt>
							<xsl:variable
								name="lowerTUID"
								select="translate(@tuid,'ABCDEFGHIJKLMNOPQRSTUVWXYZÀÁÂÄÃÈÉÊËÌÍÎÏÒÓÔÖÕÙÚÛÜÝŸÑ','abcdefghijklmnopqrstuvwxyzàáâäãèéêëìíîïòóôöõùúûüýÿñ')"
							/>
							<a name="{$lowerTUID}"></a><xsl:value-of select="@tuid" />
						</dt>
						<dd>
							<xsl:call-template name="globalReplace">
								<xsl:with-param name="origin" select="tuv/seg"/>
								<xsl:with-param name="target" select="'|'"/>
								<xsl:with-param name="replacement" select="'[br/]'"/>
							</xsl:call-template>
						</dd>
						<dd class="programmingLanguage">Targeted programming language(s):
							<xsl:for-each select="prop[@type='x-tier']">
								<xsl:choose>
									<xsl:when test=".='dojotk'">
										<span class="dojotk">JavaScript</span>
									</xsl:when>
									<xsl:when test=".='python'">
										<span class="python">Python</span>
									</xsl:when>
									<xsl:otherwise>
										<span class="javarb">Java</span>
									</xsl:otherwise>
								</xsl:choose>.
							</xsl:for-each>
						</dd>
						<dd class="note">
							<xsl:call-template name="globalReplace">
								<xsl:with-param name="origin" select="note"/>
								<xsl:with-param name="target" select="'|'"/>
								<xsl:with-param name="replacement" select="'[br/]'"/>
							</xsl:call-template>
						</dd>
					</dl>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>
	<xsl:template name="globalReplace">
		<xsl:param name="origin" />
		<xsl:param name="target" />
		<xsl:param name="replacement" />
		<xsl:variable name="output" select="translate($origin,'\','')" />
		<xsl:choose>
			<xsl:when test="contains($output,$target)">
				<xsl:value-of select="substring-before($output,$target)" />
				<xsl:choose>
					<xsl:when test="contains($replacement,'[br/]')">
						<br/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$replacement" />
					</xsl:otherwise>
				</xsl:choose>
				<xsl:call-template name="globalReplace">
					<xsl:with-param name="origin" select="substring-after($output,$target)"/>
					<xsl:with-param name="target" select="$target"/>
					<xsl:with-param name="replacement" select="$replacement"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$output" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>