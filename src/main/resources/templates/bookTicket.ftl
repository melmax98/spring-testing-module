<head>
    <title>Create event</title>
    <style>
        input[type=text] {
            width: 5%;
            box-sizing: border-box;
        }

        h2 {
            font: 2rem 'AmstelvarAlpha', sans-serif;
            text-align: center;
        }

        div {
            margin-top: 10px;
        }
    </style>
</head>

<div id="header">
    <h2>Create Event</h2>
</div>
<div id="content">
    <form action="/ticket" method="post">
        <label>
            <h4>User</h4>
            <select name="userId">
                <#list users as user>
                    <option value="${user.getUserId()}">${user}</option>
                </#list>
            </select>
        </label>
        <label>
            <h4>Event</h4>
            <select name="eventId">
                <#list events as event>
                    <option value="${event.getEventId()}">${event}</option>
                </#list>
            </select>
        </label>
        <label>
            <h4>Category</h4>
            <select name="categoryName">
                <#list categories as category>
                    <option value="${category.name()}">${category}</option>
                </#list>
            </select>
        </label>
        <label>
            <h4>Place</h4>
            <input type="text" name="place"/>
        </label>
        <div>
            <button type="submit">Create</button>
        </div>
    </form>
</div>