<?php
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
          $request_url = $this->solr_end_point . '?q=id:' . $id . '&wt=json&rows=1&indent=true';
          $contents = file_get_contents($request_url);
          $json = json_decode($contents, true);

          if(!array_key_exists('response', $json)
             || !array_key_exists('docs', $json['response'])
             || !array_key_exists(0, $json['response']['docs'])
             || !is_array($json['response']['docs'][0])
          ) {
              return false;
          }

          return $json['response']['docs'][0];
      }

      function get_asset($id, $type) {
          $statement = $this->sql_connection->prepare('SELECT * FROM ' . $type . ' WHERE id=:id');
          $statement->bindParam(':id', $id);
          $statement->execute();
          $results = $statement->fetchAll(PDO::FETCH_ASSOC);
          return array_shift($results);
      }
};
