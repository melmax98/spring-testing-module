<head>
    <title>Cancel Ticket</title>
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
    <h2>User editor</h2>
</div>
<div id="content">
    <form action="/ticket/cancel/${ticket.getTicketId()}" method="post">
        <table>
            <thead>
            <th>ID</th>
            <th>User</th>
            <th>Event</th>
            <th>Category</th>
            <th>Place</th>
            <th></th>
            </thead>
            <tbody>
            <tr>
                <td>${ticket.getTicketId()}</td>
                <td>${ticket.getUser()}</td>
                <td>${ticket.getEvent()}</td>
                <td>${ticket.getCategory()}</td>
                <td>${ticket.getPlaceNumber()}</td>
                <td><a href="/ticket/${ticket.getTicketId()}">cancel</a></td>
            </tr>
            </tbody>
        </table>
        <button class="button" type="submit">Cancel</button>
    </form>
</div>