var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var strBeacons = "",
            arrBeacons = Android.getAllBeacons();
        for(strBeaconId in arrBeacons) {
            strBeacons += strBeaconId + " [" + arrBeacons[strBeaconId] + "]\n"
        }
        appInteraction.showToast("Found beacons:\n" + strBeacons);
    },

    "getAllBeacons": function() {
        return Android.getAllBeacons();
    },

    "getClosestBeacon": function() {
        return Android.getClosestBeacon();
    },

    "getBestBeacon": function() {
        return Android.getBestBeacon();
    }
};
