var solrRequest = function() {
    "use strict";

    var strSchnabelUrl = "/api/asset/",
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
                    var json = JSON.parse(httpRequest.responseText);

                    if(callback) {
                        callback(json);
                    }
                    else {
                        return json;
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
