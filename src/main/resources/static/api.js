var base = "/myspringboot"; // 注意：此处的值，与application.yml中的server.servlet.context-path保持一致

var api = {};
api.lucene_search = base + "/lucene/search";
api.lucene_getDocument = base + "/lucene/getDocument";