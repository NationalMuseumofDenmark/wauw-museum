var Android = Android || {
    "showToast": function(strMessage) {
        console.log("showToast: '" + strMessage + "'");
        alert(strMessage);
    },
    "getAllBeacons": function() {
        console.log("getAllBeacons");
        return [
            "UUID-18998-42019",
            "UUID-18998-42020",
            "UUID-18998-42021",
            "UUID-18998-42022",
            "UUID-18998-42023",
            "UUID-18998-42024",
            "UUID-18998-42025"
        ]
    },
    "getClosestBeacon": function() {
        console.log("getClosestBeacon");
        return "UUID-18998-42019";
    }
};