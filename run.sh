mvn compile                                                                                                    
mvn clean package
echo "-- Indexing with the StandardAnalyzer --"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation index StandardAnalyzer

echo "Search Engine: StandardAnalyzer BM25 QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer BM25 QueryParser
echo "Search Engine: StandardAnalyzer BM25 MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer BM25 MultiFieldQueryParser
echo "Search Engine: StandardAnalyzer VSM QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer VSM QueryParser
echo "Search Engine: StandardAnalyzer VSM MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer VSM MultiFieldQueryParser
echo "Search Engine: StandardAnalyzer LMDirichlet QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer LMDirichlet QueryParser
echo "Search Engine: StandardAnalyzer LMDirichlet MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer LMDirichlet MultiFieldQueryParser
echo "Search Engine: StandardAnalyzer LMJelinekMercer QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer LMJelinekMercer QueryParser
echo "Search Engine: StandardAnalyzer LMJelinekMercer MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate StandardAnalyzer LMJelinekMercer MultiFieldQueryParser


echo "-- Indexing with the EnglishAnalyzer --"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation index EnglishAnalyzer

echo "Search Engine: EnglishAnalyzer BM25 QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer BM25 QueryParser
echo "Search Engine: EnglishAnalyzer BM25 MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer BM25 MultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer VSM QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer VSM QueryParser
echo "Search Engine: EnglishAnalyzer VSM MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer VSM MultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer LMDirichlet QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer LMDirichlet QueryParser
echo "Search Engine: EnglishAnalyzer LMDirichlet MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer LMDirichlet MultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer LMJelinekMercer QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer LMJelinekMercer QueryParser
echo "Search Engine: EnglishAnalyzer LMJelinekMercer MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate EnglishAnalyzer LMJelinekMercer MultiFieldQueryParser

echo "-- Indexing with the CustomAnalyzer --"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation index CustomAnalyzer

echo "Search Engine: CustomAnalyzer BM25 QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer BM25 QueryParser
echo "Search Engine: CustomAnalyzer BM25 MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer BM25 MultiFieldQueryParser
echo "Search Engine: CustomAnalyzer VSM QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer VSM QueryParser
echo "Search Engine: CustomAnalyzer VSM MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer VSM MultiFieldQueryParser
echo "Search Engine: CustomAnalyzer LMDirichlet QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer LMDirichlet QueryParser
echo "Search Engine: CustomAnalyzer LMDirichlet MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer LMDirichlet MultiFieldQueryParser
echo "Search Engine: CustomAnalyzer LMJelinekMercer QueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer LMJelinekMercer QueryParser
echo "Search Engine: CustomAnalyzer LMJelinekMercer MultiFieldQueryParser"
java -jar target/info-retrieval-project-1.0-SNAPSHOT.jar --operation evaluate CustomAnalyzer LMJelinekMercer MultiFieldQueryParser