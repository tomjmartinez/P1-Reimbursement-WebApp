document.addEventListener('DOMContentLoaded', async ()=>{
        const response = await fetch('http://localhost:7777/api/employee');
        currentUser = await response.json();
        console.log(currentUser);


    const email = document.querySelector('#email');

    console.log(currentUser);
    let username = currentUser["email"];
    email.setAttribute("placeholder", currentUser);



})