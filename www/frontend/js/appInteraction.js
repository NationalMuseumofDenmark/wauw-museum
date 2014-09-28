var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var strBeacons = "",
            strBeaconId,
            objBeacons = this.decodeJson(Android.getAllBeacons());
        for(strBeaconId in objBeacons) {
            if(objBeacons.hasOwnProperty(strBeaconId)) {
                strBeacons += strBeaconId + " [" + objBeacons[strBeaconId] + "]\n"
            }
        }
        appInteraction.showToast("Found beacons:\n" + strBeacons);
    },

    "getAllBeacons": function() {
        var objResponseData = this.decodeJson(Android.getAllBeacons());
        console.log(objResponseData);
        return objResponseData;
    },

    "getClosestBeacon": function() {
        var objResponseData = this.decodeJson(Android.getClosestBeacon());
        console.log(objResponseData);
        return objResponseData;
    },

    "getBestBeacon": function() {
        var strBestId, objResponseData = this.decodeJson(Android.getBestBeacon());
        console.log(objResponseData);
        if(objResponseData) {
            for(strBestId in objResponseData) {
                if(objResponseData.hasOwnProperty(strBestId)) {
                    return strBestId;
                }
            }
        }
        return false;
    },
    "decodeJson": function(strJson) {
        try {
            return JSON.parse(strJson);
        }
        catch(e) {
            console.error("Failed decoding JSON: " + e.message + "\n" + strJson);
            return false;
        }
    }
};
