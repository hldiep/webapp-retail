import numpy as np
from gensim.models import Word2Vec
from sklearn.metrics.pairwise import cosine_similarity
from .preprocess_use_pyvi import custom_preprocessing
from .prepare_data import get_product_details, fetch_data_from_api

# products = fetch_data_from_api()
# print(products)
# details = get_product_details(products)
# preprocessed_details = preprocess_data(details)
# Huấn luyện mô hình Word2Vec
# word2vec_model = Word2Vec(preprocessed_details, vector_size=100, window=5, min_count=1, workers=4)

def train_word2vec_model(preprocessed_details):
    # Huấn luyện mô hình Word2Vec
    word2vec_model = Word2Vec(preprocessed_details, vector_size=100, window=5, min_count=1, workers=4)
    # Lưu mô hình Word2Vec vào file
    word2vec_model.save("word2vec_model.bin")
    return word2vec_model

def load_word2vec_model():
    return Word2Vec.load("word2vec_model.bin")

def get_word2vec_vector(preprocessed, word2vec_model):
    vectors = [word2vec_model.wv[token] for token in preprocessed if token in word2vec_model.wv]
    if vectors:
        return np.mean(vectors, axis=0)  # Lấy trung bình vector các từ
    else:
        return np.zeros(word2vec_model.vector_size)

# Hàm tìm sản phẩm có độ tương đồng cao nhất
# product_vectors = [get_word2vec_vector(preprocessed, word2vec_model) for preprocessed in preprocessed_details]
def find_best_match(query,word2vec_model,product_vectors):
    preprocessed_query = custom_preprocessing(query)
    query_vector = get_word2vec_vector(preprocessed_query, word2vec_model)
    similarities = cosine_similarity(query_vector.reshape(1, -1), product_vectors)
    return np.argmax(similarities)

# best_match_index = find_best_match('thanh long')
# print("Chỉ số sản phẩm phù hợp nhất:", best_match_index)
# print("Chi tiết sản phẩm phù hợp nhất:", preprocessed_details[best_match_index])

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