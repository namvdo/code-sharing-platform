const send = () => {
    const code = {
        "code": document.getElementById("code_snippet").value
    }
    const json = JSON.stringify(code);
    fetch("/api/code/new", {
        method: 'POST',
        body: json
    })
        .then(response => response.json())
        .then(jsonResponse => jsonResponse)
        .catch(networkError => console.log(networkError.message))
}