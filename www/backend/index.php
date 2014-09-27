<?

require_once('config.php');

require_once('lib/asset.php');

/* dispatch */

$asset_handler = new Asset($sql_credentials, $solr_end_point);

$uuid = $_GET['uuid'];
if($uuid == null) {
    die('UUID not found!');
}

$sql_asset = $asset_handler->get_asset($uuid);
$solr_ref = $sql_asset['solr_ref'];
$asset = $asset_handler->get_solr_asset($solr_ref);
echo "Title: " . $asset['title_dk'];

?>
