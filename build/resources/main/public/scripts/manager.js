function approve(requestNumber){
        fetch('http://localhost:7777/api/approve-request', {
            method: 'post',
            body: JSON.stringify(requestNumber)
          });
          //.then(function(response) {return response.json()};
    }

    function deny(requestNumber){
            fetch('http://localhost:7777/api/deny-request', {
                method: 'post',
                body: JSON.stringify(requestNumber)
              });
              //.then(function(response) {return response.json()};
      }



document.addEventListener('DOMContentLoaded', () => {

    const refreshButton = document.querySelector('#refresh-requests');
    refreshButton.addEventListener('click', async ()=>{
        const response = await fetch('http://localhost:7777/api/pending')
        const reims = await response.json();
        console.log(reims);

        tableBody = document.querySelector("#m-request-table");
        removeAllChildNodes(tableBody);

        reims.map( (i) =>{
            let e = document.createElement('tr');

            let id = document.createElement('td');
            let requestNumber = i["requestNumber"];
            id.innerText = i["requestNumber"];

            let amt = document.createElement('td');
            amt.innerText = i["amount"];

            let reason = document.createElement('td');
            reason.innerText = i["reason"];

            let comment = document.createElement('td');
            comment.innerText = i["comments"];

            let approvedBy = document.createElement('td');
            approvedBy.innerText = i["approvedBy"];

            let approveB = document.createElement('BUTTON');
            approveB.innerText = "Approve";
            approveB.setAttribute("value", i["requestNumber"]);
            approveB.setAttribute("name", i["requestNumber"]);
            approveB.setAttribute("onclick", `approve(${requestNumber})`);
            //approve.setAttribute("onclick", `approve(${requestNumber})`);


            let denyB = document.createElement('BUTTON');
            denyB.innerText = "Deny";
            denyB.setAttribute("value", i["requestNumber"]);
            denyB.setAttribute("name", i["requestNumber"]);
            denyB.setAttribute("onclick", `deny(${requestNumber})`);

            //<button id="load-requests-button" onclick="loadRequests()">Approve</button>

            e.appendChild(id);
            e.appendChild(amt);
            e.appendChild(reason);
            e.append(comment);
            e.append(approvedBy);
            e.append(approveB);
            e.appendChild(denyB);


            tableBody.appendChild(e);
        })
    })

    approveB.addEventListener("onclick", (e) => {
                approve(e.target.getValue());
       }
    )

    denyB.addEventListener("onclick", (e) => {
        deny(e.target.getValue());
    }

    )

    function removeAllChildNodes(parent) {
        while (parent.firstChild) {
            parent.removeChild(parent.firstChild);
        }
    }

});


