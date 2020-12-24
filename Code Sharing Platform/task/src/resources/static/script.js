const send = async () => {
    const code = {
        "code": document.getElementById("code_snippet").value,
        "time": document.getElementById("time_restriction").value,
        "views" : document.getElementById("views_restriction").value
    }
    const json = JSON.stringify(code);
    await fetch("/api/code/new", {
        method: 'POST',
        body: json
    })
        .then(response => response.json())
        .then(jsonResponse => jsonResponse)
        .catch(networkError => console.log(networkError.message))
}
