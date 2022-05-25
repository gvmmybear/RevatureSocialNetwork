// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";

let queryString;
let token;
window.onload = function(){
    console.log('script is linked pw reset page')
    document.getElementById('submitNewPassword').addEventListener('click', submitNewPassword);
    queryString = window.location.search;
    loadURLParams();
}

function loadURLParams(){
    let urlParams = new URLSearchParams(queryString);
    token = urlParams.get('token');
    console.log(token);
}

function submitNewPassword(event){
    event.preventDefault();
    console.log('submit new pw requests')
    let confirmPassword = document.getElementById("confirmPassword").value;
    let password = document.getElementById('password').value;
    if (password != confirmPassword || password == null){
        sendPasswordError();
    }else{
        console.log(password); 
        let pwRestModel = {
            "password" : password,
            "token" : token
        }
    
        let httpRequest = new XMLHttpRequest();
        httpRequest.onreadystatechange = function(){
            if(httpRequest.readyState == 4 && httpRequest.status == 200){
                // let response = JSON.parse(httpRequest.responseText);
                console.log("request sent");
            };
        };
        clearFormsAndPrintMsg();
        httpRequest.open("POST", `${url}/change`);
        httpRequest.setRequestHeader("content-type", "application/json");
        httpRequest.send(JSON.stringify(pwRestModel));
    }
}

function clearFormsAndPrintMsg(){
    let passwordInput = document.getElementById('passwordInput');
    let confirmInput = document.getElementById('confirmInput');
    let submitButton = document.getElementById('submitButton');
    while(passwordInput.firstChild){
        passwordInput.removeChild(passwordInput.firstChild);
    }
    while(confirmInput.firstChild){
        confirmInput.removeChild(confirmInput.firstChild);
    }
    while(submitButton.firstChild){
        submitButton.removeChild(submitButton.firstChild);
    }
    let paragraph = document.getElementById("sentMsg");
    let msg = document.createTextNode("Your password was updated! Please go to login page to access your account.");
    paragraph.appendChild(msg);
}

function  sendPasswordError(){
    let paragraph = document.getElementById("pwconfirm");
    let errorMsg = document.createTextNode("Passwords do not match! Please try again.");
    paragraph.appendChild(errorMsg);
};