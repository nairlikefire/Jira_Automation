// Unable to add package declaration. The parent folder names contain identifiers that are not valid in a package name.
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.jql.parser.JqlQueryParser
import com.atlassian.jira.web.bean.PagerFilter
 
def user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def status = issue.getStatus().getSimpleStatus().getName()
def applicationUser = issue.getAssigneeUser()

if(null == applicationUser)
{
    applicationUser = user
}

if( status == "In progress")
{
    log.warn("\n Status: ${status} Transition User: ${user} Assignee:${applicationUser}")
 issue.update
    {
        setCustomFieldValue('Fixed By Developer',applicationUser) 
    }   
}
