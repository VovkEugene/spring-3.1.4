const table = $('#usersTable');
getAllUsers();

function getAllUsers() {
    table.empty()
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(user => {
            user.forEach(user => {
                let usersTable = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.roles}</td>
                            <td>
                                <button type="button" class="btn btn-sm btn-info"
                                data-bs-toogle="modal"
                                data-bs-target="#editModal"
                                onclick="editUser(${user.id})">Edit</button>
                            </td>
                            <td>
                                <button type="button" class="btn btn-sm btn-danger" 
                                data-toggle="modal"
                                data-bs-target="#deleteModal"
                                onclick="deleteUser(${user.id})">Delete</button>
                            </td>
                        </tr>)`;
                table.append(usersTable);
            })
        })
}
