<!DOCTYPE html>
<html lang="en">
<head>
    <title>Module</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px">
<div>
    <h1>Create module</h1>
    <form action="/modules" method="post">
        <p>
        <h4>Name</h4>
        <input type="text" name="name" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Type</h4>
        <input type="text" name="type" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Created date</h4>
        <input type="date" name="createdAt" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Duration in month</h4>
        <input type="number" name="duration" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Status</h4>
        <input type="text" name="status" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Description</h4>
<#--        <input type="text" name="description" style="height: 100px; font-size: 36px">-->
        <textarea name="description" style="height: 100px; font-size: 36px"></textarea>
        </p>
        <p>
            <input type="submit" style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<a href="/">Back to the main page</a>
</body>
</html>