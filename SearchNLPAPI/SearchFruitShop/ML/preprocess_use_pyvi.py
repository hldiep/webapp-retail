import re
import os
from pyvi import ViTokenizer
import gensim.parsing.preprocessing as gs_preprocessing
from .prepare_data import get_file_lines


stopwords_path = os.path.join(os.path.dirname(__file__), 'file', 'stopwords-vi.txt')
_stopWords = get_file_lines(stopwords_path)
def remove_custom_stopwords(p):
    return gs_preprocessing.remove_stopwords(p, stopwords=_stopWords)
def custom_strip_punctuation(text):
    return re.sub(r'[^\w\s_]', '', text) # Loại bỏ tất cả ký tự dấu câu trừ dấu gạch dưới

_CUSTOM_FILTERS = [
        lambda x: x.lower(), gs_preprocessing.strip_tags,custom_strip_punctuation,
        gs_preprocessing.strip_multiple_whitespaces,
        remove_custom_stopwords,]
def custom_preprocessing(data):
    # DUNG THU VIEN PYVI
    tokenized_data = ViTokenizer.tokenize(data)
    preprocessed = gs_preprocessing.preprocess_string(tokenized_data, filters=_CUSTOM_FILTERS)
    return preprocessed

def preprocess_data(list_data):
    preprocessed_data = []
    for data in list_data:
        preprocessed = custom_preprocessing(data)
        preprocessed_data.append(preprocessed)
    return preprocessed_data


# products = prepare_data.fetch_data_from_api()
# details = prepare_data.get_product_details(products)
#DUNG THU VIEN PYVI
# tokenized_details = [ViTokenizer.tokenize(detail) for detail in details]
# print("DUNG THU VIEN PyVi:")
# print(tokenized_details[3])
# Stopwords cho tiếng Việt

# preprocessed_details = preprocess_data(details)
# print("PREPROCESSED DETAIL:")
# print(preprocessed_details[1])



