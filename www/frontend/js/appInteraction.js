var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var strBeacons = "",
            arrBeacons = JSON.parse(Android.getAllBeacons());
        for(strBeaconId in arrBeacons) {
            strBeacons += strBeaconId + " [" + arrBeacons[strBeaconId] + "]\n"
        }
        appInteraction.showToast("Found beacons:\n" + strBeacons);
    },

    "getAllBeacons": function() {
        return JSON.parse(Android.getAllBeacons());
    },

    "getClosestBeacon": function() {
        return JSON.parse(Android.getClosestBeacon());
    },

    "getBestBeacon": function() {
        objBestBeacon = JSON.parse(Android.getBestBeacon());
        for(strBestId in objBestBeacon) {
            if(objBestBeacon.hasOwnProperty(strBestId)) {
                return strBestId;
            }
        }
    }
};
