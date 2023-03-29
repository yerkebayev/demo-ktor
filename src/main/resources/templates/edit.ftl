<!DOCTYPE html>
<html lang="en">
<head>
    <title>Module</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px;">
<div>
    <h1>Edit module</h1>
    <form action="/modules/${module.id}" method="post">
        <p>
        <h4>Name</h4>
        <input type="text" name="name" value="${module.name}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Type</h4>
        <input type="text" name="type" value="${module.type}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Created date</h4>
        <input type="date" name="createdAt" value="${module.createdAt}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Duration in month</h4>
        <input type="number" name="duration" value="${module.duration}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Status</h4>
        <input type="text" name="status" value="${module.status}" style="height: 50px; font-size: 36px">
        </p>
        <p>
        <h4>Description</h4>
        <textarea name="description" style="height: 100px; font-size: 36px">${module.description}</textarea>
        </p>
        <p>
            <input type="submit" name="_action" value="update" style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<div>
    <form action="/modules/${module.id}" method="post">
        <p>
            <input type="submit" name="_action" value="delete"  style="height: 50px; font-size: 36px">
        </p>
    </form>
</div>
<a href="/">Back to the main page</a>
</body>
</html>