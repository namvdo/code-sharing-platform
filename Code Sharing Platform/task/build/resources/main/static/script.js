const send = async () => {
    const code = {
        "code": document.getElementById("code_snippet").value
    }
    const json = JSON.stringify(code);
    await fetch("/api/code/new", {
        method: 'POST',
        body: json,
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(jsonResponse => jsonResponse)
        .catch(networkError => console.log(networkError.message))
}
