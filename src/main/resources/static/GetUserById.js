async function getUserById(id) {
    let url = "http://localhost:8080/admin/" + id;
    let response = await fetch(url);
    return await response.json();
}