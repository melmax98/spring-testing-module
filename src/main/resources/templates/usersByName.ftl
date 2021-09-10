<div id="header">
    <h2>List of users</h2>
</div>
<div id="content">
    <table>
        <thead>
        <th>ID</th>
        <th>Name</th>
        <th>Email</th>
        <th></th>
        </thead>
        <tbody>
        <#list usersByName as user>
            <tr>
                <td>${user.getUserId()}</td>
                <td>${user.getName()}</td>
                <td>${user.getEmail()}</td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>