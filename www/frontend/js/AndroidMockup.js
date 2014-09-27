var Android = Android || {
    "showToast": function(strMessage) {
        console.log("showToast: '" + strMessage + "'");
        alert(strMessage);
    },
    "getAllBeacons": function() {
        console.log("getAllBeacons");
        return "{\"UUID-18998-42019\": 3.532890753232,\"UUID-18998-42020\": 5.432532529950,\"UUID-18998-42021\": 1.432505343279,\"UUID-18998-42022\": 10.48374378349,\"UUID-18998-42023\": 80.47398744504,\"UUID-18998-42024\": 1.984239464294,\"UUID-18998-42025\": 7.493826493570}";
    },
    "getClosestBeacon": function() {
        console.log("getClosestBeacon");
        return "{\"UUID-18499-19998\": 7.493826493570}";
    },
    "getBestBeacon": function() {
        console.log("getBestBeacon");
        return "{\"UUID-18499-19998\": 7.493826493570}";
    }
};
