function loadApproved() {
    tableBody = document.querySelector("#e-request-table");
    removeAllChildNodes(tableBody);
    let e = document.createElement('tr');

        let id = document.createElement('td');
        id.innerText = "datatata";

        let amt = document.createElement('td');
        amt.innerText = "$$$$4";

        let reason = document.createElement('td');
        reason.innerText = "foood";

        let comment = document.createElement('td');
        comment.innerText = "I was doing important stuff for business trip";

        let approvedBy = document.createElement('td');
        approvedBy.innerText = "Not Approved";


    e.appendChild(id);
    e.appendChild(amt);
    e.appendChild(reason);
    e.append(comment);
    e.append(approvedBy);


    document.querySelector("#e-request-table").appendChild(e);
    document.querySelector("#e-request-table").appendChild(e);
}



document.addEventListener('DOMContentLoaded', () => {

    const refreshButton = document.querySelector('#refresh-requests');
    refreshButton.addEventListener('click', async ()=>{
        const response = await fetch('http://localhost:7777/api/employee')
        const reims = await response.json();
        console.log(reims);

        tableBody = document.querySelector("#e-request-table");
        removeAllChildNodes(tableBody);

        reims.map( (i) =>{
            let e = document.createElement('tr');

            let id = document.createElement('td');
            id.innerText = i["requestNumber"];

            let amt = document.createElement('td');
            amt.innerText = i["amount"];

            let reason = document.createElement('td');
            reason.innerText = i["reason"];

            let comment = document.createElement('td');
            comment.innerText = i["comments"];

            let approvedBy = document.createElement('td');
            approvedBy.innerText = i["approvedBy"];


            e.appendChild(id);
            e.appendChild(amt);
            e.appendChild(reason);
            e.append(comment);
            e.append(approvedBy);


            tableBody.appendChild(e);
        })


    })

    function removeAllChildNodes(parent) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }

});


