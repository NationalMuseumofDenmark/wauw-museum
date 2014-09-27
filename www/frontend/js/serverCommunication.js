var httpRequest = (function() {
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
    makeRequest = function(url) {
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open('GET', url);
        httpRequest.send();
    }

    alertContents = function() {
        if(httpRequest.readyState === 4) {
            if(httpRequest.status === 200) {
                alert(httpRequest.responseText);
            }
            else {
                alert('There was a problem with the request.');
            }
        }
    };
