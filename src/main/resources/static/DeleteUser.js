let formDelete = document.forms["formDelete"];
deleteUser();

async function deleteModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#deleteModal'));
    await getModal(formDelete, modal, id);

    switch (formDelete.roles.value) {
        case '1':
            formDelete.roles.value = 'ROLE_ADMIN';
            break;
        case '2':
            formDelete.roles.value = 'ROLE_USER';
            break;
    }
}

function deleteUser() {
    formDelete.addEventListener("submit", event => {
        event.preventDefault();
        fetch("http://localhost:8080/api/delete/" + formDelete.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(() => {
            $('#deleteFormCloseButton').click();
            getAllUsers();
        });
    });
}

function loadRolesDelete() {
    let select = document.getElementById("roleDelete");
    select.innerHTML = "";
    fetch("http://localhost:8080/api/roles")
        .then(res => res.json())
        .then(data => {
            data.forEach(role => {
                let option = document.createElement("option");
                option.value = role.id;
                option.text = role.name;
                select.appendChild(option);
            });
        })
        .catch(error => console.error(error));
}

window.addEventListener("load", loadRolesDelete);
