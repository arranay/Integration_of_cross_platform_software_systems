<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <head>
            <META http-equiv="Content-Type" content="text/html; charset=UTF-8" />
            <link rel="stylesheet" href="mystyle.css">
                <title><xsl:value-of select="BPMN-model/model-name"/></title>
            </link>
        </head>
        <html>
            <body>
                <table>
                    <tr>
                        <td colspan="2" class="header">Common info about BPMN model</td>
                    </tr>
                    <tr>
                        <td>Name of model:</td>
                        <td><xsl:value-of select="BPMN-model/model-name"/></td>
                    </tr>
                    <tr>
                        <td>Author:</td>
                        <td><xsl:value-of select="BPMN-model/author"/></td>
                    </tr>
                    <tr>
                        <td>Date:</td>
                        <td><xsl:value-of select="BPMN-model/date"/></td>
                    </tr>
                </table>

                <table>
                    <tr>
                        <td colspan="2" class="header">Tracks</td>
                    </tr>
                    <xsl:for-each select="BPMN-model/tracks/track">
                        <tr>
                            <td class="track"><xsl:value-of select="track-name"/></td>

                            <td>
                                <xsl:if test="actions">
                                    <table>
                                        <tr>
                                            <td colspan="2" class="items">Actions</td>
                                        </tr>
                                        <tr class="item-header">
                                            <td>Operation</td>
                                            <td>Time</td>
                                        </tr>
                                        <xsl:for-each select="actions/action">
                                            <tr>
                                                <td><xsl:value-of select="operation"/></td>
                                                <td><xsl:value-of select="time" /></td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </xsl:if>

                                <xsl:if test="events">
                                    <table>
                                        <tr>
                                            <td colspan="2" class="items">Events</td>
                                        </tr>
                                        <tr class="item-header">
                                            <td>Name</td>
                                            <td>Type</td>
                                        </tr>
                                        <xsl:for-each select="events/event">
                                            <tr>
                                                <td><xsl:value-of select="event-name"/></td>
                                                <td><xsl:value-of select="@type" /></td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </xsl:if>

                                <xsl:if test="forks">
                                    <table>
                                        <tr>
                                            <td colspan="2" class="items">Forks</td>
                                        </tr>
                                        <tr class="item-header">
                                            <td>Condition</td>
                                        </tr>
                                        <xsl:for-each select="forks/fork">
                                            <tr>
                                                <td><xsl:value-of select="condition"/></td>
                                            </tr>
                                        </xsl:for-each>
                                    </table>
                                </xsl:if>
                            </td>
                        </tr>
                    </xsl:for-each>

                    <tr class="result">
                        <td>Result time</td>
                        <td><xsl:value-of select="sum(BPMN-model/tracks/track/actions/action/time)"/> ms</td>
                    </tr>
                </table>

                <table>
                    <tr>
                        <td colspan="3" class="header">Streams</td>
                    </tr>
                    <tr class="item-header">
                        <td>From</td>
                        <td>To</td>
                        <td>Name</td>
                    </tr>
                    <xsl:for-each select="BPMN-model/streams/stream">
                        <tr>
                            <xsl:choose>
                                <xsl:when test="from/@type = 'event'">
                                    <xsl:variable name="current-item-id" select="from/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/events/event">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="event-name"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                                <xsl:when test="from/@type = 'action'">
                                    <xsl:variable name="current-item-id" select="from/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/actions/action">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="operation"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                                <xsl:when test="from/@type = 'fork'">
                                    <xsl:variable name="current-item-id" select="from/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/forks/fork">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="condition"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                            </xsl:choose>
                            <xsl:choose>
                                <xsl:when test="to/@type = 'event'">
                                    <xsl:variable name="current-item-id" select="to/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/events/event">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="event-name"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                                <xsl:when test="to/@type = 'action'">
                                    <xsl:variable name="current-item-id" select="to/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/actions/action">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="operation"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                                <xsl:when test="to/@type = 'fork'">
                                    <xsl:variable name="current-item-id" select="to/@item-id" />
                                    <xsl:for-each select="/BPMN-model/tracks/track/forks/fork">
                                        <xsl:if test="$current-item-id=@id">
                                            <td><xsl:value-of select="condition"/></td>
                                        </xsl:if>
                                    </xsl:for-each>
                                </xsl:when>
                            </xsl:choose>
                            <td><xsl:value-of select="stream-name"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>