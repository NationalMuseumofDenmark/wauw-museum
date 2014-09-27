var solrRequest = function() {
    "use strict";

    var strSchnabelUrl = "http://wauw.geekworld.dk/api/asset/",
        httpRequest = (function() {
            if(window.XMLHttpRequest) { // Mozilla, Safari, ...
                return new XMLHttpRequest();
            }
            else if(window.ActiveXObject) { // IE
                try {
                    return new ActiveXObject("Msxml2.XMLHTTP");
                }
                catch(e) {
                    try {
                        return new ActiveXObject("Microsoft.XMLHTTP");
                    }
                    catch(e) {}
                }
            }

            appInteraction.showToast("Din enhed kan desværre ikke snakke med serveren :(");
            return false;
        })(),

        makeRequest = function(strUUID, callback) {
            httpRequest.onreadystatechange = function() { getContents(callback); };
            console.log("Requesting: " + strSchnabelUrl + strUUID);
            httpRequest.open('GET', strSchnabelUrl + strUUID);
            httpRequest.send();
        },

        getContents = function(callback) {
            if(httpRequest.readyState === 4) {
                if(httpRequest.status === 200) {
                    if(callback) {
                        callback(httpRequest.responseText);
                    }
                    else {
                        return httpRequest.responseText;
                    }
                }
                else {
                    console.log("There was a problem with the request.");
                    return false;
                }
            }
        };

    this.retrieveJson = function(strUUID, callback) {
        makeRequest(strUUID, callback);
    }
};
