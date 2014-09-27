var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var strBeacons = "",
            objBeacons = JSON.parse(Android.getAllBeacons());
        for(strBeaconId in objBeacons) {
            strBeacons += strBeaconId + " [" + objBeacons[strBeaconId] + "]\n"
        }
        appInteraction.showToast("Found beacons:\n" + strBeacons);
    },

    "getAllBeacons": function() {
        var objResponseData = JSON.parse(Android.getAllBeacons());
        console.log(objResponseData);
        return objResponseData;
    },

    "getClosestBeacon": function() {
        var objResponseData = JSON.parse(Android.getClosestBeacon());
        console.log(objResponseData);
        return objResponseData;
    },

    "getBestBeacon": function() {
        var objResponseData = JSON.parse(Android.getBestBeacon());
        console.log(objResponseData)
        if(objResponseData) {
            for(strBestId in objResponseData) {
                if(objResponseData.hasOwnProperty(strBestId)) {
                    return strBestId;
                }
            }
        }
    }
};
