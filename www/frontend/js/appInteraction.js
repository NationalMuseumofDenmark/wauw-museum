var appInteraction = {
    "showToast": function(strMessage) {
        Android.showToast(strMessage);
    },

    "showBeacons": function() {
        var arrBeacons = Android.getAllBeacons();
        appInteraction.showToast("Found beacons: " + arrBeacons);
    },

    "getClosestBeacon": function() {
        return Android.getClosestBeacon();
    }
};
