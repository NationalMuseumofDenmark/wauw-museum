<?

  class Asset {

    private $solr_end_point = 'http://solr.smk.dk:8080/solr/prod_all_dk/select';
    private $sql_connection;

    function __construct() {
       $this->sql_connection = new PDO('mysql:host=localhost;dbname=wauw', 'root', 'root');
    }

    function get_solr_asset($id) {
        $request_url = $this->solr_end_point . '?q=id_s:' . $id . '&wt=json&rows=20&indent=true';
        $json_contents = json_decode(file_get_contents($request_url), true);
        return $json_contents['response']['docs'][0];
    }

    function get_asset($id) {
        $statement = $this->sql_connection->prepare('SELECT * FROM beacon WHERE uuid=:uuid');
        $statement->bindParam(':uuid', $id);
	$statement->execute();
	$results = $statement->fetchAll(PDO::FETCH_ASSOC);
        return $results[0];
    }

  };



  /* dispatch */
  
  $asset = new Asset();
  $sql_asset = $asset->get_asset('b9407f30-f5f8-466e-aff9-25556b57fe6d');
  $solr_ref = $sql_asset['solr_ref'];
  $tmp_asset = $asset->get_solr_asset($solr_ref);
  echo $tmp_asset['title_dk'];
?>
