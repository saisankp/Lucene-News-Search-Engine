echo "-- Evaluating the StandardAnalyzer --"

echo "Search Engine: StandardAnalyzer BM25 QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerBM25QueryParser
echo "Search Engine: StandardAnalyzer BM25 MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerBM25MultiFieldQueryParser
echo "Search Engine: StandardAnalyzer VSM QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerVSMQueryParser
echo "Search Engine: StandardAnalyzer VSM MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerVSMMultiFieldQueryParser
echo "Search Engine: StandardAnalyzer LMDirichlet QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerLMDirichletQueryParser
echo "Search Engine: StandardAnalyzer LMDirichlet MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerLMDirichletMultiFieldQueryParser
echo "Search Engine: StandardAnalyzer LMJelinek-Mercer QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerLMJelinekMercerQueryParser
echo "Search Engine: StandardAnalyzer LMJelinek-Mercer MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/StandardAnalyzerLMJelinekMercerMultiFieldQueryParser


echo "-- Evaluating the EnglishAnalyzer --"

echo "Search Engine: EnglishAnalyzer BM25 QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerBM25QueryParser
echo "Search Engine: EnglishAnalyzer BM25 MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerBM25MultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer VSM QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerVSMQueryParser
echo "Search Engine: EnglishAnalyzer VSM MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerVSMMultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer LMDirichlet QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerLMDirichletQueryParser
echo "Search Engine: EnglishAnalyzer LMDirichlet MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerLMDirichletMultiFieldQueryParser
echo "Search Engine: EnglishAnalyzer LMJelinek-Mercer QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerLMJelinekMercerQueryParser
echo "Search Engine: EnglishAnalyzer LMJelinek-Mercer MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/EnglishAnalyzerLMJelinekMercerMultiFieldQueryParser


echo "-- Evaluating the CustomAnalyzer --"

echo "Search Engine: CustomAnalyzer BM25 QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerBM25QueryParser
echo "Search Engine: CustomAnalyzer BM25 MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerBM25MultiFieldQueryParser
echo "Search Engine: CustomAnalyzer VSM QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerVSMQueryParser
echo "Search Engine: CustomAnalyzer VSM MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerVSMMultiFieldQueryParser
echo "Search Engine: CustomAnalyzer LMDirichlet QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerLMDirichletQueryParser
echo "Search Engine: CustomAnalyzer LMDirichlet MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerLMDirichletMultiFieldQueryParser
echo "Search Engine: CustomAnalyzer LMJelinek-Mercer QueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerLMJelinekMercerQueryParser
echo "Search Engine: CustomAnalyzer LMJelinek-Mercer MultiFieldQueryParser"
./trec_eval/trec_eval -m map qrels.assignment2.part1 ./evaluation/CustomAnalyzerLMJelinekMercerMultiFieldQueryParser