getUser();

function getUser() {
    fetch('http://localhost:8080/api/user')
        .then(response => response.json())
        .then(user => {
            const tableBody = document.getElementById('userTableBody');
            tableBody.innerHTML = '';

            const row = document.createElement('tr');
            row.innerHTML = `
                <td class="align-middle">${user.id}</td>
                <td class="align-middle">${user.username}</td>
                <td class="align-middle">${user.email}</td>
                <td class="align-middle">${user.roles.map(role => role.name).join(', ')}</td>
            `;
            tableBody.appendChild(row);
        })
        .catch(error => console.error('Error:', error));
}