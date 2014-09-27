<?
class Asset {

      private $solr_end_point;
      private $sql_connection;

      function __construct($sql_credentials, $solr_end_point) {
          $this->solr_end_point = $solr_end_point;

          $this->sql_connection = new PDO(
              $sql_credentials['connection_string'],
              $sql_credentials['username'],
              $sql_credentials['password']
          );
      }

      function get_solr_asset($id) {
          $request_url = $this->solr_end_point . '?q=id_s:' .
              $id . '&wt=json&rows=20&indent=true';
          $json_contents = json_decode(file_get_contents($request_url), true);
          return $json_contents['response']['docs'][0];
      }

      function get_asset($id) {
          $statement = $this->sql_connection->prepare(
              'SELECT * FROM beacon WHERE uuid=:uuid'
          );
          $statement->bindParam(':uuid', $id);
          $statement->execute();
          $results = $statement->fetchAll(PDO::FETCH_ASSOC);
          return $results[0];
      }

      function get_audio($id) {
          $statement = $this->sql_connection->prepare(
              'SELECT * FROM audio WHERE uuid=:uuid'
          );
          $statement->bindParam(':uuid', $id);
          $statement->execute();
          $results = $statement->fetchAll(PDO::FETCH_ASSOC);
          return $results[0];
      }
};

?>