
# space, nltk, genshim
from gensim.models import Word2Vec

import SearchFruitShop.ML.tokenize_data as tokenize_data

phrases_model = tokenize_data.get_phrases_model()
tokenize_sentences = tokenize_data.tokenized_sentences

preprocessed_details = [phrases_model[tokenize_sentence] for tokenize_sentence in tokenize_sentences]
print(preprocessed_details[2])

# Huấn luyện mô hình Word2Vec
model = Word2Vec(preprocessed_details, vector_size=100, window=5, min_count=1, workers=4)
# print("Model: ", model)
# Lấy vector của một từ cụ thể
vector = model.wv['chua']
print("Vector của 'chua': ", vector)

# Tìm từ tương tự với 'trái'
similar_words = model.wv.most_similar('chua', topn=3)
print("Từ tương tự với 'chua': ", similar_words)