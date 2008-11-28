<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="yes" encoding="US-ASCII" doctype-public="-//W3C//DTD HTML 4.01//EN" doctype-system="http://www.w3.org/TR/html4/DTD/strict.dtd"  />
<xsl:variable name="fileName" select="translate( /WebScript/Variables/name[string()  = 'badboy.saveItemRelFileName' ]/following-sibling::*[1],'\','/') " />	
<xsl:variable name="saveDateTime" select="/WebScript/Variables/name[string()  = 'badboy.saveDateTime' ]/following-sibling::*[1]" />	

  <xsl:template match="/">
  <xsl:variable name="failureCount" select="number(concat('0',/WebScript/Script/Summary/failed))" />
	<xsl:variable name="totalPlayed" select="number(concat('0',/WebScript/Script/Summary/played))" />	
	<xsl:variable name="successCount" select="number(concat('0',/WebScript/Script/Summary/succeeded))" />	
	<xsl:variable name="assertionCount" select="number(concat('0',/WebScript/Script/Summary/assertions))" />	
  <xsl:variable name="warningsCount" select="number(concat('0',/WebScript/Script/Summary/warnings))"/>
	<xsl:variable name="successPercentage" select="$successCount div $totalPlayed" />	
  <xsl:variable name="allFailureCount" select="count(descendant::Response[Summary/failed != '0'])"/>
  <!--
  <xsl:variable name="warningsCount" select="count(descendant::ResponseError)"/>
  <xsl:variable name="assertionCount" select="count(descendant::AssertionFailure)"/>
  -->
  <xsl:variable name="screenShotCount" select="count(descendant::CaptureItem)"/>
	<html>
		<head>
		<META http-equiv="Content-Type" content="text/html; charset=US-ASCII"/>
		<title>Badboy Test Results</title>		
		<style type="text/css">
						body {
							font:normal 68% verdana,arial,helvetica;
							color:#000000;
						}
						table tr td, table tr th {
							font-size: 88%;
						}
						table.details tr th{
							font-weight: bold;
							text-align:left;
							background:#a6caf0;
							white-space: nowrap;
						}

						table.details tr td{
							background:#eeeee0;
							white-space: nowrap;
						}

						table.detailsRowHighlight tr {
							background:#ffeee0;
						}

						table.detailsRowNormal tr {
							background:#eeeee0;
						}

						table.detailsCentered tr td{
              text-align: center;
						}

            #treeCellInner {
              background-color: white;
              float: left;
            }

            table tr td.treeCell {
              padding: 0px;
              margin-right: 15px;
              background: url("<xsl:value-of select='$fileName'/>-images/dot.gif") repeat-x right 80%;
            }

            table#overviewTable tr td.failed {
              color: red;
            }

						h1 {
							margin: 0px 0px 5px; font: 165% verdana,arial,helvetica
						}
						h2 {
							margin-top: 1em; margin-bottom: 0.5em; font: bold 125% verdana,arial,helvetica
						}
						h3 {
							margin-bottom: 0.5em; font: bold 115% verdana,arial,helvetica
						}
						.Failure {
							font-weight:bold; color:red;
						}
            .Centered {
              text-align: center;
            }

            a:link.FailureLink {
              color: red;
            }
	          a:visited.FailureLink {
              color: red;
            }

            .responseLogDiv {
              display: none;
            }

            .stepDiv {
              position: relative;
              margin: 3px 0px;
            }

            .summaryBlock {
              position: absolute;
              right: 0px; 
              top: 0px;
            }

            .stepImage {
              position: relative;  
              margin-right: 3px;
            }

            .headerCell {
              width: 40px;
              padding: 3px 2px;
              font-weight: bold;
              font-size: 10px;
              background: #a6caf0;
              white-space: nowrap;
              margin: 0px 2px;
              text-align: center;
            }

             .total {
              position: relative;
              top: 1px;
              width: 42px;
              background-color:#eeeee0;
              margin-top: 0px;
              margin-bottom: 0px;
              margin-left: 2px;
              margin-right: 2px;
              text-align: center;
            }


            .bigSuccess {
              color: green;
              font-size: 20px;
            }

            .bigFailure {
              color: red;
              font-size: 20px;
            }

            .bigWarning {
              color: orange;
              font-size: 20px;
            }
            ul.summaryCheck {
              padding: 1px 10px;
              margin: 4px 10px;
            }
            .hidden {
              display: none;
            }
      </style>
			<script language="javacript" type="text/javascript">
        var imagesPath='<xsl:value-of select="translate($fileName,'\','/')"/>-images';
      <![CDATA[
      function isDescendent(trChild, trParent) {
        if(trParent == trChild)
          return false;

          var parentId = trChild.getAttribute('parentId');
          if(parentId == trParent.getAttribute('scriptId')) {
            return true;
          }
        
        trChild.parent = document.getElementById('treeRow'+parentId);
        if(trChild.parent)
          return isDescendent(trChild.parent, trParent);
        else
          return false;
      }

      function expandTreeStep(stepId) {
        var parentRow = document.getElementById('treeRow'+stepId);
        parentRow.collapsed = parentRow.collapsed?false:true;
        var trs = document.getElementById('overviewTable').getElementsByTagName('tr');
        for(var i=0; i<trs.length; ++i) {
          if(trs[i] == parentRow)
            break;
        }
        ++i;
        for(; i<trs.length; ++i) {
          var tr=trs[i];
          if(isDescendent(tr,parentRow)) {
            if(parentRow.collapsed) {
              tr.className='hidden';
            }
            else {
              var p = tr.parent;
              while(p && !p.collapsed)
                p = p.parent;

              if(p && p.collapsed)
                tr.className='hidden';
              else
                tr.className='';
            }
          }
          else
            break; // First encounter with non-child tells us we are finished
        }
        document.getElementById('stepImage'+stepId).src = parentRow.collapsed ? imagesPath+'/p.gif' : imagesPath+'/m.gif';
      }

			function showFailed( divID ) {			
        failedHTML = document.getElementById(divID).innerHTML;
          newWin = window.open("error.html",'newWin',"height=800,width=600,status=yes,scrollbars=yes,toolbar=yes,menubar=yes,location=no");
          newWin.document.write(unescape(failedHTML.replace(/&amp;amp;/g,"&amp;").replace(/&amp;gt;/g,"&gt;").replace(/&amp;lt;/g,"&lt;")));			  
          newWin.focus();	          
       }

      function showLogContent(logId) {
         var logHTML=document.getElementById('log-'+logId).innerHTML;
         var logWin = window.open("log.html",'logWin',"height=500,width=800,status=yes,scrollbars=yes,toolbar=yes,menubar=yes,location=no");
         logWin.document.write(unescape(logHTML.replace(/&amp;amp;/g,"&amp;").replace(/&amp;gt;/g,"&gt;").replace(/&amp;lt;/g,"&lt;")));			  
         logWin.focus();	          
      }

      var highlights = new Array();
      function highlight(ids) {
        for(i=0;i<highlights.length; i++){
          var el = highlights[i];
          if(el) el.style.color='black';
        }
        highlights = new Array();
        if(ids.splice) {//array of ids passed
          for(i=0;i<ids.length; i++){
            var el = document.getElementById(ids[i]);
            if(el) el.style.color='red';
            if(el) el.style.fontWeight='bold';
            highlights.push(el);
          }
        }
        else {// single id passed
          var el = document.getElementById(ids);
          for(j=0; j<document.all.length; j++) {
            var el = document.all[j];
            if(el.id && (el.id==ids)) {
              el.style.color='red';
              el.style.fontWeight='bold';
              highlights.push(el);
            }
          }
        }
      }
        ]]>
      </script>		
      </head>
			<body>
        <h1>Badboy Test Results</h1>
        Report created <xsl:value-of select="$saveDateTime"/>
				<hr size="1"/>				
				<h2>Overview</h2>
				<table width="95%" cellspacing="2" cellpadding="5" border="0" class="details detailsCentered">
					<tr valign="top">
						<th>Total Played</th>
						<th>Succeeded</th>
						<th>Failed</th>
            <th>Warnings</th>
						<th>Assertions</th>
						<th>Average Time</th>
						<th>Max Time</th>
					</tr>
					<tr valign="top" class="">
						<td><xsl:value-of select="$totalPlayed"/></td>
						<td><xsl:value-of select="$successCount"/></td>						
						<td><xsl:value-of select="$failureCount"/></td>						
						<td><xsl:value-of select="$warningsCount"/></td>						
						<td><xsl:value-of select="$assertionCount"/></td>						
						<td><xsl:value-of select="round(/WebScript/Script/Summary/averageResponseTime)"/></td>
						<td><xsl:value-of select="/WebScript/Script/Summary/maxResponseTime"/></td>
					</tr>
				</table>

        <!-- If everything passed, show a "success"  -->
        <xsl:choose>
          <xsl:when test=' $totalPlayed = $successCount and $warningsCount = 0 and $assertionCount = 0 and $failureCount = 0'>
            <p class="bigSuccess">Success</p>
          </xsl:when>
          <xsl:when test=' $totalPlayed = $successCount and $warningsCount != 0 and $assertionCount = 0 and $failureCount = 0'>
            <p class="bigWarning">Passed with Warnings</p>
          </xsl:when>
          <xsl:otherwise>
            <p class="bigFailure">Test Failed</p>
          </xsl:otherwise>
        </xsl:choose>

        <hr align="left" width="95%" size="1"/>

        <table id='overviewTable' style="position: relative; width: 95%">
          <thead>
            <tr>
              <td width='500px'>&#160;</td>
              <td>&#160;</td>
              <td class="headerCell"><div>Total</div></td> 
              <td class="headerCell"><div>Succ</div></td> 
              <td class="headerCell"><div>Fail</div></td> 
              <td class="headerCell"><div>Wrn</div></td> 
              <td class="headerCell"><div>Asst</div></td> 
              <td class="headerCell"><div>Avg</div></td> 
              <td class="headerCell"><div>Max</div></td> 
            </tr>
          </thead>
          <tbody>
            <xsl:for-each select="/WebScript/Script/ChildItems/*[local-name()='Step' or local-name()='TestItem' or local-name()='SuiteItem']">
              <xsl:if test='disabled != "true"'>
                  <xsl:call-template name="Step"/>
              </xsl:if>
            </xsl:for-each>        
          </tbody>
        </table>

        <br/>
        <xsl:if test="$assertionCount != 0">
          <hr align="left" width="95%" size="1"/>
          <h2>Assertion Summary</h2>
          <table width="95%" cellspacing="2" cellpadding="5" border="0" class="details">
            <tr valign="top">
              <th width="10%">Id</th>
              <th width="15%">Name</th>
              <th width="40%" nowrap="nowrap">Rule</th>
              <th width="15%">Status (Success or Failed) </th>
              <th width="10%">Failures</th>
            </tr>
            <xsl:for-each select="//Assertion[disabled!='true']">
              <xsl:call-template name="AssertionLineItem"/>
            </xsl:for-each>					
            <xsl:for-each select="//AggregateItem[Summary/assertions!=0]">
              <tr>
                <td><xsl:value-of select="id"/></td>
                <td colspan='4' align='center'>
                  Aggregated Script <xsl:value-of select='scriptFile'/> had <xsl:value-of select='Summary/assertions'/> assertion failure(s)
                  but did not report details or screen shot.
                </td></tr>
            </xsl:for-each>					
          </table>
        </xsl:if>

        <xsl:if test="$failureCount != 0 and allFailureCount != 0">
          <hr align="left" width="95%" size="1"/>
          <h2>Failures (<xsl:value-of select="$allFailureCount"/>)</h2>
          <table width="95%" cellspacing="2" cellpadding="5" border="0" class="details">
            <tr valign="top">
              <th width="15%">Response Id</th>
              <th width="15%">Source Id</th>
              <th width="50%" nowrap="nowrap">URL/Name</th>
              <th width="20%">Error Code</th>
            </tr>
            <xsl:for-each select="descendant::Response[Summary/failed != '0']" >
                <xsl:call-template name="ResponseFailureLineItem"/>
            </xsl:for-each>					
          </table>
        </xsl:if>

        <xsl:if test="$warningsCount != 0">
				<hr align="left" width="95%" size="1"/>
        <h2>Playback Errors/Warnings (<xsl:value-of select="$warningsCount"/>)</h2>
				<table width="95%" cellspacing="2" cellpadding="5" border="0" class="details">
					<tr valign="top">
						<th width="20%">Id</th>
						<th width="15%">Source Id</th>
						<th width="20%">Category</th>
						<th width="40%">Description</th>
					</tr>
						<xsl:for-each select="//ResponseError">
							<xsl:call-template name="ResponseErrorLineItem"/>
						</xsl:for-each>					
          </table>
        </xsl:if>

        <xsl:if test="count(//AssertionFailure) != 0">
          <hr align="left" width="95%" size="1"/>
          <h2>Assertion Failure Details</h2>
          <table width="95%" cellspacing="2" cellpadding="5" border="0" class="details">
            <tr valign="top">
              <th width="10%">Assertion Id</th>
              <th width="15%">Name</th>
              <th width="40%" nowrap="nowrap">Description</th>
              <th width="10%">ScreenShot</th>
            </tr>
              <xsl:for-each select="//AssertionFailure">
                <xsl:call-template name="AssertionFailureLineItem"/>
              </xsl:for-each>					
          </table>
        </xsl:if>

        <xsl:if test="$screenShotCount != 0">
          <hr align="left" width="95%" size="1"/>
          <h2>Screen Shot Summary</h2>
          <table width="50%" cellspacing="2" cellpadding="5" border="0" class="details">
            <tr valign="top">
              <th width="20%">Id</th>
              <th width="50%">Name</th>
              <th width="30%">Thumbnail</th>
            </tr>
            <xsl:for-each select="//CaptureItem">
                <xsl:call-template name="ScreenShotLineItem"/>
            </xsl:for-each>					
          </table>
        </xsl:if>
          
        
        <xsl:if test='count(descendant::Request[Summary/played != 0]) != 0'>
          <hr align="left" width="95%" size="1"/>
          <h2>All Requests</h2>
          <table width="95%" cellspacing="2" cellpadding="5" border="0" class="details">
            <tr valign="top">
              <th width="20%">Request Id/Label</th>
              <th width="40%" nowrap="nowrap">URL</th>
              <th width="10%">Status (Success or Failed) </th>
              <th width="15%">Avg Time</th>
              <th width="15%">Max Time</th>
            </tr>
              <xsl:for-each select="//Request">
                <xsl:call-template name="RequestLineItem"/>
              </xsl:for-each>					
            </table>          
        </xsl:if>

        <xsl:if test="$screenShotCount != 0">
          <hr align="left" width="95%" size="1"/>				
          <h2>Screen Shot Detail</h2>
          <xsl:for-each select="//CaptureItem">
              <xsl:call-template name="FullSizeScreenShot"/>
          </xsl:for-each>					
        </xsl:if>

			</body>
		</html>
    </xsl:template>

    <xsl:template name="Step">
      <xsl:param name="depth">0</xsl:param>
      <xsl:param name="display">block</xsl:param>
      <xsl:variable name="lcletters">abcdefghijklmnopqrstuvwxyz</xsl:variable>
      <xsl:variable name="ucletters">ABCDEFGHIJKLMNOPQRSTUVWXYZ</xsl:variable>
      <xsl:variable name="type" select="translate(local-name(),$ucletters,$lcletters)"/>
      <xsl:variable name="numChildren" select="count(descendant::Step) + count(descendant::TestItem) +  count(descendant::ThreadItem)"/>
      <xsl:if test='local-name()="Step" or local-name()="TestItem" or local-name()="SuiteItem" or local-name()="ThreadItem"'>

        <tr id="treeRow{id}" scriptId="{id}" parentId="{../../id}">      
          <td class='treeCell'>
            <div id='treeCellInner'>
              <img src="{$fileName}-images/spacer.gif" width="{$depth*20}" height="0"/>
              <img id="stepImage{id}" onclick="expandTreeStep({id});" class="stepImage">
                <xsl:choose>
                  <xsl:when test='$numChildren != 0'> <!-- All nodes with children except root -->
                    <xsl:attribute name="src"><xsl:value-of select="concat($fileName,'-images/m.gif')"/></xsl:attribute>
                    <xsl:attribute name="hasChildren">true</xsl:attribute>
                    <xsl:attribute name="style">cursor: pointer;</xsl:attribute>
                  </xsl:when>
                  <xsl:otherwise>
                    <xsl:attribute name="src"><xsl:value-of select="concat($fileName,'-images/spacer.gif')"/></xsl:attribute>
                    <xsl:attribute name="width">9</xsl:attribute>
                    <xsl:attribute name="height">10</xsl:attribute>
                    <xsl:attribute name="hasChildren">false</xsl:attribute>
                  </xsl:otherwise>
                </xsl:choose>
              </img>
              <img src="{$fileName}-images/{$type}icon.gif"/>

              <xsl:choose>
                <xsl:when test='string-length(name)>0 or string-length(title)>0 or string-length(itemName)>0'>
              &#160;<xsl:value-of select="name"/><xsl:value-of select="title"/>&#160;<xsl:value-of select="itemName"/>&#160;
                </xsl:when>
                <xsl:when test='local-name()="ThreadItem"'>Thread Item</xsl:when>
              </xsl:choose>
            </div>
          </td>
          <td>&#160;</td>
          <td class="total"><div><xsl:value-of select="number(concat('0',Summary/played))"/>&#160;</div></td> 
          <td class="total"><div><xsl:value-of select="number(concat('0',Summary/succeeded))"/>&#160;</div></td> 
          <td class="total"><div><xsl:value-of select="number(concat('0',Summary/failed))"/>&#160;</div></td> 
          <td class="total"><div><xsl:value-of select="number(concat('0',Summary/warnings))"/>&#160;</div></td> 
          <td class="total">
            <xsl:if test='$numChildren = 0 and Summary/assertions > 0'> 
                <xsl:attribute name='class'>total failed</xsl:attribute>
              </xsl:if>
            <div><xsl:value-of select="number(concat('0',Summary/assertions))"/>&#160;</div></td> 
          <td class="total"><div><xsl:if test='Summary/averageResponseTime != ""'><xsl:value-of select="round(Summary/averageResponseTime)"/></xsl:if>&#160;</div></td> 
          <td class="total"><div><xsl:value-of select="Summary/maxResponseTime"/>&#160;</div></td> 
      </tr>
    </xsl:if>
    <span id="treeStepChildren{id}" style="display: {$display};">
      <xsl:for-each select="ChildItems/*">
        <xsl:choose>
          <xsl:when test='local-name()="Step" or local-name()="TestItem"'>
            <xsl:call-template name="Step">
              <xsl:with-param name="depth" select="$depth + 1"/>
              <xsl:with-param name="display" select="'none'"/>
            </xsl:call-template>
          </xsl:when>
          <xsl:when test='local-name()="ThreadItem"'>
            <xsl:call-template name="Step">
              <xsl:with-param name="depth" select="$depth + 1"/>
              <xsl:with-param name="display" select="'block'"/>
            </xsl:call-template>
          </xsl:when>
        </xsl:choose>
      </xsl:for-each>        
    </span>
  </xsl:template>

    
  <xsl:template name="RequestLineItem">
    <xsl:variable name="divID">responseDiv-<xsl:value-of select="id"/></xsl:variable>
    <xsl:variable name="responseHTML" select="descendant::Response[Summary/failed != 0]/content"/>
    <xsl:variable name="responseLog" select="descendant::Response[Summary/failed != 0]/logContent"/>
    <xsl:variable name="responseId" select="descendant::Response[Summary/failed != 0]/id"/>
    <xsl:if test='Summary/played != 0'>
      <tr valign="top" class="">
        <td nowrap="nowrap"><xsl:value-of select="id"/><xsl:if test="label != ''">&#160;-&#160;<xsl:value-of select="./label"/></xsl:if></td>
        <td><xsl:value-of select="./protocol"/>://<xsl:value-of select="./host"/>
          <xsl:if test='./Port != 80'>:<xsl:value-of select="./Port"/></xsl:if><xsl:value-of select="./path"/></td>
        <td class="Centered">
        <xsl:choose>
          <xsl:when test="./Summary/failed != '0'"> 
           <span style="color:red">Failed - Error&#160;
             <xsl:value-of select="descendant::Response[errorCode != '0']/errorCode"/></span><div id="{$divID}" style="display:none;">
                    <xsl:value-of  select="$responseHTML"/>
                  </div>
            <xsl:if test="$responseLog != ''">&#160;
              <a class="FailureLink" href="javascript:showLogContent({$responseId})">
                <xsl:attribute name="title"><xsl:value-of select="concat(substring($responseLog,0,80),'...')"/></xsl:attribute>
                (log)</a>
            </xsl:if>
          </xsl:when>
          <xsl:when test="./following-sibling::*[1][self::Assertion]/Passed = 'false'">
            <a class="FailureLink" href="javascript:showFailed('{$divID}');">
            <div id="{$divID}" style="display:none;">
              <xsl:value-of select="$responseHTML"/>
            </div>Failed Assertion</a>           
          </xsl:when>
          <xsl:otherwise><span style="color:green;">Success</span></xsl:otherwise>
        </xsl:choose>
      </td>
      <td class="Centered"><xsl:value-of select="round(./Summary/averageResponseTime)"/></td>				
      <td class="Centered"><xsl:value-of select="./Summary/maxResponseTime"/></td>				
      </tr>		
    </xsl:if>
	</xsl:template>

  <xsl:template name="AssertionLineItem">
      <tr valign="top" class="">
      <td nowrap="nowrap"><xsl:value-of select="id"/></td>
      <td><xsl:value-of select="itemName"/></td>
      <td>
        <xsl:for-each select="ChildItems/*">
          <xsl:choose>
            <xsl:when test="local-name()='ContentCheckItem'">
              Page <xsl:choose><xsl:when test='type = 1'>does not contain</xsl:when><xsl:otherwise>contains</xsl:otherwise></xsl:choose>
              pattern &#160; '<xsl:call-template name="wrap_text"><xsl:with-param name="text" select="pattern"/><xsl:with-param name="width" select="100"/></xsl:call-template>'
            </xsl:when>
            <xsl:when test="local-name()='BitmapCheckItem'">
              Color RGB(<xsl:value-of select="r"/>,<xsl:value-of select="b"/>,<xsl:value-of select="g"/>)
              <xsl:value-of select="Type"/>&#160;at&#160;
              (<xsl:value-of select="number(concat('0',x))"/>,<xsl:value-of select="number(concat('0',y))"/>)
            </xsl:when>
            <xsl:when test="local-name()='ResponseCheckItem'">
              Response Time is <xsl:value-of select="number(concat('0',minSeconds))"/> to <xsl:value-of select="number(concat('0',maxSeconds))"/> secs
              <xsl:if test="checkSize = 'true'">
                , Size is <xsl:value-of select="number(concat('0',minSizeKB))"/>&#160;to&#160;<xsl:value-of select="number(concat('0',maxSizeKB))"/> KB
              </xsl:if>
            </xsl:when>
            <xsl:when test="local-name()='SummaryCheckItem'">
              <xsl:choose>
                <xsl:when test="locatorType = '1'">Item <xsl:value-of select="id"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="summarySelector"/></xsl:otherwise>
              </xsl:choose>
              has <xsl:choose><xsl:when test='combineType=1'>one of</xsl:when><xsl:otherwise>all of</xsl:otherwise></xsl:choose>
              <ul class="summaryCheck">
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>played</xsl:with-param></xsl:call-template>
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>succeeded</xsl:with-param></xsl:call-template>
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>failed</xsl:with-param></xsl:call-template>
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>warnings</xsl:with-param></xsl:call-template>
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>assertions</xsl:with-param></xsl:call-template>
                <xsl:call-template name="SummaryCheckRule"><xsl:with-param name='stat'>timeouts</xsl:with-param></xsl:call-template>
              </ul>
            </xsl:when>
            <xsl:when test="local-name()='VariableCheck'">
              Variable  <xsl:value-of select="variableName"/> matches pattern '<xsl:value-of select="regex"/>'
            </xsl:when>
          </xsl:choose>
          <xsl:if test='position()!=last()'><br/></xsl:if>
        </xsl:for-each>
      </td>
      <td class="Centered"><xsl:choose>
          <xsl:when test="count(Summary/assertions) = 0 or Sumary/assertions = 0"><span style="color:green;"> Passed</span></xsl:when>
            <xsl:otherwise>
              <a style="color: red;">
                <xsl:attribute name="href">#assertionDetail<xsl:value-of select="id"/></xsl:attribute>
                <xsl:attribute name="onclick">highlight('assertionDetailRow<xsl:value-of select="id"/>');</xsl:attribute>
                <span style="color:red;"> Failed</span>
              </a>
             </xsl:otherwise>
          </xsl:choose>
      </td>
      <td class="Centered"><xsl:value-of select="./Summary/assertions"/></td>				
    </tr>		
	</xsl:template>

  <xsl:template name="SummaryCheckRule">
    <xsl:param name="stat">0</xsl:param>
    <xsl:if test='max/*[local-name()=$stat] >= 0'> <!-- less than zero means disabled -->
    <li>
        <xsl:value-of select="$stat"/> in range [<xsl:value-of select="number(concat('0',min/*[local-name()=$stat]))"/> , <xsl:value-of select="number(concat('0',max/*[local-name()=$stat]))"/>]
    </li>
    </xsl:if>
  </xsl:template>

  <xsl:template name="AssertionFailureLineItem">
      <tr valign="top" class="">
        <xsl:attribute name="name">assertionDetail<xsl:value-of select="../../id"/></xsl:attribute>
        <xsl:attribute name="id">assertionDetailRow<xsl:value-of select="../../id"/></xsl:attribute>
        <td><a><xsl:attribute name="name">assertionDetail<xsl:value-of select="../../id"/></xsl:attribute><xsl:value-of select="../../id"/></a></td>
      <td><xsl:value-of select="../../itemName"/></td>
      <td><xsl:value-of select="./description"/>&#160;<xsl:value-of select="../../pattern"/></td>
      <td><xsl:for-each select="ChildItems/CaptureItem">
          <a target="assertionImage">
            <xsl:attribute name="href"><xsl:value-of select="$fileName"/>-images/badboy-<xsl:value-of select="id"/>.png</xsl:attribute>
            <img height="60" width="60" border="0">
              <xsl:attribute name="src"><xsl:value-of select="$fileName"/>-images/badboy-<xsl:value-of select="id"/>.png</xsl:attribute>
            </img>
          </a>
        </xsl:for-each>
      </td>				
    </tr>		
	</xsl:template>
  
  <xsl:template name="ResponseErrorLineItem">
      <tr valign="top" class="">
      <td><xsl:value-of select="id"/></td>
      <td><xsl:value-of select="../../id"/></td>
      <td><xsl:value-of select="category"/></td>
      <td><xsl:value-of select="description"/> 
        <xsl:if test='url != ""'> in <xsl:value-of select="url"/></xsl:if></td>
    </tr>		
	</xsl:template>

  <xsl:template name="ScreenShotLineItem">
      <tr valign="top" class="">
      <td><xsl:value-of select="id"/></td>
      <td><xsl:value-of select="../../itemName"/></td>
      <td valign="center">
        <a>
          <xsl:attribute name="href">#ss<xsl:value-of select="id"/></xsl:attribute>
          <xsl:attribute name="onclick">window.open('<xsl:value-of select="$fileName"/>-images/badboy-<xsl:value-of select="id"/>.png','screenshot','height=800,width=600,status=yes,scrollbars=yes,toolbar=yes,menubar=yes,location=no'); return false;</xsl:attribute>
          <img height="60" width="60" border="0">
            <xsl:attribute name="src"><xsl:value-of select="$fileName"/>-images/badboy-<xsl:value-of select="id"/>.png</xsl:attribute>
          </img>
        </a>
      </td>				
    </tr>		
	</xsl:template>

  <xsl:template name="FullSizeScreenShot">
    <h3><a>
        <xsl:attribute name="name">ss<xsl:value-of select="id"/></xsl:attribute>
        </a><xsl:value-of select="Name"/></h3>
    <img border="0">
      <xsl:attribute name="src"><xsl:value-of select="$fileName"/>-images/badboy-<xsl:value-of select="id"/>.png</xsl:attribute>
    </img>
	</xsl:template>

  <xsl:template name="ResponseFailureLineItem">
    <xsl:variable name="responseId" select="id"/>
      <tr valign="top" class="">
      <td><xsl:value-of select="id"/></td>
      <td><xsl:value-of select="../../id"/></td>
      <td><xsl:choose>
        <xsl:when test="string(../../../../path)"><xsl:value-of select="../../../../path"/></xsl:when>
        <xsl:when test="string(../../../../host)"><xsl:value-of select="../../../../host"/></xsl:when>
        <xsl:otherwise><xsl:value-of select="local-name(../../../..)"/> : <xsl:value-of select="../../../../ItemName"/></xsl:otherwise>
      </xsl:choose>
      </td>
      <td><xsl:value-of select="errorCode"/><xsl:if test='string-length(logSection) &gt; 0'>&#160;
          <a href="javascript:showLogContent({$responseId})"><xsl:attribute name="title"><xsl:value-of select="concat(substring(logSection,0,80),'...')"/></xsl:attribute>(show log)</a></xsl:if>
        <div id="log-{$responseId}" class="responseLogDiv"><pre style="font-size: 11px;"><xsl:value-of select="logSection"/></pre></div>
      </td>
    </tr>		
	</xsl:template>

  <!-- Utility function -->
  <xsl:template name="wrap_text">
  <xsl:param name="text"/>
  <xsl:param name="width" select="40"/>

  <xsl:if test="string-length($text)">
    <xsl:value-of select="substring($text, 1, $width)"/>
    <xsl:if test="string-length($text)>$width"><br/></xsl:if>
    <xsl:call-template name="wrap_text">
      <xsl:with-param name="text"
       select="substring($text, $width + 1)"/>
      <xsl:with-param name="width" select="$width"/>
    </xsl:call-template>
  </xsl:if>  	
  </xsl:template>
</xsl:stylesheet>
