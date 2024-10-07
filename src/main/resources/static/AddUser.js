let formNew = document.forms["formNewUser"];
addUser();

function addUser() {
    formNew.addEventListener("submit", ev => {
        ev.preventDefault();


        let newRoles = [];
        for (let i = 0; i < formNew.roles.options.length; i++) {
            if (formNew.roles.options[i].selected) newRoles.push({
                id: formNew.roles.options[i].value,
                role: "ROLE_" + formNew.roles.options[i].text
            });
        }

        fetch("http://localhost:8080/api/add-user", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.id.value,
                username: formEdit.username.value,
                email: formEdit.email.value,
                password: formEdit.password.value,
                userRoles: newRoles
            })
        }).then(response => {
            if (response.ok) {
                formNew.reset();
                getAllUsers();
                $('#home-tab').click();
            } else {
                response.json().then(errors => {
                    displayAddErrors(errors);
                });
            }
        });
    });
}

function displayAddErrors(errors) {
    let errorAddDiv = document.getElementById("errorAddDiv");
    errorAddDiv.innerHTML = "";
    errors.forEach(error => {
        let errorSpan = document.createElement("span");
        errorSpan.className = "error-message";
        errorSpan.innerHTML = error;
        errorAddDiv.appendChild(errorSpan);
    });
}

function loadRolesAdd() {
    let select = document.getElementById("roles");
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

window.addEventListener("load", loadRolesAdd);
