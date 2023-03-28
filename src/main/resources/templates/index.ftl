
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Module</title>
</head>
<body style="text-align: center; font-family: sans-serif; font-size: 40px">
<h1>There are about Modules </h1>
<hr>
<#list modules as module>
    <div>
        <h3>
            <a href="/modules/${module.id}" style="text-decoration: none; text-decoration-color: darkblue">${module.name}</a>
        </h3>
        <h4>
            ${module.type}
        </h4>
    </div>
</#list>
<hr>
<p>
    <a href="/modules/new">Create module</a>
</p>
</body>
</html>