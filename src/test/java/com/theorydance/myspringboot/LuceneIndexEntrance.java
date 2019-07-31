package com.theorydance.myspringboot;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.theorydance.myspringboot.web.LuceneController;

public class LuceneIndexEntrance {

	private Directory d;
	private IndexWriter writer;
	
	@Before
	public void before() throws Exception{
		Path p = Paths.get(LuceneController.LUCENE_INDEX_DIR);
		d = FSDirectory.open(p);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig conf = new IndexWriterConfig(analyzer);
		if(p.toFile().exists()){
			conf.setOpenMode(OpenMode.CREATE_OR_APPEND);
		}else{
			conf.setOpenMode(OpenMode.CREATE);
		}

		writer = new IndexWriter(d, conf);
	}
	
	@After
	public void after() throws Exception{
		writer.close();
	}
	
	@Test
	public void buildIndex() throws Exception{
		File docDir = new File("D:/tmp/resources/");
		digui(docDir, writer);
	}
	
	private void digui(File file, IndexWriter writer) throws Exception{
		if(file==null){
			return;
		}
		if(file.isDirectory()){
			File[] list = file.listFiles();
			for (File sonFile : list) {
				digui(sonFile,writer);
			}
		}else{
			String filename = file.getName();
			String[] filters = new String[]{".jar", ".war", ".class", ".svn", ".svn-base"};
			for (String filter : filters) {
				if(filename.endsWith(filter)){
					return;
				}
			}
			
			Document doc = new Document();
			Field name = new StringField("name", filename, Store.YES);
			Field path = new StringField("path", file.getPath(), Store.YES);
			Field modified = new LongPoint("modified", file.lastModified());
			Field size = new LongPoint("size", file.length());
			
			String[] ends = new String[]{".txt", ".xml", ".html", ".css", ".js", ".properties", ".java"};
			boolean isText = false;
			for (String end : ends) {
				if(filename.endsWith(end)){
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
					String line = null;
					StringBuffer sb = new StringBuffer();
					while((line=br.readLine())!=null){
						sb.append(line+"\n");
					}
					br.close();
					Field contents = new TextField("contents", sb.toString(), Store.YES);
					doc.add(contents);
					isText = true;
					break;
				}
			}
			if(!isText){
				InputStream input = new FileInputStream(file);
				Field contents = new StoredField("contents", toByteArray(input));
				doc.add(contents);
			}
			doc.add(name);
			doc.add(path);
			doc.add(modified);
			doc.add(size);
			System.out.println(filename);
			if(writer.getConfig().getOpenMode()==OpenMode.CREATE){
				writer.addDocument(doc);
			}else{
				writer.updateDocument(new Term("path", file.getPath()), doc);
			}
		}
	}
	
	private byte[] toByteArray(InputStream in) throws IOException {
		 
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buffer = new byte[1024 * 4];
	    int n = 0;
	    while ((n = in.read(buffer)) != -1) {
	        out.write(buffer, 0, n);
	    }
	    return out.toByteArray();
	}
	
	/**
	 * 
	 */
	@Test
	public void deleteIndex(){
		try {
			writer.deleteAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void queryIndex() throws Exception{
		IndexReader reader = DirectoryReader.open(d);
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();
		QueryParser parser = new QueryParser("contents", analyzer);
		Query query = parser.parse("grand_ranfs");
		TopDocs docs = searcher.search(query, 10);
		ScoreDoc[] scoreDocs = docs.scoreDocs;
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docId = scoreDoc.doc;
			Document document = searcher.doc(docId);
			String name = document.get("name");
			System.out.println(scoreDoc.score + "," + name);
		}
	}

}
