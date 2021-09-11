<head>
    <title>List of users</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            text-align: center;
            padding: 8px;
        }

        h2 {
            font: 2rem 'AmstelvarAlpha', sans-serif;
            text-align: center;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2
        }

        th {
            background-color: #04AA6D;
            color: white;
        }
    </style>
</head>

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