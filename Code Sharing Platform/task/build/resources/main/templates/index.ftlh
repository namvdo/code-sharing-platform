<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../static/style.css">
    <title>Latest</title>
</head>
<body>
    <div class="container">
       <#list model["codes"] as item>
           <div class="code">
               <span id="load_date">${item.date}</span>
               <pre id="code_snippet">${item.code}</pre>
           </div>
       </#list>
    </div>
</body>
</html>