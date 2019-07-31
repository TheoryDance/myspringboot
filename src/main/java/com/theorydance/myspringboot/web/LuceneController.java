package com.theorydance.myspringboot.web;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/lucene")
public class LuceneController {
	
	public final static String LUCENE_INDEX_DIR = "D:/tmp/db/";
	
	@RequestMapping("/search")
	public Object search(@RequestParam("condition") String condition) {
		List<Map<String,Object>> list = new ArrayList<>();
		Path p = Paths.get(LUCENE_INDEX_DIR);
		try {
			Directory d = FSDirectory.open(p);
			try (IndexReader reader = DirectoryReader.open(d)){
				IndexSearcher searcher = new IndexSearcher(reader);
				Analyzer analyzer = new StandardAnalyzer();
				QueryParser parser = new QueryParser("contents", analyzer);
				Query query = parser.parse(condition);
				TopDocs docs = searcher.search(query, 10);
				ScoreDoc[] scoreDocs = docs.scoreDocs;
				for (ScoreDoc scoreDoc : scoreDocs) {
					int docId = scoreDoc.doc;
					Document document = searcher.doc(docId);
					Map<String,Object> map = new HashMap<>();
					map.put("score", scoreDoc.score);
					map.put("docId", docId);
					map.put("path", document.get("path"));
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@RequestMapping("/getDocument")
	public Object getDocument(@RequestParam("docId") int docId){
		Map<String,Object> map = new HashMap<>();
		Path p = Paths.get(LUCENE_INDEX_DIR);
		try {
			Directory d = FSDirectory.open(p);
			try (IndexReader reader = DirectoryReader.open(d)){
				IndexSearcher searcher = new IndexSearcher(reader);
				Document document = searcher.doc(docId);
				map.put("name", document.get("name"));
				map.put("contents", document.get("contents"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}