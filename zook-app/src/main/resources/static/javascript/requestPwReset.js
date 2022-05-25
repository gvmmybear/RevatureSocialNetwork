// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";

window.onload = function(){
    document.getElementById('submitPwReset').addEventListener('click', submitResetRequest);
}

function submitResetRequest(event){
    event.preventDefault();
    let userEmail = document.getElementById('userEmail').value;
    let user = {
        "userEmail" : userEmail
    }

    let httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function(){
        if(httpRequest.readyState == 4 && httpRequest.status == 200){
            let stats = httpRequest.responseText;;
            console.log(stats);
        };
    };
    appendRequestSentMsg();
    httpRequest.open("POST", `${url}/recover`);
    httpRequest.setRequestHeader("content-type", "application/json");
    httpRequest.send(JSON.stringify(user));

}

function  appendRequestSentMsg(){
    let emailInput = document.getElementById('emailInput')
    let resetSubmit = document.getElementById('resetSubmit')
    while(emailInput.firstChild){
        emailInput.removeChild(emailInput.firstChild);
    }
    while(resetSubmit.firstChild){
        resetSubmit.removeChild(resetSubmit.firstChild);
    }
    let paragraph = document.getElementById("requestSentMsg");
    let msg = document.createTextNode("Please check your email. If the email is registed with Zook, " +
    "you will receive a reset link shortly.");
    paragraph.appendChild(msg);
};