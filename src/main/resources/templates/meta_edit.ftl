<!DOCTYPE html>
<html lang="en">
<head>
    <title>Module</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px;">
<div>
    <h1>Edit meta</h1>
    <form action="/modules/${module.id}/${meta.id}" method="post">
        <p>
        <h4>Key</h4>
        <input type="text" name="key" value="${meta.metaKey}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Value</h4>
        <input type="text" name="value" value="${meta.metaValue}" style="height: 50px; font-size: 36px">
        </p>
        <p>
            <input type="submit" name="_action" value="update" style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<div>
    <form action="/modules/${module.id}/${meta.id}" method="post">
        <p>
            <input type="submit" name="_action" value="delete"  style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<a href="/modules/${module.id}/edit">Back to the module edit page</a>
<hr>
<a href="/">Back to the main page</a>
</body>
</html>