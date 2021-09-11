<head>
    <title>Create user</title>
    <style>
        input[type=text] {
            width: 20%;
            box-sizing: border-box;
        }

        h2 {
            font: 2rem 'AmstelvarAlpha', sans-serif;
            text-align: center;
        }
    </style>
</head>

<div id="header">
    <h2>Create User</h2>
</div>
<div id="content">
    <form action="/user" method="post">
        <label>
            <h4>Name</h4>
            <input type="text" name="name"/>
        </label>
        <label>
            <h4>Email</h4>
            <input type="text" name="email"/>
        </label>
        <button type="submit">Create</button>
    </form>
</div>