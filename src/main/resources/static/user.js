const tableBodyUsers = $('#usersTable tbody');

url = 'http://localhost:8080/api/users/2';

document.addEventListener('DOMContentLoaded', async function () {
    await fetch(url, {method: 'GET'})
        .then(res => res.json())
        .then(user => {
            let row = `(
            <tr>
                <td>${user.id}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(r => r.role)}</td>
            </tr>
            )`;
            tableBodyUsers.append(row);
        });
});
