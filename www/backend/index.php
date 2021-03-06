<?php
error_reporting(E_ALL);

// We need an asset, so just kill everything if one hasn't been specified
if(!array_key_exists('id', $_GET) || empty($_GET['id'])) {
    die('ID not found!');
}
$id = $_GET['id'];


try {
    // Configure end point, database connection, and setup the asset fetcher/handler thingy
    require_once('config.php');
    require_once('lib/asset.php');

    /* dispatch */

    $asset_handler = new Asset($sql_credentials, $solr_end_point);


    /* Asset from DB */
    $asset = $asset_handler->get_asset($id, 'beacon');

    /* Audio */
    $audio_asset = $asset_handler->get_asset($id, 'audio');

    /* SOLR / SMK */
    $solr_ref = $asset['solr_ref'];
    $solr_asset = $asset_handler->get_solr_asset($solr_ref);

    $final_asset = array(
        'beacon_id' => $id,
        'solr_id'   => $solr_ref,
        'solr'      => $solr_asset,
        'audio'     => $audio_asset
    );

    header('Content-type: application/json');
    echo json_encode($final_asset);
}
catch(Exception $e) {
    header('Content-type: application/json');
    echo json_encode($e->getMessage());
}
