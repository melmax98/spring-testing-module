<div id="header">
    <h2>List of events</h2>
</div>
<div id="content">
    <table>
        <thead>
        <th>ID</th>
        <th>Title</th>
        <th>Date</th>
        <th></th>
        </thead>
        <tbody>
        <#list events as event>
            <tr>
                <td>${event.getEventId()}</td>
                <td>${event.getTitle()}</td>
                <td>${event.getDate()?date}</td>
                <td><a href="/event/${event.getEventId()}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>