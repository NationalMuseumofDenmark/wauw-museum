function LoopHandler() {
    var counter = 0;
    var lastBeacon = '';

    this.loopNearestBeacon = function loopNearestBeacon() {

        var strBeaconId = appInteraction.getClosestBeacon();
        if(strBeaconId === lastBeacon) {
            counter = counter + 1;
        }
        else {
            counter = 0;
        }

        if(counter >= 3 && elNearestBeacon.innerText !== lastBeacon) {
            elNearestBeacon.innerText = lastBeacon;
        }

        lastBeacon = strBeaconId;
        setTimeout(loopNearestBeacon, 1000);
    };
};
