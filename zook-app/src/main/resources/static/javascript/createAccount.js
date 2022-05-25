// Launch URL
const url = "http://ec2-54-241-44-71.us-west-1.compute.amazonaws.com:9039";
// Dev URL
// const url = "http://localhost:9039";

window.onload = function(){
    
    document.getElementById("submitInfo").addEventListener("click", createAccount);
  
    getProfanity();
    //freeze on profanityCheck doesn't work as intended, but leaving it in to show the learning process
    Object.freeze(profanityCheck);
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
    clearMessage();
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
  
    if (userPassword == confirmPassword && profanityDetected == false){

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
            let response = (httpRequest.responseText);
            console.log(response);
            if (response=="Success"){
                
                // let paragraph = document.getElementById("pwconfirm");
                // let errorMsg = document.createTextNode("Account Created Successfully. Please return to login");
                // paragraph.appendChild(errorMsg);
                document.getElementById("pwconfirm").innerText = "Account Created Successfully. Please return to login";
            }else{
                
                // let paragraph = document.getElementById("pwconfirm");
                // let errorMsg = document.createTextNode("Couldn't create account: " + response);
                // paragraph.appendChild(errorMsg);
                document.getElementById("pwconfirm").innerText = ("Couldn't create account: " + response);
            }
            // true is successful userAdd false means either duplicate key
            // error OR when profanity filter is implemented that the name
            // violates community guidelines.
            // console.log(response); // if returns false we can implement secondary logic to 
            // tell the user WHY they could not create an account.
        };
    };
    httpRequest.open("POST", `${url}/addUser`);
    httpRequest.setRequestHeader("content-type", "application/json");
    httpRequest.send(JSON.stringify(user));
};

/**
 * sendPasswordError() does DOM manipulation if user creating an account enters passwords
 * that do not match.
 */
function  sendPasswordError(){
   
    // let paragraph = document.getElementById("pwconfirm");
    // let errorMsg = document.createTextNode("Passwords do not match! Please try again.");
    // paragraph.appendChild(errorMsg);
    document.getElementById("pwconfirm").innerText = "Passwords do not match! Please try again.";
};

function  sendProfanityError(){
    
    console.log("Watch the language please");
    // let paragraph = document.getElementById("pwconfirm");
    // let errorMsg = document.createTextNode("Watch your language please.");
    // paragraph.appendChild(errorMsg);
    document.getElementById("pwconfirm").innerText = "Watch the language please";
};



/**
 * profanityList() stores an array of profanities from the db, used to check for profanity before 
 * a post or user can be created
 * 
 * after testing, we realized this was a poor place to add a profanity filter, we're leaving this part active
 * because it's slightly faster than the second check that is done where the user can't just update a function and get around
 * the check
 */
profanityList= [];
function getProfanity(){

    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function (){
        if(xhttp.readyState = 4 && xhttp.status == 200){
           profanityList = JSON.parse(xhttp.responseText);
       
           //this makes the list of profanity immutable, but ultimately ended up not mattering because the profanityCheck function could 
           //be circumvented, and the .freeze didn't seem to work as I had hoped on the function
           Object.freeze(profanityList);


            
        }
        
    }

    xhttp.open('GET', `${url}/allprofanity`);

    xhttp.send();


}

/**
 * //pass in a text field, will check if the text contains any of the listed profanities and 
 * flip the profanityDetected boolean to true
 * //this is the section that is easily broken and why it was re-enforced with a server side check
 * @param {*} inputField 
 */
function profanityCheck(inputField){
    // profanityDetected = false;

    for (let i = 0; i<profanityList.length;i++){
        let textToCheck = inputField.toLowerCase();
        if (textToCheck.includes(profanityList[i].profanity)){
            profanityDetected = true;
        }
        
    }

}

function clearMessage(){
   

}

