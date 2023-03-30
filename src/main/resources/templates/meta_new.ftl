<!DOCTYPE html>
<html lang="en">
<head>
    <title>Meta</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px">
<div>
    <h1>Create meta</h1>
    <form action="/modules/${module.id}/edit" method="post">
        <p>
        <h4>Key</h4>
        <input type="text" name="key" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Value</h4>
        <input type="text" name="value" style="height: 50px; font-size: 36px">
        </p>
        <p>
            <input type="submit" style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<a href="/modules/${module.id}/edit">Back to the module edit page</a>
<hr>
<a href="/">Back to the main page</a>
</body>
</html>