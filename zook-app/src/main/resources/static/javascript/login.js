// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";

window.onload = function (){
    console.log("script is linked!");
    document.getElementById("loginButton").addEventListener("click", login);
}

function login(event){
    event.preventDefault();
    console.log("login called")
    let userEmail = document.getElementById("email").value;
    let userPassword = document.getElementById("password").value;
    // console.log(email, password);

    let user = {
        "userEmail" : userEmail,
        "userPassword" : userPassword
    };

    let httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function(){
        if(httpRequest.readyState == 4 && httpRequest.status == 200){
            let response = JSON.parse(httpRequest.responseText);
            console.log(response);
            
            window.location.href = `${url}/home`;
            // routeToProfilePage();
        };
    };
    httpRequest.open("POST", `${url}/login`);
    httpRequest.setRequestHeader("content-type", "application/json");
    httpRequest.send(JSON.stringify(user));
}

