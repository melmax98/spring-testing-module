<div id="header">
    <h2>Event editor</h2>
</div>
<div id="content">
    <form action="/event/update/${event.getEventId()}" method="post">
        <label>
            <input type="text" value="${event.getTitle()}" name="title"/>
        </label>
        <label>
            <input type="text" value="${event.getDate()?date}" name="date"/>
        </label>
        <input type="hidden" value="${event.getEventId()}" name="eventId"/>
        <button type="submit">Save</button>
    </form>
    <form action="/event/delete/${event.getEventId()}" method="post">
        <input type="hidden" value="${event.getEventId()}" name="eventId"/>
        <button type="submit">Delete</button>
    </form>
</div>