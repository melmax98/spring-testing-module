<div id="header">
    <h2>User editor</h2>
</div>
<div id="content">
    <form action="/user/update/${user.getUserId()}" method="post">
        <label>
            <input type="text" value="${user.getName()}" name="name"/>
        </label>
        <label>
            <input type="text" value="${user.getEmail()}" name="email"/>
        </label>
        <input type="hidden" value="${user.getUserId()}" name="userId"/>
        <button type="submit">Save</button>
    </form>
    <form action="/user/delete/${user.getUserId()}" method="post">
        <input type="hidden" value="${user.getUserId()}" name="userId"/>
        <button type="submit">Delete</button>
    </form>
</div>