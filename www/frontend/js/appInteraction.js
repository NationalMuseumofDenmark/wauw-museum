var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var arrBeacons = Android.getAllBeacons();
        appInteraction.showToast("Found beacons: " + arrBeacons);
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
