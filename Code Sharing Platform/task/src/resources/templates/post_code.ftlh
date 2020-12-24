<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Create</title>
</head>
<form action="/api/code/new" method="POST">
    <label for="code_snippet"></label>
    <textarea name="code" id="code_snippet" cols="30" rows="10"></textarea>
    <label for="time_restriction"></label><input id="time_restriction" type="text"/>
    <label for="views_restriction"></label><input id="views_restriction" type="text"/>
    <button id="send_snippet" type="submit" onclick="send()">Submit</button>
</form>
<script src="../static/script.js"></script>
<body>

</body>
</html>