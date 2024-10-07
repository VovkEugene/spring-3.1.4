async function getModal(form, modal, id){
    modal.show();
    let user = await getUserById(id);
    form.id.value = user.id;
    form.username.value = user.username;
    form.email.value = user.email;
    form.roles.value = user.roles;
}