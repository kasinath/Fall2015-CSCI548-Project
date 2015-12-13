//=====================================================================================
$(document).ready(function() {
    $('#datePicker1')
        .datepicker({
            format: 'mm/dd/yyyy'
        });
        

 $('#datePicker2')
        .datepicker({
            format: 'mm/dd/yyyy'
        });
        
}); //End Document Ready

//=====================================================================================

$(function () {
    var options = {
    };
    $('.grid-stack').gridstack(options);
});


$(document).on("click",".close", function (e) {
  e.preventdefault;
$(this).closest(".grid-stack-item").fadeOut(300);
});



//=====================================================================================
var map;

function initMap() {
  var myLatLng = {lat: 34.052235, lng:  -118.243683};
    
    var day1 = [];
    var item1 = {};
    item1["name"] = "Metropolitan Museum of Art"
    myLatLng = {lat: 40.7766, lng:  -73.9521};
    item1["coords"] = myLatLng;  
    
    day1[0] = item1; 
    
    item1 = {};
    item1["name"] = "Empire State Building"
    myLatLng = {lat: 40.7484, lng:  -73.98564};
    item1["coords"] = myLatLng;    
    day1[1] = item1;
    
      item1 = {};
    item1["name"] = "Statue of Liberty"
    myLatLng = {lat: 40.7038, lng:  -74.0138};
    item1["coords"] = myLatLng;    
    day1[2] = item1;
 
          item1 = {};
    item1["name"] = "Ellis Island"
    myLatLng = {lat: 40.6986898, lng:  -74.0428993};
    item1["coords"] = myLatLng;    
    day1[3] = item1;
    
              item1 = {};
    item1["name"] = "Invade the Skies"
    myLatLng = {lat: 40.6934955, lng:  -73.9880736};
    item1["coords"] = myLatLng;    
    item1["event"] = true; 
    
    day1[4] = item1;
    

    
  var map = new google.maps.Map(document.getElementById('map1'), {
    zoom: 8,
    center: myLatLng
  });

    
    for(var i=0;i<day1.length;i++)
        {

            if(day1[i]["event"])
                {
                var marker = new google.maps.Marker({
                position: day1[i]["coords"],
                map: map,
                title: day1[i]["name"],
                icon : 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'

              });
                }
            else
                {
                                var marker = new google.maps.Marker({
    position: day1[i]["coords"],
    map: map,
    title: day1[i]["name"]
  });
                }
 
        }
 
    
    
     day1 = [];
    item1 = {};
    item1["name"] = "Central Park"
    myLatLng = {lat:40.7828687, lng:  -73.9675491};
    item1["coords"] = myLatLng;  
    
    day1[0] = item1; 
    
    item1 = {};
    item1["name"] = "Columbia University"
    myLatLng = {lat: 40.7587, lng:  -73.980867};
    item1["coords"] = myLatLng;    
    day1[1] = item1;
    
      item1 = {};
    item1["name"] = "Rockefeller Center"
    myLatLng = {lat: 40.758744, lng:  -73.9808676};
    item1["coords"] = myLatLng;    
    day1[2] = item1;
 
          item1 = {};
    item1["name"] = "Museum of Jewish Heritage"
    myLatLng = {lat: 40.706064, lng:  -74.017974};
    item1["coords"] = myLatLng;    
    day1[3] = item1;
    
              item1 = {};
    item1["name"] = "Times Square"
    myLatLng = {lat: 40.758899, lng:  -73.987325};
    item1["coords"] = myLatLng;    
    
    day1[4] = item1;
    
    
    
    
     var map2 = new google.maps.Map(document.getElementById('map2'), {
    zoom: 8,
    center: myLatLng
  });

    
    for(var i=0;i<day1.length;i++)
        {

            if(day1[i]["event"])
                {
                                var marker = new google.maps.Marker({
    position: day1[i]["coords"],
    map: map2,
    title: day1[i]["name"],
    icon : 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
                            
  });
                }
            else
                {
                                var marker = new google.maps.Marker({
    position: day1[i]["coords"],
    map: map2,
    title: day1[i]["name"]
  });
                }
 
        }
 
    
    
       day1 = [];
    item1 = {};
    item1["name"] = "New York Public Library"
    myLatLng = {lat:40.753186, lng:  -73.9844474};
    item1["coords"] = myLatLng;  
    
    day1[0] = item1; 
    
    item1 = {};
    item1["name"] = "Ground Zero Museum"
    myLatLng = {lat: 40.7412306, lng:  -74.0089117};
    item1["coords"] = myLatLng;    
    day1[1] = item1;
    
      item1 = {};
    item1["name"] = "Grnad Central Terminal"
    myLatLng = {lat: 40.7527302, lng:  -73.9794234};
    item1["coords"] = myLatLng;    
    day1[2] = item1;
 
          item1 = {};
    item1["name"] = "Madam Tussads"
    myLatLng = {lat: 40.7564797, lng:  -73.9910556};
    item1["coords"] = myLatLng;    
    day1[3] = item1;
    
              item1 = {};
    item1["name"] = "Harry Potter Trivia"
    myLatLng = {lat: 40.7614887, lng:  -73.9929487};
    item1["coords"] = myLatLng;    
    item1["event"] = true;
    day1[4] = item1;
    
    
    
    
    
     var map3 = new google.maps.Map(document.getElementById('map3'), {
    zoom: 8,
    center: myLatLng
  });

    
    for(var i=0;i<day1.length;i++)
        {

            if(day1[i]["event"])
                {
                                var marker = new google.maps.Marker({
    position: day1[i]["coords"],
    map: map3,
    title: day1[i]["name"],
    icon : 'http://maps.google.com/mapfiles/ms/icons/green-dot.png'
                            
  });
                }
            else
                {
                                var marker = new google.maps.Marker({
    position: day1[i]["coords"],
    map: map3   ,
    title: day1[i]["name"]
  });
                }
 
        }
 
    
}

//=====================================================================================


function cityClick(id)
{
	var len = 3;
	var classprefix = "city-btn-"
	var cities = ["Los Angeles", "New York" , "London"];
	for(i=0;i<3;i++)
	{
		if(i!=id)
		{
            $('#'+classprefix+i).removeClass("btn-primary");
            $('#'+classprefix+i).val = "no";
        }	
		else
		{
            $('#'+classprefix+i).addClass("btn-primary");
            $('#'+classprefix+i).attr("value",cities[i]);
        }	
        
				
	}


}
function IntClick(id)
{
	var len = 10;
	var classprefix = "interest-btn-"
	if ($('#'+classprefix+id).hasClass("btn-primary"))
		$('#'+classprefix+id).removeClass("btn-primary");
	else
		$('#'+classprefix+id).addClass("btn-primary");
	
}

function go()
{

	
	var len = 10;
	var  msg = "" ;
	var classprefix = "interest-btn-";
    var i=0;
	for(i=0;i<len;i++)
	{
		if ($('#'+classprefix+i).hasClass("btn-primary"))
			{
				
				msg +=  $('#'+classprefix+i).attr("value") + ",";

			}					
	}	
    var url = "results.html?msg="+msg;
     window.location=url;


}

//------------------------------------------------------------
// FACEBOOK LOGIN
//------------------------------------------------------------
var DEBUG=false;
window.fbAsyncInit = function()
{
    FB.Event.subscribe('auth.statusChange', onFacebookStatusChange);
    FB.init({
      appId  : '1757038341182981',
      status : true, // check login status
      cookie : true, // enable cookies to allow the server to access the session
      xfbml  : true  // parse XFBML
    });

	FB.getLoginStatus(onFacebookInitialLoginStatus);

};

(function() {
var e = document.createElement('script');
e.src = document.location.protocol + '//connect.facebook.net/en_US/all.js';
e.async = true;
document.getElementById('fb-root').appendChild(e);
}());

function facebookLogin()
{
    FB.login(onFacebookLoginStatus, {scope:'read_stream'});
}

/*
* Callback function for FB.getLoginStatus
*/
function onFacebookInitialLoginStatus(response)
{
    if (DEBUG)
	{
		alert("onFacebookLoginStatus(), "
		  + "\nresponse.status="+response.status
		  +" \nresponse.session="+response.session
		  +" \nresponse.perms="+response.perms);
	}
    if (response.status=="connected" && response.authResponse)
    {
		// At this stage, user is logged in.
		// For this example, I am logging out the user if the user is already logged in.
		FB.logout();
    }
    else
    {
		// User if not logged in.
    }
}

/*
* Callback function for 'auth.statusChange' event.
*/
function onFacebookStatusChange(response)
{
    if (DEBUG)
	{
		alert("onFacebookStatusChange(), "
		  + "\nresponse.status="+response.status
		  +" \nresponse.session="+response.session
		  +" \nresponse.perms="+response.perms);
	}
}

/*
* Callback function for FB.login
*/
function onFacebookLoginStatus(response)
{
    if (DEBUG)
	{
		alert("onFacebookLoginStatus(), "
		  + "\nresponse.status="+response.status
		  +" \nresponse.session="+response.session
		  +" \nresponse.perms="+response.perms);
	}
    if (response.status=="connected" && response.authResponse)
    {
		alert("You are all set.");
		var loginButtonDiv=document.getElementById("fb-login-button-div");
		loginButtonDiv.style.display="none";
		var contentDiv=document.getElementById("user-is-authenticated");
		contentDiv.innerHTML="User has been authenticated.  The application is ready to use.";
    }
    else
    {
		alert("Please login to enjoy this application.");
    }
}

function loginUsingPopup()
{
	var paramsLocation=window.location.toString().indexOf('?');
	var params=""; 
	if (paramsLocation>=0)
		params=window.location.toString().slice(paramsLocation);
		
	window.open("https://www.facebook.com/dialog/oauth?client_id=1757038341182981&redirect_uri='http://localhost:8080/IIWApp/index.jsp'",
		"loginWindow", "height=400, width=800");
	
	facebookLogin();
}
 
function loginUsingOAUTH()
{
	var paramsLocation=window.location.toString().indexOf('?');
	var params=""; 
	if (paramsLocation>=0)
		params=window.location.toString().slice(paramsLocation);

	top.location = 'https://graph.facebook.com/oauth/authorize?client_id=1757038341182981&redirect_uri=http://localhost:8080/IIWApp/index.jsp';
}
