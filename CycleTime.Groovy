import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.IssueInputParameters
import com.atlassian.jira.issue.fields.CustomField
import java.util.Date
import java.text.DecimalFormat
import java.text.SimpleDateFormat

log.warn(" Created time start")
def changeHistoryManager = ComponentAccessor.getChangeHistoryManager()
def created = changeHistoryManager.getChangeItemsForField(issue, "status").find {
    it.toString == "In progress" 
}?.getCreated()

if(null == created )
{
    log.warn(" Created is null and setting it to current time")
    created = new java.util.Date()
}

log.warn(" get resolved date")

def resolved = changeHistoryManager.getChangeItemsForField(issue, "status").find {
    it.toString == "Done" 
}?.getCreated()

if(null == resolved )
{
    log.warn(" Resolved date is null and setting it to current time")
    resolved = new java.util.Date()
}

def inProgressDateRaw = created?.getTime()
def resolvedDateRaw = resolved?.getTime()

def inProgressDate = created?.getTime()
def resolvedDate = resolved?.getTime()

inProgressDate ? new Date(inProgressDate) : null
resolvedDate ? new Date(resolvedDate) : null

SimpleDateFormat  dateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
  

log.warn(" Created time ${dateFormat.format(inProgressDate)}")
log.warn(" Resolved time ${dateFormat.format(resolvedDate)}")

if (null != inProgressDate && null != resolvedDate){
	
	// 1000*60*60*24=86400000
	def cycleTimeRaw = ( resolvedDateRaw - inProgressDateRaw )/ 86400000
	  
   log.warn(" cycle time ${cycleTimeRaw}")

   DecimalFormat df = new DecimalFormat("0.0#");
	def cycleTime = df.format(cycleTimeRaw)
   
   log.warn(" cycle time ${cycleTime}")
   

	if (null != cycleTime) {
		issue.update{
         setCustomFieldValue('Cycle Time',cycleTime) 
    }
	}
}
