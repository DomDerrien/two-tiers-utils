<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" />
	<xsl:template match="tmx/body">
		{
		<xsl:for-each select="tu">
			<xsl:for-each select="prop">
				<xsl:if test="@type='x-tier' and .='dojotk'">
						"<xsl:value-of select="../@tuid" />":"<xsl:value-of select="../tuv/seg" />",
				</xsl:if>
			</xsl:for-each>
		</xsl:for-each>
		"build", "@rwa.stageId@"}
	</xsl:template>
</xsl:stylesheet>