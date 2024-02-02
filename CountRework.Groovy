import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueInputParameters
import com.atlassian.jira.issue.fields.CustomField
import java.text.DecimalFormat
import java.text.SimpleDateFormat


Double reworkData = (issue.getCustomFieldValue('Rework') as Double)
if ( reworkData == null) 
 reworkData = 1
else
 reworkData = reworkData + 1

log.warn("Rework:${reworkData}")

DecimalFormat df = new DecimalFormat("0.0#");
	def rework = df.format(reworkData)
issue.update
{
     setCustomFieldValue('Rework',rework) 
}
