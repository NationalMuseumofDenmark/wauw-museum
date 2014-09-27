<?php
die('Workz!');
require_once('config.php');
require_once('lib/asset.php');

/* dispatch */

$asset_handler = new Asset($sql_credentials, $solr_end_point);

if(!array_key_exists('id', $_GET) || empty($_GET['id'])) {
    die('ID not found!');
}
$id = $_GET['id'];

/* Asset from DB */
$asset = $asset_handler->get_asset($id);
echo '<pre>Asset found: ' . var_export($asset, true) . '</pre>';

/* Audio */
$audio_asset = $asset_handler->get_audio_asset($id);
echo '<pre>Audio found: ' . var_export($audio_asset, true) . '</pre>';

/* SOLR / SMK */
$solr_ref = $asset['solr_ref'];
$solr_asset = $asset_handler->get_solr_asset($solr_ref);
echo '<pre>Solr entry found: ' . var_export($solr_asset, true) . '</pre>';

$final_asset = array(
    "solr"=>$solr_asset,
    "audio"=>$audio_asset
);

header('Content-type: application/json');
echo json_encode($final_asset);
