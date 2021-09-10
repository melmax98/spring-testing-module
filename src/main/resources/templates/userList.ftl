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
        <#list users as user>
            <tr>
                <td>${user.getUserId()}</td>
                <td>${user.getName()}</td>
                <td>${user.getEmail()}</td>
                <td><a href="/user/${user.getUserId()}">edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>