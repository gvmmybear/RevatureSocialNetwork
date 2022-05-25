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
            
            window.location.href = "http://localhost:9039/home";
            // routeToProfilePage();
        };
    };
    httpRequest.open("POST", "http://localhost:9039/login");
    httpRequest.setRequestHeader("content-type", "application/json");
    httpRequest.send(JSON.stringify(user));
}

