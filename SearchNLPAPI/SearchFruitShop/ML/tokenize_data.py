#Nguồn tham khảo
#https://github.com/stopwords-iso/stopwords-vi/blob/master/stopwords-vi.txt
import re
import gensim.parsing.preprocessing as gs_preprocessing
from nltk.tokenize import word_tokenize
from gensim.models import Phrases
from SearchFruitShop.search.ML import prepare_data

# Lấy dữ liệu từ API và đổ vào
# api_url = "http://127.0.0.1:8000/api/all"
products = prepare_data.fetch_data_from_api()
details = prepare_data.get_product_details(products)
print(details[0])
def get_all_sentences(details):
    sentences =[]
    for detail in details:
        detail_tmp= re.sub(r'(\n)+', '.', detail)
        sentences.extend([sentence.strip() for sentence in re.split(r'[.!?]', detail_tmp) if sentence.strip()])
    return sentences

sentences = get_all_sentences(details)
print(sentences[2])

# Định nghĩa hàm remove_stopwords cho tiếng Việt
def remove_custom_stopwords(p):
    # Stopwords cho tiếng Việt
    stopWords = prepare_data.get_file_lines("../search/file/stopwords-vi.txt")
    return gs_preprocessing.remove_stopwords(p, stopwords=stopWords)
def custom_preprocessing(data):
    CUSTOM_FILTERS = [
        lambda x: x.lower(), gs_preprocessing.strip_tags, gs_preprocessing.strip_punctuation,
        gs_preprocessing.strip_multiple_whitespaces,
        remove_custom_stopwords,]
    # gs_preprocessing.strip_numeric,
    #    gs_preprocessing.stem_text,
    # gs_preprocessing.strip_short,  #Dùng nếu muốn bỏ các từ ngắn như ổi, vì, a....
    #    gs_preprocessing.remove_stopwords,  # Dùng nếu có stopwords mặc định của Gensim
    preprocessed_data = []
    for d in data:
        preprocessed = gs_preprocessing.preprocess_string(d, filters=CUSTOM_FILTERS)
        preprocessed_data.append(preprocessed)
    return preprocessed_data

sentences_preprocessed = custom_preprocessing(sentences)
print(sentences_preprocessed[2])

phrases_model = Phrases(sentences_preprocessed, min_count=1, threshold=1)

tokenized_sentences = [word_tokenize(sentence.lower()) for sentence in sentences]
print(tokenized_sentences[2])
print(phrases_model[tokenized_sentences[2]])
print()
def get_phrases_model():
    return phrases_model