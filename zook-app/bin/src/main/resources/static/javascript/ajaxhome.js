/* adding an empty js file, mostly so the file folders will upload to the repo without using .gitkeep
* We can probably use this to also route our front end requests to the server as well.
* I prefer using ajax over the js promises, just because it seems a little less confusing to me but
* I don't mind switching if anyone has a different prefernce. -christian */

// this script is now linked to the home.html file while server is running on port: 9039. 
window.onload = function(){
    console.log("testing script linking from home.html");
};
