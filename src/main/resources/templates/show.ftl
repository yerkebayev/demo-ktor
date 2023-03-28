<#-- @ftlvariable name="module" type="com.example.models.Module" -->
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Module</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px">
<h1>There are about Modules </h1>
<hr>
<div>
    <p>
    <h4>Name</h4>
    <p>${module.name}</p>
    </p>
    <p>
    <h4>Type</h4>
    <p>${module.type}</p>
    </p>
    <p>
    <h4>Created date</h4>
    <p>${module.createdAt}</p>
    </p>
    <p>
    <h4>Duration in month</h4>
    <p>${module.duration}</p>
    </p>
    <p>
    <h4>Status</h4>
    <p>${module.status}</p>
    </p>
    <p>
    <h4>Description</h4>
    <p>${module.description}</p>
    </p>
    <hr>
    <p>
        <a href="/modules/${module.id}/edit">Edit module</a>
    </p>
</div>
<a href="/">Back to the main page</a>
</body>
</html>