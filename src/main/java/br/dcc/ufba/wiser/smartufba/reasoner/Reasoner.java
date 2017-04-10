package br.dcc.ufba.wiser.smartufba.reasoner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Derivation;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
;

public class Reasoner {

	// private static String fname = "http://192.168.0.109:3030/sistemasweb/";
	 private static String NS = "@prefix j.0: <http://www.loa-cnr.it/ontologies/DUL.owl#>";
	private static String fname = "";
	 
	 static {
	 try{
	 
		 Properties prop = getProp();
		 fname = prop.getProperty("addressModel");
	 }
	 catch(IOException iox)
	 {
		 System.out.println("Arquivo n�o localizado");
	 }
	} 	
	 
	 
	 public void reasoner(){
			
		    Model data = FileManager.get().loadModel(fname );
		    
		
		    String rules = "[rule1: (?a j.0:hasDataValue ?b) (?b j.0:p ?c) -> (?a j.0:p ?c)]";
		   
		    /*  Regra sendo elaborada pelo Gustavo
			   
		   // List<Rule> regras = Rule.parseRules(rules);
		    
		    GenericRuleReasoner reasoner = new GenericRuleReasoner(Rule.rulesFromURL("C:\\Users\\Cleber\\workspace\\semantic-reasoner\\src\\main\\resources\\rules.txt"));
		    reasoner.setDerivationLogging(true);
		    InfModel inf = ModelFactory.createInfModel(reasoner, data);
		    
		    
		    PrintWriter out = new PrintWriter(System.out);
		    
		    
		    for (StmtIterator i = inf.listStatements(inf.getResource(NS+"A"), 	inf.getProperty(NS+"p"), inf.getResource(NS+"D")); i.hasNext(); ) {
		        Statement s = i.nextStatement(); 
		        System.out.println("Statement is " + s);
		        for (Iterator id = inf.getDerivation(s); id.hasNext(); ) {
		            Derivation deriv = (Derivation) id.next();
		            deriv.printTrace(out, true);        
		          }     
		    }
		    out.flush(); 	
		    
		    */
		  
		    UpdateModel updatemodel = new UpdateModel();
		    
		    //Caso ocorra o Match na infer�ncia o modelo � atualizado  ()
		   
		    String tripleStoreURI = "" +
	                "PREFIX  j.1: <http://purl.oclc.org/NET/ssnx/ssn#>\n" +
	                "PREFIX  j.0: <http://www.loa-cnr.it/ontologies/DUL.owl#>\n" +
	                "PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" +
	               
	                "DELETE { <http://wiser.dcc.ufba.br/smartUFBA/devices/ufbaino#obsValue_14915308050001491530865086>\n" + 
          "						       a                 j.1:ObservationValue ;\n"  + 
          "                            j.0:hasDataValue  \"37\"^^xsd:double ;\n" +
          "                            j.0:isSettingFor  false .}\n" +
           
          "INSERT { <http://wiser.dcc.ufba.br/smartUFBA/devices/ufbaino#obsValue_14915308050001491530865086>\n" +
          "                  a                 j.1:ObservationValue ;\n" +
          "                  j.0:hasDataValue  \"37\"^^xsd:double ;\n" +
          "                  j.0:isSettingFor  true .}\n " +
          
  "  WHERE { <http://wiser.dcc.ufba.br/smartUFBA/devices/ufbaino#obsValue_14915308050001491530865086>" +
  "                   a                 j.1:ObservationValue ;\n" +
  "                   j.0:hasDataValue  \"37\"^^xsd:double ;\n" +
  "                   j.0:isSettingFor  false . }";
   
		
		 
		  
	 	  updatemodel.updateTripleStore(tripleStoreURI, data,fname);
		    
		    

		}

	 public static Properties getProp() throws IOException {
			Properties props = new Properties();
			FileInputStream file = new FileInputStream("./main/resources/br.ufba.dcc.wiser.smartufba.semanticrules.properties");
			props.load(file);
			return props;
		}

	 
	 public static void main (String [] args){
		 Reasoner reasoner = new Reasoner();
		 reasoner.reasoner();
	 }
	

}
