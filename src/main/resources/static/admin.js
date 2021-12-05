const tableBodyUsers = document.querySelector('#usersTable tbody');
const btnEdit = document.getElementById('edit');
const btnDelete = document.getElementById('delete');

const formAdd = document.getElementById('addForm');
const formEdit = document.getElementById('editForm');
const formDelete = document.getElementById('deleteForm');

const modalEdit = document.getElementById('editModal');
const modalDelete = document.getElementById('deleteModal');

url = 'http://localhost:8080/api/users';

document.addEventListener('DOMContentLoaded', async function () {
    await getUsersTable();
});

async function getUsersTable() {
    const response = await fetch(url, {method: 'GET'})
        .catch(error => {console.log(error)});
    const json = await response.json();
    tableBodyUsers.innerHTML = '';
    json.forEach(user => addRow(tableBodyUsers.insertRow(), user));
    return json;
}

const addRow = (row, user) => {
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.roles.map(r => r.role)}</td>
        <td>${btnEdit.outerHTML}</td>
        <td>${btnDelete.outerHTML}</td>
    `;
}
const blankForm = (form) => {
    form.elements.firstName.value = '';
    form.elements.lastName.value = '';
    form.elements.age.value = '';
    form.elements.email.value = '';
    form.elements.password.value = '';
};

const showModalForm = (prefix, form, rowData) => {
    const [id, firstName, lastName, age, email, role] = rowData;
    const roles = role.textContent.split(',');
    form.action = url + `/${id.textContent}`;

    form.elements[`${prefix}-id`].value = id.textContent;
    form.elements[`${prefix}-firstname`].value = firstName.textContent;
    form.elements[`${prefix}-lastname`].value = lastName.textContent;
    form.elements[`${prefix}-age`].value = age.textContent;
    form.elements[`${prefix}-email`].value = email.textContent;
    [...form.elements[`${prefix}-role`].options]
        .map(o => o.selected = roles.includes(o.textContent));
};

$('#editModal').on('shown.bs.modal', function (event) {
    showModalForm('edit',
        this.querySelector('form'),
        event.relatedTarget.closest('tr').children);
})

$('#deleteModal').on('shown.bs.modal', function (event) {
    showModalForm('delete',
        this.querySelector('form'),
        event.relatedTarget.closest('tr').children);
})

formAdd.addEventListener('submit', async function (event) {
    event.preventDefault();
    const response = await fetch(url, {
        method: 'POST',
        contentType: 'application/json; charset=utf-8',
        body: new FormData(this)})
        .catch(error => {console.log(error)});

    await getUsersTable();
    blankForm(formAdd);
    $('#navTabs li:first-child button').tab('show');
});

formEdit.addEventListener('submit', async function (event) {
    event.preventDefault();
    const response = await fetch(this.action, {
        method: 'PUT',
        contentType: 'application/json; charset=utf-8',
        body: new FormData(this)})
        .catch(error => {console.log(error)});

    await getUsersTable();
    $(modalEdit).modal('hide');
});

formDelete.addEventListener('submit', async function (event) {
    event.preventDefault();
    const response = await fetch(this.action, {
        method: 'DELETE',
        contentType: 'application/json; charset=utf-8',
        body: new FormData(this)})
        .catch(error => {console.log(error)});

    await getUsersTable();
    $(modalDelete).modal('hide');
});
