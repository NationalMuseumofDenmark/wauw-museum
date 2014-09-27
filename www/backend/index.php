<?php

require_once('config.php');
require_once('lib/asset.php');

/* dispatch */

$asset_handler = new Asset($sql_credentials, $solr_end_point);

$id = $_GET['id'];
if($id == null) {
    die('ID not found!');
}

/* Asset from DB */
$asset = $asset_handler->get_asset($id);

/* Audio */
$audio_asset = $asset_handler->get_audio_asset($id);

/* SOLR / SMK */
$solr_ref = $asset['solr_ref'];
$solr_asset = $asset_handler->get_solr_asset($solr_ref);

$final_asset = array(
    "solr"=>$solr_asset,
    "audio"=>$audio_asset
);

echo json_encode($final_asset);

?>

