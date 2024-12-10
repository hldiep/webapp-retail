import numpy as np
from gensim.models import Word2Vec
from sklearn.metrics.pairwise import cosine_similarity

import SearchFruitShop.ML.prepare_data as prepare_data
import SearchFruitShop.ML.preprocess_use_pyvi as preprocess


products = prepare_data.fetch_data_from_api()
details = prepare_data.get_product_details(products)
print(details[0])
preprocessed_details = preprocess.preprocess_data(details)
print(preprocessed_details [0])
# Huấn luyện mô hình Word2Vec
word2vec_model = Word2Vec(preprocessed_details, vector_size=100, window=5, min_count=1, workers=4)

def get_word2vec_vector(preprocessed):
    vectors = [word2vec_model.wv[token] for token in preprocessed if token in word2vec_model.wv]
    if vectors:
        return np.mean(vectors, axis=0)  # Lấy trung bình vector các từ
    else:
        return np.zeros(word2vec_model.vector_size)

# Hàm tìm sản phẩm có độ tương đồng cao nhất
product_vectors = [get_word2vec_vector(preprocessed) for preprocessed in preprocessed_details]
def find_best_match(query):
    preprocessed_query = preprocess.custom_preprocessing(query)
    print(preprocessed_query)
    query_vector = get_word2vec_vector(preprocessed_query)
    similarities = cosine_similarity(query_vector.reshape(1, -1), product_vectors)
    return np.argmax(similarities)
query ='thanh long'
best_match_index = find_best_match(query)
print("Chỉ số sản phẩm phù hợp nhất:", best_match_index)
print("Chi tiết sản phẩm phù hợp nhất:", preprocessed_details[best_match_index])

# def get_sentence_bert_vector(sentence):
#     sentence_bert_model = SentenceTransformer('sentence-transformers/all-MiniLM-L6-v2')  # Mô hình Sentence-BERT nhẹ
#     return sentence_bert_model.encode(sentence)

#
# # Huấn luyện mô hình Word2Vec
# model = Word2Vec(preprocessed_details, vector_size=100, window=5, min_count=1, workers=4)
# vector = model.wv['xoài']
# # print("Vector của 'xoài': ", vector)
#
# # Tìm từ tương tự với 'trái'
# similar_words = model.wv.most_similar('xoài', topn=5)
# print("Từ tương tự với 'xoài': ", similar_words)