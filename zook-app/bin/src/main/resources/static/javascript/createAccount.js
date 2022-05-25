window.onload = function(){
    
    document.getElementById("submitInfo").addEventListener("click", createAccount);
    getProfanity();
};

window.onpageshow = function(event){
    if (event.persisted){
        console.log("Backed into page");
        getProfanity();
    }
}


/**
 * createAccount() gathers html form fields and attempts to create a 
 * new user object and insert into the database via ajax request.
 * @param {*} event 
 */
function createAccount(event){
    event.preventDefault();
    console.log("create account called")
    profanityDetected = false;

    // get html form values from create account page
    let userFName = document.getElementById("firstName").value;

    let userLName = document.getElementById("lastName").value;
    let userEmail = document.getElementById("userEmail").value;
    let userPassword = document.getElementById("myPassword").value;
    let confirmPassword = document.getElementById("confirmPassword").value;
    let userSpecies = document.getElementById("species").value
    //check for profanity vs profanityList
    profanityCheck(userFName);
    profanityCheck(userLName);
    profanityCheck(userEmail);
    profanityCheck(userPassword);
    
    // if passwords match then continue with creating account
    // could possibly also add profanity filter here instead? TBD
    if (userPassword == confirmPassword && profanityDetected == false){
        console.log(true)
        // makes json user object 
        let user = {
            "userEmail" : userEmail,
            "userPassword" : userPassword,
            "userFName" : userFName,
            "userLName" : userLName,
            "userSpecies" : userSpecies
        };
        // then calls request to server with ajax function
        addUser(user);
    }else{
        console.log(false);
        // TODO: use DOM to tell user that passwords do NOT match with some text to screen.
        if (userPassword != confirmPassword){
            sendPasswordError();
        }
        if (profanityDetected==true){
            sendProfanityError();
        }
    };

};

// ajax request to addUser endpoint
/**
 * addUser() sends POST request to database to add new user.
 * @param {*} user JSON object which contains User Model attributes
 */
function addUser(user){
   
    let httpRequest = new XMLHttpRequest();
    httpRequest.onreadystatechange = function(){
        if(httpRequest.readyState == 4 && httpRequest.status == 200){
            let response = JSON.parse(httpRequest.responseText);
            // true is successful userAdd false means either duplicate key
            // error OR when profanity filter is implemented that the name
            // violates community guidelines.
            // console.log(response); // if returns false we can implement secondary logic to 
            // tell the user WHY they could not create an account.
        };
    };
    httpRequest.open("POST", "http://localhost:9039/addUser");
    httpRequest.setRequestHeader("content-type", "application/json");
    httpRequest.send(JSON.stringify(user));
};

/**
 * sendPasswordError() does DOM manipulation if user creating an account enters passwords
 * that do not match.
 */
function  sendPasswordError(){
    let paragraph = document.getElementById("pwconfirm");
    let errorMsg = document.createTextNode("Passwords do not match! Please try again.");
    paragraph.appendChild(errorMsg);
};

function  sendProfanityError(){
    console.log("Watch the language please");
    let paragraph = document.getElementById("pwconfirm");
    let errorMsg = document.createTextNode("Watch your language please.");
    paragraph.appendChild(errorMsg);
};



/**
 * profanityList() stores an array of profanities from the db, used to check for profanity before 
 * a post or user can be created
 */
let profanityList= [];
function getProfanity(){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
        if(xhttp.readyState = 4 && xhttp.status == 200){
            profanityList = JSON.parse(xhttp.responseText);
            
        }
        
    }

    xhttp.open('GET', 'http://localhost:9039/allprofanity');

    xhttp.send();


}

/**
 * //pass in a text field, will check if the text contains any of the listed profanities and 
 * flip the profanityDetected boolean to true
 * @param {*} inputField 
 */

function profanityCheck(inputField){
    profanityDetected = false;

    for (let i = 0; i<profanityList.length;i++){
        let textToCheck = inputField.toLowerCase();
        if (textToCheck.includes(profanityList[i].profanity)){
            console.log("This is catching profanity in fname");
            profanityDetected = true;
            console.log(profanityDetected," in the profanityCheck");
        }
        
    }

}